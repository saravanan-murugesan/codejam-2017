package sarav;

import org.omg.CORBA.TIMEOUT;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class BathroomStalls {
    final List<Stall> stalls;
    final int totalStalls;
    final int totalPeople;

    public Stall chooseStall(){
        Stall stall = stalls.stream().parallel().filter(s -> !s.isOccupied).sorted().findFirst().get();
        stall.isOccupied = true;
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.submit(() -> {
            if(stall.id > 0) {
                for(int i=stall.id-1; i >= 0; i--) {
                    Stall s = stalls.get(i);
                    s.emptyStallsOnRight = stall.id - s.id - 1;
                    if(s.isOccupied) {
                        stall.emptyStallsOnLeft  = s.emptyStallsOnRight;
                        break;
                    }
                }
            }
        });
        executorService.submit(() -> {
            if(stall.id < totalStalls-1) {
                for(int i=stall.id+1; i < totalStalls; i++) {
                    Stall s = stalls.get(i);
                    s.emptyStallsOnLeft = s.id - stall.id - 1;
                    if(s.isOccupied) {
                        stall.emptyStallsOnRight = s.emptyStallsOnLeft;
                        break;
                    }
                }
            }
        });
        executorService.shutdown();
        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(this.toString());
        return stall;
    }

    public Stall getLastOccupiedStall() {
        int remainingPeople = this.totalPeople;
        Stall stall = null;
        while(remainingPeople-- > 0) {
            stall = chooseStall();
        }
        return stall;
    }

    public void stallStats() {
        System.out.println(stalls);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("1");
        stalls.forEach(s -> builder.append(s.isOccupied ? "1" : "0"));
        builder.append("1");
        return builder.toString();
    }

    public BathroomStalls(int totalStalls, int totalPeople) {
        stalls = new ArrayList<>(totalStalls);
        for(int i=0; i<totalStalls; i++){
            stalls.add(i, new Stall(i, false, i, totalStalls-i-1));
        }
        this.totalStalls = totalStalls;
        this.totalPeople = totalPeople;
    }

    public static void main(String[] args) throws FileNotFoundException {
        //Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        Scanner in = new Scanner(new BufferedReader(new FileReader("/Users/saravm/Work/git/codejam2017/src/main/resources/bathroom/sample.in")));
        int t = in.nextInt();
        ExecutorService executorService = Executors.newFixedThreadPool(t);
        Map<Integer, Stall> result = Collections.synchronizedMap(new HashMap<>(t));
        for (int i = 1; i <= t; ++i) {
            final int testCaseNumber = i;
            final int numberOfStalls = in.nextInt();
            final int numberOfPeople = in.nextInt();
            executorService.submit(() -> {
                BathroomStalls bathroomStalls = new BathroomStalls(numberOfStalls, numberOfPeople);
                Stall stall = bathroomStalls.getLastOccupiedStall();
                result.put(testCaseNumber, stall);
            });
        }
        executorService.shutdown();
        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        SortedSet<Integer> sortedResults = new TreeSet<>(result.keySet());
        sortedResults.forEach( testCaseNumber -> System.out.println("Case #" + testCaseNumber + ": " + result.get(testCaseNumber).getMaxDistanceFromOccupiedStall() + " " + result.get(testCaseNumber).getMinDistanceFromOccupiedStall()));
    }

}

class Stall implements Comparable<Stall>{
    public final int id;
    public boolean isOccupied;
    public int emptyStallsOnLeft;
    public int emptyStallsOnRight;

    public Stall(final int id, final boolean isOccupied, final int emptyStallsOnLeft, final int emptyStallsOnRight) {
        this.id = id;
        this.isOccupied = isOccupied;
        this.emptyStallsOnLeft = emptyStallsOnLeft;
        this.emptyStallsOnRight = emptyStallsOnRight;
    }

    public int getMinDistanceFromOccupiedStall() {
        return Integer.min(emptyStallsOnLeft, emptyStallsOnRight);
    }

    public int getMaxDistanceFromOccupiedStall() {
        return Integer.max(emptyStallsOnLeft, emptyStallsOnRight);
    }

    @Override
    public int compareTo(final Stall s) {
        // descending order
        int min1 = this.getMinDistanceFromOccupiedStall();
        int max1 = this.getMaxDistanceFromOccupiedStall();
        int min2 = s.getMinDistanceFromOccupiedStall();
        int max2 = s.getMaxDistanceFromOccupiedStall();
        if(min1 != min2) {
            return -(min1-min2);
        } else if(max1 != max2) {
            return -(max1-max2);
        } else {
            return -(s.id - this.id);
        }
    }

    @Override
    public String toString() {
        return "Id: "+id + " Occupied: "+ isOccupied +" Empty on Left: "+emptyStallsOnLeft+" Empty on right: "+emptyStallsOnRight;
    }
}

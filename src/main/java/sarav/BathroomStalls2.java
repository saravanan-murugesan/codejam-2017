package sarav;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class BathroomStalls2 {

    ExecutorService executorService = Executors.newFixedThreadPool(100);


    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(new BufferedReader(new FileReader("/Users/saravm/Work/git/codejam2017/src/main/resources/bathroom/sample.in")));
        int t = in.nextInt();
        ExecutorService executorService = Executors.newFixedThreadPool(t);
        Map<Integer, Stall> result = Collections.synchronizedMap(new HashMap<>(t));
        for (int i = 1; i <= t; ++i) {
            final int testCaseNumber = i;
            final int numberOfStalls = in.nextInt();
            final int numberOfPeople = in.nextInt();
            executorService.submit(() -> {
                LinkedList stalls = new LinkedList();
                for(int j=numberOfStalls; j>=0; j--) {
                    stalls.push(j);
                }
                LinkedList.TNode preferredStall = stalls.sortedListToBST();

                for(int k=1; k<= numberOfPeople;k++) {
                    preferredStall.isOccupied = true;

                }
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







        //System.out.println(getMiddle(1,6));

    }

    static long getMiddle(long first, long last) {
        return (last - first) / 2 + 1;
    }


}

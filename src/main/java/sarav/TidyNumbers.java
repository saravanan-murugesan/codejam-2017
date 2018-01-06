package sarav;

import java.io.IOException;
import java.time.Instant;
import java.util.concurrent.*;

public class TidyNumbers {

    public static boolean isTidy(long number) {
        char[] digits = String.valueOf(number).toCharArray();
        for(int i=digits.length-1; i > 0; i--) {
            if(digits[i] < digits[i-1]) {
                return false;
            }
        }
        return true;
    }

    public static long getTidy(long maxLimit) {
        StringBuilder s = new StringBuilder(String.valueOf(maxLimit));
        if(s.indexOf("0") > -1) {
            int zeroIndex = s.indexOf("0");
            String a = s.substring(zeroIndex);
            String replacement = "";
            for(int i=0; i<a.length(); i++) {
                replacement += "9";
            }
            s.replace(zeroIndex, zeroIndex+a.length(), replacement);
            s.setCharAt(zeroIndex-1, String.valueOf(Integer.valueOf(String.valueOf(s.charAt(zeroIndex -1)))-1).charAt(0));
        }
        return Long.valueOf(s.toString());
    }

    public static void main( String[] args ) throws IOException {
        IOUtil d = new IOUtil("tidy/B-large.in", "tidy/B-large.out");
        Instant start = Instant.now();
        System.out.println("Start time" + start);
        ExecutorService executor = Executors.newFixedThreadPool(100);
        CountDownLatch latch = new CountDownLatch(d.numberOfTestCases);
        for(int tc=1; tc <= d.numberOfTestCases; tc++) {
            final long number = Long.valueOf(d.getNextTestCase());
            final int t =tc;
            System.out.println("working on "+tc+": "+number);
            executor.submit(new Runnable() {
                public void run() {
                    long n = number;
                    while(!isTidy(n)) {
                        n--;
                        n = getTidy(n);
                    }
                    latch.countDown();
                    System.out.println(t + " : "+number);
                }
            });
        }
        try {
            latch.await();
        } catch (InterruptedException E) {
        }

//        d.finishWritingOutput();
    }
}



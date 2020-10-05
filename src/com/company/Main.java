package com.company;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
//        int[] test_arr = new int[]{-19,1,18,2,17, 3, 16, 4, 15, 4, 15};
//
//        test_arr = GenerateNumbers(10000, 100000);

        //ArrayList<int[]> output = ThreeSumBruteForce(test_arr);

        //System.out.println(String.format("Found %d solutions:", output.size()));
//        for(int[] arr : output){
//            System.out.println(String.format("%s + %s + %s  |  %s + %s + %s", arr[0], arr[1], arr[2], test_arr[arr[0]], test_arr[arr[1]], test_arr[arr[2]]));
//        }

//        //test_arr = GenerateNumbers(30, 1000);
//
//        ArrayList<int[]> output2 = ThreeSumKillianMethod(test_arr);
//
//        System.out.println(String.format("Found %d solutions:", output2.size()));
//        for(int[] arr : output2){
//            System.out.println(String.format("%s + %s + %s  |  %s + %s + %s", arr[0], arr[1], arr[2], test_arr[arr[0]], test_arr[arr[1]], test_arr[arr[2]]));
//        }

//        Arrays.sort(test_arr);
//
//        ArrayList<int[]> output3 = ThreeSumMethod2(test_arr);
//
//        System.out.println(String.format("Found %d solutions:", output3.size()));
//        for(int[] arr : output3){
//            System.out.println(String.format("%s + %s + %s  |  %s + %s + %s", arr[0], arr[1], arr[2], test_arr[arr[0]], test_arr[arr[1]], test_arr[arr[2]]));
//        }

        padder("N"); padder("Brute Force"); padder("Doubling Ratios"); padder("Expected"); padder("Faster"); padder("Doubling Ratios"); padder("Expected"); padder("Fastest"); padder("Doubling Ratios"); padder("Expected");
        System.out.println();
        long[] prevTimes = {1,1,1};
        int count = 100;
        while(true){
            padder(Integer.toString(count));
            prevTimes = Run(count, prevTimes);
            count *= 2;
        }
        //String[] testStrings = {"Brute", "Fastest", "Faster"};
    }

    private static long[] Run(int size, long[] prevTimes){
        int[] test_arr = GenerateNumbers(size, size * 3);

        String[] testStrings = {"Brute", "Fastest", "Faster"};
        int i = 0;
        for(String sortType : testStrings){
            if(prevTimes[i] > 10000000000L && sortType == "Brute"){
                padder("N/A");
                padder("N/A");
                padder("N/A");
                i++;
                continue;
            }
            if(prevTimes[i] > 30000000000L){
                padder("N/A");
                padder("N/A");
                padder("N/A");
                i++;
                continue;
            }
            long timeStampBefore = getCpuTime();
            double expected = 1;
            if(sortType == "Brute"){
                ThreeSumBruteForce(test_arr);
                expected = 2*2*2;
            }
            else if(sortType == "Fastest"){
                ThreeSumKillianMethod(test_arr);
                expected = 2*2;
            }
            else if(sortType == "Faster"){
                Arrays.sort(test_arr);
                ThreeSumMethod2(test_arr);
                expected = 2*2*(1 + (1/size));
            }

            long timeStampAfter = getCpuTime();
            long time = timeStampAfter - timeStampBefore;
            if(time <= 0){
                time = 1;
            }
            double actual = (time / prevTimes[i]);
            prevTimes[i] = time;


            numberPadder(time);
            padder(Double.toString(actual) + "x");
            padder(Double.toString(expected) + "x");

            i++;
        }

        System.out.println();

        return prevTimes;
    }


    public static ArrayList<int[]> ThreeSumMethod2(int[] arr){
        // arrays suck so heres a class that fixes that, not a fan of java
        ArrayList<int[]> output = new ArrayList<>();

        for(int i = 0; i < arr.length; i++){
            // number being looked for, indexes of first two numbers
            for(int j = i + 1; j < arr.length; j++){
                int loc =  Arrays.binarySearch(arr, (arr[i]+arr[j]) * -1);
                if(loc <= i || loc <= j){
                    continue;
                }
                if(loc != -1){
                    output.add(new int[]{i,j,loc});
                }
            }
        }
        return output;
    }

    // BOUND MUST BE EQUAL OR GREATER THAN SIZE
    // It could be very inefficient below this
    public static int[] GenerateNumbers(int size, int bound){
        int[] arr = new int[size];
        HashMap<Integer, Boolean> map = new HashMap<>();
        Random r = new Random();
        for (int i = 0; i < size; i++) {
            // negative numbers
            int num;
            do{
                num = r.nextInt(bound) + 1;
                if(r.nextBoolean()){
                    num *= -1;
                }
            }while(map.containsKey(num));
            map.put(num, true);
            arr[i] = num;
        }
        return arr;
    }

    // Using something I learned from two sum
    // I will now do magic
    // just two sum with some slightly changed logic
    public static ArrayList<int[]> ThreeSumKillianMethod(int[] arr){
        // arrays suck so heres a class that fixes that, not a fan of java
        ArrayList<int[]> output = new ArrayList<>();

        for(int i = 0; i < arr.length; i++){
            // number being looked for, indexes of first two numbers
            HashMap<Integer, int[]> map = new HashMap<>();
            for(int j = i + 1; j < arr.length; j++){
                // if the number is being looked for
                if(map.containsKey(arr[j])){
                    // retrieve
                    int[] res = map.get(arr[j]);
                    // add to return
                    output.add(new int[]{res[0],res[1],j});
                }
                // put the number that is being looked for
                map.put((arr[i]+arr[j]) * -1, new int[]{i,j});
            }
        }
        return output;
    }



    // check every possible element
    public static ArrayList<int[]> ThreeSumBruteForce(int[] arr){
        ArrayList<int[]> output = new ArrayList<>();
        for(int i = 0; i < arr.length; i++){
            for(int j = i + 1; j < arr.length; j++){
                for(int k = j + 1; k < arr.length; k++){
                    if( (arr[i] + arr[j] + arr[k]) == 0){
                        output.add(new int[]{i,j,k});
                    }
                }
            }
        }
        return output;
    }


    private static void padder(String str){
        int maxPadding = 20;
        String padding = "";
        for(int i = str.length(); i < maxPadding; i++){
            padding += " ";
        }
        System.out.print("|" + str + padding + "|");
    }

    private static void numberPadder(long number){
        String appended = "";
        if(number < 8000000000000L && number > 8000000000L){
            appended = "s";
            number /= 1000000000;
        }
        else if(number < 8000000000L && number > 8000000){
            appended = "ms";
            number /= 1000000;
        }
        else if(number < 8000000 && number > 8000){
            appended = "us";
            number /= 1000;
        }
        else if(number < 8000){
            appended = "ns";
        }
        padder(Long.toString(number) + appended);
    }

    public static long getCpuTime(){
        ThreadMXBean bean = ManagementFactory.getThreadMXBean();
        return bean.isCurrentThreadCpuTimeSupported() ? bean.getCurrentThreadCpuTime() : 0L;
    }
}

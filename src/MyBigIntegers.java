import javafx.util.converter.IntegerStringConverter;

import javax.print.DocFlavor;
import java.util.Arrays;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

public class MyBigIntegers {

    static String ResultsFolderPath = "/home/diana/Results/"; // pathname to results folder
    static FileWriter resultsFile;
    static PrintWriter resultsWriter;

    static int numberOfTrials = 1;
    static int MAXINPUTSIZE = (int) Math.pow(1.5, 28);
    static int MININPUTSIZE = 1;
    static int Nums = 90;
    static long fibResult = 0;
    static int Base = 10;

    public static class bigIntegers {
        int BigNum1[] = new int[35];
        public int sign;

    }

    public static class bigInt{
        public String num1;
        public String num2;
    }


    public static bigIntegers setBigNumber(bigIntegers bigNumber) {
        bigIntegers BI = new bigIntegers();
        bigIntegers BI2 = new bigIntegers();
        int i = 0;

        for (i = 0; i < BI.BigNum1.length; i++) {
            BI.BigNum1[i] = (int) (0 + Math.random() * (9 - 0));
        }
        BI.sign = 1;


        return BI;
    }

    public static String ToString(bigIntegers BI) {
        StringBuilder stringBuilder = new StringBuilder();
        String s1 = "";
        int xx = 0;
        int x = 0;
        int count = 0;
        for (xx = 0; xx < BI.BigNum1.length; xx++) {
            if(BI.BigNum1[xx] == 0){
                count++;
            }
            else{
                break;
            }

        }
        if(BI.sign == -1){
            stringBuilder.append("-");
        }
        for (x = count; x < BI.BigNum1.length; x++) {
            stringBuilder.append(BI.BigNum1[x]);

        }
        s1 = s1 + stringBuilder;
        //System.out.println("Final string: " + s1);

        return s1;
    }

    public static void main(String[] args) {
        //bigIntegers testBI = new bigIntegers();

        //bigIntegers BI1 = setBigNumber(testBI);
        //System.out.println("Original Array: " + Arrays.toString(BI1.BigNum1));
        //bigIntegers BI2 = setBigNumber(testBI);
        //ToString(BI1);
        //ToString(BI2);
        //bigIntegers BI3 =  add(BI1, BI2, Base);
        //ToString(BI3);



        // run the whole experiment at least twice, and expect to throw away the data from the earlier runs, before java has fully optimized

        //System.out.println("Running first full experiment...");
        runFullExperiment("test-Exp1-ThrowAway.txt");
        //System.out.println("Running second full experiment...");
        runFullExperiment("test-Exp2.txt");
        //System.out.println("Running third full experiment...");
        runFullExperiment("test-Exp3.txt");
    }

    static void runFullExperiment(String resultsFileName) {


        //declare variables for doubling ratio
        double[] averageArray = new double[1000];
        double currentAv = 0;
        double doublingTotal = 0;
        int x = 0;
        bigIntegers testBI = new bigIntegers();
        bigIntegers testBI2 = new bigIntegers();
        bigIntegers BI = setBigNumber(testBI);
        bigIntegers BI2 = setBigNumber(testBI2);
        BI.sign = 1;
        BI2.sign = 1;
        String num1 = ToString(BI);
        String num2 = ToString(BI2);
        bigInt n1 = new bigInt(){};
        //num1 = "99999999999999999999999999999999999999999999999999999999";
        //num2 = "-1";
        System.out.println("num 1 = " + num1);
        System.out.println("num 2 = " + num2);
        if ((num1.charAt(0) == '-' || num2.charAt(0) == '-') && (num1.charAt(0) != '-' || num2.charAt(0) != '-')) {
            System.out.print("-");
        }

        if (num1.charAt(0) == '-' &&
                num2.charAt(0) != '-')
        {
            num1 = num1.substring(1);
        }
        else if (num1.charAt(0) != '-' &&
                num2.charAt(0) == '-')
        {
            num2 = num2.substring(1);
        }
        else if (num1.charAt(0) == '-' &&
                num2.charAt(0) == '-')
        {
            num1 = num1.substring(1);
            num2 = num2.substring(1);
        }
        bigIntegers bigIntegersAdded = add(BI, BI2, Base);
        String Added = ToString(bigIntegersAdded);
        System.out.println("Sum = " + Added);

        String BI4 = times(num1, num2, Base);

        System.out.println(BI4);

        //set up print to file
        try {
            resultsFile = new FileWriter(ResultsFolderPath + resultsFileName);
            resultsWriter = new PrintWriter(resultsFile);
        } catch (Exception e) {
            System.out.println("*****!!!!!  Had a problem opening the results file " + ResultsFolderPath + resultsFileName);
            return; // not very foolproof... but we do expect to be able to create/open the file...
        }

        //declare variables for stop watch
        ThreadCpuStopWatch BatchStopwatch = new ThreadCpuStopWatch(); // for timing an entire set of trials
        ThreadCpuStopWatch TrialStopwatch = new ThreadCpuStopWatch(); // for timing an individual trial

        //add headers to text file
        resultsWriter.println("#X(Value)  N(Size)  AverageTime        FibNumber   NumberOfTrials"); // # marks a comment in gnuplot data
        resultsWriter.flush();

        /* for each size of input we want to test: in this case starting small and doubling the size each time */
        for (int inputSize = 0; inputSize <= Nums; inputSize++) {
            //test run for fibonacci numbers
            //verifyFib(inputSize);

            // progress message...
            //System.out.println("Running test for input size "+inputSize+" ... ");

            /* repeat for desired number of trials (for a specific size of input)... */
            long batchElapsedTime = 0;
            // generate a list of randomly spaced integers in ascending sorted order to use as test input
            // In this case we're generating one list to use for the entire set of trials (of a given input size)
            // but we will randomly generate the search key for each trial
            //System.out.print("    Generating test data...");

            //generate random integer list
            //long resultFib = Fib(x);

            //print progress to screen
            //System.out.println("...done.");
            //System.out.print("    Running trial batch...");

            /* force garbage collection before each batch of trials run so it is not included in the time */
            System.gc();


            // instead of timing each individual trial, we will time the entire set of trials (for a given input size)
            // and divide by the number of trials -- this reduces the impact of the amount of time it takes to call the
            // stopwatch methods themselves
            BatchStopwatch.start(); // comment this line if timing trials individually

            // run the trials
            for (long trial = 0; trial < numberOfTrials; trial++) {
                // generate a random key to search in the range of a the min/max numbers in the list
                //long testSearchKey = (long) (0 + Math.random() * (testList[testList.length-1]));
                /* force garbage collection before each trial run so it is not included in the time */
                // System.gc();

                //TrialStopwatch.start(); // *** uncomment this line if timing trials individually
                /* run the function we're testing on the trial input */

                //fibResult = Fib(inputSize);
                //System.out.println(result);
                // batchElapsedTime = batchElapsedTime + TrialStopwatch.elapsedTime(); // *** uncomment this line if timing trials individually
            }
            batchElapsedTime = BatchStopwatch.elapsedTime(); // *** comment this line if timing trials individually
            double averageTimePerTrialInBatch = (double) batchElapsedTime / (double) numberOfTrials; // calculate the average time per trial in this batch

            //put current average time in array of average times. We will be able to use this to calculate the doubling ratio
            averageArray[x] = averageTimePerTrialInBatch;

            //skip this round if this is the first one (no previous average for calculation)
            if (inputSize != 0) {
                doublingTotal = averageTimePerTrialInBatch / averageArray[x - 1]; //Calculate doubling ratio

            }
            x++;
            //int countingbits = countBits(inputSize);
            /* print data for this size of input */
            resultsWriter.printf("%6d %15.2f %20d %4d\n", inputSize, averageTimePerTrialInBatch, fibResult, numberOfTrials); // might as well make the columns look nice
            resultsWriter.flush();
            //System.out.println(" ....done.");
        }
    }

    public static bigIntegers add(bigIntegers B1, bigIntegers B2, int Base) {

        int[] total = new int[B1.BigNum1.length+1];
        int[] num1 = new int[]{};
        int[] num2 = new int[]{};
        int temp = 0;
        int drop = 0;
        int carry = 0;
        bigIntegers Result = new bigIntegers();

        for (int i = 0; i < B1.BigNum1.length; i++) {
            if (B1.BigNum1[i] == 0) {
                if (B2.BigNum1[i] != 0) {
                    num1 = B2.BigNum1;
                    num2 = B1.BigNum1;
                    break;
                }
            } else {
                num1 = B1.BigNum1;
                num2 = B2.BigNum1;
                break;
            }

        }
        System.out.println(num1.length);

        for(int x = num1.length - 1; x >= 0; x--){

            temp = num1[x] + num2[x] + carry;
            //System.out.println("Temp = " + temp);
            carry =(int) Math.floor(temp/Base);
            drop = temp % Base;
            //System.out.println(total[num1.length-1]);
            total[x+1] = drop;

        }
        total[0] = carry;
        Result.BigNum1 = total;
        //System.out.println(Arrays.toString(total));
        return Result;
    }

    public static String times(String num1String, String num2String, int Base){

        //long num1 = Long.parseLong(num1String.trim());
        //long num2 = Long.valueOf(num2String.trim());
        //long total = karatsuba(num1, num2, Base);

        int length1 = num1String.length();
        int length2 = num2String.length();
        if(length1 == 0 || length2 == 0){
            return "0";
        }

        int result[] = new int[length1 + length2];

        int index1 = 0;
        int index2 = 0;
        int i,j = 0;
        for( i = length1 - 1; i >=0; i--){
            int carry = 0;
            int n1 = num1String.charAt(i) - '0';

            index2 = 0;

            for(j = length2 - 1; j>= 0; j--){
                int n2 = num2String.charAt(j) - '0';

                int total = n1 * n2 + result[index1 + index2] + carry;

                carry = total/Base;

                result[index1 + index2] = total %Base;

                index2++;
            }
            if(carry > 0 )
            {
                result[index1 + index2] += carry;
            }
            index1++;
        }

        i = result.length-1;
        while(i >= 0 && result[i] == 0){
            i--;
        }

        if(i == -1){
            return "0";
        }

        String s = "";

        while(i >=0){
            s += (result[i--]);
        }

        return s;
    }

}


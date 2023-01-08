package Matala_2_1;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Ex2_1 {

    /**The function creates text files and returns an array
     of the file names.
     * @param n = number of files that we will create
     * @param seed = used to initialize the random number generator
     * @param bound = max lines in file
     * @return array with the names of  files
     * @throws IOException
     */

    public static String[] createTextFiles(int n, int seed, int bound) throws IOException {
        String [] names_file  = new String[n];
            Random r = new Random(seed);
        for (int i = 0; i < n; i++) {
            int lines = r.nextInt(bound);
            File file = new File("file_" + (i + 1) + ".txt"); //get name to the file
            file.createNewFile();//create n files
            names_file[i] = file.getName(); //save the names of the files
            FileWriter w = new FileWriter(file);
            for (int j = 0; j < lines; j++) { //another for to write in file
                w.write("Hello World\n"); //we write "Hello Word" in each line of the file
            }
            w.close();
        }

        return names_file;
    }

    /**A function that receives an array with file names and returns the total number of lines in them
     * @param fileNames = An array of filenames
     * @return The number of lines in all the files whose names are in the array we received
     * @throws IOException
     */

    public static int getNumOfLines(String[] fileNames) throws IOException {
        int count = 0;
        String str;
        int len = fileNames.length;
            for (int i = 0; i < len; i++) {
                FileReader s = new FileReader(fileNames[i]);
                BufferedReader r = new BufferedReader(s);
                while (!((str = r.readLine())==null)) {
                    count++;
                }
                r.close();
            }

        return count;
    }

    /** A function that receives an array with file names and returns the total number of lines in them
     *  using multiple threads
     * @param fileNames = An array of filenames
     * @return The number of lines in all the files whose names are in the array we received
     * @throws InterruptedException
     */

    public static int getNumOfLinesThreads(String[] fileNames) throws InterruptedException {
        Thread_run[] thread_file = new Thread_run[fileNames.length];
        int len = thread_file.length;
        for (int i = 0; i < len; i++) {
            thread_file[i] = new Thread_run(fileNames[i]);
            thread_file[i].start();
        }
        int count_lines = 0;
        for (int i = 0; i < len; i++) {

            thread_file[i].join();

            count_lines += thread_file[i].getNum_lines();
            }
        return count_lines;
    }

    /** A function that receives an array with file names and returns the total number of lines in them
     * using a thread pool
     * @param fileNames = An array of filenames
     * @return The number of lines in all the files whose names are in the array we received
     * @throws ExecutionException
     * @throws InterruptedException
     */

    public static int getNumOfLinesThreadPool(String[] fileNames) throws ExecutionException, InterruptedException {

        int len = fileNames.length;
        Future<Integer>[] factory  = new Future[len];
        ExecutorService exe = Executors.newFixedThreadPool(len);

            for (int i = 0; i < len; i++) {
                Thread_call task = new Thread_call(fileNames[i]);
                factory[i] = exe.submit(task);
            }
            int count_lines = 0;
            for (Future<Integer> x : factory) {

                    count_lines += x.get();
            }
            exe.shutdown();


        return count_lines;

    }

    public static void main(String [] args) throws IOException, InterruptedException, ExecutionException {

        //System.out.println(getNumOfLines(createTextFiles(5,1,10)));

        String [] str = createTextFiles(100,1,100);

        double start = System.currentTimeMillis();
        getNumOfLines(str);
        double end = System.currentTimeMillis();
        System.out.println("Time of method 2: "+ (end-start)+ " millisecond\n");

        start = System.currentTimeMillis();
        getNumOfLinesThreads(str);
        end = System.currentTimeMillis();
        System.out.println("Time of method 3: "+ (end-start) + " millisecond\n");

        start = System.currentTimeMillis();
        getNumOfLinesThreadPool(str);
        end = System.currentTimeMillis();
        System.out.println("Time of method 4: "+ (end-start) + " millisecond\n");



    }
}

package Matala_2_1;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.Callable;

public class Thread_call implements Callable<Integer> {

    private String names_file;
    private int num_lines;

    public Thread_call(String fileName){
        this.names_file = fileName;
    }

    public int getNum_lines() {
        return num_lines;
    }



    @Override
    public Integer call()  {
        int count = 0;
        String str;
        try {
                FileReader s = new FileReader(this.names_file);
                BufferedReader r = new BufferedReader(s);
                while (!((str = r.readLine())==null)) {
                    count++;
                }
                r.close();

        } catch (IOException ep) {
            ep.printStackTrace();
        }

        return count;
    }

}

package Matala_2_1;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Thread_run extends Thread {

    private String names_file;
    private int num_lines;

    public Thread_run(String fileName){
        this.names_file = fileName;
    }

    public int getNum_lines() {
        return num_lines;
    }

    public void run(){
        int count = 0;
        String str;
        try {
                FileReader s = new FileReader(this.names_file);
                BufferedReader r = new BufferedReader(s);
                while (!((str = r.readLine())==null)) {
                    count++;
                }
                this.num_lines = count;
                r.close();

        } catch (IOException ep) {
            ep.printStackTrace();
        }
    }

}

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import java.util.concurrent.*;


public class Tests {

    public static final Logger logger = LoggerFactory.getLogger(Tests.class);
    @Test
    public void partialTest(){
        CustomExecutor customExecutor = new CustomExecutor();
        Task task = Task.createTask(()->{
            int sum = 0;
            for (int i = 1; i <= 10; i++) {
                sum += i;
            }
            //System.out.println(sum);
            return sum;
        }, TaskType.COMPUTATIONAL);
        Future sumTask = customExecutor.submit(task);
        final int sum;
        try {
            sum = (int) sumTask.get(1, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new RuntimeException(e);
        }
        logger.info(()-> "Sum of 1 through 10 = " + sum);
        Callable<Double> callable1 = ()-> {
            return 1000 * Math.pow(1.02, 5);
        };
        Callable<String> callable2 = ()-> {
            StringBuilder sb = new StringBuilder("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
            return sb.reverse().toString();
        };
        // var is used to infer the declared type automatically
        Future priceTask = customExecutor.submit(()-> {
            return 1000 * Math.pow(1.02, 5);
        }, TaskType.COMPUTATIONAL);
        Future reverseTask = customExecutor.submit(callable2, TaskType.IO);
        final Double totalPrice;
        final String reversed;
        try {
            totalPrice = (Double) priceTask.get();
            reversed = (String) reverseTask.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }


        Task task1 = Task.createTask(()->{
         int factorial = 1;
            for(int i=1; i<=4 ; i++){
            factorial *= i;
            }
            //System.out.println(factorial);
            return factorial;
        }, TaskType.COMPUTATIONAL);
        Future factorialTask1 = customExecutor.submit(task1);
        final int factorial;
        try {
            factorial = (int) factorialTask1.get(1, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new RuntimeException(e);
        }
        logger.info(()-> "The factorial of 4 = " + factorial);


        Callable<Integer> callable3 = ()-> {
            int ans =0;
            ans = ((536)%10) * 3;
            return ans;
        };
        Task adi = Task.createTask(callable1, TaskType.OTHER);
        Task hod = Task.createTask(callable3, TaskType.COMPUTATIONAL);

        int p = adi.compareTo(hod);
        //System.out.println(p);
        if(p == 1){
            logger.info(()-> "Hod is preferred from adi");
        }

        logger.info(()-> "Reversed String = " + reversed);
        logger.info(()->String.valueOf("Total Price = " + totalPrice));
        logger.info(()-> "Current maximum priority = " +
                customExecutor.getCurrentMax());
        customExecutor.gracefullyTerminate();

    }
}








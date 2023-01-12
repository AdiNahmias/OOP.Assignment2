import java.util.concurrent.*;

public class CustomExecutor<T>  extends ThreadPoolExecutor{
    private int arr[] = new int [10];

    public CustomExecutor(){

        super(Runtime.getRuntime().availableProcessors()/2,Runtime.getRuntime().availableProcessors() -1,
                300,TimeUnit.MILLISECONDS, new PriorityBlockingQueue<>(Runtime.getRuntime().availableProcessors()/2,
                        (t1,t2) ->{
                    Task task1 = (Task) t1;
                    Task task2 = (Task) t2;
                    return task1.compareTo(task2);
                }));
    }

    @Override
    protected <T> RunnableFuture<T> newTaskFor(Callable<T> callable) {
        return Task.createTask(callable);
    }

    /** Go over the array that keeps priorities of each task
     * @return return the maxPriority in O(10)
     */
    public int getCurrentMax() {
        for (int i = 0; i < 10; i++) {
            if (arr[i]>0){
                return i+1;
            }

        }
        return 0;
    }

    /**
     * A method for inserting Task instances into a priority task queue
     * @param task
     * @return ThreadPoolExecutor with task
     * @param <T> =  generic type
     */

    public <T> Future<T> submit(Task<T> task) {
        arr[task.getPriority()]++;
        return super.submit((Callable<T>) task);
    }



    /**
     * A method whose purpose is to queue an operation that can be performed asynchronously
     * with TaskType as a parameter
     * @param task
     * @return ThreadPoolExecutor with task
     * @param <T> =  generic type
     */
    public <T> Future<T> submit(Callable task, TaskType type) {
        Task new_task  = Task.createTask(task, type);
        return submit(new_task);
    }


    /**
     A method whose purpose is to queue an operation that can be executed asynchronously
     * without TaskType as a parameter
     * @param task
     * @return ThreadPoolExecutor with task
     * @param <T> =  generic type
     */
    public <T> Future<T> submit(Callable<T> task) {
        Task new_task  = Task.createTask(task);
        return submit(new_task);
    }


    public void gracefullyTerminate() throws InterruptedException {
        super.shutdown();
       super.awaitTermination(300,TimeUnit.MILLISECONDS);

    }


    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        Task y = ((Task)r);
    }
}

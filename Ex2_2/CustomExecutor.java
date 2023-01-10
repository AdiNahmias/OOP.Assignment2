import java.util.concurrent.*;

import static java.lang.Runtime.getRuntime;

public class CustomExecutor<T> {

    private PriorityBlockingQueue<Runnable> queue;
    private ThreadPoolExecutor pool;
    int min_pool;
    int max_pool;
    int max_priority;


    public CustomExecutor(){
        this.max_priority = Integer.MAX_VALUE;
        this.queue = new PriorityBlockingQueue<>();
        this.min_pool = getRuntime().availableProcessors()/2;
        this.max_pool = getRuntime().availableProcessors() -1;
        this.pool = new ThreadPoolExecutor(this.min_pool, this.max_pool, 300, TimeUnit.MILLISECONDS, this.queue);
    }


    /** We will update the max_priority value each time a task is inserted into the queue to get this number in o(1).
     * @return max_priority value.
     */
    public int getCurrentMax() {
        return max_priority;
    }



    /** A method for inserting Task instances into a priority task queue
     * @param task
     * @return pool with task
     * @param <T> = generic type
     */
    public <T> Future<T> submit(Task task) {
        this.max_priority = Math.min(this.max_priority, task.getPriority());
        return this.pool.submit(task);
    }



    /** A method whose purpose is to queue an operation that can be performed asynchronously
     * with TaskType as a parameter
     * @param task
     * @return pool with task
     * @param <T> = generic type
     */
    public <T> Future<T> submit(Callable task, TaskType type) {
        Task new_task  = Task.createTask(task, type);
        this.max_priority = Math.min(this.max_priority, new_task.getPriority());
        return this.pool.submit(task);
    }

    /** A method whose purpose is to queue an operation that can be executed asynchronously
     * without TaskType as a parameter
     * @param task
     * @return pool with task
     * @param <T> = generic type
     */
    public <T> Future<T> submit(Callable task){
        Task new_task  = Task.createTask(task);
        this.max_priority = Math.min(this.max_priority, new_task.getPriority());
        return this.pool.submit(task);

    }

    public void gracefullyTerminate(){
        pool.shutdown();
    }




}

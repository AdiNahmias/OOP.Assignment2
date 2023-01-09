import javax.print.attribute.standard.MediaSize;
import java.lang.reflect.Type;
import java.util.concurrent.Callable;
import java.util.function.Supplier;
public class Task<T> implements Callable<T>, Comparable<Task<T> > {

    private Callable  task;
    private TaskType type;
    private int priority;

    private Task(Callable<T> t, TaskType type){
        this.task = t;
        this.type = type;
        this.priority = type.getPriorityValue();
    }

    private Task(Callable<T> t){
        this.task = t;
        this.type = TaskType.OTHER;
        this.priority = 3;
    }

    public static Task createTask(Callable t, TaskType type){
        return new Task(t,type);
    }

    public static Task createTask(Callable t){
        return new Task(t);
    }

    @Override
    public T call() throws Exception {
        return (T) this.task.call();
    }


    @Override
    public int compareTo(Task<T> t) {
        if(t.priority == this.priority){
            return 0;
        }else if (t.priority < this.priority){
            return 1;
        }
        else return -1;
    }

    public int getPriority() {
        return this.priority;
    }
}

# OOP.Assignment2

Part A

In part A of the assignment we define several functions:
In the first function we created n files and, in each file, a random number of lines obtained by using java's random class. The function returned an array in which we saved the file names.
After that we implemented 3 more functions with different methods but they all have the same goal, to return the total number of lines of all the files we created.

The "getNumOfLine" function:
The function receives an array with file names and runs in a loop on each file separately.
Using the BufferedReader the function checks how many lines are in each file and adds this 
number to the count variable which finally returns it.

The "getNumOfLineThread" function:
In this function we used the Thread_run class that we built that extends from the Thread class. Also here the function receives an array with file names. First we created an array with the size of the array we received and then in a loop we kept variables of the Thread_run type for each file. For each variable in the array we will activate the start function which will cause the activation of the run function that we implemented in Thread_run. The function run will calculate the total number of lines of the files (in the same method as in the function (getNumOfLine).

The function "getNumOfLineThreadPool":
In this function we used the Thread_call class that we built that inherits (implements) from the callable class.
Also here the function receives an array with file names.
First, we built a linked list that will be used to hold the Future values, then we used the executorServic class to create a ThreadPool similar to the amount of files.
Then we ran the loop to create tasks, that is, the counting of the lines in each file which is done by the call function (in the same method as in the getNumOfLine function) that we built in ThreadCall, and put them in the linked list.
And finally we ran the loop on each element in the linked list and update the number of rows and return it.

Running times:
From the analysis of the running times that can be seen in main we can see that using Thread does optimize the process. The running times of the functions we used in Threads are significantly smaller than the running time without using them. This is because Threads are used by us in executing processes at the same time and therefore will perform a task in a shorter time. We note that in order to see the differences we created a large number of files, and ran the three functions with their array of names. We have seen that using ThreadPool is more efficient than using a normal Thread and this is because if a certain TreadPool has finished its task, it can move on to the next task without waiting. Unlike a normal Thread where join method is used.



UML diagram 



![image](https://user-images.githubusercontent.com/118722490/211202362-627797a9-0821-409c-a044-606b0c96f452.png)


Part B


In this part we created 2 class, Task and CustomExecutor.

TASK
This is a generic class and in it we implement callable to perform the tasks and implement Comparable to define how to differentiate between the types of tasks.
We created 2 types of constructors and defined them as private in order not to expose the constructors and allow access to them, and in order to create a new instance of type TASK we created public functions that are used as constructors, thus we used the Factory Method Design Pattern.

Customexecutor
This is a generic class and we used it as Priority Blocking Queue to send the tasks to be executed according to their priority. We used a blocking queue so that it would not be possible to insert new tasks when the queue is confirmed to be full (that is blocked), or in the event that the queue is empty and there are no tasks to perform. And the queue sorts the tasks according to their priority that we defined in the compareTo method
Every time we added a task to the queue we made sure to update the max_priority accordingly. We did this so that we could later get the maximum priority in the queue in O(1) complexity, (by the function getCurrentMax).


UML diagram



![image](https://user-images.githubusercontent.com/118722490/211408831-785aa878-a85d-4587-a78d-19dca8c7e96c.png)








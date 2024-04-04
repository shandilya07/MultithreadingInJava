### What is .join() operation in Java?
* Main thread as the parent thread
  * When we start a program, usually the execution begins with main() method. This method runs on the main thread. This
    can be understood as the parent thread since it spawns the other threads.
* Independent execution of threads
  * When you create and start other threads, they run concurrently with the main thread unless instructed otherwise. So
    under normal circumstances, all threads run independent of each other. More explicitly, no thread waits for other
    thread.
* What is join()
  * Imagine threads to be lines of execution. So, when we call .join() on a certain thread, it means the parent thread,
    the main thread in this case (could be any thread which created the thread on which .join() is being called) is saying
    "Hey thread, once you are done executing your task, join my flow of execution". It's like the parent thread waits for
    the completion of the child thread and then continues with its execution.
* My perspective
  * I find that the join keyword is not very intuitive at first for the kind of operation it's doing. Somewhat better 
    terms could have been .waitForCompletion() or .completeThenContinue(). 
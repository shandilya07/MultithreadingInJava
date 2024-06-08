package forkJoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class WorkLoadSplitter extends RecursiveAction {

    private final long workLoad;

    public WorkLoadSplitter(long workLoad) {
        this.workLoad = workLoad;
    }

    @Override
    protected void compute() {
        if (this.workLoad > 16) {
            System.out.println("Work Load too big, thus splitting : " + this.workLoad);
            long firstWorkLoad = this.workLoad/2;
            long secondWorkLoad = this.workLoad - firstWorkLoad;

            WorkLoadSplitter firstSplit = new WorkLoadSplitter(firstWorkLoad);
            WorkLoadSplitter secondSplit = new WorkLoadSplitter(secondWorkLoad);

            firstSplit.fork();
            secondSplit.fork();
        } else {
            System.out.println("Work Load within limits! Task being executed for workload : " + this.workLoad);
        }
    }
}

class WorkLoadSplitDemo {
    public static void main(String[] args) {
        try (ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors())) {
            WorkLoadSplitter splitter = new WorkLoadSplitter(128);
            pool.invoke(splitter);
        }
    }
}
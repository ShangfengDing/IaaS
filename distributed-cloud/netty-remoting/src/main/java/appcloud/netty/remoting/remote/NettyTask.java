package appcloud.netty.remoting.remote;

/**
 * Created by lizhenhao on 2017/11/21.
 */
public class NettyTask implements Runnable{

    public long taskStartTime = System.currentTimeMillis();

    public long taskEndTime = 0;

    public Runnable runnable;


    public NettyTask(Runnable runnable) {
        this.runnable = runnable;
    }

    public void run() {
        if (runnable != null) {
            runnable.run();
        }
        taskEndTime = System.currentTimeMillis();
    }


    @Override
    public String toString() {
        return "NettyTask{" +
                "taskStartTime=" + taskStartTime +
                ", taskEndTime=" + taskEndTime +
                ", runnable=" + runnable +
                '}';
    }
}

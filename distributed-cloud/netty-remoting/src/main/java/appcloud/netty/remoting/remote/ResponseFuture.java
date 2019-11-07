package appcloud.netty.remoting.remote;

import appcloud.netty.remoting.InvokeCallBack;
import appcloud.netty.remoting.protocol.RemoteCommand;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by lizhenhao on 2017/11/7.
 */
public class ResponseFuture {

    private int resquesId;

    private RemoteCommand remoteCommand;

    private boolean sendResquestOk = false;

    private final CountDownLatch countDownLatch = new CountDownLatch(1);

    private Throwable throwable;

    private InvokeCallBack invokeCallBack;

    private AtomicBoolean excuteCallBackOnlyOnce = new AtomicBoolean(false);

    public void executeCallBack() {
        if (sendResquestOk && excuteCallBackOnlyOnce.compareAndSet(false,true)) {
            invokeCallBack.opreationCompelete();
        }
        else {
            // TODO: 2017/11/15 未发送成功执行的逻辑
        }
    }

    public ResponseFuture(int resquesId) {
        this.resquesId = resquesId;
    }

    public ResponseFuture(int resquesId, InvokeCallBack invokeCallBack) {
        this.resquesId = resquesId;
        this.invokeCallBack = invokeCallBack;
    }

    public void setSendRequestOk(boolean flag) {
        this.sendResquestOk = flag;
    }

    public boolean getSendRequestOk() {
        return this.sendResquestOk;
    }

    public void setThrowable(Throwable cause) {
        this.throwable = cause;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void putResponse(RemoteCommand remoteCommand) {
        this.remoteCommand = remoteCommand;
        this.countDownLatch.countDown();
    }

    public RemoteCommand await(long timeout) throws InterruptedException {
        this.countDownLatch.await(timeout, TimeUnit.MILLISECONDS);
        if (remoteCommand == null) return null;
        else return remoteCommand;
    }

    public InvokeCallBack getInvokeCallBack() {
        return invokeCallBack;
    }

    public void setInvokeCallBack(InvokeCallBack invokeCallBack) {
        this.invokeCallBack = invokeCallBack;
    }
}

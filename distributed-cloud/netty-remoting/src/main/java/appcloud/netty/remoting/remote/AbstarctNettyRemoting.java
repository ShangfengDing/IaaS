package appcloud.netty.remoting.remote;

import appcloud.netty.remoting.InvokeCallBack;
import appcloud.netty.remoting.common.RequestCode;
import appcloud.netty.remoting.common.Pair;
import appcloud.netty.remoting.exception.RemotingSendRequestException;
import appcloud.netty.remoting.exception.RemotingTimeOutException;
import appcloud.netty.remoting.protocol.RemoteCommand;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

/**
 * Created by lizhenhao on 2017/11/7.
 */
public abstract class AbstarctNettyRemoting {

    protected final static Logger LOGGER = LoggerFactory.getLogger(AbstarctNettyRemoting.class);
    /**
     * use to store unhandler response
     */
    protected Map<Integer,ResponseFuture> responseTable = new ConcurrentHashMap<Integer, ResponseFuture>();

    /**
     * use to store processor,async process request(cost time operation must be async)
     */
    protected Map<Integer,Pair<NettyRequestProcessor,ExecutorService>> processMap = new HashMap<Integer, Pair<NettyRequestProcessor, ExecutorService>>();

    /**
     * use to limit oneway send speed
     */
    protected Semaphore semaphoreOneway = new Semaphore(NettySystemConfig.semaphorePermits);

    /**
     * use to limit async send speed
     */
    protected Semaphore semaphoreAsync = new Semaphore(NettySystemConfig.semaphorePermits);


    /**
     * Sync send request
     * @param channel
     * @param request
     * @return
     * @throws RemotingSendRequestException
     * @throws RemotingTimeOutException
     * @throws InterruptedException
     */
    public RemoteCommand invokeSyncImp(final Channel channel, RemoteCommand request,long timeoutmillions) throws RemotingSendRequestException, RemotingTimeOutException, InterruptedException {

        final int resquestId = request.getRequestId();
        try {
            final ResponseFuture responseFuture = new ResponseFuture(resquestId);
            responseTable.put(resquestId, responseFuture);
            channel.writeAndFlush(request).addListener(new ChannelFutureListener() {
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if (channelFuture.isSuccess()) {
                        responseFuture.setSendRequestOk(true);
                        return;
                    } else {
                        responseFuture.setSendRequestOk(false);
                    }

                    responseTable.remove(resquestId);
                    responseFuture.setThrowable(channelFuture.cause());
                    responseFuture.putResponse(null);
                    LOGGER.warn("send request fail ", channelFuture.cause());
                }
            });

            RemoteCommand responseCommand = responseFuture.await(timeoutmillions);
            if (responseCommand == null) {
                if (responseFuture.getSendRequestOk() == false) {
                    throw new RemotingSendRequestException("send request fail",responseFuture.getThrowable());
                }
                else {
                    throw new RemotingTimeOutException("recived response time out");
                }
            }
            return responseCommand;

        } catch (Exception e) {
            LOGGER.info("send request fail");
            throw e;
        }finally {
            responseTable.remove(resquestId);
        }
    }

    /**
     * sned one way request
     * @param channel
     * @param request
     * @param timeoutmillions
     * @throws InterruptedException
     * @throws RemotingSendRequestException
     * @throws RemotingTimeOutException
     */

    public void invokeOnewayImp(final Channel channel,RemoteCommand request,long timeoutmillions) throws InterruptedException, RemotingSendRequestException, RemotingTimeOutException {
        request.markOneWayFlag();
        boolean acquire = this.semaphoreOneway.tryAcquire(timeoutmillions, TimeUnit.MILLISECONDS);
        if (acquire) {
            try {
                channel.writeAndFlush(request).addListener(new ChannelFutureListener() {
                    public void operationComplete(ChannelFuture channelFuture) throws Exception {
                        semaphoreOneway.release();
                        if (channelFuture.isSuccess()) {
                            LOGGER.info("invokeOneway: oneway message send success");
                        }
                        else {
                            LOGGER.error("invokeOneway: oneway message send fail",channelFuture.cause());
                        }
                    }
                });

            } catch (Exception e) {
                LOGGER.error("invokeOneway: oneway message send exception",e);
                throw new RemotingSendRequestException("invokeOneway: oneway message send exception",e.getCause());
            }
        }
        else {
            String info = String.format("invokeOneway:send oneway message to <"+ channel.remoteAddress() +"> times too much and acquire semaphoreOneway fail, semaphoreOneway avabile premit is %d,wait thread nums is %d",
                    this.semaphoreOneway.availablePermits(),
                    this.semaphoreOneway.getQueueLength());
            LOGGER.info(info);
            throw new RemotingTimeOutException(info);
        }
    }


    /**
     * send asyc request
     * @param channel
     * @param request
     * @param timeoutmillions
     * @return
     * @throws RemotingSendRequestException
     * @throws RemotingTimeOutException
     * @throws InterruptedException
     */
    public void invokeAsyncImp(final Channel channel, RemoteCommand request, long timeoutmillions, final InvokeCallBack invokeCallBack) throws RemotingSendRequestException, RemotingTimeOutException, InterruptedException {
        final int resquestId = request.getRequestId();

        boolean acquire = this.semaphoreAsync.tryAcquire(timeoutmillions, TimeUnit.MILLISECONDS);
        if (acquire) {
            try {
                final ResponseFuture responseFuture = new ResponseFuture(resquestId,invokeCallBack);
                responseTable.put(resquestId, responseFuture);
                channel.writeAndFlush(request).addListener(new ChannelFutureListener() {
                    public void operationComplete(ChannelFuture channelFuture) throws Exception {
                        if (channelFuture.isSuccess()) {
                            responseFuture.setSendRequestOk(true);
                            return;
                        } else {
                            responseFuture.setSendRequestOk(false);
                        }

                        responseTable.remove(resquestId);
                        responseFuture.setThrowable(channelFuture.cause());
                        responseFuture.putResponse(null);
                        executeCallBack(responseFuture);
                        LOGGER.warn("invokeAsyncImp:send request fail ", channelFuture.cause());
                    }
                });

            } catch (Exception e) {
                LOGGER.error("invokeAsyncImp:send request exception", e);
                throw new RemotingSendRequestException("invokeAsyncImp:send request exception", e);
            } finally {
                responseTable.remove(resquestId);
            }
        }
        else {
            String info = String.format("invokeAsync:send async message to <"+ channel.remoteAddress() +"> times too much and acquire semaphoreAsync fail, semaphoreAsync avaliable premit is %d,wait thread nums is %d",
                    this.semaphoreAsync.availablePermits(),
                    this.semaphoreAsync.getQueueLength());
            LOGGER.info(info);
            throw new RemotingTimeOutException(info);
        }
    }


    /**
     * 异步执行回调函数
     * @param responseFuture
     */
    public void executeCallBack(final ResponseFuture responseFuture) {
        ExecutorService callBackExecutor = getCallBackExecutor();
        if (callBackExecutor != null) {
            callBackExecutor.submit(new Runnable() {
                public void run() {
                    responseFuture.executeCallBack();
                }
            });
        }
        else {
            LOGGER.info("no thread to execute callback ,using current thread");
            responseFuture.executeCallBack();
        }
    }

    /**
     * 子类定义回调函数执行的线程池
     * @return
     */
    public abstract ExecutorService getCallBackExecutor();


    /**
     * 主要执行罗逻辑
     * @param ctx
     * @param remoteCommand
     */
    public void process(final ChannelHandlerContext ctx,final RemoteCommand remoteCommand) {
        if (!remoteCommand.responseType()) {
            processRequest(ctx, remoteCommand);
        } else {
            processResponse(ctx, remoteCommand);
        }
    }

    /**
     * 处理response
     * @param ctx
     * @param response
     */
    public void processResponse(final ChannelHandlerContext ctx,final RemoteCommand response) {
        int uniqe = response.getRequestId();
        ResponseFuture responseFuture = responseTable.get(uniqe);
        if (responseFuture != null) {
            responseFuture.putResponse(response);
            responseTable.remove(uniqe);
            if (responseFuture.getInvokeCallBack() != null) {
                executeCallBack(responseFuture);
            }
        }
        else {
            LOGGER.warn("no request correspond this response");
        }
    }

    /**
     * 处理request
     * @param ctx
     * @param remoteCommand
     */
    public void processRequest(final ChannelHandlerContext ctx, final RemoteCommand remoteCommand) {
        final Pair<NettyRequestProcessor,ExecutorService> pair = processMap.get(remoteCommand.getCode());
        ExecutorService processExecutor = pair.getObject2();
        if (pair != null) {
            LOGGER.info("获取到request对应的处理逻辑，requestCode：" + remoteCommand.getCode());
            Runnable processRun = new Runnable() {
                public void run() {
                    try {
                        NettyRequestProcessor processor = pair.getObject1();
                        RemoteCommand response = processor.processRequest(ctx,remoteCommand);
                        if (remoteCommand.oneWayType()) {
                            LOGGER.info("request is oneway type,no need to send response");
                            return;
                        }
                        response.setRequestId(remoteCommand.getRequestId());
                        response.markResponseFlag();
                        ctx.writeAndFlush(response).addListener(new ChannelFutureListener() {
                            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                                LOGGER.info("send response for request {}",remoteCommand.getRequestId());
                            }
                        });
                    } catch (Exception e) {
                        LOGGER.error("request {} process fail,exception {}",remoteCommand.getRequestId(),e);
                        RemoteCommand response = RemoteCommand.createReponseRemoteCommand(RequestCode.SYSTEM_ERROR,"request process fail");
                        ctx.writeAndFlush(response);
                    }
                }
            };

            if (pair.getObject1().rejectRequest()) {
                LOGGER.info("Server is too much request,reject process request");
                String remark = "Server is too much request,reject process request";
                RemoteCommand response = RemoteCommand.createReponseRemoteCommand(remoteCommand.getCode(),remark);
                response.setRequestId(remoteCommand.getRequestId());
                response.setCode(RequestCode.SYSTEM_ERROR);
                sendResponse(ctx,response);
            }
            NettyTask task = new NettyTask(processRun);
            processExecutor.submit(task);
        }
        else {
            LOGGER.info("System do not support this kind of code request");
            String remark = "System do not support this kind of code request";
            RemoteCommand response = RemoteCommand.createReponseRemoteCommand(remoteCommand.getCode(),remark);
            response.setRequestId(remoteCommand.getRequestId());
            response.setCode(RequestCode.SYSTEM_ERROR);
            sendResponse(ctx,response);
        }
    }

    /**
     * 发送response
     * @param ctx
     * @param response
     */
    public void sendResponse(final ChannelHandlerContext ctx,final RemoteCommand response) {
        ctx.writeAndFlush(response).addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                LOGGER.info("send response for request {}",response.getRequestId());
            }
        });
    }
}

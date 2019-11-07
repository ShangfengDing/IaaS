package appcloud.netty.remoting.remote;

import appcloud.netty.remoting.RemoteClient;
import appcloud.netty.remoting.exception.RemotingConnectionException;
import appcloud.netty.remoting.exception.RemotingSendRequestException;
import appcloud.netty.remoting.exception.RemotingTimeOutException;
import appcloud.netty.remoting.protocol.RemoteCommand;
import appcloud.netty.remoting.util.ChannelUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.DefaultEventExecutorGroup;

import java.net.SocketAddress;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by lizhenhao on 2017/11/9.
 */
public class NettyRemotingClient extends AbstarctNettyRemoting implements RemoteClient{

    public final long LOCK_TIMEOUT_MILLIS = 3000;
    public final Bootstrap bootstrap = new Bootstrap();
    public final EventLoopGroup eventLoopGroup;
    public final DefaultEventExecutorGroup defaultEventExecutorGroup;
    public ExecutorService callBackExecutor;
    /**
     * use to store channel
     */
    public final ConcurrentHashMap<String/*addr*/,ChannelWapper> channelTable = new ConcurrentHashMap<String, ChannelWapper>();

    /**
     * lock for channelTables
     */
    public final Lock channelTableLock = new ReentrantLock();

    public NettyRemotingClient() {
        eventLoopGroup = new NioEventLoopGroup(1, new ThreadFactory() {
            private AtomicInteger index = new AtomicInteger(0);

            public Thread newThread(Runnable r) {
                return new Thread(r,"nioEventLoopGroup thread index" + index.getAndIncrement());
            }
        });

        defaultEventExecutorGroup = new DefaultEventExecutorGroup(1, new ThreadFactory() {
            private AtomicInteger index = new AtomicInteger(0);

            public Thread newThread(Runnable r) {
                return new Thread(r,"eventExecutor group thread index" + index.getAndIncrement());
            }
        });

        callBackExecutor = Executors.newSingleThreadExecutor();
    }

    public void start() {
        Bootstrap bootstrap = this.bootstrap.group(this.eventLoopGroup).channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY,true)
                .option(ChannelOption.SO_KEEPALIVE,false)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS,NettySystemConfig.connectTimeOut)
                .option(ChannelOption.SO_SNDBUF,NettySystemConfig.socketSndbufSize)
                .option(ChannelOption.SO_RCVBUF,NettySystemConfig.socketRcvbufSize)
                .handler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(
                                defaultEventExecutorGroup,
                                new NettyEncoder(),
                                new NettyDecoder(),
                                new NettyConnectManageHandler(),
                                new NettyClientProcessReadHandler()
                        );
                    }
                });
    }

    public void shutdown() {
        try {
            eventLoopGroup.shutdownGracefully();
        } catch (Exception e) {
            LOGGER.error("Netty client eventLoopGroup shutdown exception {}",e);
        }
        if (defaultEventExecutorGroup != null) {
            defaultEventExecutorGroup.shutdownGracefully();
        }

        if (callBackExecutor != null) {
            try {
                callBackExecutor.shutdown();
            } catch (Exception e) {
                LOGGER.error("Netty client callBackExecutor shutdown exception {}",e);
            }
        }
    }


    public RemoteCommand invokeSync(String addr,RemoteCommand remoteCommand,long timeoutmillions) throws RemotingTimeOutException, InterruptedException, RemotingSendRequestException, RemotingConnectionException {
        Channel channel = this.getandCreateChannel(addr);
        if (channel != null && channel.isActive()) {
            try {
                RemoteCommand responseCommand = invokeSyncImp(channel,remoteCommand,timeoutmillions);
                return responseCommand;
            } catch (RemotingTimeOutException e) {
                LOGGER.error("invokeSync: remoting time out address is {}",e.getAddr());
                throw e;
            } catch (RemotingSendRequestException e) {
                LOGGER.error("invokeSync: remoting send request exception address is {}",e.getAddr());
                throw e;
            }
        }
        else {
            LOGGER.error("invokeSync: remoting time out address is {}",addr);
            throw new RemotingConnectionException("invokeSync: remoting connecting exception",addr);
        }
    }

    public void invokeOneWay(String addr,RemoteCommand remoteCommand,long timeoutmillions) throws RemotingTimeOutException, InterruptedException, RemotingSendRequestException, RemotingConnectionException {
        Channel channel = this.getandCreateChannel(addr);
        if (channel != null && channel.isActive()) {
            try {
                invokeOnewayImp(channel,remoteCommand,timeoutmillions);
            } catch (RemotingTimeOutException e) {
                LOGGER.error("invokeSync: remoting time out address is {}",e.getAddr());
                throw e;
            } catch (RemotingSendRequestException e) {
                LOGGER.error("invokeSync: remoting send request exception address is {}",e.getAddr());
                throw e;
            }
        }
        else {
            LOGGER.error("invokeSync: remoting time out address is {}",addr);
            throw new RemotingConnectionException("invokeSync: remoting connecting exception",addr);
        }
    }

    public Channel getandCreateChannel(String addr) throws InterruptedException {
        ChannelWapper channelWapper = channelTable.get(addr);
        if (channelWapper!=null && channelWapper.isOk()) {
            return channelWapper.getChannel();
        }
        return this.createChannel(addr);
    }

    private Channel createChannel(String addr) throws InterruptedException {
        ChannelWapper channelWapper = channelTable.get(addr);
        if (channelWapper != null && channelWapper.isOk()) {
            return channelWapper.getChannel();
        }

        try {
            boolean createNewConnection = false;
            if (this.channelTableLock.tryLock(LOCK_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)) {
                channelWapper = channelTable.get(addr);
                if (channelWapper != null && channelWapper.isOk()) {
                    return channelWapper.getChannel();
                }
                else if (channelWapper!=null && !channelWapper.getChannelFuture().isDone()) {
                    createNewConnection = false;
                }
                else {
                    createNewConnection = true;
                }
                if (createNewConnection) {
                    ChannelFuture channelFuture = this.bootstrap.connect(ChannelUtil.String2SocketAddress(addr));
                    channelWapper = new ChannelWapper(channelFuture);
                    this.channelTable.put(addr,channelWapper);
                }
            }
            else {
                LOGGER.warn("createChannel :lock acquire timeout {}",this.LOCK_TIMEOUT_MILLIS);
            }
        } catch (InterruptedException e) {
            LOGGER.error("createChannel :create channel exception",e);
            throw e;
        } finally {
            this.channelTableLock.unlock();
        }

        if (channelWapper != null) {
            if (channelWapper.getChannelFuture().awaitUninterruptibly(NettySystemConfig.connectTimeOut,TimeUnit.MILLISECONDS)) {
                if (channelWapper.isOk()) {
                    LOGGER.info("createChannel: channel connect success!!");
                    return channelWapper.getChannel();
                }
                else {
                    LOGGER.error("createChannel: channel connect fail!!");
                }
            }
            else {
                LOGGER.error("createChannel: channel connect timeout {}",NettySystemConfig.connectTimeOut);
            }
        }

        return null;

    }


    public void closeChannel(String addr,Channel channel) {
        if (channel == null)
            return;
        String address = null == addr?ChannelUtil.SocketAddress(channel):addr;
        ChannelWapper preCw = channelTable.get(address);
        if (preCw == null) {
            return;
        }
        try {
            if (this.channelTableLock.tryLock(LOCK_TIMEOUT_MILLIS,TimeUnit.MILLISECONDS)) {
                boolean removeItemFlag;
                try {
                    if (preCw.getChannel() == channel) {
                        removeItemFlag = true;
                    }
                    else {
                        LOGGER.info("closeChannel:channel has already close");
                        removeItemFlag = false;
                    }

                    if (removeItemFlag) {
                        this.channelTable.remove(addr);
                        ChannelUtil.closeChannel(channel);
                        LOGGER.info("closeChannel:channel remove success!{}", addr);
                    }
                } catch (Exception e) {
                    LOGGER.error("closeChannel:close channel exception",e);
                }
                finally {
                    this.channelTableLock.unlock();
                }
            }
            else {
                LOGGER.error("closeChannel:acquire lock time out {}",LOCK_TIMEOUT_MILLIS);
            }
        } catch (InterruptedException e) {
            LOGGER.error("closeChannel:close channel exception",e);
        }

    }

    public ExecutorService getCallBackExecutor() {
        return this.callBackExecutor;
    }

    static class ChannelWapper {
        private ChannelFuture channelFuture;

        public ChannelWapper(ChannelFuture channelFuture) {
            this.channelFuture = channelFuture;
        }

        public boolean isOk() {
            Channel channel = channelFuture.channel();
            if (channel != null && channel.isActive()) {
                return true;
            }
            return false;
        }
        public Channel getChannel() {
            return channelFuture.channel();
        }

        public ChannelFuture getChannelFuture() {
            return channelFuture;
        }

    }


    class NettyClientProcessReadHandler extends SimpleChannelInboundHandler<RemoteCommand> {

        protected void channelRead0(ChannelHandlerContext ctx, RemoteCommand remoteCommand) throws Exception {
            LOGGER.info("接收到response，准备处理");
            process(ctx,remoteCommand);
        }
    }

    class NettyConnectManageHandler extends ChannelDuplexHandler {

        @Override
        public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise future) throws Exception {
            String address = ChannelUtil.SocketAddress(remoteAddress);
            LOGGER.info("NETTY CLIENT CONNECT {}",address);
            super.connect(ctx,remoteAddress, localAddress, future);
        }


        @Override
        public void disconnect(ChannelHandlerContext ctx, ChannelPromise future) throws Exception {
            String address = ChannelUtil.SocketAddress(ctx.channel());
            LOGGER.info("NETTY CLIENT DISCONNECT {}",address);
            closeChannel(address,ctx.channel());
            super.disconnect(ctx,future);
        }

        @Override
        public void close(ChannelHandlerContext ctx, ChannelPromise future) throws Exception {
            String address = ChannelUtil.SocketAddress(ctx.channel());
            LOGGER.info("NETTY CLIENT CLOSE {}",address);
            //TODO 2018/4/16 有可能是此处close两次出错，导致空指针；也可能是下面的异常捕捉时再次报错
            closeChannel(address,ctx.channel());
            super.close(ctx,future);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            String address = ChannelUtil.SocketAddress(ctx.channel());
            LOGGER.info("NETTY CLIENT EXCEPTION CAUGHT {}",cause);
            closeChannel(address,ctx.channel());
            throw new Exception(cause.getMessage());
        }


    }


}

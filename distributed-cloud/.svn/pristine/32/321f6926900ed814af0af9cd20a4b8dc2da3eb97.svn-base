package appcloud.netty.remoting.remote;

import appcloud.netty.remoting.RemoteServer;
import appcloud.netty.remoting.common.Pair;
import appcloud.netty.remoting.protocol.RemoteCommand;
import appcloud.netty.remoting.util.ChannelUtil;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.EventExecutorGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by lizhenhao on 2017/11/14.
 */
public class NettyRemotingServer extends AbstarctNettyRemoting implements RemoteServer {

    protected final static Logger LOGGER = LoggerFactory.getLogger(NettyRemotingServer.class);

    private final ServerBootstrap serverBootstrap;
    private final EventLoopGroup eventLoopBossGroup;
    private final EventLoopGroup eventLoopWorkerGroup;
    private final EventExecutorGroup defaultEventExecutorGroup;
    private ExecutorService callBackExecutor;
    private int port;

    public NettyRemotingServer() {
        this(NettySystemConfig.serverPort);
    }

    public NettyRemotingServer(int serverPort) {
        this.port = serverPort;
        serverBootstrap = new ServerBootstrap();
        eventLoopBossGroup = new NioEventLoopGroup(1, new ThreadFactory() {
            public Thread newThread(Runnable r) {
                AtomicInteger index = new AtomicInteger();
                return new Thread(r,"nioEventLoopBossGroup thread index"+index.getAndIncrement());
            }
        });

        eventLoopWorkerGroup = new NioEventLoopGroup(NettySystemConfig.nioLoopWorkerGroupNumber, new ThreadFactory() {
            public Thread newThread(Runnable r) {
                AtomicInteger index = new AtomicInteger();
                return new Thread(r,"nioEventLoopWorkerGroup thread index"+index.getAndIncrement());            }
        });

        defaultEventExecutorGroup = new NioEventLoopGroup(1, new ThreadFactory() {
            public Thread newThread(Runnable r) {
                AtomicInteger index = new AtomicInteger();
                return new Thread(r,"defaultEventExecutorGroup thread index"+index.getAndIncrement());               }
        });
        callBackExecutor = Executors.newSingleThreadExecutor();
    }

    public void start() {
        LOGGER.info("the listen port is: "+port);
        this.serverBootstrap.group(this.eventLoopBossGroup,this.eventLoopWorkerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG,1024)
                .option(ChannelOption.SO_REUSEADDR,true)
                .option(ChannelOption.SO_KEEPALIVE,false)
                .childOption(ChannelOption.TCP_NODELAY,true)
                .childOption(ChannelOption.SO_SNDBUF,NettySystemConfig.socketSndbufSize)
                .childOption(ChannelOption.SO_RCVBUF,NettySystemConfig.socketRcvbufSize)
                .localAddress(port)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(
                                defaultEventExecutorGroup,
                                new NettyEncoder(),
                                new NettyDecoder(),
                                new NettyConnectManageHandler(),
                                new NettyServerProcessReadHandler()
                        );
                    }
                });
        try {
            ChannelFuture sync = this.serverBootstrap.bind().sync();
        } catch (InterruptedException e) {
            LOGGER.error("this.serverBootstrap.bind().sync() error");
        }
    }


    public void shutdown() {
        try {
            eventLoopBossGroup.shutdownGracefully();
            eventLoopWorkerGroup.shutdownGracefully();
        } catch (Exception e) {
            LOGGER.error("Netty server Boss and Worker Group shutdown exception {}",e);
        }
        if (defaultEventExecutorGroup != null) {
            defaultEventExecutorGroup.shutdownGracefully();
        }

        if (callBackExecutor != null) {
            try {
                callBackExecutor.shutdown();
            } catch (Exception e) {
                LOGGER.error("Netty server callBackExecutor shutdown exception {}",e);
            }
        }
    }

    public void registerProcessor(int code,NettyRequestProcessor processor, ExecutorService executor) {
        this.processMap.put(code,new Pair<NettyRequestProcessor, ExecutorService>(processor, executor));
    }

    public ExecutorService getCallBackExecutor() {
        return this.callBackExecutor;
    }


    class NettyServerProcessReadHandler extends SimpleChannelInboundHandler<RemoteCommand> {

        protected void channelRead0(ChannelHandlerContext ctx, RemoteCommand remoteCommand) throws Exception {
            LOGGER.info("接收到request，准备处理");
            process(ctx,remoteCommand);
        }
    }

    class NettyConnectManageHandler extends ChannelDuplexHandler {
        public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
            String address = ChannelUtil.SocketAddress(ctx.channel());
            LOGGER.info("NETTY SERVER REGISTER {}",address);
            super.channelRegistered(ctx);
        }

        public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
            String address = ChannelUtil.SocketAddress(ctx.channel());
            LOGGER.info("NETTY SERVER UNREGISTER {}",address);
            super.channelUnregistered(ctx);
        }

        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            String address = ChannelUtil.SocketAddress(ctx.channel());
            LOGGER.info("NETTY SERVER CHANNELACTIVE {}",address);
            super.channelActive(ctx);
        }

        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
            String address = ChannelUtil.SocketAddress(ctx.channel());
            LOGGER.info("NETTY SERVER CHANNELINACTIVE {}",address);
            super.channelInactive(ctx);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            LOGGER.info("NETTY CLIENT EXCEPTION CAUGHT {}",cause);
        }
    }

}

package appcloud.netty.remoting.remote;

import appcloud.netty.remoting.protocol.RemoteCommand;
import appcloud.netty.remoting.util.ChannelUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;

/**
 * Created by lizhenhao on 2017/11/9.
 */
public class NettyEncoder extends MessageToByteEncoder<RemoteCommand> {

    protected final static Logger LOGGER = LoggerFactory.getLogger(NettyEncoder.class);

    protected void encode(ChannelHandlerContext channelHandlerContext, RemoteCommand remoteCommand, ByteBuf byteBuf) throws Exception {
        try {
            ByteBuffer remote = remoteCommand.encode();
            byteBuf.writeBytes(remote);
        } catch (Exception e) {
            LOGGER.error("encode remoteCommand fail",e);
            if (remoteCommand != null) {
                LOGGER.error("not send command",remoteCommand.toString());
            }
            ChannelUtil.closeChannel(channelHandlerContext.channel());
        }


    }
}

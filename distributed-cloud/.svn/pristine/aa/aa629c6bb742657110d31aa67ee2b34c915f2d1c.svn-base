package appcloud.netty.remoting.remote;

import appcloud.netty.remoting.protocol.RemoteCommand;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;

/**
 * Created by lizhenhao on 2017/11/9.
 */
public class NettyDecoder extends LengthFieldBasedFrameDecoder{

    protected final static Logger LOGGER = LoggerFactory.getLogger(NettyDecoder.class);

    public NettyDecoder() {
        super(NettySystemConfig.frameMaxLength,0,4,0,4);
    }

    public Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception{
        ByteBuf frame = null;
        try {
            frame = (ByteBuf) super.decode(ctx,in);
            if (null == frame) {
                return null;
            }
            ByteBuffer byteBuffer = frame.nioBuffer();
            return RemoteCommand.decode(byteBuffer);
        } catch (Exception e) {
            LOGGER.error("decode exception");
        } finally {
            if (null != frame) {
                frame.release();
            }
        }
        return null;
    }


}

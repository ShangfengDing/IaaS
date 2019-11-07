package appcloud.netty.remoting.util;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

/**
 * Created by lizhenhao on 2017/11/13.
 */
public class ChannelUtil {

    protected final static Logger LOGGER = LoggerFactory.getLogger(ChannelUtil.class);


    public static void closeChannel(Channel channel) {
        channel.close().addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                LOGGER.info("channel close success");
            }
        });

    }

    public static InetSocketAddress String2SocketAddress(String addr) {
        String[] strings = addr.split(":");
        InetSocketAddress isa = new InetSocketAddress(strings[0],Integer.valueOf(strings[1]));
        return isa;
    }

    public static String SocketAddress(Channel channel) {
        SocketAddress address = channel.remoteAddress();
        String addr = address.toString();
        if (addr == null) {
            return null;
        }
        else {
            return addr.substring(addr.lastIndexOf("/")+1);
        }

    }

    public static String SocketAddress(SocketAddress address) {
        String addr = address.toString();
        if (addr == null) {
            return null;
        }
        else {
            return addr.substring(addr.lastIndexOf("/")+1);
        }

    }

}

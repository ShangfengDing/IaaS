import appcloud.netty.remoting.common.SerializeType;
import appcloud.netty.remoting.exception.RemotingConnectionException;
import appcloud.netty.remoting.exception.RemotingSendRequestException;
import appcloud.netty.remoting.exception.RemotingTimeOutException;
import appcloud.netty.remoting.protocol.RemoteCommand;
import appcloud.netty.remoting.remote.NettyRemotingClient;
import appcloud.netty.remoting.remote.NettyRemotingServer;

/**
 * Created by lizhenhao on 2017/11/20.
 */
public class Client {


    public static void main(String[] args) throws InterruptedException, RemotingConnectionException, RemotingSendRequestException, RemotingTimeOutException {
        NettyRemotingClient client= new NettyRemotingClient();
        client.start();
        NettyRemotingServer server = new NettyRemotingServer();
        server.start();
        RemoteCommand remoteCommand = new RemoteCommand();
        remoteCommand.setCode(103);
        remoteCommand.setSerializeType(SerializeType.JSON);
        remoteCommand.setConsumHeader(new Header("hello"));
        client.invokeSync("localhost:8118",remoteCommand,1000000);

    }
}

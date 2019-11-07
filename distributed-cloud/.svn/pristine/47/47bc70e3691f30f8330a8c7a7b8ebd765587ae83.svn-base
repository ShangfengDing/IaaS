package appcloud.netty.remoting;

import appcloud.netty.remoting.exception.RemotingConnectionException;
import appcloud.netty.remoting.exception.RemotingSendRequestException;
import appcloud.netty.remoting.exception.RemotingTimeOutException;
import appcloud.netty.remoting.protocol.RemoteCommand;

/**
 * Created by lizhenhao on 2017/11/13.
 */
public interface RemoteClient extends RemoteService {

    public RemoteCommand invokeSync(String addr,RemoteCommand remoteCommand,long timeoutmillions) throws RemotingTimeOutException, InterruptedException, RemotingSendRequestException, RemotingConnectionException;

    public void invokeOneWay(String addr,RemoteCommand remoteCommand,long timeoutmillions) throws RemotingTimeOutException, InterruptedException, RemotingSendRequestException, RemotingConnectionException;



}

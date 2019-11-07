package com.distributed.dbproxy;

import appcloud.netty.remoting.remote.NettyRemotingServer;
import com.distributed.common.rpc.RPCServiceServer;
import com.distributed.dbproxy.constant.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author Idan
 * @date 2017/12/7
 *
 */
public class DbProxyApplication extends RPCServiceServer{

    private static final Logger logger = LoggerFactory.getLogger(DbProxyApplication.class);

    private static ApplicationContext context;

    public DbProxyApplication() {
        super(new NettyRemotingServer(Constants.SERVER_PORT),0, null);
    }

    static  {
        context = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
    }

    public static ApplicationContext getContext() {
        if (context == null) {
            context = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
        }
        return context;
    }

    public static Object getBean(String beanName) {
        return getContext().getBean(beanName);
    }

    @Override
    public void start() {
        logger.info("rpc server start……");
        super.start();
    }



    public static void main (String args[]) {
        DbProxyApplication application = new DbProxyApplication();
        application.start();
    }
}
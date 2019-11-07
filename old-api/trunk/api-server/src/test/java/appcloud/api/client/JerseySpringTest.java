package appcloud.api.client;

import org.junit.After;
import org.junit.Before;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.request.RequestContextListener;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.spi.spring.container.servlet.SpringServlet;
import com.sun.jersey.test.framework.JerseyTest;
import com.sun.jersey.test.framework.WebAppDescriptor;

/**
 * <p>
 * JersyTestNGTest
 * </p>
 * @version 1.0
 * @author Ameeth Paatil
 * @since Sep 13, 2011
 */
public class JerseySpringTest {
    private static JerseyTest jersyTest;
    
    public JerseySpringTest(String packageName) {
        jersyTest = new JerseyTest(new WebAppDescriptor.Builder(
            "packageName" ).contextPath( "" )
            .contextParam(
                "contextConfigLocation", "classpath:testManagerConfig.xml" )
            .servletClass( SpringServlet.class )
            .contextListenerClass( ContextLoaderListener.class )
            .requestListenerClass( RequestContextListener.class )
            .build() ) {

        };
    }

    public WebResource resource() {
        return jersyTest.resource();
    }

    public Client client() {
        return jersyTest.client();
    }


    @Before
    public void setUp()
        throws Exception {
        jersyTest.setUp();
    } 

    @After
    public void tearDown()
        throws Exception {
        jersyTest.tearDown();
    }
}
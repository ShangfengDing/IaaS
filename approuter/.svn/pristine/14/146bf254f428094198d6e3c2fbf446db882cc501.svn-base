/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package appcloud.approuter.common;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
/**
 *
 * @author huahui
 */
public class Constants {

    static {
        final Logger logger = Logger.getLogger("App configuration");
        logger.info("+++++++++++App configuration information++++++++++++");
        try {
            Properties p = new ConfigurationUtil().getPropertyFileConfiguration("app.properties");
            

        } catch (IOException e) {
            logger.fatal("Failed to init app configuration", e);
            throw new RuntimeException("Failed to init app configuration", e);
        }
        logger.info("----------App configuration successfully----------");
    }
}

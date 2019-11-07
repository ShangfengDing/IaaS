package appcloud.distributed.common;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zouji on 2018/1/11.
 */
public class ConfigurationUtil {
    final static Logger logger = Logger.getLogger(ConfigurationUtil.class);
    final static Pattern ENV_PATTERN = Pattern.compile("\\$\\{(.*?)\\}");
    String filePath;

    public Properties getPropertyFileConfiguration(String filePath) throws IOException {
        logger.info("Load configuration for " + filePath);
        this.filePath = filePath;
        Properties properties = new EnvWrappedProperties();
        properties.load(getClass().getClassLoader().getResourceAsStream(filePath));
        return properties;
    }

    class EnvWrappedProperties extends Properties {
        private EnvWrappedProperties() {super();}

        private String envString(String s) {
            String env;
            Matcher m = ENV_PATTERN.matcher(s);
            while (m.find()) {
                env = m.group(1);
                //repalce会escape blackslash(\) dollar($),将其转换
                s = m.replaceFirst(System.getProperty(env).replace("\\", "\\\\").replace("$", "\\$"));
                m = m.reset(s);
            }
            return s;
        }

        @Override
        public String getProperty(String key) {
            String s = super.getProperty(key);
            if (s == null) {
                throw new IllegalStateException("初始化属性" + key + "失败, 请在"
                        + filePath
                        + "中增加该属性的声明或者在Constants.java中删除载入该属性，否则，请检查属性名是否拼写错误!");
            }
            return envString(s);
        }

        @Override
        public String getProperty(String key, String defaultValue) {
            String s = super.getProperty(key);
            if (s == null) {
                logger.info("Use default value[" + defaultValue + "] for " + key);
                s = defaultValue;
            }
            return envString(s);
        }
    }
}

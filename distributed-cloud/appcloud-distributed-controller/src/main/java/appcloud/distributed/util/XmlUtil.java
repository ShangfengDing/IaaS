package appcloud.distributed.util;

import appcloud.distributed.configmanager.RouteInfo;
import com.distributed.common.utils.UuidUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lizhenhao on 2017/12/26.
 */
public class XmlUtil {

    protected final static Logger LOGGER = LoggerFactory.getLogger(XmlUtil.class);
    private final static String BROKER_RESOURCE_PATH = "/cloud_properties.xml";
    public final static String MASTER = "master";
    public final static String LOCAL = "slave";

    //xml属性名
    public final static String MASTERINFO = "MasterInfo";
    public final static String LOCALINFO = "LocalInfo";

    /**
     * 将xml文件解析成document类型
     * @param xml
     * @param xsd
     * @return
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    public static Document getDocument(InputSource xml, InputStream xsd)
            throws ParserConfigurationException,SAXException,IOException {
        Document doc = null;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        if (xsd != null) {
            dbf.setNamespaceAware(true);
        }
        DocumentBuilder builder = dbf.newDocumentBuilder();
        doc = builder.parse(xml);
        return doc;
    }

    public static Map<String,RouteInfo> getAllRouteInfo(String path) {
        try {
            InputStream stream = XmlUtil.class.getResourceAsStream(path);
            Document document = XmlUtil.getDocument(new InputSource(stream), null);
            RouteInfo master =  parseRouteInfo(document,MASTERINFO);
            RouteInfo slave = parseRouteInfo(document,LOCALINFO);
            Map<String,RouteInfo> map = new HashMap<String, RouteInfo>();
            map.put(MASTER,master);
            map.put(LOCAL,slave);
            return map;
        } catch (Exception e) {
            LOGGER.error("parse xml file error",e);
            System.exit(1);
            return null;
        }
    }

    /**
     * 获取master和local信息
     * @return
     */
    public static Map<String,RouteInfo> getAllRouteInfo() {
        return getAllRouteInfo(BROKER_RESOURCE_PATH);
    }



    public static RouteInfo parseRouteInfo(final Document document,final String type)
            throws IOException, ParserConfigurationException, SAXException {
        NodeList doc = document.getElementsByTagName(type);
        RouteInfo routeInfo = new RouteInfo();
        Element masterElement = (Element) doc.item(0);

        NodeList ipNode = masterElement.getElementsByTagName("Ip");
        NodeList portNode = masterElement.getElementsByTagName("Port");
        NodeList dataCenterNode = masterElement.getElementsByTagName("DataCenter");
        NodeList domainNode = masterElement.getElementsByTagName("Domain");

        String ip = ipNode.item(0).getTextContent();
        String port = portNode.item(0).getTextContent();
        String dataCenter = dataCenterNode.item(0).getTextContent();
        String domain = domainNode.item(0).getTextContent();


        routeInfo.setIpAddress(ip);
        routeInfo.setPort(port);
        routeInfo.setAddr(StringUtil.address(ip,port));
        routeInfo.setDataCenter(dataCenter);
        routeInfo.setDomainName(domain);

        //uuid到时候要换
        routeInfo.setUuid(UuidUtil.getRandomUuid());

        return routeInfo;
    }


}

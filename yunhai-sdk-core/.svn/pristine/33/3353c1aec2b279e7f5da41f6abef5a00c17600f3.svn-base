package appcloud.core.sdk.reader;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import appcloud.core.sdk.exceptions.ClientException;
import appcloud.core.sdk.utils.XmlUtils;

public class XmlReader implements Reader {

	private static Logger logger = Logger.getLogger(XmlReader.class);
	Map<String, String> map = new HashMap<String, String>();

	@Override
	public Map<String, String> read(String response, String endpoint)
			throws ClientException {
		// TODO Auto-generated method stub
		Element root;
		try {
			logger.info("response = " + response);
			logger.info("endpoint = " + endpoint);
			root = XmlUtils.getRootElementFromString(response);
			read(root, endpoint, false);
		}catch (ParserConfigurationException e) {
			new ClientException("SDK.InvalidXMLParse", e.toString());
		}catch (SAXException e) {
			new ClientException("SDK.InvalidXMLFormat", e.toString());
		} catch (IOException e) {
			new ClientException("SDK.InvalidContent", e.toString());
		}
		return map;
	}

	private void read(Element element, String path, boolean appendPath) {
		path = buildPath(element, path, appendPath);
		logger.info("path = " + path);
		List<Element> childElements = XmlUtils.getChildElements(element);
		if(childElements.size() == 0) {
			map.put(path, element.getTextContent());
			return;
		}
		List<Element> listElements = XmlUtils.getChildElements(element, childElements.get(0).getNodeName());
		logger.info("childElements size = " + childElements.size() + ": listElements size = " + listElements.size());
		if(listElements.size() > 1 && childElements.size() == listElements.size()) {
			elementsAsList(childElements, path);
		}else if(listElements.size() == 1 && childElements.size() ==1) {
			elementsAsList(listElements, path);
			read(childElements.get(0), path, true);
		}else {
			for(Element childElement : childElements) {
				read(childElement, path, true);
			}
		}
	}

	private String buildPath(Element element, String path, boolean appendPath) {
		// TODO Auto-generated method stub
		return appendPath ? path + "." + element.getNodeName() : path;
	}

	private void elementsAsList(List<Element> listElements, String path) {
		// TODO Auto-generated method stub
		map.put(path + ".Length", String.valueOf(listElements.size()));
		for(int i=0; i<listElements.size(); i++) {
			read(listElements.get(i), path + "[" + i + "]", false);
		}
	}
}

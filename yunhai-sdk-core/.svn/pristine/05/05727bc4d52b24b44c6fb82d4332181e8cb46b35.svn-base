package appcloud.core.sdk.regions;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import appcloud.core.sdk.exceptions.ClientException;
import appcloud.core.sdk.utils.XmlUtils;

public class InternalEndpointsParser implements IEndpointsProvider{

	private final static String BUNDLED_ENPOINTS_RESOURCE_PATH = "/endpoints.xml";

	public List<Endpoint> getEndpoints() throws ClientException {
		// TODO Auto-generated method stub
		InputStream stream = this.getClass().getResourceAsStream(BUNDLED_ENPOINTS_RESOURCE_PATH);
		try {
			return parseEndpoints(stream);
		}catch(IOException e) {
			throw new ClientException("SDK.MissingEndpointsFile", "Internal endpoints file is missing.");
		} catch (ParserConfigurationException e) {
			throw new ClientException("SDK.InvalidEndpointsFile", "Internal endpoints file is missing.");
		} catch (SAXException e) {
			throw new ClientException("SDK.EndpointsFileMalformed", "Internal endpoints file is missing.");
		}
	}

	//获取所有的endpoint
	private static List<Endpoint> parseEndpoints(final InputStream input) 
			throws IOException, ParserConfigurationException, SAXException {
		Document document = XmlUtils.getDocument(new InputSource(input), null);
		NodeList endpointNodes = document.getElementsByTagName("Endpoint");
		List<Endpoint> endpoints = new ArrayList<Endpoint>();
		for(int index1 = 0; index1 < endpointNodes.getLength(); index1++) {
			Element endpoint = (Element) endpointNodes.item(index1);
			Set<String> regionIds = new HashSet<String>();
			List<ProductDomain> products = new ArrayList<ProductDomain>();
			NodeList regionNodes = endpoint.getElementsByTagName("RegionId");
			NodeList productNodes = endpoint.getElementsByTagName("Product");
			for(int index2 = 0; index2 < regionNodes.getLength(); index2++) {
				regionIds.add(((Element)regionNodes.item(index2)).getTextContent());
			}
			for(int index3 = 0; index3 < productNodes.getLength(); index3++) {
				Element element = (Element)(productNodes.item(index3));
				NodeList productNames = element.getElementsByTagName("ProductName");
				NodeList zoneIds = element.getElementsByTagName("ZoneId");
				NodeList domainNames = element.getElementsByTagName("DomainName");
				for(int index4=0; index4<productNames.getLength(); index4++) {
					String productName = ((Element)productNames.item(index4)).getTextContent();
					String zoneId = ((Element)zoneIds.item(index4)).getTextContent();
					String domainName = ((Element)domainNames.item(index4)).getTextContent();
					products.add(new ProductDomain(productName,zoneId,domainName));
				}
			}
			endpoints.add(new Endpoint(endpoint.getAttribute("name"), regionIds, products));
		}
		return endpoints;
	}

}

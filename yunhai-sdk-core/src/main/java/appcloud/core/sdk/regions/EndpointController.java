package appcloud.core.sdk.regions;

import appcloud.core.sdk.exceptions.ClientException;
import appcloud.core.sdk.utils.XmlUtils;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by Idan on 2018/3/11.
 */
public class EndpointController implements IEndpointsProvider {

    private final static String BUNDLED_ENPOINTS_RESOURCE_PATH = "/endpoints.xml";
    private IEndpointsProvider endpointsProvider = null;

    @Override
    public List<Endpoint> getEndpoints() throws ClientException {
        InputStream stream = this.getClass().getResourceAsStream(BUNDLED_ENPOINTS_RESOURCE_PATH);
        try {
            Boolean switchRs = switchController(stream);
            if (!switchRs) {
                endpointsProvider = new InternalEndpointsParser();
            } else {
                endpointsProvider = new ExternalEndpointsProvider();
            }
            return endpointsProvider.getEndpoints();
        }catch(IOException e) {
            throw new ClientException("SDK.MissingEndpointsFile", "Internal endpoints file is missing.");
        } catch (ParserConfigurationException e) {
            throw new ClientException("SDK.InvalidEndpointsFile", "Internal endpoints file is missing.");
        } catch (SAXException e) {
            throw new ClientException("SDK.EndpointsFileMalformed", "Internal endpoints file is missing.");
        }

    }

    private Boolean switchController(final InputStream input)
            throws IOException, ParserConfigurationException, SAXException {
        Document document = XmlUtils.getDocument(new InputSource(input), null);
        NodeList controller = document.getElementsByTagName("ControllerEnable");
        System.out.println("controller:"+controller.item(0).getTextContent());
        String rs = controller.item(0).getTextContent();
        return Boolean.parseBoolean(rs);
    }

    public static void main (String args[]) {
        EndpointController controller = new EndpointController();
        try {
            System.out.println(controller.getEndpoints());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

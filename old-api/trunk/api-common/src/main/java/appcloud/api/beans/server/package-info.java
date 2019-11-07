@XmlSchema(
xmlns = {
	@XmlNs(namespaceURI = "http://docs.openstack.org/compute/api/v1.1", prefix = ""),
	@XmlNs(namespaceURI = "http://www.w3.org/2005/Atom", prefix = "atom"),
	@XmlNs(namespaceURI = "http://docs.openstack.org/compute/ext/flavor_access/api/v1.1", prefix = "os-flavor-access")
},
namespace = "http://docs.openstack.org/compute/api/v1.1",
elementFormDefault = XmlNsForm.QUALIFIED,
attributeFormDefault = javax.xml.bind.annotation.XmlNsForm.UNQUALIFIED)
package appcloud.api.beans.server;

import javax.xml.bind.annotation.XmlSchema;
import javax.xml.bind.annotation.XmlNsForm;
import javax.xml.bind.annotation.XmlNs;
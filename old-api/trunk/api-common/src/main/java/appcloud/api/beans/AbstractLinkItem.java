package appcloud.api.beans;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.UriBuilder;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlTransient;

public abstract class AbstractLinkItem {	
	
	//public List<Link> links = new ArrayList<Link>();
	
	@XmlElements(
			@XmlElement(name="link", type=Link.class, namespace="http://www.w3.org/2005/Atom")
	)
	public List<Link> getLinks() {
		List<Link> links = new ArrayList<Link>();
		try {
			links.add(getBookmark());
			links.add(getSelf());
		} catch (Exception e) {
			//e.printStackTrace();
		}
		
		return links;
	}
	
	public void setLinks(List<Link> links) {
		
	}
	
	private Link getBookmark() {
		Link link = new Link();
		link.rel = "bookmark";
		link.href = _getBookmark();
		
		return link;
	}
	
	@XmlTransient
	public Link getSelf() {
		Link link = new Link();
		link.rel = "self";
		link.href = _getBookmark();
		//link.href = _getSelf();
		
		return link;
	}
	
	protected UriBuilder getUriBuilder() {
		return UriBuilder.fromUri("http://api.free4lab.com/api");		
	}
		
	protected abstract URI _getBookmark();
	
	//protected abstract URI _getSelf();	
}

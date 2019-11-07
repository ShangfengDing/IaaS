package appcloud.vmcontroller;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import appcloud.rpc.tools.RpcException;

public class SnapshotConfig {
    public String snapshotName;  
    private Document domainXml;
    private Element domain;
    
    
    /**
     * 构造初始配置文件：
     * @param instance
     * @param instanceType
     * @throws RpcException 
     */
    public SnapshotConfig(String snapshotName){

    	this.snapshotName = snapshotName;
    	this.domainXml = DocumentHelper.createDocument();
    	this.domain = domainXml.addElement("domainsnapshot");
		domain.addElement("name").setText(snapshotName);
        domain.addElement("disks");      
    }
    
    /**
     * 生成xml描述文件：
     * 参数解析、流程控制
     */
    public String generateDesXML() {
    	return domainXml.asXML();
    }

public static void main(String[] args) {
	System.out.println(new SnapshotConfig("haha").generateDesXML());
}
    
    

}

package appcloud.vmcontroller.virt;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import appcloud.common.model.VmSecurityGroup;
import appcloud.common.model.VmSecurityGroupRule;
import appcloud.vmcontroller.util.VMCUtil;

public class NetworkFilterConfig {

    static final Logger logger = Logger.getLogger(NetworkFilterConfig.class);
    private Document domainXml = null;
    private Element filter = null;
    
    private String name = null; //VmSecurityGroup的name
    
    private List<RuleConfig> ruleConfigs = new ArrayList<RuleConfig>();

    public NetworkFilterConfig(VmSecurityGroup securityGroup) {
    	String nfName = VMCUtil.generateName(securityGroup.getUuid());
    	this.name = nfName;
    }
    
    public String generateFilterConf(List<VmSecurityGroupRule> SGRules){
    	//1. create rules
    	createRules(SGRules);
    	
    	//2. 准备好数据，创建xml
        formatBasicProp();
        
        return domainXml.asXML();
    }
    
    private void formatBasicProp() {
    	//1. create basic xml
        domainXml = DocumentHelper.createDocument();
        filter = domainXml.addElement("filter");
        filter.addAttribute("name", name);
        
        //2. create default filter reference
        formatDefaultFilterref();
      
        //3. create rules
        if (ruleConfigs != null && ruleConfigs.size() > 0) {
            formatRule();
        }
        
        //4. create default drop rules
        formatDefaultEndRule();
    }

    /**
     * 生成rule配置
     */
    private void formatRule() {
        logger.debug("start create the rule of the nwfilter!");
        for (RuleConfig rc : ruleConfigs) {
        	/*
        	 *   <rule action='accept' direction='inout' priority='500'>
    				<tcp dstportstart='8080'/>
  				 </rule>
        	 * 
        	 */
        	Element rule = filter.addElement("rule").addAttribute("action", "accept")
        		   								    .addAttribute("direction", rc.getDirection());
        	Element protocol = rule.addElement(rc.getProtocol());
        	if(rc.getDstportStart() != null){
        		protocol.addAttribute("dstportstart", rc.getDstportStart().toString());
        	}
        	if(rc.getDstportEnd() != null){
        		protocol.addAttribute("dstportend", rc.getDstportEnd().toString());
        	}
        	if(rc.getSrcIpFrom() != null){
        		protocol.addAttribute("srcipfrom", rc.getSrcIpFrom());
        	}
        	if(rc.getSrcIpTo() != null){
        		protocol.addAttribute("srcipto", rc.getSrcIpTo());
        	}
        }

    }
    
    /**
     * default filter reference    
     */
    private void formatDefaultFilterref() {
        filter.addElement("filterref").addAttribute("filter", "no-ip-spoofing");
        filter.addElement("filterref").addAttribute("filter", "no-arp-spoofing");
    }
    
    private void formatDefaultEndRule() {
        
        //0.允许3389端口 -- win远程桌面要用到3389
        filter.addElement("rule").addAttribute("action", "accpet").addAttribute("direction", "inout")
              .addElement("tcp").addAttribute("dstportstart", "3389");
        
    	//1.允许对外所有的主动访问
    	filter.addElement("rule").addAttribute("action", "accept").addAttribute("direction", "out")
    		  .addElement("all").addAttribute("state", "NEW,ESTABLISHED,RELATED,INVALID");

    	//2.允许DHCP访问
    	filter.addElement("rule").addAttribute("action", "accept").addAttribute("direction", "in")
    		  .addElement("udp").addAttribute("srcportstart", "67");
    	
    
    	//3.可以允许服务器主动连接的入流量——如果不启用，虚拟机对外连接将无法受到ack+syn(第二次握手)
    	filter.addElement("rule").addAttribute("action", "accept").addAttribute("direction", "in")
		  	  .addElement("all").addAttribute("state", "ESTABLISHED,RELATED");
        
    	//最后，drop所有其他包
        filter.addElement("rule").addAttribute("action", "drop").addAttribute("direction", "in")
        	  .addElement("all");
    }
    
    /**
     * create rule
     * @param SGRules
     * @return
     */
    private List<RuleConfig> createRules(List<VmSecurityGroupRule> SGRules) {
        if (ruleConfigs == null) {
        	ruleConfigs = new ArrayList<RuleConfig>();
        }
    	
        for (VmSecurityGroupRule rule : SGRules) {
            ruleConfigs.add(new RuleConfig(rule));
        }
        
        return ruleConfigs;
    }

    public Document getDomainXml() {
        return domainXml;
    }

    public void setDomainXml(Document domainXml) {
        this.domainXml = domainXml;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<RuleConfig> getRuleConfigs() {
        return ruleConfigs;
    }

    public void setRuleConfigs(List<RuleConfig> ruleConfigs) {
        this.ruleConfigs = ruleConfigs;
    }

}

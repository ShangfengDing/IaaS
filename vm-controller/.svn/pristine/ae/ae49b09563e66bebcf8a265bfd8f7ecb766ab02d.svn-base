package appcloud.vmcontroller.virt;

import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import appcloud.vmcontroller.VMControllerConf;

public class NwFilterConfig {

    static final Logger logger = Logger.getLogger(NwFilterConfig.class);
    private VMControllerConf conf = VMControllerConf.getInstance();
    private Document domainXml = null;
    private String name = null; //是VmSecurityGroup的name
    private List<RuleConfig> ruleConfigs = null;

    public String getNwFilterConf(){
        formatBasicProp();
        return domainXml.asXML();
    }
    
    private void formatBasicProp() {
        domainXml = DocumentHelper.createDocument();
        Element filter = domainXml.addElement("filter");
        filter.addAttribute("name", name);
        Element filterref = filter.addElement("filterref");
        filterref.addAttribute("filter", conf.getFilterRefName());
        Element ruleStart = filter.addElement("rule");
        ruleStart.addAttribute("action", conf.getPort22Action()).addAttribute("direction", "in");
        ruleStart.addElement("tcp").addAttribute("dstportstart", "22");
      
        if (ruleConfigs != null && ruleConfigs.size() > 0) {
            formatRule(filter);
        }
        Element ruleEnd = filter.addElement("rule");
        ruleEnd.addAttribute("action", "drop").addAttribute("direction", "inout").addElement("all");
        logger.debug(domainXml.asXML());
    }

    /**
     * 生成rule相关配置
     */
    private void formatRule(Element filter) {
        logger.debug("start create the rule of the nwfilter!");
        for (RuleConfig ruleConfig : ruleConfigs) {
           
            if (ruleConfig.getProtocol() != null) {
                if (ruleConfig.getDstportStart() != null) {
                   filter.addElement("rule").addAttribute("acction", "accept")
                                 .addAttribute("direction", ruleConfig.getDirection())
                                 .addElement(ruleConfig.getProtocol())
                                 .addAttribute("srcportstart", ruleConfig.getDstportStart())
                                 .addAttribute("srcipfrom", ruleConfig.getSrcIpFrom())
                                 .addAttribute("srcipto", ruleConfig.getSrcIpTo());

                }
            }

        }

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

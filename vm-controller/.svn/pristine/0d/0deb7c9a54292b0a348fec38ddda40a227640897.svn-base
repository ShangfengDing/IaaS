package appcloud.vmcontroller.impl;

import org.apache.log4j.Logger;
import org.libvirt.Connect;
import org.libvirt.LibvirtException;

import appcloud.vmcontroller.VMControllerConf;

public class VMCservice {
	public static Connect virtCon = null;

    static final Logger logger = Logger.getLogger(VMControllerImpl.class.getName());
    private VMControllerConf conf = VMControllerConf.getInstance();

    public VMCservice(){
        if (virtCon == null) {
            String conStr = conf.getVirConnStr();
            try {
				virtCon = new Connect(conStr);
			} catch (LibvirtException e) {
			    logger.info(e.getMessage());
			}
        }
    }
    
    
}

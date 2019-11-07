package appcloud.vmcontroller.virt;

import java.util.ArrayList;


public class test {
	private static LibvirtConfig tt = new LibvirtConfig();
	
	public static void main(String[] args){
		tt.uuid = "12345";
		tt.name = "12345";
		tt.vcpus = 2;
		tt.memory = 100;
//		Document d = DocumentHelper.createDocument();
		tt.formatBasicProp();
		
		tt.osType = "hvm";
		tt.osBootDev.add("hd");
		tt.osBootDev.add("cdrom");
		tt.formatOs();
		
		
		tt.formatFeatures();
		
		tt.setClock();
		System.out.print(tt.getStringConfig()+"\n");
		
		String ss = "192.168.13.1:/nfs/list:/home/image/image.xx";
		String[] a = ss.split(":", 3);
		for (int i = 0; i < a.length; i++)
			System.out.print(a[i]+"\n");
		
		String[] b = ss.split("/");
		String fs = a[1] + "/" + b[b.length - 1];
		System.out.print(fs+"\n");
		
		
	}

}

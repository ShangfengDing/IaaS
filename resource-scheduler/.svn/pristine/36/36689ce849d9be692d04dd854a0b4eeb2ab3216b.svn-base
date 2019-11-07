/**
 * File: TestVolumeService.java
 * Author: weed
 * Create Time: 2013-4-24
 */
package appcloud.resourcescheduler.service;

import java.util.Scanner;

import appcloud.resourcescheduler.service.impl.VolumeServiceImpl;
import appcloud.rpc.tools.RpcException;

/**
 * @author weed
 *
 */
public class TestVolumeService {

	/**
	 * @param args
	 * @throws RpcException 
	 */
	public static void main(String[] args) throws RpcException {
		VolumeService volumeService = new VolumeServiceImpl();
		volumeService.attachVolume("4224af6ebcc64bbea74a73e4ad878a80", "966765273c9040008e33a817fb3e451e", null);
		System.out.println("attachVolume done!");
		
		volumeService.detachVolume("4224af6ebcc64bbea74a73e4ad878a80", "966765273c9040008e33a817fb3e451e", null);
		System.out.println("detachVolume done!");
	}

}

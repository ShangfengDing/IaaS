package appcloud.api.client;

import java.util.Date;
import java.util.HashMap;

import appcloud.api.beans.*;

public class TestException {

	static void print(String str) {
		System.out.println(str);
	}
	
	static void print(Boolean b) {
		System.out.println(b);
	}
	static void testBool() {
		print(String.valueOf(true));
		print(String.valueOf(false));
		print(Boolean.valueOf("true"));
		print(Boolean.valueOf("ttt"));
	}
	
	static void print(Object obj) {
		System.out.println(obj);
	}
	
	static void testAggregate() {
		AggregateClient client = new AggregateClient();
		Aggregate aggregate = client.get("110", 987);
		if(aggregate == null) {
			System.out.println("NULL !!!!!!");
		}
		else
			System.out.println(aggregate.name);
	}
	
	static void testFlavor() {
		FlavorClient client = new FlavorClient();
		Flavor flavor = client.get("110", 987);
		if(flavor == null) {
			System.out.println("NULL !!!!!!");
		}
		else
			System.out.println(flavor.name);
	}
	
	static void testHost() {
		HostClient client = new HostClient();
		Host flavor = client.get("110", "fswerfawf");
		if(flavor == null) {
			System.out.println("NULL !!!!!!");
		}
		else
			System.out.println(flavor.hostName);
	}
	

	static void testImage() {
		ImageClient client = new ImageClient();
		Image img = client.get("110", "faqwera");
		if(img == null) {
			System.out.println("NULL !!!!!!");
		}
		else
			System.out.println(img.name);
	}
	
	static void testSecurityGroup() {
		SecurityGroupClient client = new SecurityGroupClient();
		SecurityGroup group = client.get("110", 999);
		if(group == null) {
			System.out.println("NULL !!!!!!");
		}
		else
			System.out.println(group.name);
	}

	static void testSnapshot() {
		SnapshotClient client = new SnapshotClient();
		Snapshot group = client.get("110", "999");
		if(group == null) {
			System.out.println("NULL !!!!!!");
		}
		else
			System.out.println(group.displayName);
	}
	
	static void testVolumeAttachment() {
		VolumeAttachmentClient client = new VolumeAttachmentClient();
		VolumeAttachment attachment = client.get("110", "ssss", "ttrewe");
		if(attachment == null) {
			System.out.println("NULL !!!!!!");
		}
		else
			System.out.println(attachment.volumeId);
	}
	
	static void testVolume() {
		VolumeClient client = new VolumeClient();
		Volume volume = client.get("110", "ttrewe");
		if(volume == null) {
			System.out.println("NULL !!!!!!");
		}
		else
			System.out.println(volume.displayDescription);
	}
}

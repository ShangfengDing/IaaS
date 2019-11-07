package appcloud.common.util.io;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class NFSUtilTest {

//	@Test
	public void testMount() throws IOException {
		String host = "10.108.123.17";
		String path = "images/test.img";
		NFSUtil.umount(host);
		assertEquals(false, NFSUtil.fileExists(host, path));
		assertEquals(false, NFSUtil.isMounted(host));
		assertEquals(true, NFSUtil.mount(host, NFSUtil.DEFAULT_NFS_SRV_PATH));
		assertEquals(true, NFSUtil.isMounted(host));
		assertEquals(true, NFSUtil.fileExists(host, path));
		
		assertEquals(false, NFSUtil.mount(host, NFSUtil.DEFAULT_NFS_SRV_PATH));
		assertEquals(true, NFSUtil.umount(host));
		assertEquals(false, NFSUtil.isMounted(host));
		
		
	}

}

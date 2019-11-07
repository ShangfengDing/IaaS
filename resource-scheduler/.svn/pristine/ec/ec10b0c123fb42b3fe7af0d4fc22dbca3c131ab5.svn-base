/**
 * File: TestPrintstacktrace.java
 * Author: weed
 * Create Time: 2013-6-23
 */
package appcloud.resourcescheduler.impl;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import appcloud.common.errorcode.VMSEC;

/**
 * @author weed
 *
 */
public class TestPrintstacktrace {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
        Exception e = new Exception();
		
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw); 
		e.printStackTrace(pw);
		pw.flush();
		pw.close();
		
		System.out.println(sw.toString());
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(baos);
		e.printStackTrace(ps);
		ps.flush();
		ps.close();
		
		System.out.println(baos.toString());
	}

}

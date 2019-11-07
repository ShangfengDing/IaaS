/**
 * File: ErrorCodeTest.java
 * Author: weed
 * Create Time: 2012-11-26
 */
package appcloud.common.errorcode;

/**
 * @author weed
 * 
 */
public class ErrorCodeTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		System.out.println(
				ErrorCode.getMessage(AREC.NO_ERROR));
		System.out.println(
				ErrorCode.getMessage(AREC.SRC_NOT_EXIST));
		System.out.println(
				ErrorCode.getMessage(AREC.DST_NOT_EXIST));
		System.out.println(
				ErrorCode.getMessage(AREC.RE_EXISTED));
	}

}

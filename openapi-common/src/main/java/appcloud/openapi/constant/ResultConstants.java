package appcloud.openapi.constant;


/**
 * 本类中定义了接口的一些返回参数字段
 * @author hgm
 */
public class ResultConstants {

	public final static String TOTAL_COUNT = "TotalCount"; //查询出的对象列表的总数
	public final static String PAGE_NUMBER = "PageNumber"; //当前显示的页码
	public final static String PAGE_SIZE = "PageSize";     //每页显示的对象总数
	public final static String INSTANCE_STATUS_SET = "InstanceStatusSet";  // 云主机实例状态信息
    public static final String IMAGE_DETAIL_ITEM = "ImageDetailItem"; //镜像具体状态
    public static final String IMAGE_LIST = "ImageList"; //镜像的列表
    public static final String IMAGE_DETAIL_SET = "imageDetailSet"; //镜像信息
    public static final String INSTANCE_MONITOR_SET = "instanceMonitorSet"; //监控信息
	
}

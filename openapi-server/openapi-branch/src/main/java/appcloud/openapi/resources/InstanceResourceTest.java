package appcloud.openapi.resources;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import appcloud.openapi.constant.Constants;
import appcloud.openapi.response.BaseResponse;


@RestController
public class InstanceResourceTest extends BaseController{
	
	private static Logger logger = Logger.getLogger(InstanceResourceTest.class);
	
	@RequestMapping(method = RequestMethod.GET,  params="Action=test1" , produces = { "application/json"})
	@ResponseBody
	public BaseResponse CreateInstance(@RequestParam(value = Constants.ZONE_ID, required = false) String zoneId) throws JSONException {
		System.out.println(11);
		return new BaseResponse("11111111");
	}

	@RequestMapping(method = RequestMethod.GET,  params="Action=test2", produces = { "application/xml" })
	@ResponseBody
	public BaseResponse ShutdownInstance(@RequestParam(value = Constants.ZONE_ID, required = false) String zoneId) {
		System.out.println(22);
		return new BaseResponse("2222222222","error", "losing parameter");
	}
	
	@RequestMapping(method = RequestMethod.GET,  params="Action=test3" , produces = { "application/json","application/xml"})
	@ResponseBody
	public BaseResponse StartInstance(@RequestParam(value = Constants.ZONE_ID, required = false) String zoneId) {
		System.out.println(22);
		response.setStatus(400); 
		return new BaseResponse("2222222222");
	}
	
}

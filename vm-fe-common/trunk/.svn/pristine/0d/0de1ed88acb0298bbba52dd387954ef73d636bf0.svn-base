package com.appcloud.vm.fe.util;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.StrutsStatics;
import org.apache.struts2.json.JSONResult;
import org.apache.struts2.json.JSONUtil;
import org.codehaus.jackson.map.ObjectMapper;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.util.ValueStack;

/**
 * @author jianglei
 *
 */
public class JacksonJsonResult extends JSONResult{
	private static final long serialVersionUID = 123123122L;
	private static ObjectMapper mapper = new ObjectMapper();

	public void execute(ActionInvocation invocation) throws Exception {
		ActionContext actionContext = invocation.getInvocationContext();
        HttpServletRequest request = (HttpServletRequest) actionContext.get(StrutsStatics.HTTP_REQUEST);
        HttpServletResponse response = (HttpServletResponse) actionContext.get(StrutsStatics.HTTP_RESPONSE);

        try {
            String json;
            Object rootObject;
            if (this.isEnableSMD()) {
                // generate SMD
                //此处原来是rootObject = this.writeSMD(invocation);
            	//在struts2的2.1.8.1版本的JSONResult.class中有writeSMD方法，但struts2版本升至2.3.15.1时，
            	//JSONResult.class用buildSMDObject方法代替了writeSMD
                rootObject = this.buildSMDObject(invocation);
            } else {
                // generate JSON
                if (this.getRoot() != null) {
                    ValueStack stack = invocation.getStack();
                    rootObject = stack.findValue(this.getRoot());
                } else {
                    rootObject = invocation.getAction();
                }
            }
            json = mapper.writeValueAsString(rootObject);
            json = addCallbackIfApplicable(request, json);

            boolean writeGzip = JSONUtil.isGzipInRequest(request);

            writeToResponse(response, json, writeGzip);

        } catch (IOException exception) {
            throw exception;
        }
	}
	
	

}

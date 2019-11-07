package appcloud.admin.action.user;

import appcloud.admin.action.base.BaseAction;
import appcloud.api.client.AcUserClient;

import com.appcloud.vm.fe.util.ClientFactory;
import org.apache.struts2.ServletActionContext;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class UserListAction extends BaseAction{
	private static final long serialVersionUID = -8978894090535415113L;
	private AcUserClient acuserClient = ClientFactory.getAcUserClient();
	Long userNum;

	public String execute() {
		userNum = acuserClient.count(null, null ,null, null);
		return SUCCESS;

	}
	public void count(){
		try{
			userNum = acuserClient.count(null, null ,null, null);
			HttpServletResponse responses = ServletActionContext.getResponse();
			responses.setCharacterEncoding("utf-8");
			PrintWriter writer = responses.getWriter();
			writer.write(userNum.toString());
			writer.flush();
			writer.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Long getUserNum() {
		return userNum;
	}

	public void setUserNum(Long userNum) {
		this.userNum = userNum;
	}

}

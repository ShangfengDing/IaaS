package appcloud.openapi.response;

import appcloud.openapi.datatype.AppkeyItem;
import appcloud.openapi.datatype.UserDetailItem;
import appcloud.openapi.datatype.VmUserItem;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

//import lombok.Data;

/**
 * Created by Idan on 2017/10/19.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "CreateUserForDisResponse")
@Data
public class UserCreateForDisResponse extends BaseResponse {

    private AppkeyItem appkeyItem;
    private VmUserItem vmUserItem;

    public UserCreateForDisResponse() {}

    public UserCreateForDisResponse(String requestId, String errorCode, String message) {
        super(requestId, errorCode, message);
    }

    public UserCreateForDisResponse(String requestId, String errorCode, String message, VmUserItem vmUserItem, AppkeyItem appkeyItem) {
        super(requestId, errorCode, message);
        this.vmUserItem = vmUserItem;
        this.appkeyItem = appkeyItem;
    }
}

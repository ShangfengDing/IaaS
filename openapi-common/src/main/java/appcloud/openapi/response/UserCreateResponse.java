package appcloud.openapi.response;

import appcloud.openapi.datatype.UserDetailItem;
import lombok.Data;
//import lombok.Data;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Idan on 2017/10/19.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "CreateUserResponse")
@Data
public class UserCreateResponse extends BaseResponse {

    private UserDetailItem userDetailItem;

    public UserCreateResponse() {}

    public UserCreateResponse(String requestId, String errorCode, String message) {
        super(requestId, errorCode, message);
    }

    public UserCreateResponse(String requestId, String errorCode, String message, UserDetailItem userDetailItem) {
        super(requestId, errorCode, message);
        this.userDetailItem = userDetailItem;
    }
}

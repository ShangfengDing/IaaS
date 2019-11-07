package appcloud.openapi.response;

import appcloud.openapi.datatype.GroupDetailItem;
import appcloud.openapi.datatype.UserDetailItem;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Idan on 2017/10/19.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "CreateGroupResponse")
@Data
public class GroupCreateResponse extends BaseResponse {

    private GroupDetailItem groupDetailItem;

    public GroupCreateResponse() {}

    public GroupCreateResponse(String requestId, String errorCode, String message) {
        super(requestId, errorCode, message);
    }

    public GroupCreateResponse(String requestId, String errorCode, String message, GroupDetailItem groupDetailItem) {
        super(requestId, errorCode, message);
        this.groupDetailItem = groupDetailItem;
    }
}

package appcloud.core.sdk.auth;

import java.util.Calendar;
import java.util.Date;
/**
 * 安全证书信息，没有用户对应一个
 * @author hegui
 *
 */
public class Credential {

	private Date refreshDate;
	private Date expiredDate;
	private String appkeyId;
	private String appkeySecret;
	private String securityToken;
	public Credential() {
		this.refreshDate = new Date();
	}

	public Credential(String keyId, String keySecret) {
		this.appkeyId = keyId;
		this.appkeySecret = keySecret;
		this.refreshDate = new Date();
	}

	public Credential(String keyId, String keySecret, int expiredHours) {
		this.appkeyId = keyId;
		this.appkeySecret = keySecret;
		this.refreshDate = new Date();
		setExpiredDate(expiredHours);
	}

	public boolean isExpired() {
		if(null == this.expiredDate) {
			return false;
		}
		if(this.expiredDate.after(new Date())) {
			return false;
		}
		return true;
	}
	public Date getRefreshDate() {
		return refreshDate;
	}

	public void setRefreshDate(Date refreshDate) {
		this.refreshDate = refreshDate;
	}

	public Date getExpiredDate() {
		return expiredDate;
	}

	public void setExpiredDate(int expiredHours) {
		if(expiredHours > 0) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			cal.add(Calendar.HOUR, expiredHours);
		}
	}

	public String getAppkeyId() {
		return appkeyId;
	}

	public void setAppkeyId(String appkeyId) {
		this.appkeyId = appkeyId;
	}

	public String getAppkeySecret() {
		return appkeySecret;
	}

	public void setAppkeySecret(String appkeySecret) {
		this.appkeySecret = appkeySecret;
	}

	public String getSecurityToken() {
		return securityToken;
	}

	public void setSecurityToken(String securityToken) {
		this.securityToken = securityToken;
	}
}

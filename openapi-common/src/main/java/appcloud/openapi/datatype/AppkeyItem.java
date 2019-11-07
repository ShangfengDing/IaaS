package appcloud.openapi.datatype;

import lombok.Data;

/**
 * Created by Idan on 2018/1/15.
 */

@Data
public class AppkeyItem {

    private Integer id;

    private Integer userId;

    private String userEmail;

    private String appkeyId;

    private String appkeySecret;

    private String provider;

    private String appname;

    private String region;

    private String zone;

    private String appcloudUserEmail;

    private Integer state;

    public AppkeyItem(){}

    public AppkeyItem(Integer id, Integer userId, String userEmail, String appkeyId, String appkeySecret, String provider, String appname,
                      String region, String zone, String appcloudUserEmail, Integer state) {
        this.id = id;
        this.userId = userId;
        this.userEmail = userEmail;
        this.appkeyId = appkeyId;
        this.appkeySecret = appkeySecret;
        this.provider = provider;
        this.appname = appname;
        this.region = region;
        this.zone = zone;
        this.appcloudUserEmail = appcloudUserEmail;
        this.state = state;
    }
}

package appcloud.distributed.zookeeper;

import lombok.Data;

/**
 * Created by Idan on 2018/5/7.
 */
@Data
public class ServerInfo {

    private String path;
    private String name;
    private String loadBalance;

}

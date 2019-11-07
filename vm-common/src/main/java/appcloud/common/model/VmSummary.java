package appcloud.common.model;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * @author lin
 * 基础设施总表
 * 2018/7/20
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@jid")
public class VmSummary {
    private Integer id;
    private String uuid;
    private String name;                //设备名称
    private VmSummaryTypeEnum type;       //类型：服务器，存储设备，网络设备，非受控虚拟机
    private String model;               //型号与规格
    private String position;            //位置
    private String description;         //备注

    public VmSummary(){super();}

    public VmSummary(Integer id,String uuid, String name, VmSummaryTypeEnum type, String model, String position, String description) {
        super();
        this.id = id;
        this.uuid = uuid;
        this.name = name;
        this.type = type;
        this.model = model;
        this.position = position;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public VmSummaryTypeEnum getType() {
        return type;
    }

    public void setType(VmSummaryTypeEnum type) {
        this.type = type;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "VmSummary{" +
                "id=" + id +
                ", uuid=" + uuid +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", model='" + model + '\'' +
                ", position='" + position + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public static enum VmSummaryTypeEnum {
        DISKDEVICE,NETDEVICE,OTHER;
        public String toString(){
            switch (this){
                case NETDEVICE:
                    return "存储";
                case DISKDEVICE:
                    return "网络设备";
                case OTHER:
                    return "其他";
            }
            return super.toString();
        }
    }
}
//('SERVICE','DISKDEVICE','NETDEVICE','UNCONTROLLEDVM')

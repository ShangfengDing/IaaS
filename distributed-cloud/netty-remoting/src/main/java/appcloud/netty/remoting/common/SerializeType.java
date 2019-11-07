package appcloud.netty.remoting.common;

/**
 * Created by lizhenhao on 2017/11/7.
 */
public enum  SerializeType {
    JSON((byte)0),
    OTHERS((byte)1),
    STREAM((byte)2);

    private byte code;


    SerializeType(byte code) {
        this.code = code;
    }

    public static SerializeType valueOf(byte code) {
        for(SerializeType serializeType : SerializeType.values()) {
            if (serializeType.code == code){
                return serializeType;
            }
        }
        return null;
    }

    public static byte value(SerializeType serializeType) {
        if (serializeType == JSON) {
            return 0;
        } else if (serializeType == OTHERS) {
            return 1;
        } else if (serializeType == STREAM) {
            return 2;
        } else {
            return -1;
        }
    }
}

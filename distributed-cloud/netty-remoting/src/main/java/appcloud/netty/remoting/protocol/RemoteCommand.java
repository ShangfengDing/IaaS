package appcloud.netty.remoting.protocol;

import appcloud.netty.remoting.common.ConsumerHeader;
import appcloud.netty.remoting.common.RequestCode;
import appcloud.netty.remoting.common.ResponseCode;
import appcloud.netty.remoting.common.SerializeType;
import appcloud.netty.remoting.util.JsonUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by lizhenhao on 2017/11/7.
 */
public class RemoteCommand implements Serializable {

    private static final Long serialVersionUID = 1L;

    protected final static Logger LOGGER = LoggerFactory.getLogger(RemoteCommand.class);

    public static AtomicInteger id = new AtomicInteger(0);

    public static ConcurrentHashMap<Class<? extends ConsumerHeader>,Field[]> CLASS_FIELD = new ConcurrentHashMap();

    /**
     * reflect to get field's basic type
     */
    public static HashMap<Class<?>,String> CANONICAL_NAME_CACHE = new HashMap<Class<?>, String>();
    private static final String STRING_CANONICAL_NAME = String.class.getCanonicalName();
    private static final String DOUBLE_CANONICAL_NAME_1 = Double.class.getCanonicalName();
    private static final String DOUBLE_CANONICAL_NAME_2 = double.class.getCanonicalName();
    private static final String INTEGER_CANONICAL_NAME_1 = Integer.class.getCanonicalName();
    private static final String INTEGER_CANONICAL_NAME_2 = int.class.getCanonicalName();
    private static final String LONG_CANONICAL_NAME_1 = Long.class.getCanonicalName();
    private static final String LONG_CANONICAL_NAME_2 = long.class.getCanonicalName();
    private static final String BOOLEAN_CANONICAL_NAME_1 = Boolean.class.getCanonicalName();
    private static final String BOOLEAN_CANONICAL_NAME_2 = boolean.class.getCanonicalName();
    private static final String LIST_CANNOICAL_NAME = List.class.getCanonicalName();

    public final static int RPC_TYPE = 0;
    public final static int RPC_ONEWAY = 1;


    /**
     * each requestId is different
     */
    private int requestId = id.getAndIncrement();

    /**
     * operation code
     */
    private int code;

    /**
     * request or response flag
     */
    private int flag = 0;

    /**
     * body is not to be serialized
     */
    private transient byte[] body;

    /**
     * how to Serialize
     *
     */
    @JsonIgnore
    private transient SerializeType serializeType;

    /**
     * consumer header
     */
    private transient ConsumerHeader consumerHeader;

    /**
     * use to store consumer header
     */
    private HashMap<String,Object> extFileds;
    /**
     * use to find server cause
     */
    private String remark;


    /**
     * create request remotCommand
     * @param code
     * @param type
     * @param consumHeader
     * @return
     */
    public static RemoteCommand createRequestRemoteCommand(int code,SerializeType type,ConsumerHeader consumHeader) {
        RemoteCommand remoteCommand = new RemoteCommand();
        remoteCommand.setCode(code);
        remoteCommand.setConsumHeader(consumHeader);
        remoteCommand.setSerializeType(type);
        return remoteCommand;
    }

    public static RemoteCommand createReponseRemoteCommand(int code,String remark) {
        return createReponseRemoteCommand(code,remark,SerializeType.JSON);
    }

    public static RemoteCommand createReponseRemoteCommand(int code,String remark,SerializeType serializeType) {
        RemoteCommand remoteCommand = new RemoteCommand();
        remoteCommand.setCode(code);
        remoteCommand.setRemark(remark);
        remoteCommand.setSerializeType(serializeType);
        return remoteCommand;
    }

    public static RemoteCommand createResponseRemoteCommand(int code, String remark, SerializeType serializeType, ConsumerHeader consumerHeader) {
        RemoteCommand remoteCommand = new RemoteCommand();
        remoteCommand.setCode(code);
        remoteCommand.setRemark(remark);
        remoteCommand.setSerializeType(serializeType);
        remoteCommand.setConsumHeader(consumerHeader);
        return remoteCommand;
    }

    public static RemoteCommand createDefaultRemoteCommand() {
        return createReponseRemoteCommand(RequestCode.DEFAULT, ResponseCode.DEFAULT);
    }

    public static RemoteCommand createErrorRemoteCommand() {
        return createReponseRemoteCommand(RequestCode.SYSTEM_ERROR, ResponseCode.ERROR);
    }

    public static RemoteCommand createSuccessRemoteCommand() {
        return RemoteCommand.createReponseRemoteCommand(RequestCode.SUCCESS, ResponseCode.SUCCESSED);
    }

    /**
     * judge flag type
     * @return
     */
    public int markResponseFlag() {
        int bits = 1 << RPC_TYPE;
        return this.flag |= bits;
    }
    public int markOneWayFlag() {
        int bits = 1 << RPC_ONEWAY;
        return this.flag |= bits;
    }

    public boolean responseType() {
        int bits = 1 << RPC_TYPE;
        return (this.flag & bits) == bits;
    }

    public boolean oneWayType() {
        int bits = 1 << RPC_ONEWAY;
        return (this.flag & bits) == bits;
    }
    /**
     * encode remoteCommand
     * @return
     */
    public ByteBuffer encode() {
        int length = 0;
        byte[] headData;
        headData = encodeHeaderData();

        //head length
        length += headData.length;
        //4 is for headData or bodyData's length
        length += 4;
        if (this.body != null) {
            length += body.length;
        }
        //4 is for sum length's bit number
        ByteBuffer byteBuffer = ByteBuffer.allocate(length+4);

        byteBuffer.putInt(length);
        byteBuffer.put(markSerializeType(headData.length,this.serializeType));
        byteBuffer.put(headData);
        if (this.body != null) {
            byteBuffer.put(body);
        }
        byteBuffer.flip();
        return byteBuffer;
    }


    /**
     * make serializeType as top bit position
     * @param length
     * @param serializeType
     * @return
     */
    private byte[] markSerializeType(int length,SerializeType serializeType) {
        byte[] result = new byte[4];

        result[0] = (byte) (SerializeType.value(serializeType)&0xFF);
        result[1] = (byte) ((length >> 16)&0xFF);
        result[2] = (byte) ((length >> 8)&0xFF);
        result[3] = (byte) ((length)&0xFF);
        return result;
    }

    /**
     * encode header data to json byte[]
     * @return
     */

    public byte[] encodeHeaderData() {
        this.markConsumerHeader();
        if (this.serializeType == SerializeType.JSON) {
            return JsonUtil.jsonBytes(this);
        }
        // TODO: 2017/11/8 其他序列化方式
        else if (this.serializeType == SerializeType.OTHERS) {
            return null;
        } else if (this.serializeType == SerializeType.STREAM){
            return JsonUtil.ObjectToByte(this);

        }
        return null;
    }

    /**
     * transfer consumerHeader to extFields
     */
    public void markConsumerHeader() {
        if (consumerHeader == null) {
            LOGGER.info("there is no consumer header");
            return;
        }
        if (extFileds == null) {
            extFileds = new HashMap<String, Object>();
        }
        Field[] fields = getClazzFields(consumerHeader.getClass());
        for (Field field : fields) {
            String name = field.getName();
            if(!Modifier.isStatic(field.getModifiers())) {
                Object value = null;
                try {
                    field.setAccessible(true);
                    value = field.get(this.consumerHeader);
                } catch (IllegalAccessException e) {
                    LOGGER.error("get field value fail", e);
                }
                if (value != null) {
//                    if (value instanceof List) {
//                        this.list = (List<Object>) value;
//                    } else if (value instanceof String) {
//                        extFileds.put(name, value.toString());
//                    } else {
                        extFileds.put(name, value);
//                    }
                }
            }
        }
    }

    public Field[] getClazzFields(Class<? extends ConsumerHeader> clazz) {
        Field[] fields = CLASS_FIELD.get(clazz);
        if (fields == null) {
            fields = clazz.getDeclaredFields();
            synchronized (CLASS_FIELD) {
                CLASS_FIELD.put(clazz,fields);
            }
        }
        return fields;
    }


    /**
     *
     *
     * decode process
     * @param byteBuffer
     * @return
     */
    public static RemoteCommand decode(ByteBuffer byteBuffer) {
        int length = byteBuffer.limit();
        int oriHeadLength = byteBuffer.getInt();
        int headLength = getRealHeadLength(oriHeadLength);

        byte[] headData = new byte[headLength];
        byteBuffer.get(headData);
        RemoteCommand remoteCommand = decodeHeaderData(headData,markSerializeType(oriHeadLength));
        int bodyLength = length - 4 - headLength;
        if (bodyLength > 0) {
            byte[] bodyData = new byte[bodyLength];
            byteBuffer.get(bodyData);
            remoteCommand.setBody(bodyData);
        }
        return remoteCommand;
    }

    /**
     * decode head data
     * @param headData
     * @param serializeType
     * @return
     */
    public static RemoteCommand decodeHeaderData(byte[] headData,SerializeType serializeType) {
        RemoteCommand remoteCommand = null;
        switch (serializeType) {
            case JSON:
                return JsonUtil.jsonObject(headData,RemoteCommand.class);
            case OTHERS:
                break;
            case STREAM:
                remoteCommand = JsonUtil.ByteToObject(headData);
                return remoteCommand;
            default:
                return null;
        }
        return null;
    }

    /**
     * get serialize type
     * @param headLength
     * @return
     */
    public static SerializeType markSerializeType(int headLength) {
        return SerializeType.valueOf((byte) ((headLength >> 24)&0xFF));
    }

    /**
     * get real head length(six bits number)
     * @param length
     * @return
     */
    private static int getRealHeadLength(int length) {
        return length&0xFFFFFF;
    }


    /**
     * decode consumer header
     * @param consumerHeader
     * @return
     */
    public ConsumerHeader decodeConsumerHeader(Class<? extends ConsumerHeader> consumerHeader) {
        ConsumerHeader header;
        try {
            header = consumerHeader.newInstance();
        } catch (InstantiationException e) {
            LOGGER.error("decode consumer header fail");
            return null;
        } catch (IllegalAccessException e) {
            LOGGER.error("decode consumer header fail");
            return null;
        }

        Field[] fields = getClazzFields(consumerHeader);

        for (Field field : fields) {
            if (!Modifier.isStatic(field.getModifiers())) {
                String name = field.getName();
                String canonicalName = getCanonicalName(field.getType());
                Object value = null;
                value = this.extFileds.get(name);
//                if (canonicalName.equals(STRING_CANONICAL_NAME)) {
//                    value = this.extFileds.get(name);
//                }
//                else if (canonicalName.equals(INTEGER_CANONICAL_NAME_1) || canonicalName.equals(INTEGER_CANONICAL_NAME_2)) {
//                    value = this.extFileds.get(name);
//                }
//                else if (canonicalName.equals(LONG_CANONICAL_NAME_1) || canonicalName.equals(LONG_CANONICAL_NAME_2)) {
//                    value  = this.extFileds.get(name);
//                }
//                else if (canonicalName.equals(DOUBLE_CANONICAL_NAME_1) || canonicalName.equals(DOUBLE_CANONICAL_NAME_2
//                )) {
//                    value = this.extFileds.get(name);
//                }
//                else if (canonicalName.equals(BOOLEAN_CANONICAL_NAME_1) || canonicalName.equals(BOOLEAN_CANONICAL_NAME_2)) {
//                    value = this.extFileds.get(name);
//                }
////                else if (canonicalName.equals(LIST_CANNOICAL_NAME)) {
////                    value = this.list;
////                }
//                else {
//                    value = this.extFileds.get(name);
//                }
                field.setAccessible(true);
                try {
                    field.set(header, value);
                } catch (IllegalAccessException e) {
                    LOGGER.error("field decode fail",field,e);
                }
            }
        }
        return header;
    }

    private String getCanonicalName(Class clazz) {
        String canonicalName = CANONICAL_NAME_CACHE.get(clazz);
        if (canonicalName == null) {
            synchronized (CANONICAL_NAME_CACHE) {
                CANONICAL_NAME_CACHE.put(clazz,clazz.getCanonicalName());
                canonicalName = clazz.getCanonicalName();
            }
        }
        return canonicalName;
    }

    @Override
    public String toString() {
        return "RemoteCommand{" +
                "requestId=" + requestId +
                ", code=" + code +
                ", flag=" + flag +
                ", body=" + Arrays.toString(body) +
                ", serializeType=" + serializeType +
                ", consumerHeader=" + consumerHeader +
                ", extFileds=" + extFileds +
                ", remark='" + remark + '\'' +
                '}';
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public SerializeType getSerializeType() {
        return serializeType;
    }

    public void setSerializeType(SerializeType serializeType) {
        this.serializeType = serializeType;
    }

    @JsonIgnore
    public ConsumerHeader getConsumHeader() {
        return consumerHeader;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    @JsonIgnore
    public void setConsumHeader(ConsumerHeader consumHeader) {
        this.consumerHeader = consumHeader;
    }

    public HashMap<String, Object> getExtFileds() {
        return extFileds;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setExtFileds(HashMap<String, Object> extFileds) {
        this.extFileds = extFileds;
    }
}

package appcloud.api.XmlAdapter;


import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * Created by zouji on 2017/11/23.
 */
public class TimestampXmlAdapter extends XmlAdapter<String, Timestamp>{

    @Override
    public Timestamp unmarshal(String timestampStr) throws Exception {
        if (timestampStr != null && !timestampStr.equals(""))
            return Timestamp.valueOf(timestampStr);
        else
            return null;
    }

    @Override
    public String marshal(Timestamp timestamp) throws Exception {
        if (timestamp == null ) {
            return "";
        } else {
            return timestamp.toString();
        }
    }
}

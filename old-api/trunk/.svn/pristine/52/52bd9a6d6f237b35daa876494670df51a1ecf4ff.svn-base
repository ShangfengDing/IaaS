package appcloud.api.beans;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public final class LinkAdaptor  extends XmlAdapter<Metadata, Map<String, String>>{

	@Override
	public Metadata marshal(Map<String, String> arg0) throws Exception {
		if (arg0 == null) {
			return null;
		}
		
		Metadata metas = new Metadata();
		
		for (Entry<String, String> entry : arg0.entrySet()) {
			Meta meta = new Meta();
			meta.key = entry.getKey();
			meta.value = entry.getValue();
			metas.metas.add(meta);
		}
		
		return metas;
	}

	@Override
	public Map<String, String> unmarshal(Metadata arg0) throws Exception {
		if (arg0 == null) {
			return null;
		}
		
		HashMap<String, String> hashMap = new HashMap<String, String>();
		
		for (Meta meta : arg0.metas) {
			hashMap.put(meta.key, meta.value);
		}
		
		return hashMap;
	}

}

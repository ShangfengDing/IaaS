package appcloud.dbproxy.lol;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.free4lab.lol.client.Messenger;
import com.free4lab.lol.client.TimeLineScanner;
import com.free4lab.lol.message.Message;
import com.free4lab.lol.message.MessageFactory;

import appcloud.common.model.Load;
import appcloud.common.proxy.LoadProxy;

public class LolLoadProxy implements LoadProxy{
	private static HashMap<String, Load> loadCache = new HashMap<String, Load>();
	private AtomicLong offset = new AtomicLong(0);
	private Messenger messenger = new Messenger();
	private static final ObjectMapper mapper = new ObjectMapper();

	@Override
	public Load getCurLoadByUuid(String uuid) throws Exception {
		if(loadCache.containsKey(uuid)) {
			return loadCache.get(uuid);
		} else {
			TimeLineScanner a = new TimeLineScanner("yh3.basicload", System.currentTimeMillis()-10000);
			for (int i=0; i< 1000; i++) {
				Message m = a.nextMessage();
				if (m != null) {
					Load load = mapper.readValue(m.getContent(), Load.class);
					if (load.getFatherUuid() == uuid) return load;
				} else {
					break;
				}
			}
		}
		
		return null;
	}

	@Override
	public void save(Load load) throws Exception {
		loadCache.put(load.getFatherUuid(), load);
		
		byte[] content = mapper.writeValueAsBytes(load);
		
		Message m = MessageFactory.getInstance().createMessage("yh3.basicload", content, "LolLoadProxy", offset.incrementAndGet());
		
		messenger.send(m);
	}

	@Override
	public void deleteByUuid(String uuid) throws Exception {
		// TODO Auto-generated method stub		
	}
	
	public static void main(String[] args) {
		LolLoadProxy p = new LolLoadProxy();
		Load l = new Load();
		l.setFatherUuid("nima");
		l.setCpuUsePercent((float) 1.2);
		try {
			p.save(l);
			Thread.sleep(5000);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			System.out.println("nima: " + p.getCurLoadByUuid("nima"));
			System.out.println("woqu: " + p.getCurLoadByUuid("woqu"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}

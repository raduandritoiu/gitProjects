import java.util.concurrent.ConcurrentHashMap;

import radua.servers.session.ISession;
import radua.servers.session.ISessionKey;
import testing.ServerTesting;
import testing.providers.simple.TestNumbers;

public class Main {
	public static void main(String[] args) throws Exception
	{
//		TwoThreadsTesting.run_1();
//		ServerTesting.run();
		
//		TestNumbers.run();
//		TestProvidersAaa.run();
		
//		TestProvidersDoi.run();
//		TestProvidersBbb.run();
		
		ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
		boolean e = false;
		String s1 = null;
		
		map.put("aaa", 1);
		map.put("bbb", 2);
		map.put("ccc", 3);
		
		map.get("bbb");
		e = map.isEmpty();
		
		map.remove("aaa");
		map.remove("ccc");
		map.remove("bbb");
		
		map.get("bbb");
		e = map.isEmpty();
		
		s1 = new String("asds");
	}
}

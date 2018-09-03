import testing.ServerTesting;
import testing.TwoThreadsTesting;
import testing.providers.one.TestProvidersAaa;
import testing.providers.one.TestProvidersUnu;
import testing.providers.two.TestProvidersBbb;
import testing.providers.two.TestProvidersDoi;

public class Main {
	public static void main(String[] args) throws Exception
	{
//		TwoThreadsTesting.run_1();
		ServerTesting.run();
		
//		TestProvidersUnu.run();
//		TestProvidersAaa.run();
		
//		TestProvidersDoi.run();
//		TestProvidersBbb.run();
	}
}

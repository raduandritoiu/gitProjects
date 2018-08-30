package testing.providers.two;

public class TestProvidersDoi
{
	static Cinci cinci;
	static Patru patru;
	static Trei trei;
	static Doi doi;
	static Unu unu;
	
	public static void run() throws Exception
	{
		test_1();
		test_2();
		test_3();
		test_4();
		test_5();
		
		test_final();
	}

	private static void init() throws Exception
	{
		unu = null; doi = null; trei = null; patru = null; cinci = null;
		cinci = new Cinci();
		patru = new Patru(cinci);
		trei = new Trei(patru);
		doi  = new Doi(trei);
		unu = new Unu(doi);
		System.out.println("\n\n======================================");
	}
	
	private static void test_1() throws Exception
	{
		init();
		trei.start();
		System.out.println("======================================");
		patru.stop();
	}
	
	private static void test_2() throws Exception
	{
		init();
		doi.start();
		System.out.println("======================================");
		doi.stopWait();
	}
	
	private static void test_3() throws Exception
	{
		init();
		unu.start();
		System.out.println("======================================");
		trei.stop();
	}
	
	private static void test_4() throws Exception
	{
		init();
		patru.start();
		System.out.println("======================================");
		cinci.tick();
		System.out.println("======================================");
		cinci.stopWait();
	}
	
	private static void test_5() throws Exception
	{
		init();
		cinci.start();
		System.out.println("======================================");
		unu.packetReceived();
		System.out.println("======================================");
		unu.stop();
	}
	
	private static void test_final() throws Exception
	{
		System.out.println("\n\n");
		init();
		unu.start();
		System.out.println("======================================");
		unu.packetReceived();
		System.out.println("======================================");
		cinci.stop();
	}

	
	
}
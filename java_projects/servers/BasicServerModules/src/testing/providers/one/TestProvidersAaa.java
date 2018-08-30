package testing.providers.one;

public class TestProvidersAaa
{
	static Aaaa aaa;
	static Bbbb bbb;
	static Cccc ccc;
	static Dddd ddd;
	static Eeee eee;
	
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
		aaa = null; bbb = null; ccc = null; ddd = null; eee = null;
		aaa = new Aaaa();
		bbb = new Bbbb(aaa);
		ccc = new Cccc(bbb);
		ddd = new Dddd(ccc);
		eee = new Eeee(ddd);
		System.out.println("\n\n======================================");
	}
	
	private static void test_1() throws Exception
	{
		init();
		ccc.start();
		System.out.println("======================================");
		ddd.stop();
	}
	
	private static void test_2() throws Exception
	{
		init();
		bbb.start();
		System.out.println("======================================");
		bbb.stopWait();
	}
	
	private static void test_3() throws Exception
	{
		init();
		aaa.start();
		System.out.println("======================================");
		ccc.stop();
	}
	
	private static void test_4() throws Exception
	{
		init();
		ddd.start();
		System.out.println("======================================");
		eee.tick();
		System.out.println("======================================");
		eee.stopWait();
	}
	
	private static void test_5() throws Exception
	{
		init();
		eee.start();
		System.out.println("======================================");
		aaa.packetReceived();
		System.out.println("======================================");
		aaa.stop();
	}
	
	private static void test_final() throws Exception
	{
		System.out.println("\n\n");
		init();
		aaa.start();
		System.out.println("======================================");
		aaa.packetReceived();
		System.out.println("======================================");
		eee.stop();
	}

	
	
}
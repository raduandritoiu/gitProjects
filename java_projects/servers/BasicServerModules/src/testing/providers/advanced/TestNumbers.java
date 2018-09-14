package testing.providers.advanced;

public class TestNumbers
{
	static Cinci cinci;
	static Patru patru;
	static Trei trei;
	static Doi doi;
	static Unu unu;
	
	public static void run() throws Exception
	{
		test_normal();
		test_links();
	}
	
	public static void test_normal() throws Exception
	{
		test_1();
		test_2();
		test_3();
		test_4();
		test_5();
	}

	private static void init() throws Exception
	{
		unu = null; doi = null; trei = null; patru = null; cinci = null;
		cinci = new Cinci();
		patru = new Patru();
		trei = new Trei();
		doi  = new Doi();
		unu = new Unu();
		System.out.println("\n\n/======================================/");
	}
	
	private static void test_1() throws Exception
	{
		init();
		trei.start();
		System.out.println("======================================");
		doi.start();
		System.out.println("======================================");
		patru.stop();
	}
	
	private static void test_2() throws Exception
	{
		init();
		doi.start();
		System.out.println("======================================");
		cinci.stop();
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
		unu.start();
		System.out.println("======================================");
		trei.stop();
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
	
	
	private static void test_links() throws Exception
	{
		test_6();
		test_7();
		test_8();
		test_9();
	}
	
	private static void test_6() throws Exception
	{
		System.out.println("\n\n======================================");
		Cinci cinci = new Cinci();
		cinci.linkProvider(new Patru()).linkProvider(new Trei()).linkProvider(new Doi()).linkProvider(new Unu());
		cinci.start();
		System.out.println("======================================");
		cinci.stop();
	}
	
	private static void test_7() throws Exception
	{
		System.out.println("\n\n======================================");
		Unu unu = new Unu();
		unu.linkHandler(new Doi()).linkHandler(new Trei()).linkHandler(new Patru()).linkHandler(new Cinci());
		unu.start();
		System.out.println("======================================");
		unu.stop();
	}
	
	private static void test_8() throws Exception
	{
		System.out.println("\n\n======================================");
		Unu unu = new Unu();
		Trei trei = new Trei();
		unu.linkHandler(new Doi()).linkHandler(trei);
		trei.linkHandler(new Patru()).linkHandler(new Cinci());
		trei.start();
		System.out.println("======================================");
		unu.stop();
		System.out.println("======================================");
		trei.stop();
	}

	private static void test_9() throws Exception
	{
		System.out.println("\n\n======================================");
		Unu unu = new Unu();
		Trei trei = new Trei();
		unu.linkHandler(new Doi()).linkHandler(trei);
		unu.start();
		System.out.println("======================================");
		trei.linkHandler(new Patru()).linkHandler(new Cinci());
		System.out.println("======================================");
		unu.stop();
	}
	
	private static void test_10() throws Exception
	{
		System.out.println("\n\n======================================");
		Unu unu = new Unu();
		Trei trei = new Trei();
		unu.linkHandler(new Doi()).linkHandler(trei);
		unu.start();
		System.out.println("======================================");
		patru = new Patru();
		patru.linkHandler(new Cinci());
		trei.linkHandler(patru);
		System.out.println("======================================");
		unu.stop();
	}
}
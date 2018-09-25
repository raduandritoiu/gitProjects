package testing.providers.simple;

public class TestNumbers
{
	static Unu unu;
	static Doi doi;
	static Trei trei;
	static Patru patru;
	static Cinci cinci;
	static Sase sase;
	static Sapte sapte;
	
	
	private static void reset()
	{
		System.out.println("\n\n/======================================/");
		unu = null; doi = null; trei = null; patru = null; cinci = null; sase = null; sapte = null;
	}
	
	private static void init() throws Exception
	{
		reset();
		unu = new Unu(); 		doi  = new Doi(); 		trei = new Trei();
		patru = new Patru(); 	cinci = new Cinci(); 	sase = new Sase();
		sapte = new Sapte();
	}
	
	
	public static void run() throws Exception
	{
//		test_normal();
//		test_missing();
		test_extra ();
	}
	
	public static void test_normal() throws Exception
	{
		test_1();
		test_2();
		test_3();
		test_4();
	}

	private static void test_1() throws Exception
	{
		init();
		unu.linkInner(doi).linkInner(trei).linkInner(patru).linkInner(cinci).linkInner(sase).linkInner(sapte);
		trei.start();
		System.out.println("======================================");
		doi.start();
		System.out.println("======================================");
		patru.stop();
	}
	
	private static void test_2() throws Exception
	{
		
		init();
		sapte.linkOuter(sase).linkOuter(cinci).linkOuter(patru).linkOuter(trei).linkOuter(doi).linkOuter(unu);
		doi.start();
		System.out.println("======================================");
		cinci.stop();
		System.out.println("======================================");
		doi.stopWait();
	}
	
	private static void test_3() throws Exception
	{
		reset();
		unu = new Unu();
		unu.linkInner(new Doi()).linkInner(new Trei()).linkInner(new Patru()).linkInner(new Cinci()).linkInner(new Sase()).linkInner(new Sapte());
		unu.start();
		System.out.println("======================================");
		unu.packetReceived();
		System.out.println("======================================");
		unu.stop();
	}
	
	private static void test_4() throws Exception
	{
		reset();
		sapte = new Sapte();
		sapte.linkOuter(new Sase()).linkOuter(new Cinci()).linkOuter(new Patru()).linkOuter(new  Trei()).linkOuter(new Doi()).linkOuter(new  Unu());
		sapte.start();
		System.out.println("======================================");
		sapte.tick();
		System.out.println("======================================");
		sapte.stopWait();
	}
	
	
	
	private static void test_missing() throws Exception
	{
		test_5();
		test_6();
		test_7();
		test_8();
		test_9();
	}
	
	private static void test_5() throws Exception
	{
		reset();
		unu = new Unu();
		unu.linkInner(new Trei()).linkInner(new Cinci()).linkInner(new Sapte());
		unu.start();
		System.out.println("======================================");
		unu.packetReceived();
		System.out.println("======================================");
		unu.stop();
	}
	
	private static void test_6() throws Exception
	{
		reset();
		sapte = new Sapte();
		sapte.linkOuter(new Sase()).linkOuter(new Patru()).linkOuter(new  Unu());
		sapte.start();
		System.out.println("======================================");
		sapte.tick();
		System.out.println("======================================");
		sapte.stopWait();
	}
	
	private static void test_7() throws Exception
	{
		reset();
		doi = new Doi();
		doi.linkInner(new Patru()).linkInner(new Cinci());
		doi.start();
		System.out.println("======================================");
		doi.stop();
	}
	
	private static void test_8() throws Exception
	{
		reset();
		unu = new Unu();
		unu.linkInner(new Doi()).linkInner(new Cinci()).linkInner(new Sase());
		unu.start();
		System.out.println("======================================");
		unu.packetReceived();
		System.out.println("======================================");
		unu.stop();
	}
	
	private static void test_9() throws Exception
	{
		reset();
		sapte = new Sapte();
		sapte.linkOuter(new Sase()).linkOuter(new Cinci()).linkOuter(new  Trei());
		sapte.start();
		System.out.println("======================================");
		sapte.tick();
		System.out.println("======================================");
		sapte.stopWait();
	}
	
	
	
	private static void test_extra() throws Exception 
	{
		test_10();
		test_11();
	}
	
	private static void test_10() throws Exception
	{
		reset();
		unu = new Unu();
		unu.linkInner(new Doi()).linkInner(new Patru()).linkInner(new Trei()).linkInner(new Patru()).linkInner(new Cinci()).
		linkInner(new Trei()).linkInner(new Sase()).linkInner(new Sase()).linkInner(new Sapte());
		unu.start();
		System.out.println("======================================");
		unu.packetReceived();
		System.out.println("======================================");
		unu.stop();
	}
	
	private static void test_11() throws Exception
	{
		reset();
		sapte = new Sapte();
		sapte.linkOuter(new Doi()).linkOuter(new Cinci()).linkOuter(new Patru()).linkOuter(new Sase()).linkOuter(new Sase()).
		linkOuter(new Trei()).linkOuter(new Doi()).linkOuter(new Cinci()).linkOuter(new  Unu());
		sapte.start();
		System.out.println("======================================");
		sapte.tick();
		System.out.println("======================================");
		sapte.stopWait();
	}

	
	
//	
//	private static void test_5() throws Exception
//	{
//		init();
//		cinci.start();
//		System.out.println("======================================");
//		unu.packetReceived();
//		System.out.println("======================================");
//		unu.stop();
//	}
//	
//	
//	private static void test_links() throws Exception
//	{
//		test_6();
//		test_7();
//		test_8();
//		test_9();
//	}
//	
//	private static void test_6() throws Exception
//	{
//		System.out.println("\n\n======================================");
//		Cinci cinci = new Cinci();
//		cinci.linkProvider(new Patru()).linkProvider(new Trei()).linkProvider(new Doi()).linkProvider(new Unu());
//		cinci.start();
//		System.out.println("======================================");
//		cinci.stop();
//	}
//	
//	private static void test_7() throws Exception
//	{
//		System.out.println("\n\n======================================");
//		Unu unu = new Unu();
//		unu.linkHandler(new Doi()).linkHandler(new Trei()).linkHandler(new Patru()).linkHandler(new Cinci());
//		unu.start();
//		System.out.println("======================================");
//		unu.stop();
//	}
//	
//	private static void test_8() throws Exception
//	{
//		System.out.println("\n\n======================================");
//		Unu unu = new Unu();
//		Trei trei = new Trei();
//		unu.linkHandler(new Doi()).linkHandler(trei);
//		trei.linkHandler(new Patru()).linkHandler(new Cinci());
//		trei.start();
//		System.out.println("======================================");
//		unu.stop();
//		System.out.println("======================================");
//		trei.stop();
//	}
//
//	private static void test_9() throws Exception
//	{
//		System.out.println("\n\n======================================");
//		Unu unu = new Unu();
//		Trei trei = new Trei();
//		unu.linkHandler(new Doi()).linkHandler(trei);
//		unu.start();
//		System.out.println("======================================");
//		trei.linkHandler(new Patru()).linkHandler(new Cinci());
//		System.out.println("======================================");
//		unu.stop();
//	}
//	
//	private static void test_10() throws Exception
//	{
//		System.out.println("\n\n======================================");
//		Unu unu = new Unu();
//		Trei trei = new Trei();
//		unu.linkHandler(new Doi()).linkHandler(trei);
//		unu.start();
//		System.out.println("======================================");
//		patru = new Patru(new Cinci());
//		trei.linkHandler(patru);
//		System.out.println("======================================");
//		unu.stop();
//	}
}
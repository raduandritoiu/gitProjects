package testing.vers1.providers;

public class TestLetters
{
	static Alpha alpha;
	static Beta beta;
	static Gamma gama;
	static Delta delta;
	static Epsilon epsilon;
	
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
		alpha = null; beta = null; gama = null; delta = null; epsilon = null;
		alpha = new Alpha();
		beta = new Beta(alpha);
		gama = new Gamma(beta);
		delta = new Delta(gama);
		epsilon = new Epsilon(delta);
		System.out.println("\n\n======================================");
	}
	
	private static void test_1() throws Exception
	{
		init();
		gama.start();
		System.out.println("======================================");
		delta.stop();
	}
	
	private static void test_2() throws Exception
	{
		init();
		beta.start();
		System.out.println("======================================");
		beta.stopWait();
	}
	
	private static void test_3() throws Exception
	{
		init();
		alpha.start();
		System.out.println("======================================");
		gama.stop();
	}
	
	private static void test_4() throws Exception
	{
		init();
		delta.start();
		System.out.println("======================================");
		epsilon.tick();
		System.out.println("======================================");
		epsilon.stopWait();
	}
	
	private static void test_5() throws Exception
	{
		init();
		epsilon.start();
		System.out.println("======================================");
		alpha.packetReceived();
		System.out.println("======================================");
		alpha.stop();
	}
	
	private static void test_final() throws Exception
	{
		System.out.println("\n\n");
		init();
		alpha.start();
		System.out.println("======================================");
		alpha.packetReceived();
		System.out.println("======================================");
		epsilon.stop();
	}

	
	
}
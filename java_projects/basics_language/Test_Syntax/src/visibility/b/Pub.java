package visibility.b;

public class Pub 
{
	public int pub_f() { return 1; }
	protected int prot_f() { return 2; }
	/*packate_p*/ int pack_f() { return 3; }
	private int priv_f() { return 4; }
}

package visibility.a;

public class Pack implements IPack 
{
	protected int pub_f() { return 5; }
	int prot_f() { return 5; }
	int pack_f() { return 5; }
	int priv_f() { return 5; }
}
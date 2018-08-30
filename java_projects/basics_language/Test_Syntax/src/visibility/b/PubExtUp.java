package visibility.b;

public class PubExtUp extends Pub
{
	public int pub_f() { return 101; }			// VISIBILITY CAN BE UPGRADED
	public int prot_f() { return 102; }			// VISIBILITY CAN BE UPGRADED
	public int pack_f() { return 103; }			// VISIBILITY CAN BE UPGRADED
	public int priv_f() { return 104; }			// VISIBILITY CAN BE UPGRADED
}

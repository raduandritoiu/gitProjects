package visibility.use;

import visibility.b.PackExt;
import visibility.b.PackExtUp;
import visibility.b.Pub;
//import visibility.b.Pack; 			// IS NOT VISIBLE
import visibility.b.PubExtUp;

public class UseClasses 
{
	public static void use()
	{
		Pub pub = new Pub();
		int x = 0;
		x = pub.pub_f();
//		x = pub.prot_f();				// IS NOT VISIBLE
//		x = pub.pack_f();				// IS NOT VISIBLE
//		x = pub.priv_f();				// IS NOT VISIBLE

		
//		Pack pack = new Pack();			// IS NOT VISIBLE
		
		
		PubExtUp pubExtUp = new PubExtUp();
		x = pubExtUp.pub_f();			// VISIBILITY CAN BE UPGRADED
		x = pubExtUp.prot_f();			// VISIBILITY CAN BE UPGRADED
		x = pubExtUp.pack_f();			// VISIBILITY CAN BE UPGRADED
		x = pubExtUp.priv_f();			// VISIBILITY CAN BE UPGRADED
		
		
		PackExt packExt = new PackExt();	// VISIBILITY CAN BE UPGRADED
		x = packExt.pub_f();
//		x = packExt.prot_f();			// IS NOT VISIBLE
//		x = packExt.pack_f();			// IS NOT VISIBLE
//		x = packExt.priv_f();			// IS NOT VISIBLE

		
		PackExtUp packExtUp = new PackExtUp();	// VISIBILITY CAN BE UPGRADED
		x = packExtUp.pub_f();
		x = packExtUp.prot_f();			// VISIBILITY CAN BE UPGRADED
		x = packExtUp.pack_f();			// VISIBILITY CAN BE UPGRADED
		x = packExtUp.priv_f();			// VISIBILITY CAN BE UPGRADED
		
		
		
		
		
	}
}

package testing.linking;

import radua.servers.packetProcs.linking.GraphLinks;

public class TestLinks 
{
	private static A a;
	private static B b;
	private static C c;
	private static D d;
	private static E e;
	private static F f;
	private static G g;
	private static H h;
	private static I i;
	private static J j;
	private static K k;
	private static L l;
	private static M m;
	private static N n;
	private static O o;
	private static P p;
	private static Q q;
	private static R r;
	private static T t;
	private static U u;
	private static V v;
	private static void init() {
		a = new A(); b = new B(); c = new C(); d = new D(); e = new E(); f = new F(); g = new G(); 
		h = new H(); i = new I(); j = new J(); k = new K(); l = new L(); m = new M(); n = new N(); 
		o = new O(); p = new P(); q = new Q(); r = new R(); t = new T(); u = new U(); v = new V();
	}
	private static void clear() {
		a = null; b = null; c = null; d = null; e = null; f = null; g = null; 
		h = null; i = null; j = null; k = null; l = null; m = null; n = null; 
		o = null; p = null; q = null; r = null; t = null; u = null; v = null;
	}
	
	
	
	public static void run()
	{
		int i1 = 5;
		int a1 = 0;
		a1 = i1;
		i1 ++;
		
		Integer i2 = 5;
		Integer a2 = 0;
		a2 = i1;
		inc(i2);
		
		
		
		init();
		
		a.linkInner(b).linkInner(c).linkInner(d).linkInner(e);
		c.linkInner(f).linkInner(g).linkInner(h).linkInner(q);
		t.linkInner(l).linkInner(k).linkInner(j).linkInner(i).linkInner(g);
		i.linkInner(m).linkInner(n);
		m.linkInner(o).linkInner(p).linkInner(q);
		k.linkInner(r).linkInner(i);
		t.linkInner(v).linkInner(l);
		u.linkInner(v);
		GraphLinks graph = GraphLinks.CreateGraph(d);
		graph.printGraph();
		clear();
	}
	
	public static void inc(Integer i) {
		i++;
	}
}

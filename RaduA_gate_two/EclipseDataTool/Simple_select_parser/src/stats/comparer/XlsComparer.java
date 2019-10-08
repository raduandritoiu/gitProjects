package stats.comparer;

public class XlsComparer 
{
	public void compare() {
	}
	
	private static class PriceRow {
		protected String name;
		protected String desc;
		protected float price;
		protected int pos;
		
		public PriceRow() {}
		public PriceRow(String nName, String nDesc, float nPrice, int nPos) {
			name = nName;
			desc = nDesc;
			price = nPrice;
			pos = nPos;
		}
		
		public boolean equals(PriceRow row) {
			if (!name.equals(row.name)) return false;
			if (!desc.equals(row.desc)) return false;
			if (price != row.price) return false;
			return true;
		}
	}
}

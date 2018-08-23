import connections.MyConnection;


public class Main {
	public static void main(String[] args) {
		System.out.println("bag pula");
		
		MyConnection conn = new MyConnection();
		conn.setConnection();
		conn.testSelect();
		conn.closeConnection();
		
	}
}

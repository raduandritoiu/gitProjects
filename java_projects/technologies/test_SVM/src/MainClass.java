import libsvm.svm;


public class MainClass {
	public static void main(String args[]) {
		System.out.println("start test!\n");
//		svm_train test = new svm_train();
		try {
			svm_train.main(args);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}

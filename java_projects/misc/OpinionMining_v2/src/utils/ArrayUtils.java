package utils;

public class ArrayUtils {

	static public boolean intersects(String[] arr1, String[] arr2) {
		if (arr1 == null)
			return false;
		if (arr2 == null)
			return false;
		
		for (int i=0; i < arr1.length; i++) {
			for (int j=0; j < arr2.length; j++) {
				if (arr1[i].equals(arr2[j])) {
					return true;
				}
			}
		}
		return false;
	}
}

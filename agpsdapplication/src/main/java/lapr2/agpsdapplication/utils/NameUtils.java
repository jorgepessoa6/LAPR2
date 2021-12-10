package lapr2.agpsdapplication.utils;

public class NameUtils {

	private NameUtils() {
	}

	public static String getFirstName(String name) {
		String[] names = name.trim().split(" ");
		return names[0];
	}

	public static String getLastName(String name) {
		String[] names = name.trim().split(" ");
		return names[names.length - 1];
	}
}

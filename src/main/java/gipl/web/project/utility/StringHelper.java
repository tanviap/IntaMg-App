package gipl.web.project.utility;

/**
 * -----------------------------------------------------
 * This class handles commonly used string operations.
 * -----------------------------------------------------
 */
public class StringHelper {
	
	/**
	 * This method check whether the input string is null or empty.
	 * @param value
	 * @return
	 */
	public static boolean IsNullOrEmpty(String value) {
		return value == null || value.isEmpty();
	}
	
	/**
	 * This method converts null value to empty string.
	 * @param value
	 * @return
	 */
	public static String AvoidNull(String value) {
		return value == null ? "" : value;
	}
	
	/**
	 * This method compares two strings matching case.
	 * @param valueOne
	 * @param valueAnother
	 * @return
	 */
	public static boolean CompareWithCase(String valueOne, String valueAnother) {
		valueOne = AvoidNull(valueOne);
		valueAnother = AvoidNull(valueAnother);
		
		return valueOne.equals(valueAnother);
	}
	
	/**
	 * This method compares two strings ignoring case.
	 * @param valueOne
	 * @param valueAnother
	 * @return
	 */
	public static boolean CompareIgnoreCase(String valueOne, String valueAnother) {
		valueOne = AvoidNull(valueOne);
		valueAnother = AvoidNull(valueAnother);
		
		return valueOne.equalsIgnoreCase(valueAnother);
	}
	
}

package gipl.web.project.utility;

import java.util.Random;

/**
 * ---------------------------------------
 * This class manages OTP creation.
 * ---------------------------------------
 */
public class OTPHelper {
	
	private static int OTP_LENGTH = 4;
	private static String NUMBERS_STRING_FOR_OTP = "0123456789";
	
	/**
	 * This method generates OTP of specific length.
	 * @return
	 */
	public static String generateOTP() {
		char[] otpArray = new char[OTP_LENGTH];
		
		// Generate OTP.
		Random random = new Random();
		int numberStringLen = NUMBERS_STRING_FOR_OTP.length();
		for (int i = 0; i < OTP_LENGTH; i++) {
			otpArray[i] = NUMBERS_STRING_FOR_OTP.charAt(random.nextInt(numberStringLen));
		}
		
		return otpArray.toString();
	}
	
}

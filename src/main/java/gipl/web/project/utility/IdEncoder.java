package gipl.web.project.utility;

import java.nio.ByteBuffer;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;

/**
 * ------------------------------------------------------------------
 * This class handles encoding and decoding of unique identifier. 
 * ------------------------------------------------------------------
 */
public class IdEncoder {
	
	/**
	 * This method encodes single UID.
	 * @param uuid
	 * @return
	 */
	public static String encodeUID(UUID uuid) {
		return encodeUIDPair(uuid, new UUID(0, 0));
	}
	
	/**
	 * This method encodes UID and UID Cipher.
	 * @param uid
	 * @param uidCipher
	 * @return
	 */
	public static String encodeUIDPair(UUID uid, UUID uidCipher) {
		// Initialize.
		StringBuilder builder = new StringBuilder(64);
		String uidStr = uid.toString();
		String uidCipherStr = uidCipher.toString();
		
		int shift = fromHexDigitChar(uidCipherStr.charAt(0));
		int uidLength = uidStr.length();
		for (int i = 0; i < uidLength; i++) {
			builder.append(uidCipherStr.charAt(i));
			builder.append(uidStr.charAt((i + shift) % uidLength));
		}		
		
		return builder.toString();
	}
	
	private static byte fromHexDigitChar(char ch) {
		// Initialize.
		byte retByte = (byte) 0xFF;
		ch = Character.toLowerCase(ch);
		
		if (ch >= '0' && ch <= '9') {
			retByte = (byte)((int)ch - (int)'0');
		} else if (ch >= 'a' && ch <= 'f') {
			retByte = (byte)(((int)ch - (int)'a') + 10);
		}
		
		return retByte;
	}
	
	/**
	 * ---------------------------------------------------
	 * This class servers as a return type for decoding.
	 * ---------------------------------------------------
	 */
	public static class UIDPair {
		public UUID uid;
		public UUID uidCipher;
	}
	
	/**
	 * This method decodes string UID and returns UID and UID Cipher.
	 * @param uidStr
	 * @return
	 */
	public static UIDPair decodeUIDPair(String uidStr) {
		UIDPair uidPair = new IdEncoder.UIDPair();
		uidPair.uid = new UUID(0, 0);
		uidPair.uidCipher = new UUID(0, 0);
		
		if (StringHelper.IsNullOrEmpty(uidStr) == false && 
				uidStr.length() == 64) {
			// Initialize.
			char[] uidArray = new char[32];
			char[] uidCipherArray = new char[32];
			int shift = fromHexDigitChar(uidStr.charAt(0));
			int uidLength = uidStr.length();
			int chPos = 0;
			
			for (int i = 0; i < uidLength; i += 2) {
				uidCipherArray[chPos] = uidStr.charAt(i);
				uidArray[(chPos + shift) % 32] = uidStr.charAt(i + 1);
				chPos++;
			}
			
			uidPair.uid = UUID.fromString(uidArray.toString());
			uidPair.uidCipher = UUID.fromString(uidCipherArray.toString());
		}
		
		return uidPair;
	}
	
	/**
	 * ----------------------------------------------------------------------------------------
	 * This class makes use of org.apache.commons.codec.binary.Base64.
	 * Reference: https://stackoverflow.com/questions/772802/storing-uuid-as-base64-string
	 * ----------------------------------------------------------------------------------------
	 */
	
	/**
	 * This method returns base64 string from the specified UUID.
	 * @param uuid
	 * @return
	 */
	public static String getBase64FromUUID(UUID uuid) {
		// Initialize.
		ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[16]);
		
		byteBuffer.putLong(uuid.getMostSignificantBits());
		byteBuffer.putLong(uuid.getMostSignificantBits());
		
		return Base64.encodeBase64URLSafeString(byteBuffer.array());
	}
	
	/**
	 * This method returns UUID from the given base64 string.
	 * @param base64
	 * @return
	 */
	public static UUID getUUIDFromBase64(String base64) {
		byte[] bytes = Base64.decodeBase64(base64);
		ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
		
		return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
	}
	
}

package gipl.web.project.utility;

import java.nio.ByteBuffer;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;

/**
 * -------------------------------------------------------------------
 * This class handles encoding and decoding of unique identifier.
 * This class makes use of org.apache.commons.codec.binary.Base64.
 * Reference: https://stackoverflow.com/questions/772802/storing-uuid-as-base64-string
 * -------------------------------------------------------------------
 */
public class IdEncoder {
	
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

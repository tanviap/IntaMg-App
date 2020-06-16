package gipl.web.project.utility;

/**
 * ------------------------------------------------------------------
 * This class defines commonly used constants and functionalities.
 * ------------------------------------------------------------------
 */
public class Common {
	
	/**
	 * This enum defines error codes for API response.
	 */
	public enum ErrorCode {
		OK(0),
		INTERNAL(1),
		NO_DATA(2),
		INVALID_INPUT(3),
		INVALID_SESSION(4),
		AUTH_FAIL(5),
		DUPLICATE_DATA(6);
		
		private int errorCode;
		private String errorMessage;
		
		ErrorCode(int errorCode) {
			this.errorCode = 1 << errorCode;
		}
		
		public int getErrorCode() {
			return errorCode;
		}

		public String getErrorMessage() {
			switch (errorCode) {
			case 2:
				errorMessage = "";
				break;
			case 4:
				errorMessage = "Internal error has occurred. Please try again later.";
				break;
			case 8:
				errorMessage = "No data available.";
				break;
			case 16:
				errorMessage = "You have entered invalid data. Please check.";
				break;
			case 32:
				errorMessage = "Invalid session. Please log in again to continue.";
				break;
			case 64:
				errorMessage = "Authorization failure.";
				break;
			case 128:
				errorMessage = "Data already exists.";
				break;

			default:
				break;
			}
			
			return errorMessage;
		}
	}
	
	/**
	 * This enum specifies status for data row.
	 */
	public enum StatusType {
		ACTIVE(0),
		DELETED(1),
		OBSOLETE(2);
		
		private int statusType;
		
		StatusType(int statusType) {
			this.statusType = 1 << statusType;
		}

		public int getStatusType() {
			return statusType;
		}
	}
	
}

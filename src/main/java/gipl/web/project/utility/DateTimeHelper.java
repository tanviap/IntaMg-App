package gipl.web.project.utility;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * ---------------------------------------------------
 * This class handles Date related functionalities.
 * ---------------------------------------------------
 */
public class DateTimeHelper {
	
	/**
	 * This enum specifies required time-zones.
	 */
	public enum TimeZone {
		UTC(null), // If the time
		IN("Asia/Kolkata");

		TimeZone(String timeZoneString) {
			this.timeZoneString = timeZoneString;
		}
		
		private String timeZoneString;
		
		public String getTimeZoneString() {
			return timeZoneString;
		}
	}
	
	private static String DT_FORMAT_DDMMYYYY_HHMMSS = "dd-MM-yyyy HH:mm:ss";

	/**
	 * This method returns current date-time in UTC time-zone.
	 * @return
	 */
	public static Date getCurrentDateTimeInUTC() {
		ZonedDateTime utcDateTime = ZonedDateTime.now(ZoneOffset.UTC);		
		return Date.from(utcDateTime.toInstant());
	}
	
	/**
	 * This method transforms current date-time in UTC to the specific date format.
	 * @return
	 */
	public static String getCurrentDateTimeInUTCAsString() {
		DateFormat formatter = new SimpleDateFormat(DT_FORMAT_DDMMYYYY_HHMMSS);		
		return formatter.format(getCurrentDateTimeInUTC());
	}
	
	/**
	 * This method returns current date-time in given time-zone. 
	 * If the time-zone is not provided, Date in UTC will return.
	 * @param timeZone
	 * @return
	 */
	public static String getCurrentDateTimeInTimeZone(String timeZone) {
		if (StringHelper.IsNullOrEmpty(timeZone)) {
			return getCurrentDateTimeInUTCAsString();
		}		
		
		return ZonedDateTime.now(ZoneId.of(timeZone)).format(DateTimeFormatter.ofPattern(DT_FORMAT_DDMMYYYY_HHMMSS));
	}
	
	/**
	 * This method converts given date in UTC to the given time-zone.
	 * @param date
	 * @param timeZone
	 * @return
	 */
	public static String convertDateFromUTCToTimeZone(Date date, String timeZone) {
		// Validate.
		if (date == null) {
			return null;
		}
		
		DateFormat formatter = new SimpleDateFormat(DT_FORMAT_DDMMYYYY_HHMMSS);		
		if (!StringHelper.IsNullOrEmpty(timeZone)) {
			formatter.setTimeZone(java.util.TimeZone.getTimeZone(timeZone));
		}		
		
		return formatter.format(date);
	}
	
	/**
	 * This method converts date-time in string format to date-time in UTC.
	 * @param dateTimeInTimeZone
	 * @param timeZone
	 * @return
	 */
	public static Date convertDateTimeToUTC(String dateTimeInTimeZone, String timeZone) {
		// Validate.
		if (StringHelper.IsNullOrEmpty(dateTimeInTimeZone)) {
			return null;
		}
		
		DateFormat formatter = new SimpleDateFormat(DT_FORMAT_DDMMYYYY_HHMMSS);
		if (!StringHelper.IsNullOrEmpty(timeZone)) {
			formatter.setTimeZone(java.util.TimeZone.getTimeZone(timeZone));
		}
		
		try {
			return formatter.parse(dateTimeInTimeZone);
		} catch (ParseException ex) { }
		
		return null;
	}
	
}

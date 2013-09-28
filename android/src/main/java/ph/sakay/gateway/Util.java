package ph.sakay.gateway;

import java.util.regex.Pattern;
import java.security.MessageDigest;
import android.util.Base64;

public class Util {

	private static final Pattern leadingCodes = Pattern.compile("^[\\+0]?(63)?");
	private static final Pattern validNumber = Pattern.compile("^9\\d{9}$");
	private static final String salt = "REPLACE_ME";

	public static boolean isValidNumber(String number) {
		return validNumber.matcher(number).matches();
	}

	public static String normalizeNumber(String number) {
		return leadingCodes.matcher(number).replaceFirst("");
	}

	public static String hash(String number) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA1");
			number = salt + number + salt;
			byte[] digest = md.digest(number.getBytes("UTF-8"));
			return Base64.encodeToString(digest, Base64.NO_WRAP | Base64.NO_PADDING);
		}
		catch (Exception e) {
			return "hashfailure";
		}
	}
}

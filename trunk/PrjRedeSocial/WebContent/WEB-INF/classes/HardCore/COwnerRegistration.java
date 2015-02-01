package HardCore;

import java.security.*;

public class COwnerRegistration {
	static private String VALID_CHARS = "0123456789ABCDEFGHJKLMNPQRTUVWXY";



	static public String GenerateKey(String sRegisteredName, String sAppChars) {
		StringBuffer sKey = new StringBuffer();
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			byte[] sMD5 = md5.digest((sRegisteredName.toUpperCase() + sAppChars).getBytes());
			for(int lCount=1; lCount<=16; lCount++) {
				int lChar = ((int)(sMD5[lCount-1] & 0xFF)) % 32;
				sKey.append(VALID_CHARS.substring(lChar, lChar+1));
			}
		} catch (NoSuchAlgorithmException e) {
		}
		return "" + sKey;
	}



	static public boolean IsKeyOK(String sKey, String sRegisteredName, String sAppChars) {
		StringBuffer sTestKey = new StringBuffer();
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			byte[] sMD5 = md5.digest((sRegisteredName.toUpperCase() + sAppChars).getBytes());
			for(int lCount=1; lCount<=16; lCount++) {
				int lChar = ((int)(sMD5[lCount-1] & 0xFF)) % 32;
				sTestKey.append(VALID_CHARS.substring(lChar, lChar+1));
			}
		} catch (NoSuchAlgorithmException e) {
		}
		return (sKey.equals("" + sTestKey));
	}



}

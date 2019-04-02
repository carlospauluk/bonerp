import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;
import java.util.UUID;


public class GerarCodigo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			//Initialize SecureRandom
			//This is a lengthy operation, to be done only upon
			//initialization of the application
			SecureRandom prng = SecureRandom.getInstance("SHA1PRNG");

			//generate a random number
			String randomNum = new Integer(prng.nextInt(999999999)).toString();

			System.out.println(UUID.randomUUID().toString());

			for (int i = 0; i < 1000; i++) {

				//System.out.println(Math.abs(prng.nextLong()));

				System.out.println("BLA: " + GerarCodigo.gerarCodigoCupom());
			}

			//get its digest
			MessageDigest sha = MessageDigest.getInstance("SHA-1");
			byte[] result = sha.digest(randomNum.getBytes());

			System.out.println("Random number: " + randomNum);
			System.out.println("Message digest: " + GerarCodigo.hexEncode(result));
		} catch (NoSuchAlgorithmException ex) {
			System.err.println(ex);
		}

	}

	/**
	 * The byte[] returned by MessageDigest does not have a nice
	 * textual representation, so some form of encoding is usually performed.
	 *
	 * This implementation follows the example of David Flanagan's book
	 * "Java In A Nutshell", and converts a byte array into a String
	 * of hex characters.
	 *
	 * Another popular alternative is to use a "Base64" encoding.
	 */
	static private String hexEncode(byte[] aInput) {
		StringBuilder result = new StringBuilder();
		char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		for (byte b : aInput) {
			result.append(digits[(b & 0xf0) >> 4]);
			result.append(digits[b & 0x0f]);
		}
		return result.toString();
	}

	private static int gerarCodigoCupom() {

		int min = 1;
		int max = 9999;

		// NOTE: Usually this should be a field rather than a method
		// variable so that it is not re-seeded every call.
		Random rand = new Random();

		// nextInt is normally exclusive of the top value,
		// so add 1 to make it inclusive
		int randomNum = rand.nextInt((max - min) + 1) + min;

		return randomNum;

	}

}

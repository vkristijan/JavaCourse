package hr.fer.zemris.java.tecaj.hw07.crypto;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import static hr.fer.zemris.java.tecaj.hw07.crypto.Utility.hextobyte;

/**
 * A basic cryptography program capable of calculating the
 * <a href="https://en.wikipedia.org/wiki/Checksum">cheksum</a> of a file using
 * <a href="https://en.wikipedia.org/wiki/SHA-2">SHA-256 algorithm</a>. The
 * calculated checksum is compared to the expected one to determine if the given
 * file was corrupted or not. <br>
 * The second capability of this program is to encrypt or decrypt a file using
 * <a href="https://en.wikipedia.org/wiki/Advanced_Encryption_Standard">AES</a>
 * algorithm. For the algorithm to work, the user must specify a correct
 * encryption key and initialization vector. The generated information is stored
 * in a file. <br>
 * The information about what to do with this program and what files to use are
 * given in the console line arguments. The first argument is the desired
 * operation ("checksha", "encrypt" or "decrypt"). The second argument is the
 * file that should be used for input, and the third one is the file that should
 * be generated as output. In case of a checksum calculation, there is no output
 * file.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
public class Crypto {
	/**
	 * Program entry point.
	 * 
	 * @param args
	 *            console arguments.
	 */
	public static void main(String[] args) {
		if (args.length == 0) {
			System.err.println("Command line arguments expected, but nothing was given.");
			System.exit(1);
		}

		String command = args[0].trim().toLowerCase();
		switch (command) {
			case "checksha":
				checkSha(args);
				break;
			case "encrypt":
				encryptDecrypt(args);
				break;
			case "decrypt":
				encryptDecrypt(args);
				break;
			default:
				System.err.println("The provided command is unsupported!");
				System.exit(1);
		}
	}

	/**
	 * Encrypts or decrypts the file defined in the second argument. The result
	 * is stored in a new file that is created, as described in the third
	 * argument. The first argument describes the operation to be done. <br>
	 * The
	 * <a href="https://en.wikipedia.org/wiki/Advanced_Encryption_Standard">AES
	 * </a> algorithm is used for the encryption or decryption. <br>
	 * In order for the method to work, the user must input a 16 byte password
	 * (32 hex digits) and an initialization vector (another 32 hex digits).
	 * 
	 * @param args
	 *            a {@link String} array defining the operation to be done and
	 *            the files that should be used.
	 */
	private static void encryptDecrypt(String[] args) {
		Utility.checkArguments(args, 3);
		String mode = args[0].toLowerCase();

		Path path = Paths.get(args[1]);
		if (!Files.exists(path)) {
			System.err.println("The provided file does not exist!");
			System.exit(1);
		}
		Path destination = Paths.get(args[2]);

		Scanner sc = new Scanner(System.in);
		System.out.println(
			"Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):");
		System.out.print("> ");
		String keyText = sc.next();
		System.out.println(
			"Please provide initialization vector as hex-encoded text (32 hex-digits):");
		System.out.print("> ");
		String ivText = sc.next();
		sc.close();

		try (BufferedInputStream br = new BufferedInputStream(
			new FileInputStream(path.toFile()));
				BufferedOutputStream out = new BufferedOutputStream(
					new FileOutputStream(destination.toFile()))) {

			SecretKeySpec keySpec = new SecretKeySpec(hextobyte(keyText), "AES");
			AlgorithmParameterSpec paramSpec = new IvParameterSpec(hextobyte(ivText));
			Cipher cipher;
			cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(mode.equals("encrypt") ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE,
				keySpec, paramSpec);

			byte[] data = new byte[4096];

			int readCount;
			while ((readCount = br.read(data)) != -1) {
				out.write(cipher.update(data, 0, readCount));
			}
			out.write(cipher.doFinal());

			System.out.println(mode.substring(0, 1).toUpperCase() + mode.substring(1)
					+ "ion completed. Generated file " + destination.getFileName()
					+ " based on file " + path.getFileName() + ".");

		} catch (IOException e) {
			System.err.println("Unable to read the given file!");
			System.exit(1);
		} catch (GeneralSecurityException e) {
			System.err.println("There was an error during evaluation!");
			System.err.println(e.getMessage());
			System.exit(1);
		}

	}

	/**
	 * Calculates the
	 * <a href="https://en.wikipedia.org/wiki/Checksum">cheksum</a> of a file
	 * using <a href="https://en.wikipedia.org/wiki/SHA-2">SHA-256 algorithm</a>
	 * . The calculated checksum is compared to the expected one to determine if
	 * the given file was corrupted or not. <br>
	 * In order for the method to work, the user must input the expected sha
	 * digest that should be compared with the calculated one.
	 * 
	 * @param args
	 *            a {@link String} array defining the operation to be done and
	 *            the files that should be used.
	 */
	private static void checkSha(String[] args) {
		Utility.checkArguments(args, 2);

		Path path = Paths.get(args[1]);
		if (!Files.exists(path)) {
			System.err.println("The provided file does not exist!");
			System.exit(1);
		}

		Scanner sc = new Scanner(System.in);
		System.out.println(
			"Please provide expected sha-256 digest for " + path.getFileName() + ":");
		System.out.print("> ");
		String expected = sc.next();
		sc.close();

		try (BufferedInputStream br = new BufferedInputStream(
			new FileInputStream(path.toFile()))) {

			MessageDigest shaDigest = MessageDigest.getInstance("SHA-256");
			byte[] data = new byte[4096];

			int readCount;
			while ((readCount = br.read(data)) != -1) {
				shaDigest.update(data, 0, readCount);
			}

			byte[] digest = shaDigest.digest();

			String calculated = Utility.bytesToHexString(digest);
			System.out.print("Digesting completed. Digest of" + path.getFileName());
			if (calculated.equals(expected)) {
				System.out.println(" matches expected digest.");
			} else {
				System.out.println(
					" does not match the expected digest. Digest was: " + calculated);
			}

		} catch (IOException e) {
			System.err.println("Unable to read the given file!");
			System.exit(1);
		} catch (NoSuchAlgorithmException e) {
			System.err.println("The \"SHA-256\" algorithm is not supported!");
			System.exit(1);
		}

	}
}

package br.com.procempa.modus.stationmonitor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import br.com.procempa.modus.utils.CryptoUtils;

public class AccessKeyManager {
	
	private static final String START_KEY = "pr0cempa";
	private static final String ACCESSKEY_FILENAME = "accesskey";
	private static final File KEY_FILE = new File(ACCESSKEY_FILENAME);

	public static String getAccessKey() throws AccessKeyException {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(KEY_FILE));
			return reader.readLine();
		} catch (FileNotFoundException e) {
			return resetAccessKey();
		} catch (IOException e) {
			throw new FileReadKeyException();
		}
	}

	public static String resetAccessKey() throws SaveKeyException {
		String key = CryptoUtils.encripty(START_KEY);
		try {
			saveKey(key);
		} catch (IOException e) {
			throw new SaveKeyException(e.getMessage());
		}
		
		return key;
	}
	
	public static void saveKey(String key) throws IOException {
		FileWriter writer = new FileWriter(KEY_FILE);
		writer.write(key);
		writer.flush();
	}

	public static boolean isValidKey(String key) throws AccessKeyException {
		key = CryptoUtils.encripty(key);
		return key.equals(getAccessKey());
	}
	
	public static boolean isValidKey(char[] key) throws AccessKeyException {
		String keyS = String.valueOf(key);
		return isValidKey(keyS);
	}	
}
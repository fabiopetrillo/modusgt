package br.com.procempa.modus.utils;

import java.util.Random;
import java.util.UUID;

public class IDUtils {

	public static String getUUID(){
		return new UUID(new Random().nextLong(), System.currentTimeMillis()).toString();
	}
}

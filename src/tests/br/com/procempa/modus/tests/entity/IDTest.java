package br.com.procempa.modus.tests.entity;

import java.util.Random;
import java.util.UUID;

import junit.framework.TestCase;

public class IDTest extends TestCase {
	
	public void testCreateID(){
		for (int i = 0; i < 2000; i++) {
			UUID uuid = new UUID(new Random().nextLong(), System.currentTimeMillis());
			UUID uuid2 = new UUID(new Random().nextLong(), System.currentTimeMillis());
			assertNotSame(uuid.toString(), uuid2.toString());
		}
	}

}

package br.com.procempa.modus.tests.utils;

import junit.framework.TestCase;
import br.com.procempa.modus.utils.SocketUtils;

public class SocketUtilTest extends TestCase {
	public void testSendCommand() throws Exception {
		SocketUtils.sendCommand("10.110.115.231", 0, "close");
	}
}

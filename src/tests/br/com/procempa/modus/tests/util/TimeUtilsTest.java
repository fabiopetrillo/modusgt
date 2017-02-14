package br.com.procempa.modus.tests.utils;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import br.com.procempa.modus.utils.TimeUtils;
import junit.framework.TestCase;

public class TimeUtilsTest extends TestCase {
	
	public void testFormatedTime(){
		//Testa segundo
		Long num = new Long(1000);
		assertEquals("00:00:01",TimeUtils.formatTime(num));
		
		//Testa minuto
		num = new Long(60*1000+12000);
		assertEquals("00:01:12",TimeUtils.formatTime(num));
		
		//Testa horas
		num = new Long(3*60*60*1000+5*60*1000+20000);
		assertEquals("03:05:20",TimeUtils.formatTime(num));
		
		//Testa limites de hora
		num = new Long(25*60*60*1000+10*60*1000+20000);
		assertEquals("25:10:20",TimeUtils.formatTime(num));
		
		//Testa horas
		num = new Long(-3*60*60*1000-5*60*1000-20000);
		assertEquals("-03:05:20",TimeUtils.formatTime(num));
	}
	
	public void testInitialDate(){
		Date d = new Date();
		d = TimeUtils.initialDate(d);
		
		assertTrue(StringUtils.contains(d.toString(), "00:00:00"));
		
	}
}

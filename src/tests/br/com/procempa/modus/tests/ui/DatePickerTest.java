package br.com.procempa.modus.tests.ui;

import java.util.Calendar;

import junit.framework.TestCase;

import org.jdesktop.swingx.JXDatePicker;

import br.com.procempa.modus.ui.DatePicker;

public class DatePickerTest extends TestCase {
	
	public void testCreateDatePicker() {
		DatePicker picker = new DatePicker();
		assertNotNull(picker);
	}
	
	public void testDatePickerVerifier() {
		//Data Válida
		JXDatePicker picker = new DatePicker();
		assertTrue(picker.getInputVerifier().shouldYieldFocus(picker));

		//Data Inválida
		Calendar calendar = Calendar.getInstance();
		calendar.set(10000, 12, 31);
		picker = new DatePicker(calendar.getTimeInMillis());
		assertFalse(picker.getInputVerifier().shouldYieldFocus(picker));
	}
}

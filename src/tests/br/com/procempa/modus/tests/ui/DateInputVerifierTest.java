package br.com.procempa.modus.tests.ui;

import java.util.Calendar;

import javax.swing.InputVerifier;

import junit.framework.TestCase;

import org.jdesktop.swingx.JXDatePicker;

import br.com.procempa.modus.ui.JXDatePickerInputVerifier;

public class DateInputVerifierTest extends TestCase {
	
	public void testVerifier() {
		
		InputVerifier verifier = new JXDatePickerInputVerifier();

		//Data Válida
		JXDatePicker picker = new JXDatePicker();
		picker.setInputVerifier(verifier);
		assertTrue(picker.getInputVerifier().shouldYieldFocus(picker));

		//Data Inválida
		Calendar calendar = Calendar.getInstance();
		calendar.set(10000, 12, 31);
		picker = new JXDatePicker(calendar.getTimeInMillis());
		picker.setInputVerifier(verifier);
		assertFalse(picker.getInputVerifier().shouldYieldFocus(picker));
	}
}

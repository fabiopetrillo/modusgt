package br.com.procempa.modus.ui;

import java.util.Calendar;

import javax.swing.InputVerifier;
import javax.swing.JComponent;

import org.jdesktop.swingx.JXDatePicker;

public class JXDatePickerInputVerifier extends InputVerifier {

	@Override
	public boolean verify(JComponent input) {
		JXDatePicker picker = (JXDatePicker) input;
		Calendar cal = Calendar.getInstance();
		cal.setTime(picker.getDate());
		if ( cal.get(Calendar.YEAR) > 9999) {
			return false;
		}
		return true;
	}
}

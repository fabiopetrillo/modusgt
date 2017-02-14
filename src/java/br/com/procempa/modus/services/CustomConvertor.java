package br.com.procempa.modus.services;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.betwixt.expression.Context;
import org.apache.commons.betwixt.strategy.ObjectStringConverter;

public class CustomConvertor extends ObjectStringConverter {
	
	private static final long serialVersionUID = 6261670216193728796L;
	private static final SimpleDateFormat formatter = new SimpleDateFormat(
			"dd/MM/yyyy");

	@Override
	public String objectToString(Object object, Class type,	Context context) {
		if (object != null) {
			if (object instanceof java.util.Date) {
				return formatter.format((java.util.Date) object);
			}
		}
		return super.objectToString(object, type, context);
	}

	@Override
	public Object stringToObject(String string, Class type, Context context) {
		if (string != null) {
			if (type == java.lang.Integer.class) {
				return Integer.parseInt(string);
			} else if (type == java.lang.Long.class) {
				return Long.parseLong(string);
			} else if (type == java.util.Date.class) {
				String strFormat = "dd/MM/yyyy"; 
				DateFormat myDateFormat = new SimpleDateFormat(strFormat);
				Date myDate = null;
				try {
				     myDate = myDateFormat.parse(string);
				} catch (ParseException e) {
				     System.out.println("@@@@ Invalid Date Parser Exception ");
				     e.printStackTrace();
				}				
				return myDate;
			} else if (type == java.lang.Boolean.class) {
				return string == "1" ? Boolean.TRUE : Boolean.FALSE;
			}
		}
		return super.stringToObject(string, type, context);
	}
}
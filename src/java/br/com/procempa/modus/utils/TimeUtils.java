package br.com.procempa.modus.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;

public class TimeUtils {
	/**
	 * Monta um string com dias horas:minutos:segundos
	 * 
	 * @param time
	 *            tempo a ser apresentado
	 * @return string formatado
	 */
	public static String formatTime(long time) {
		long horas, minutos, segundos;
		
		boolean isNegative = (time < 0);
		time = Math.abs(time);
		horas = (time / DateUtils.MILLIS_PER_HOUR);
		minutos = (time / DateUtils.MILLIS_PER_MINUTE)
				- (horas * 60);
		segundos = (time / DateUtils.MILLIS_PER_SECOND)
				- (((horas * 60) + minutos) * 60);
		String stringH = StringUtils.leftPad(String.valueOf(horas), 2, "0");
		String stringM = StringUtils.leftPad(String.valueOf(minutos), 2,
				"0");
		String stringS = StringUtils.leftPad(String.valueOf(segundos), 2,
				"0");
		String result = stringH + ":" + stringM + ":" + stringS;
		if (isNegative){
			result = "-" + result;
		}
		
		return result;
	}
	
	public static Date initialDate(Date date) {
		int year;
		int month;
		int day;

		SimpleDateFormat df = new SimpleDateFormat("yyyy");
		year = Integer.parseInt(df.format(date));
		
		df = new SimpleDateFormat("MM");
		month = Integer.parseInt(df.format(date));
		
		df = new SimpleDateFormat("dd");
		day = Integer.parseInt(df.format(date));

		GregorianCalendar gc = new GregorianCalendar(year, month-1, day);
		
		return gc.getTime();
	}
}

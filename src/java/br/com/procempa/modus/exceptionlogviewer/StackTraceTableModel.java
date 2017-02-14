package br.com.procempa.modus.exceptionlogviewer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import br.com.procempa.modus.services.ExceptionLogService;

public class StackTraceTableModel implements TableModel {

	private static final long serialVersionUID = -3490359825503403512L;
	private static String columnName[] = new String [] {"Classe","Método","Arquivo","Linha"};
	private String stackTrace;
	private String traceMatrix[][];
	private int countLine = -1;

	
	public StackTraceTableModel(String trace) {
		this.stackTrace = trace;
	}

	private void buildMatrix() {
		
		BufferedReader bufferReader = new BufferedReader(new StringReader(
				stackTrace));
		try {
			bufferReader.mark(stackTrace.length() + 1);
			countLine = 0;
			while (bufferReader.readLine() != null) {
				countLine++;
			}
			traceMatrix = new String[countLine][4];
			
			bufferReader.reset();

			for (int i = 0; i < countLine; i++) {
				int next = 0;
				String trace = bufferReader.readLine();
				for (int j = 0; j < 4; j++) {
					int tmp = trace.indexOf(ExceptionLogService.STACK_DELIMITER, next);
					int lastPosition = tmp != -1 ? tmp : trace.length();  
					traceMatrix[i][j] = trace.substring(next,  lastPosition); 
					next = lastPosition + 1;
				}
			}
		} catch (IOException e) {
			// TODO colocar tratamento de exceções
			e.printStackTrace();
		}
	}

	public Object getValueAt(int row, int col) {
		return (String) traceMatrix[row][col]; 
	}

	public void addTableModelListener(TableModelListener arg0) {
		// TODO Auto-generated method stub
	}

	public Class<?> getColumnClass(int c) {
		Object o = getValueAt(0, c);
		if (null == o) {
			o = new Object();
		}
		return o.getClass();
	}

	public int getColumnCount() {
		return 4;
	}

	public String getColumnName(int col) {
		return columnName[col];
	}

	public int getRowCount() {
		if (countLine == -1) {
			buildMatrix();
			return 0;
		} else {
			return countLine;
		}
	}

	public boolean isCellEditable(int arg0, int arg1) {
		return false;
	}

	public void removeTableModelListener(TableModelListener arg0) {
		// TODO Auto-generated method stub
	}

	public void setValueAt(Object arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
	}
}
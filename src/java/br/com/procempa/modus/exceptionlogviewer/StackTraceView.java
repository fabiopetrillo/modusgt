package br.com.procempa.modus.exceptionlogviewer;

import java.awt.BorderLayout;

import javax.swing.ListSelectionModel;

import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.decorator.AlternateRowHighlighter;
import org.jdesktop.swingx.decorator.Highlighter;
import org.jdesktop.swingx.decorator.HighlighterPipeline;

import br.com.procempa.modus.ui.SearchTableModel;

public class StackTraceView extends JXTable{

	private static final long serialVersionUID = -7608244837777540825L;

	private static StackTraceView panel;
	
	SearchTableModel tableModel;
	
	public static StackTraceView getInstance(String stackTrace) {
		panel = new StackTraceView();
		panel.setLayout(new BorderLayout());
		panel.setModel(new StackTraceTableModel(stackTrace));
		panel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		panel.setColumnControlVisible(true);
		panel.setHighlighters(new HighlighterPipeline(
				new Highlighter[] { new AlternateRowHighlighter() }));		
		return panel;
	}
}

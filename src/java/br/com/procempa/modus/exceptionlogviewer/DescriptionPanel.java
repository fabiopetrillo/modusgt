package br.com.procempa.modus.exceptionlogviewer;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import br.com.procempa.modus.entity.ExceptionLog;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class DescriptionPanel extends JPanel{

	private static final long serialVersionUID = -4213549712469534756L;

	private static DescriptionPanel panel;
	
	private ExceptionLog exceptionLog;
	
	private JTextArea contextMessageField;
	private JScrollPane contextMessageScroll;
	private JTextArea messageField;
	private JScrollPane messageScroll;
	private JTextField idField;
	private JTextField exceptionField;
	private JTextField usuarioField;
	private JTextField telecentroField;
	private JTextField timeStampField;
	
	private PanelBuilder builder;
	private CellConstraints cc;

	public static DescriptionPanel getInstance(ExceptionLog exceptionLog) {
		panel = new DescriptionPanel();
		panel.setLayout(new BorderLayout());
		panel.exceptionLog = exceptionLog;
		panel.buildPanel();			
		return panel;
	}

	private void buildPanel() {
		initComponents();
		
		builder.addLabel("Id:", cc.xy(1, 1));
		builder.add(idField, cc.xy(3, 1));		
		builder.addLabel("Usuário:", cc.xy(5, 1));
		builder.add(usuarioField, cc.xy(7, 1));
		builder.addLabel("Telecentro:", cc.xy(9, 1));
		builder.add(telecentroField, cc.xy(11, 1));
		builder.addLabel("Excessão:", cc.xy(1, 3));
		builder.add(exceptionField, cc.xyw(3, 3, 5));
		builder.addLabel("Timestamp:", cc.xy(9, 3));
		builder.add(timeStampField, cc.xy(11, 3));
		builder.addLabel("Mensagem:", cc.xy(1, 5));
		builder.add(messageScroll, cc.xywh(3, 5, 9, 3));
		builder.addLabel("Contexto:", cc.xy(1, 9));
		builder.add(contextMessageScroll, cc.xywh(3, 9, 9, 3));	
		
		add(builder.getPanel());
	}

	private void initComponents() {
		idField = new JTextField(exceptionLog.getId().toString());
		idField.setEditable(false);
		idField.setBackground(Color.WHITE);
		
		timeStampField = new JTextField(exceptionLog.getTimestamp().toString());
		timeStampField.setEditable(false);
		timeStampField.setBackground(Color.WHITE);
		
		exceptionField = new JTextField(exceptionLog.getException());
		exceptionField.setEditable(false);
		exceptionField.setBackground(Color.WHITE);
		
		usuarioField = new JTextField(exceptionLog.getUser());
		usuarioField.setEditable(false);
		usuarioField.setBackground(Color.WHITE);
		
		telecentroField = new JTextField(exceptionLog.getTelecentro());
		telecentroField.setEditable(false);
		telecentroField.setBackground(Color.WHITE);

		messageField = new JTextArea(exceptionLog.getMessage());
		messageField.setWrapStyleWord(true);
		messageField.setLineWrap(true);
		messageField.setEditable(false);
		messageField.setBackground(Color.WHITE);
		messageScroll = new JScrollPane(messageField,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		contextMessageField = new JTextArea(exceptionLog.getContextMessage());
		contextMessageField.setWrapStyleWord(true);
		contextMessageField.setLineWrap(true);
		contextMessageField.setEditable(false);
		contextMessageField.setBackground(Color.WHITE);
		contextMessageScroll = new JScrollPane(contextMessageField,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		builder = new PanelBuilder(getFormLayout());
		builder.setDefaultDialogBorder();
		cc = new CellConstraints();
	}
	
	public FormLayout getFormLayout() {
		FormLayout layout = new FormLayout(
				"right:min, 7dlu, 40dlu:grow, 15dlu, right:min, 7dlu, 125dlu:grow, 15dlu, right:min, 7dlu,85dlu:grow", // cols
				"p,2dlu,p,2dlu,p,15dlu:grow,p,2dlu,p,15dlu:grow,p,0dlu,");// rows
		return layout;
	}
}

package br.com.procempa.modus.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import br.com.procempa.modus.entity.ExceptionLog;
import br.com.procempa.modus.services.ExceptionLogService;
import br.com.procempa.modus.utils.SwingUtils;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class ErrorView extends JFrame {

	private static final long serialVersionUID = 8874072026087323661L;

	static ErrorView view;
	
	private JButton sendButton;
	
	private JButton cancelButton;
	
	private JButton detalhesButton;

	private JTextArea descricaoField;

	private JScrollPane descricaoScroll;
	
	private JTextArea exceptionMessageField;

	private JScrollPane exceptionMessageScroll;

	private JTextArea stackTrace;

	private JScrollPane stackTraceScroll;

	private PanelBuilder builder;

	private CellConstraints cc;

	private JTextArea message;
	
	private ExceptionLog log;

	private ErrorView() {
		super();
	}
	
    public static Component getInstance(ExceptionLog log) {
        view = new ErrorView();
        view.setLog(log);
		view.buildPanel();
		
		view.setSize(new Dimension(500, 250));
		view.setIconImage(IconFactory.createDelete().getImage());
		view.setTitle("Erro");
		view.setAlwaysOnTop(true);
		view.setResizable(false);
		SwingUtils.center(view); 
        return view;
    }
    
    public void buildPanel() {
		initComponents();
		
		Action actionSend = new AbstractAction("") {

			private static final long serialVersionUID = 6046283236100561103L;

			public void actionPerformed(ActionEvent e) {
				ExceptionLogService.updateUserDescription(log, descricaoField.getText());
				dispose();
			}
		};
		
		Action actionCancel = new AbstractAction("") {

			private static final long serialVersionUID = -4024551828179782942L;

			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		};
		
		Action actionDetalhes = new AbstractAction("") {

			private static final long serialVersionUID = -7348547884735352373L;

			public void actionPerformed(ActionEvent e) {
				if (getSize().equals(new Dimension(500, 480))) {
					setSize(new Dimension(500, 250));
					detalhesButton.setText("Detalhes >>");
				} else {
					setSize(new Dimension(500, 480));
					detalhesButton.setText("Detalhes <<");
				}
			}
		};

		sendButton.setAction(actionSend);
		sendButton.setText("Enviar");
		sendButton.setToolTipText("Envia erro e descrição para os administradores");
		
		cancelButton.setAction(actionCancel);
		cancelButton.setText("Cancelar");

		detalhesButton.setAction(actionDetalhes);
		detalhesButton.setText("Detalhes >>");
		detalhesButton.setToolTipText("Mostra os detalhes do erro gerado");
		
		build();
		
		add(builder.getPanel());
	}

	public void initComponents() {
		sendButton = new JButton();
		cancelButton =  new JButton();
		
		detalhesButton = new JButton();

		descricaoField = new JTextArea();
		descricaoField.setLineWrap(true);
		descricaoScroll = new JScrollPane(descricaoField,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		message = new JTextArea();
		String msg = "Ocorreu um erro no sistema. ";
		msg += "Este erro foi identificado com o número " + log.getId() + ". ";
		msg += "Se preferir, descreva a ação que o gerou para facilitar a sua correção.";
		message.setText(msg);
		message.setEnabled(false);
		message.setWrapStyleWord(true);
		message.setLineWrap(true);
		message.setBorder(BorderFactory.createRaisedBevelBorder());
		message.setDisabledTextColor(Color.BLACK);
		message.setFont(new Font(message.getFont().getFontName(),Font.BOLD, message.getFont().getSize()));

		stackTrace = new JTextArea(log.getStackTrace());	
		stackTrace.setEditable(false);
		stackTrace.setBorder(BorderFactory.createRaisedBevelBorder());
		stackTrace.setBackground(Color.WHITE);
		stackTraceScroll = new JScrollPane(stackTrace,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		exceptionMessageField= new JTextArea(log.getException() + ": \n" + log.getMessage());
		exceptionMessageField.setEditable(false);
		exceptionMessageField.setBorder(BorderFactory.createRaisedBevelBorder());
		exceptionMessageField.setBackground(Color.WHITE);
		exceptionMessageScroll = new JScrollPane(exceptionMessageField,
				JScrollPane.VERTICAL_SCROLLBAR_NEVER,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		

		builder = new PanelBuilder(getFormLayout());
		builder.setDefaultDialogBorder();
		cc = new CellConstraints();
	}

	public void build() {
		builder.add(message, cc.xywh(1, 1, 6, 1));
		builder.addSeparator("Descrição", cc.xyw(1, 3, 6));
		builder.add(descricaoScroll, cc.xywh(1, 5, 6, 17));
		builder.add(detalhesButton, cc.xy(1, 23));
		builder.add(sendButton, cc.xy(4, 23));
		builder.add(cancelButton, cc.xy(6, 23));
		builder.addLabel("Mensagem: ", cc.xy(1,25));
		builder.add(exceptionMessageScroll, cc.xywh(1,26,6,5));
		builder.addLabel("StackTrace: ", cc.xy(1,33));
		builder.add(stackTraceScroll, cc.xywh(1, 34, 6, 25));
	}

	public FormLayout getFormLayout() {
		FormLayout layout = new FormLayout(
				"right:pref, 3dlu, 60dlu:grow, right:min, 7dlu, right:min", // cols
				"p,7dlu,p,7dlu,p,3dlu,p,7dlu,p,7dlu,p,7dlu,p,7dlu,p,7dlu,p,7dlu,p,7dlu,p,7dlu,p,11dlu,"
						+ "p,7dlu,p,7dlu,p,7dlu,p,7dlu,p,7dlu,p,7dlu,p,7dlu,p,7dlu,p,7dlu,p,7dlu,"
						+ "p,7dlu,p,7dlu,p,7dlu,p,7dlu,p,7dlu,p,7dlu,p,7dlu,p,7dlu,p,7dlu,p,7dlu,");// rows
		return layout;
	}
	
	public ExceptionLog getLog() {
		return log;
	}

	public void setLog(ExceptionLog log) {
		this.log = log;
	}	
}
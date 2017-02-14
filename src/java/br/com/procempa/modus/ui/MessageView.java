package br.com.procempa.modus.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import br.com.procempa.modus.utils.SwingUtils;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class MessageView extends JFrame{

	private static final long serialVersionUID = -186295103471220038L;

	static MessageView view;
	
	protected JButton okButton;
	
	protected JTextArea message;
	
	private String mensagem;
	
	private JLabel icon;
	
	protected PanelBuilder builder;

	protected CellConstraints cc;
	
	public MessageView(ImageIcon icon, String mensagem) {
		this.mensagem = mensagem;
		this.icon = new JLabel(icon);
	}
	
    public static Component getInstance(ImageIcon icon, String mensagem) {
        view = new MessageView(icon, mensagem);
		view.buildPanel();
		
		view.setSize(new Dimension(320, 170));
		view.setIconImage(IconFactory.createModus().getImage());
		view.setTitle("Atenção");
		view.setAlwaysOnTop(true);
		view.setResizable(false);
		SwingUtils.center(view); 
        return view;
    }
	
	protected void initComponents() {
		
		okButton = new JButton();
		
		message = new JTextArea(mensagem);
		message.setEnabled(false);
		message.setWrapStyleWord(true);
		message.setLineWrap(true);
		message.setBorder(BorderFactory.createRaisedBevelBorder());
		message.setDisabledTextColor(Color.BLACK);
		
		builder = new PanelBuilder(getFormLayout());
		builder.setDefaultDialogBorder();
		cc = new CellConstraints();
	}
	
	public void buildPanel() {
		initComponents();
		
		Action actionOk = new AbstractAction("") {

			private static final long serialVersionUID = 6046283236100561103L;

			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		};

		okButton.setAction(actionOk);
		okButton.setText("OK");
		
		build();
		
		add(builder.getPanel());
	}
	
	public void build() {
		builder.add(icon, cc.xywh(1, 3, 1, 13));
		builder.add(message, cc.xywh(3, 3, 3, 15));	
		builder.add(okButton, cc.xy(5, 19));
	}
	
	public FormLayout getFormLayout() {
		FormLayout layout = new FormLayout(
				"right:pref, 7dlu, 7dlu:grow, 50dlu, right:min", // cols
				"p,4dlu,p,7dlu,p,7dlu,p,7dlu,p,7dlu,p,7dlu,p,7dlu,p,7dlu,p,7dlu,p,7dlu,p,7dlu,p,7dlu,"
				+"p,7dlu,p,7dlu,p,7dlu,p,7dlu,p,7dlu,p,7dlu,p,7dlu,p,7dlu,p,7dlu,p,7dlu,p,7dlu,");// rows
		return layout;
	}
}

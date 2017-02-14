package br.com.procempa.modus.ui;

import java.awt.Component;
import java.awt.Dimension;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import br.com.procempa.modus.utils.SwingUtils;

public class ValidationView extends MessageView{

	private static final long serialVersionUID = 3930776112314582283L;

	static ValidationView view;
	
	private List<String> validationList;
	
	private JTextArea validationField;

	private JScrollPane validationScroll;
	
	public ValidationView(ImageIcon icon, String mensagem, List<String> validationList){
		super(icon, mensagem);
		this.validationList = validationList;
	}
	
	public static Component getInstance(ImageIcon icon, String mensagem, List<String> validationList) {
		view = new ValidationView(icon, mensagem, validationList);
		view.buildPanel();
		
		view.setSize(new Dimension(320, 245));
		view.setIconImage(IconFactory.createModus().getImage());
		view.setTitle("Atenção");
		view.setAlwaysOnTop(true);
		view.setResizable(false);
		SwingUtils.center(view); 
		
        return view;
    }

	@Override
	public void build() {
		super.build();
		
		builder.add(message, cc.xywh(3, 3, 3, 11));
		builder.add(validationScroll, cc.xywh(1, 15, 5, 17));
		builder.add(okButton, cc.xy(5, 33));
	}

	@Override
	protected void initComponents() {
		super.initComponents();
		
		validationField = new JTextArea();
		validationField.setEditable(false);
		
		for (String validation : validationList) {
			validationField.setText(validationField.getText() + " - " + validation + "\n");
		}
		
		validationScroll = new JScrollPane(validationField,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	}
	
	
}

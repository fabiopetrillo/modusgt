package br.com.procempa.modus.stationmonitor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import br.com.procempa.modus.ui.IconFactory;
import br.com.procempa.modus.utils.SwingUtils;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class UnblockScreen extends JFrame {

	private static final long serialVersionUID = -3299868143083096733L;
	
	private static UnblockScreen screen;

	private JButton okButton;
	private JButton cancelButton;
	private JPasswordField keyField;
	
	private PanelBuilder builder;
	private CellConstraints cc;	
	
	private UnblockScreen() {
		setIconImage(IconFactory.createStationMonitorTray().getImage());
		setTitle("Monitor da Estação");
		setAlwaysOnTop(true);
		setUndecorated(true);
		setBackground(Color.BLACK);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setSize(SwingUtils.getScreenDimension());
		toFront();
	}
	
	public static UnblockScreen getInstance() {
		if (screen == null) {
			screen = new UnblockScreen();
			screen.buildPanel();		
		}
		return screen;
	}

	public static void splash() {
		UnblockScreen screen = getInstance();
		screen.setVisible(true);
		screen.requestFocusInWindow();
		screen.keyField.requestFocus();
	}
	
    public void buildPanel() {
		initComponents();
		
		Action actionOk = new AbstractAction("Ok") {

			private static final long serialVersionUID = 6046283236100561103L;

			public void actionPerformed(ActionEvent evt)  {
				try {
					if (AccessKeyManager.isValidKey(keyField.getPassword())) {
						setVisible(false);
						TimerScreen.splash();
					} else {
						BlockScreen.splash();
					}
					keyField.setText("");
				} catch (AccessKeyException e) {
					// TODO Apresentar mensagem de erro ao usuario
					e.printStackTrace();
				}
			}
		};
		okButton.setAction(actionOk);
		
		keyField.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					okButton.getAction().actionPerformed(null);
				}
			}
			public void keyReleased(KeyEvent e) {}
			public void keyTyped(KeyEvent e) {}
		});
		
		Action actionCancel = new AbstractAction("Cancelar") {

			private static final long serialVersionUID = -4024551828179782942L;

			public void actionPerformed(ActionEvent e) {
				keyField.setText("");
				BlockScreen.splash();
			}
		};
		cancelButton.setAction(actionCancel);
		
		
		addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					BlockScreen.splash();
				}
			}
			public void keyReleased(KeyEvent e) {}
			public void keyTyped(KeyEvent e) {}
		});
		
		build();
		
		setLayout(null);
		Dimension d = SwingUtils.getScreenDimension();
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0,(int) d.getWidth(),(int) d.getHeight());
		panel.setBackground(Color.BLACK);
		panel.setLayout(null);

		JPanel form = builder.getPanel();
		form.setBackground(Color.BLACK);
		form.setBounds((int) d.getWidth()/2 - 100, (int) d.getHeight()/2 - 60, 214, 120);
		panel.add(form);
		
		JLabel bkgroup = new JLabel(IconFactory.createUnblock());
		bkgroup.setBounds((int) d.getWidth()/2 - 320, (int) d.getHeight()/2 - 240, 640, 480);
		panel.add(bkgroup);
		
		add(panel);
    }
    
	@Override
	public void paint(Graphics g) {
		Dimension d = SwingUtils.getScreenDimension();
		g.fillRect(0, 0, (int) d.getWidth(), (int) d.getHeight());
		super.paint(g);
	}

	public void initComponents() {
		okButton = new JButton();
		cancelButton =  new JButton();
		keyField = new JPasswordField();
		
		builder = new PanelBuilder(getFormLayout());
		builder.setDefaultDialogBorder();
		cc = new CellConstraints();		
	}
	
	public void build() {
		JLabel label = builder.addLabel("Chave de Acesso", cc.xyw(1,1,3));
		label.setForeground(Color.GRAY);
		Font font = new Font(Font.DIALOG,Font.BOLD,16);
		label.setFont(font);
		builder.add(keyField, cc.xyw(1, 3,3));
		builder.add(okButton, cc.xy(1, 5));
		builder.add(cancelButton, cc.xy(3, 5));
	}
	
	public FormLayout getFormLayout() {
		FormLayout layout = new FormLayout(
				"45dlu, 3dlu, 45dlu", // cols
				"p,7dlu,p,7dlu,p");// rows
		return layout;
	}	
}
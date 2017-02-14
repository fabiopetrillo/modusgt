package br.com.procempa.modus.stationmonitor;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.event.ListDataListener;

import org.apache.commons.lang.StringUtils;

import br.com.procempa.modus.entity.Equipamento;
import br.com.procempa.modus.services.EquipamentoDataServices;
import br.com.procempa.modus.services.Logger;
import br.com.procempa.modus.services.UserContext;
import br.com.procempa.modus.ui.IconFactory;
import br.com.procempa.modus.ui.Login;
import br.com.procempa.modus.utils.CryptoUtils;

public class StationMonitor {

	public static void main(String[] args) {

		if (SystemTray.isSupported()) {
			createSystemTray();
		}

		try {
			UIManager
					.setLookAndFeel("com.jgoodies.looks.plastic.PlasticXPLookAndFeel");
		} catch (Exception e) {
			// Likely PlasticXP is not in the class path; ignore.
		}
		
		boolean modusControl = true;

		// TODO Parser simples; substituir por org.apache.commons.cli
		for (int i = 0; i < args.length; i++) {
			if (args[i].contains("-l")) {
				modusControl = false;
				break;
			}
		}
		
		for (int i = 0; i < args.length; i++) {
			if (args[i].contains("--newkey")) {
				if (args.length >= i+1) {
					try {
						AccessKeyManager.saveKey(CryptoUtils.encripty(args[i+1]));
					} catch (IOException e) {
						JOptionPane.showMessageDialog(null, "Falha ao salvar a chave.");
					}
				}
			}
			
			if (args[i].contains("--time")) {
				if (args.length >= i+1) {
					int time = Integer.parseInt(args[i+1]);
					TimerScreen.getInstance().setSessionTime(time);
				}
			}
		}
		
		if (modusControl) {
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					runModusControl();
				}
			});
		} else {
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					runLocalControl();
				}

			});
		}
	}

	private static void createSystemTray() {
		TrayIcon tray = new TrayIcon(IconFactory.createStationMonitorTray().getImage(),
				"Monitor de Tempo");
		
		tray.setPopupMenu(createTrayMenu());
		try {
			SystemTray.getSystemTray().add(tray);
		} catch (AWTException e) {
			//Se ocorrer alguma exception, realmente
			//não fazer nada; somente imprimir o trace
			e.printStackTrace();
		}
	}
	
	public static PopupMenu createTrayMenu() {
		PopupMenu menu = new PopupMenu();

		final MenuItem itemShowTimer = new MenuItem("Esconder Timer");
		itemShowTimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (TimerScreen.getInstance().isVisible()) {
					TimerScreen.getInstance().setVisible(false);
					itemShowTimer.setLabel("Mostrar Timer");
				} else {
					TimerScreen.splash();
					itemShowTimer.setLabel("Esconder Timer");
				}
			}
		});

		MenuItem itemTerminate = new MenuItem("Encerrar");
		itemTerminate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TimerScreen.getInstance().stopTimer();
				BlockScreen.splash();
			}
		});
		
		
		menu.add(itemShowTimer);
		menu.add(itemTerminate);
		return menu;
	}

	private static void runModusControl() {
		try {
			Equipamento eq = null;

			String rotulo = System.getProperty("rotulo");

			if (StringUtils.isEmpty(rotulo)) {
				Login.show();
				JPanel panel = new JPanel(new BorderLayout());
				JLabel text = new JLabel(
						"Selecione o equipamento a ser controlado:");

				List<Equipamento> eqs = EquipamentoDataServices
						.getNaoControlados(UserContext.getInstance()
								.getTelecentro());
				if (eqs.isEmpty()) {
					JOptionPane
							.showMessageDialog(
									null,
									"Não existem nenhum equipamento disponível. Por favor, "
											+ "cadastre está máquina para torná-la disponível.");
					throw new Exception("Sem máquinas disponíveis.");
				}
				JComboBox stationComboBox = new JComboBox(
						new EquipamentoComboBoxModel(eqs));
				panel.add(text, BorderLayout.NORTH);
				panel.add(stationComboBox, BorderLayout.SOUTH);

				JOptionPane.showMessageDialog(null, panel,
						"Monitor de Estação", JOptionPane.INFORMATION_MESSAGE);

				eq = (Equipamento) stationComboBox.getSelectedItem();

			} else {
				eq = EquipamentoDataServices.getRotulo(rotulo);
			}

			if (eq != null) {
				String ip = System.getProperty("ip");

				Logger.info("Local IP: " + ip);
				try {
					InetAddress.getByName(ip);
				} catch (Exception e) {
					JOptionPane
							.showMessageDialog(
									null,
									"Por favor, informe um endereço "
											+ "ip válido da máquina no arquivo modus.conf.");
					throw new Exception(
							"Informe o ip da máquina no arquivo modus.conf");

				}
				TimerScreen.getInstance().setSessionTime(
						eq.getTelecentro().getTempo());
				BlockScreen.splash();

				// Associa o ip ao equipamento
				eq.setIpAddress(ip);
				MonitorServerSocket.createServer(eq);
			}

			System.exit(0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(-1);
		}
	}

	private static void runLocalControl() {
		BlockScreen.splash();
	}
}

class EquipamentoComboBoxModel implements ComboBoxModel {
	Object item;

	List<Equipamento> items;

	public EquipamentoComboBoxModel(List<Equipamento> list) {
		this.items = list;
	}

	public Object getSelectedItem() {
		return item;
	}

	public void setSelectedItem(Object anItem) {
		item = anItem;
	}

	public void addListDataListener(ListDataListener l) {
	}

	public Object getElementAt(int index) {
		return items.get(index);
	}

	public int getSize() {
		return items.size();
	}

	public void removeListDataListener(ListDataListener l) {
	}
}
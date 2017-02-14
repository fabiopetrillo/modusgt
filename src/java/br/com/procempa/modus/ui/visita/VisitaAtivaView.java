package br.com.procempa.modus.ui.visita;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import org.apache.commons.lang.time.DateFormatUtils;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.table.DefaultTableColumnModelExt;

import br.com.procempa.modus.entity.Equipamento;
import br.com.procempa.modus.entity.Visita;
import br.com.procempa.modus.services.EquipamentoDataServices;
import br.com.procempa.modus.services.Logger;
import br.com.procempa.modus.services.UserContext;
import br.com.procempa.modus.services.VisitaDataServices;
import br.com.procempa.modus.stationmonitor.CommandProcessor;
import br.com.procempa.modus.ui.IconFactory;
import br.com.procempa.modus.ui.InternalPanel;
import br.com.procempa.modus.ui.SearchTableColumn;
import br.com.procempa.modus.ui.SearchTableModel;
import br.com.procempa.modus.ui.equipamento.EquipamentoSearch;
import br.com.procempa.modus.utils.SocketUtils;
import br.com.procempa.modus.utils.SwingUtils;
import br.com.procempa.modus.utils.TimeUtils;

public class VisitaAtivaView extends JFrame {

	private static final long serialVersionUID = -1337855742910683572L;

	static VisitaAtivaView view;

	InternalPanel panel;

	JXTable table;

	JButton saveButton;

	JButton refreshButton;

	JButton waitButton;

	JScrollPane scrollPane;

	ArrayList<ClockLabel> itemsClock = new ArrayList<ClockLabel>();

	Timer timer = new Timer();

	private VisitaAtivaView() {
		setMinimumSize(new Dimension(450, 500));
		setTitle("Modus - Gerenciamento de Telecentros");
		setAlwaysOnTop(false);
		setIconImage(IconFactory.createModus().getImage());
	}

	public static VisitaAtivaView getInstance() {
		if (view == null) {
			view = new VisitaAtivaView();
			view.buildPanel();
		} else {
			view.refresh();
		}
		return view;
	}

	/**
	 * Atualiza a lista de visitas ativas
	 * 
	 */
	public void refresh() {
		((VisitaAtivaTableModel) view.table.getModel()).refresh();
	}

	/**
	 * Reconstrui a janela, sicronizando os timers
	 * 
	 */
	private void remake() {
		timer.cancel();
		timer = new Timer();
		itemsClock.clear();
		scrollPane.getViewport().remove(0);
		scrollPane.getViewport().add(createTable());
		saveButton.setEnabled(false);
	}

	private void initComponents() {
		refreshButton = new JButton();
		saveButton = new JButton();
		waitButton = new JButton();
		scrollPane = new JScrollPane();
		saveButton.setEnabled(false);
		waitButton.setEnabled(true);
	}

	private void buildPanel() {
		initComponents();

		ImageIcon usersIcon = IconFactory.createVisitaAtiva16();

		panel = new InternalPanel(usersIcon, "Visitas Ativas");
		panel.add(scrollPane);

		JToolBar panelToolBar = new JToolBar();
		ImageIcon refreshIcon = IconFactory.createRefresh();
		Action actionRefresh = new AbstractAction("", refreshIcon) {
			private static final long serialVersionUID = 2160290915195663552L;

			public void actionPerformed(ActionEvent e) {
				refresh();
				saveButton.setEnabled(false);
			}
		};
		refreshButton.setAction(actionRefresh);
		refreshButton.setToolTipText("Atualizar");

		ImageIcon waitIcon = IconFactory.createEquipamentoFree16();
		Action actionWait = new AbstractAction("", waitIcon) {
			private static final long serialVersionUID = 8212678574091610500L;

			public void actionPerformed(ActionEvent e) {
				Visita visita = (Visita) table.getValueAt(table
						.getSelectedRow(), -1);
				VisitaDataServices.activeWaitList(visita);
				// Thread para atualização da lista de equipamentos
				new Thread(new Runnable() {
					public void run() {
						EquipamentoSearch.getInstance().refresh();
					}
				}).start();
				remake();
			}
		};
		waitButton.setAction(actionWait);
		waitButton.setToolTipText("Libera equipameto para lista de espera");
		waitButton.setEnabled(false);

		ImageIcon saveIcon = IconFactory.createEncerraVisita();
		Action actionSave = new AbstractAction("", saveIcon) {

			private static final long serialVersionUID = 8212678574091610500L;

			public void actionPerformed(ActionEvent e) {
				// TODO É possível acelerar o encerramento de lotes
				// de visitas colocando da encerramento em threads
				// separadas ou enviando o pacote de visitas ativas
				// para o application server.
				// ArrayList<Thread> threadList = new ArrayList<Thread>();

				for (int row : table.getSelectedRows()) {
					try {
						Visita v = (Visita) table.getValueAt(row, -1);
						Logger.debug("Encerrando visita ativa....");
						v = VisitaDataServices.terminate(v.getId());
						VisitaSearch.getInstance().getTableModel().replaceItem(
								v);
						Logger.debug("Visita ativa encerrada...");
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}

				// Thread para atualização da lista de equipamentos
				new Thread(new Runnable() {
					public void run() {
						EquipamentoSearch.getInstance().refresh();
					}
				}).start();

				// Reconstroi a table para remontar os
				// timers corretamente
				remake();
			}
		};
		saveButton.setAction(actionSave);
		saveButton.setToolTipText("Encerrar Visita");
		saveButton.setEnabled(false);

		panelToolBar.add(refreshButton);
		panelToolBar.add(waitButton);
		panelToolBar.add(saveButton);

		panel.setToolBar(panelToolBar);

		scrollPane.getViewport().add(createTable());
		view.add(panel);
		SwingUtils.center(view);
	}

	protected Component createTable() {
		SearchTableColumn nomeColumn = new SearchTableColumn("usuario.nome",
				"Usuário");
		SearchTableColumn equipamentoColumn = new SearchTableColumn(
				"equipamento.rotulo", "Equipamento");
		SearchTableColumn dataInicioColumn = new SearchTableColumn(
				"dataInicio", "Início");
		SearchTableColumn clckColumn = new SearchTableColumn("", "Tempo");

		DefaultTableColumnModelExt columnModel = new DefaultTableColumnModelExt();
		columnModel.addColumn(nomeColumn);
		columnModel.addColumn(equipamentoColumn);
		columnModel.addColumn(dataInicioColumn);
		columnModel.addColumn(clckColumn);

		table = new JXTable(new VisitaAtivaTableModel(columnModel));
		TableColumn clockColumn = table.getColumnModel().getColumn(3);
		clockColumn.setCellRenderer(new ClockCellRenderer());
		clockColumn.setPreferredWidth(50);

		table.getColumnModel().getColumn(0).setPreferredWidth(170);
		table.getColumnModel().getColumn(1).setPreferredWidth(70);
		table.getColumnModel().getColumn(1).setCellEditor(new EquipamentoComboCellEditor());

		table.getColumnModel().getColumn(2).setPreferredWidth(38);

		table.setRowHeight(20);

		MouseListener tableMouseListener = new MouseListener() {

			public void mouseClicked(MouseEvent e) {
				saveButton.setEnabled(table.getSelectedRowCount() > 0);
				Visita v = (Visita) table
						.getValueAt(table.getSelectedRow(), -1);
				if (v != null) {
					waitButton.setEnabled(v.isListaEspera());
				} else {
					waitButton.setEnabled(false);
				}
			}

			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
			}

			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
			}

			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
			}

			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
			}
		};
		table.addMouseListener(tableMouseListener);
		table.setSortable(false);
		return table;
	}

	class VisitaAtivaTableModel extends SearchTableModel {

		private static final long serialVersionUID = 5121525792254279987L;

		private static final int COLUMN_USUARIO = 0;

		private static final int COLUMN_EQUIPAMENTO = 1;

		private static final int COLUMN_INICIO = 2;

		private static final int COLUMN_CLOCK = 3;

		public VisitaAtivaTableModel(DefaultTableColumnModelExt columnModel) {
			super(columnModel);
		}

		@SuppressWarnings("unchecked")
		@Override
		public List<Visita> getList() {
			try {
				if (list == null) {
					list = VisitaDataServices.getVisitasAtivas(UserContext
							.getInstance().getTelecentro());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return list;
		}

		@Override
		public boolean isCellEditable(int row, int col) {
			return col == COLUMN_EQUIPAMENTO ? true : false;
		}

		public Object getValueAt(int row, int col) {
			Object value = null;

			if (null != table.getRowSorter()) {
				row = table.getRowSorter().convertRowIndexToModel(row);
			}

			switch (col) {
			case COLUMN_USUARIO:
				value = getList().get(row).getUsuario().getNome();
				break;
			case COLUMN_EQUIPAMENTO:
				if (getList().get(row).getEquipamento() != null) {
					value = getList().get(row).getEquipamento().getRotulo();
				} else {
					value = "Lista de Espera";
				}
				break;
			case COLUMN_INICIO:
				value = DateFormatUtils.format(getList().get(row)
						.getDataInicio(), "HH:mm:ss");
				break;
			case COLUMN_CLOCK:
				break;
			default:
				if (row > -1) {
					value = getList().get(row);
				}
			}
			return value;
		}

		@Override
		public void setValueAt(Object aValue, int row, int column) {
			if (column == COLUMN_EQUIPAMENTO) {
				try {
					List<String> messages = new ArrayList<String>();
					Visita visita = (Visita) getList().get(row);

					Equipamento eq = EquipamentoDataServices.getRotulo((String) aValue);
					visita.setEquipamento(eq);

					visita = (Visita) VisitaDataServices.persist((Visita) getList().get(row), messages);
					getList().set(row,VisitaDataServices.getVisita(visita.getId()));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				super.setValueAt(aValue, row, column);
			}
		}
	}

	class ClockCellRenderer implements TableCellRenderer {

		public Component getTableCellRendererComponent(JTable t, Object value,
				boolean isSelected, boolean hasFocus, int row, int column) {
			if (itemsClock.size() <= row) {
				Visita visita = ((Visita) t.getValueAt(row, -1));
				ClockLabel clockLabel = new ClockLabel();
				itemsClock.add(clockLabel);
				ClockTimerTask task = new ClockTimerTask(visita, clockLabel, t);

				timer.schedule(task, 0, 1000);
			}
			return itemsClock.get(row);
		}
	}

	class ClockLabel extends JLabel {

		private static final long serialVersionUID = -8264518923296109337L;

		ClockLabel() {
			setFont(new Font(getFont().getName(), Font.BOLD, 15));
		}
	}

	class ClockTimerTask extends TimerTask implements Runnable {
		JLabel label;

		Visita visita;

		JTable targetTable;

		boolean enviado1 = false;

		boolean enviado2 = false;

		ClockTimerTask(Visita v, JLabel l, JTable t) {
			this.label = l;
			this.visita = v;
			this.targetTable = t;
		}

		@Override
		public void run() {
			label.setText(TimeUtils.formatTime(getTime()));
			if (targetTable != null) {
				targetTable.repaint();
			}

			if (!visita.isListaEspera() && visita.isAtiva()) {
				if (UserContext.getInstance().getTelecentro()
						.getEncerramentoAutomatico()) {

					final int tempo = UserContext.getInstance().getTelecentro()
							.getTempo();
					if (getMinutes() <= tempo) {
						final double delta = tempo - getMinutes();

						if ((delta > 1 && delta < 2) && !enviado2) {
							sendWarm(visita, CommandProcessor.TIME_2MIN_COMMAND);
							enviado2 = true;
						} else if ((delta > 0 && delta < 1) && !enviado1) {
							sendWarm(visita, CommandProcessor.TIME_1MIN_COMMAND);
							enviado1 = true;
						}
					} else {
						// Se acabou o tempo, encerra a visita
						try {
							visita = VisitaDataServices.terminate(visita
									.getId());

							VisitaSearch.getInstance().getTableModel()
									.replaceItem(visita);
							// Thread para atualização da lista de equipamentos
							new Thread(new Runnable() {
								public void run() {
									EquipamentoSearch.getInstance().refresh();
								}
							}).start();
							// TODO Corrigir o problema da piscada depois do
							// remake.
							remake();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

				}
			}
		}

		void sendWarm(Visita visita, final String command) {
			final Equipamento eq = EquipamentoDataServices
					.getEquipamento(visita.getEquipamento().getId());
			System.out.println(eq.isOpened());

			if (eq != null && eq.isOpened()) {
				new Thread(new Runnable() {
					public void run() {
						try {
							SocketUtils.sendCommand(eq.getIpAddress(), eq
									.getPort(), command);

						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}).start();
			}
		}

		public long getStartTime() {
			return visita.getDataInicio().getTime();
		}

		public long getTime() {
			long time = ((new Date()).getTime() - getStartTime());
			return time;
		}

		public double getMinutes() {
			return getTime() / 60000.0;
		}

		public JLabel getLabel() {
			return label;
		}

		public void setLabel(JLabel l) {
			this.label = l;
		}

		public Visita getVisita() {
			return visita;
		}

		public void setVisita(Visita visita) {
			this.visita = visita;
		}
	}
}

class EquipamentoComboCellEditor extends DefaultCellEditor {

	private static final long serialVersionUID = 2888692381405782728L;

	public EquipamentoComboCellEditor() {
		super(new JCheckBox());

		final JComboBox combo = new JComboBox();
		combo.setModel(new EquipamentoLivreComboBoxModel(UserContext
				.getInstance().getTelecentro(), false));
		editorComponent = combo;
		delegate = new EditorDelegate() {

			private static final long serialVersionUID = 5198447967838432076L;

			public Object getCellEditorValue() {
				return combo.getSelectedItem().toString();
			}
		};
		combo.addActionListener(delegate);
		combo.setRequestFocusEnabled(false);
	}
}
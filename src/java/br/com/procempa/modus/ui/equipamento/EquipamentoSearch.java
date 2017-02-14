/*
 * Created on 25/04/2006
 *
 */
package br.com.procempa.modus.ui.equipamento;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.regex.Pattern;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jdesktop.swingx.decorator.Filter;
import org.jdesktop.swingx.decorator.PatternFilter;

import br.com.procempa.modus.entity.Equipamento;
import br.com.procempa.modus.services.EquipamentoDataServices;
import br.com.procempa.modus.ui.IconFactory;
import br.com.procempa.modus.ui.SearchPanel;
import br.com.procempa.modus.ui.SearchTableColumn;
import br.com.procempa.modus.ui.TableMouseListener;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class EquipamentoSearch extends SearchPanel {

	private static final long serialVersionUID = 7220255293813707603L;

	private static EquipamentoSearch panel;

	JButton stationButton;

	JButton downMonitorButton;

	JTextField rotuloField;

	private EquipamentoSearch(ImageIcon icon, String title) {
		super(icon, title);
	}

	public static EquipamentoSearch getInstance() {
		if (panel == null) {
			panel = new EquipamentoSearch(IconFactory.createEquipamento16(),
					"Lista de Equipamentos");
			panel.setTableModel(new EquipamentoTableModel());
			panel.buildPanel();
		}

		return panel;
	}

	protected void initComponents() {
		rotuloField = new JTextField();
		stationButton = new JButton();
		downMonitorButton = new JButton();
		addEnabledButtons(stationButton);
		addEnabledButtons(downMonitorButton);
	}

	@Override
	public void build() {
		addTableColumn(new SearchTableColumn("rotulo", "Rótulo"));
		addTableColumn(new SearchTableColumn("marca", "Marca"));
		addTableColumn(new SearchTableColumn("processador", "Processador"));
		addTableColumn(new SearchTableColumn("ipAddress", "Endereço IP"));
		addTableColumn(new SearchTableColumn("port", "Porta"));
		addTableColumn(new SearchTableColumn("statusDesc", "Status"));

		Action actionStation = new AbstractAction("", IconFactory
				.createEquipamentoFree16()) {
			private static final long serialVersionUID = 4680946165447301272L;

			public void actionPerformed(ActionEvent e) {
				Equipamento eq = (Equipamento) getTable().getValueAt(
						getTable().getSelectedRow(), -1);
				// Atualiza equipamento
				eq = EquipamentoDataServices.getEquipamento(eq.getId());
				if (eq.isOpened()) {
					EquipamentoDataServices.closeStation(eq);
				} else if (eq.isClosed()) {
					EquipamentoDataServices.openStation(eq);
				}
				refresh();
				stationButton.setEnabled(false);
				downMonitorButton.setEnabled(false);				
			}
		};

		stationButton.setAction(actionStation);
		stationButton.setToolTipText("Libera/bloqueia equipamento");
		addToolBarButton(stationButton);
		stationButton.setEnabled(false);

		Action actionMonitorDown = new AbstractAction("", IconFactory
				.createDown16()) {

			private static final long serialVersionUID = -489877560767758884L;

			public void actionPerformed(ActionEvent e) {
				Equipamento eq = (Equipamento) getTable().getValueAt(
						getTable().getSelectedRow(), -1);
				// Atualiza equipamento
				eq = EquipamentoDataServices.getEquipamento(eq.getId());
				if (eq.isControled()) { 
					EquipamentoDataServices.downMonitor(eq);
				}
				refresh();
				stationButton.setEnabled(false);
				downMonitorButton.setEnabled(false);
			}
		};

		downMonitorButton.setAction(actionMonitorDown);
		downMonitorButton.setToolTipText("Derruba controle do equipamento");
		addToolBarButton(downMonitorButton);
		downMonitorButton.setEnabled(false);

		Action actionRotulo = new AbstractAction() {
			private static final long serialVersionUID = -1009802912211411495L;

			public void actionPerformed(ActionEvent e) {
				applyFilters();
			}
		};
		rotuloField.setAction(actionRotulo);
	}

	@Override
	public void clearFilterFields() {
		rotuloField.setText("");
	}

	@Override
	public Action getDeleteAction() {
		return EquipamentoActionFactory.makeDelete();
	}

	@Override
	public Action getEditAction() {
		return EquipamentoActionFactory.makeEdit();
	}

	@Override
	public JPanel getFilterPanel() {
		FormLayout layout = new FormLayout("right:min, 3dlu, 200dlu:grow", // cols
				"p"); // rows

		PanelBuilder builder = new PanelBuilder(layout);
		builder.setDefaultDialogBorder();

		CellConstraints cc = new CellConstraints();

		builder.addLabel("Rótulo:", cc.xy(1, 1));
		builder.add(rotuloField, cc.xy(3, 1));
		return builder.getPanel();
	}

	@Override
	public Filter[] getFilters() {
		return new Filter[] { new PatternFilter(rotuloField.getText(),
				Pattern.CASE_INSENSITIVE, 0) };
	}

	@Override
	public Action getNewAction() {
		return EquipamentoActionFactory.makeNew();
	}

	@Override
	public MouseListener getTableMouseListener() {
		MouseListener tableMouseListener = new TableMouseListener(this) {

			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				if (panel.getTable().getSelectedRowCount() == 1) {
					Equipamento eq = (Equipamento) getTable().getValueAt(
							getTable().getSelectedRow(), -1);
					stationButton.setEnabled(true);
					downMonitorButton.setEnabled(true);
					if (eq.isOpened()) {
						stationButton.setIcon(IconFactory
								.createEquipamentoBlock16());
						stationButton.setToolTipText("Bloqueia Equipamento");

					} else if (eq.isClosed() || eq.isReady()) {
						stationButton.setIcon(IconFactory
								.createEquipamentoFree16());
						stationButton.setToolTipText("Libera Equipamento");
					} else {
						stationButton.setEnabled(false);
						downMonitorButton.setEnabled(false);
					}
				} else {
					stationButton.setEnabled(false);
					downMonitorButton.setEnabled(false);
				}
			}
		};

		return tableMouseListener;
	}
}
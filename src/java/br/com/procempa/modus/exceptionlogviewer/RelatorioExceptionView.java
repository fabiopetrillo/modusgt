package br.com.procempa.modus.exceptionlogviewer;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import org.jdesktop.swingx.JXDatePicker;
import org.jdesktop.swingx.JXTaskPane;
import org.jdesktop.swingx.JXTaskPaneContainer;

import br.com.procempa.modus.report.ExceptionLogDataSource;
import br.com.procempa.modus.services.ExceptionLogService;
import br.com.procempa.modus.services.RelatorioExcecaoVO;
import br.com.procempa.modus.ui.IconFactory;
import br.com.procempa.modus.ui.ReportFactory;
import br.com.procempa.modus.ui.SimplePanel;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.uif_lite.panel.SimpleInternalFrame;

public class RelatorioExceptionView extends SimplePanel{

	private static final long serialVersionUID = -4002627579610313941L;

	private static RelatorioExceptionView panel ;
	
	JXTaskPane taskpane;
	
	SimpleInternalFrame panelHead;
	
	private JXDatePicker datePickerInicial;
	private JXDatePicker datePickerFinal;
	private JPanel relatorioPanel;
	
	private Component relatorioException;
	private Date dataInicial;
	private Date dataFinal;
	
	
	private RelatorioExceptionView(ImageIcon icon, String title) {
		super(icon, title);
	}

	
	public static RelatorioExceptionView getInstance() {
		panel = new RelatorioExceptionView(IconFactory.createCurso16(),
				"Relatório de Exceções");
		panel.dataInicial = new Date();
		panel.dataFinal = new Date();
		panel.buildPanel();
		return panel;
	}
	
	
	public void montaRelatorioExcecoes() {
		HashMap<String, Object> params = new HashMap<String, Object>();
		List<RelatorioExcecaoVO> relatorio = new ArrayList<RelatorioExcecaoVO>();
		try {
			relatorio= ExceptionLogService.getListVO(datePickerInicial.getDate().getTime(), datePickerFinal.getDate().getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		params.put("dataInicial", datePickerInicial.getDate());
		params.put("dataFinal", datePickerFinal.getDate());
		relatorioPanel.add(ReportFactory.getInstance().getComponent("RelatorioExcecoes.xml", params, new ExceptionLogDataSource(relatorio)));
		getMainPanel().add(relatorioPanel);
		getMainPanel().setBorder(null);	
	}

	@Override
	protected void initComponents() {
		datePickerInicial = new JXDatePicker(dataInicial.getTime());
		datePickerInicial.setFormats(new String[] {"EEE dd/MM/yyyy"});
		datePickerFinal = new JXDatePicker(dataFinal.getTime());
		datePickerFinal.setFormats(new String[] {"EEE dd/MM/yyyy"});
		relatorioPanel = new JPanel(new BorderLayout());
	}
	
	@Override
	public Action getCloseAction() {
		return new AbstractAction() {

			private static final long serialVersionUID = -2040136701558742600L;

			public void actionPerformed(ActionEvent e) {
				Main.getInstance().buildPanel(new JLabel(IconFactory.createWelcome()));
			}
		};
	}

	@Override
	public Component buildMainPanel() {		
		setMainPanel(new JXTaskPaneContainer());
		getMainPanel().setLayout(new BorderLayout(0, 10));
		
		JToolBar searchToolBar = new JToolBar();

		taskpane = new JXTaskPane();
		taskpane.setTitle("Período");
		taskpane.setIcon(IconFactory.createAbout());
		taskpane.setLayout(new BorderLayout());
		
		JButton okButton = new JButton();
		Action actionOk = new AbstractAction("", IconFactory.createSearch()) {

			private static final long serialVersionUID = 0L;

			public void actionPerformed(ActionEvent e) {
				relatorioPanel.removeAll();
				panel.montaRelatorioExcecoes();
				relatorioPanel.validate();
				relatorioPanel.repaint();
			}
		};

		okButton.setAction(actionOk);
		okButton.setToolTipText("Gera Relatório");
		searchToolBar.add(okButton);
	
		panelHead = new SimpleInternalFrame("");
		panelHead.add(getParametersPanel());
		panelHead.setToolBar(searchToolBar);
		
		taskpane.add(panelHead, BorderLayout.CENTER);
		
		getMainPanel().add(taskpane, BorderLayout.NORTH);
					
		if(relatorioException != null)
			panel.montaRelatorioExcecoes();
		
		return getMainPanel();
	}
	
	public JPanel getParametersPanel() {
		FormLayout layout = new FormLayout(
				"right:min, 3dlu, 100dlu:grow, 10dlu, right:min,3dlu, 100dlu:grow", // cols
				"p"); // rows

		PanelBuilder builder = new PanelBuilder(layout);
		builder.setDefaultDialogBorder();

		CellConstraints cc = new CellConstraints();

		builder.addLabel("Data Inicial:", cc.xy(1, 1));
		builder.add(datePickerInicial, cc.xy(3, 1));
		builder.addLabel("DataFinal:", cc.xy(5, 1));
		builder.add(datePickerFinal, cc.xy(7, 1));
		return builder.getPanel();
	}
	
}


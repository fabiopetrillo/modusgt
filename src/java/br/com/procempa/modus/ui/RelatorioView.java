package br.com.procempa.modus.ui;

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
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import org.jdesktop.swingx.JXDatePicker;
import org.jdesktop.swingx.JXTaskPane;
import org.jdesktop.swingx.JXTaskPaneContainer;

import br.com.procempa.modus.entity.Telecentro;
import br.com.procempa.modus.entity.Usuario;
import br.com.procempa.modus.entity.Visita;
import br.com.procempa.modus.report.RelatorioUsuariosDataSource;
import br.com.procempa.modus.report.RelatorioVisitaAUDataSource;
import br.com.procempa.modus.report.RelatorioVisitaDataSource;
import br.com.procempa.modus.services.RelatorioVisitaVO;
import br.com.procempa.modus.services.UserContext;
import br.com.procempa.modus.services.UsuarioDataServices;
import br.com.procempa.modus.services.VisitaDataServices;
import br.com.procempa.modus.utils.TimeUtils;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.uif_lite.panel.SimpleInternalFrame;

public class RelatorioView extends SimplePanel{

	private static final String VISITAS_USUARIO = "Visitas por Usuário";

	private static final String VISITAS = "Visitas";

	private static final String USUARIOS = "Usuários";

	private static final long serialVersionUID = -2446709421144462570L;

	private static RelatorioView panel ;
	
	JXTaskPane taskpane;
	
	SimpleInternalFrame panelHead;
	
	private JXDatePicker datePickerInicial;
	private JLabel labelPickerInicial;
	private JXDatePicker datePickerFinal;
	private JLabel labelPickerFinal;
	private JComboBox tipoRelatorio;
	private JPanel relatorioPanel;
	
	private Component relatorio;
	private Date dataInicial;
	private Date dataFinal;
	private Telecentro telecentro;
	
	private RelatorioView(ImageIcon icon, String title) {
		super(icon, title);
	}
	
	public static RelatorioView getInstance() {
		panel = new RelatorioView(IconFactory.createCertificado16(),
				"Relatórios");

		panel.dataInicial = TimeUtils.initialDate(new Date());
		panel.dataFinal = new Date();
		panel.telecentro = UserContext.getInstance().getTelecentro();
		panel.buildPanel();
		return panel;
	}
	
	
	public void montaRelatorio() {
		HashMap<String, Object> params = new HashMap<String, Object>();

		params.put("dataInicial", datePickerInicial.getDate());
		params.put("dataFinal", datePickerFinal.getDate());
		String tipo = (String) tipoRelatorio.getSelectedItem();
		try {
			if( tipo.equals(VISITAS_USUARIO)){
				List<RelatorioVisitaVO> relatorio = new ArrayList<RelatorioVisitaVO>();
				
				relatorio = VisitaDataServices.getListRelatorioAU(datePickerInicial.getDate(), datePickerFinal.getDate(), telecentro);		
				relatorioPanel.add(ReportFactory.getInstance().getComponent("RelatorioVisitaAgrupadoUsuarios.xml", params, new RelatorioVisitaAUDataSource(relatorio)));
			} 
			if(tipo.equals(VISITAS)){
				List<Visita> relatorio = new ArrayList<Visita>();
				
				relatorio = VisitaDataServices.getListRelatorio(datePickerInicial.getDate(), datePickerFinal.getDate(), telecentro);		
				relatorioPanel.add(ReportFactory.getInstance().getComponent("RelatorioVisita.xml", params, new RelatorioVisitaDataSource(relatorio)));		
			}		
			if(tipo.equals(USUARIOS)){
				List<Usuario> relatorio = new ArrayList<Usuario>();
				
				relatorio = UsuarioDataServices.getList();
				relatorioPanel.add(ReportFactory.getInstance().getComponent("RelatorioUsuarios.xml", params, new RelatorioUsuariosDataSource(relatorio)));
			}
			
			getMainPanel().add(relatorioPanel);
			getMainPanel().setBorder(null);	
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void initComponents() {
		datePickerInicial = new JXDatePicker(dataInicial.getTime());
		datePickerInicial.setFormats(new String[] {"EEE dd/MM/yyyy"});
		datePickerInicial.setVisible(false);
		labelPickerInicial = new JLabel("Data Inicial:");
		labelPickerInicial.setVisible(false);
		datePickerFinal = new JXDatePicker(dataFinal.getTime());
		datePickerFinal.setFormats(new String[] {"EEE dd/MM/yyyy"});
		datePickerFinal.setVisible(false);
		labelPickerFinal = new JLabel("Data Final:");
		labelPickerFinal.setVisible(false);
		relatorioPanel = new JPanel(new BorderLayout());
		tipoRelatorio = new JComboBox(new String[]{"", USUARIOS, VISITAS, VISITAS_USUARIO});
		tipoRelatorio.setAction(tipoRelatorioAction());
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
	
	public Action tipoRelatorioAction() {
		return new AbstractAction() {

			private static final long serialVersionUID = 2523853210848713008L;

			public void actionPerformed(ActionEvent e) {
				if(tipoRelatorio.getSelectedItem().equals(VISITAS) || tipoRelatorio.getSelectedItem().equals(VISITAS_USUARIO)){
					datePickerInicial.setVisible(true);
					labelPickerInicial.setVisible(true);
					datePickerFinal.setVisible(true);
					labelPickerFinal.setVisible(true);
				} else {
					datePickerInicial.setVisible(false);
					labelPickerInicial.setVisible(false);
					datePickerFinal.setVisible(false);
					labelPickerFinal.setVisible(false);
				}
			}
		};
	}


	@Override
	public Component buildMainPanel() {		
		setMainPanel(new JXTaskPaneContainer());
		getMainPanel().setLayout(new BorderLayout(0, 10));
		
		JToolBar searchToolBar = new JToolBar();

		taskpane = new JXTaskPane();
		taskpane.setTitle("Parâmetros");
		taskpane.setIcon(IconFactory.createAbout());
		taskpane.setLayout(new BorderLayout());
		
		JButton okButton = new JButton();
		Action actionOk = new AbstractAction("", IconFactory.createSearch()) {

			private static final long serialVersionUID = -3591294419128193505L;

			public void actionPerformed(ActionEvent e) {
				relatorioPanel.removeAll();
				panel.montaRelatorio();
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
					
		if(relatorio != null){
			panel.montaRelatorio();
		}
			
		
		return getMainPanel();
	}
	
	public JPanel getParametersPanel() {
		FormLayout layout = new FormLayout(
				"right:min, 3dlu, 100dlu:grow, 10dlu, right:min,3dlu, 100dlu:grow", // cols
				"p, 7dlu, p"); // rows

		PanelBuilder builder = new PanelBuilder(layout);
		builder.setDefaultDialogBorder();

		CellConstraints cc = new CellConstraints();

		builder.addLabel("Tipo de Relatório:", cc.xy(1, 1));
		builder.add(tipoRelatorio, cc.xy(3, 1));
		builder.add(labelPickerInicial, cc.xy(1, 3));
		builder.add(datePickerInicial, cc.xy(3, 3));
		builder.add(labelPickerFinal, cc.xy(5, 3));
		builder.add(datePickerFinal, cc.xy(7, 3));
		
		
		return builder.getPanel();
	}
	
}

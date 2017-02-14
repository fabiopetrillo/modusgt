package br.com.procempa.modus.ui.curso.inscricao;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;

import org.jdesktop.swingx.JXTaskPaneContainer;

import br.com.procempa.modus.entity.Inscricao;
import br.com.procempa.modus.entity.Turma;
import br.com.procempa.modus.report.CertificadoDataSource;
import br.com.procempa.modus.services.InscricaoDataServices;
import br.com.procempa.modus.ui.IconFactory;
import br.com.procempa.modus.ui.Main;
import br.com.procempa.modus.ui.ReportFactory;
import br.com.procempa.modus.ui.SimplePanel;
import br.com.procempa.modus.ui.curso.CursoSearch;

public class CertificadoView extends SimplePanel {

	private static final long serialVersionUID = 3553694859470208092L;

	private static CertificadoView panel ;
	
	private Turma turma;

	public CertificadoView(ImageIcon icon, String title, Turma turma) {
		super(icon, title);
		this.turma = turma;
	}

	public static CertificadoView getInstance(Turma turma) {
		panel = new CertificadoView(IconFactory.createCertificado16(),
				"Certificado", turma);
		panel.turma = turma;
		panel.buildPanel();
		panel.montaCertificado();
		return panel;
	}
	
	
	public void montaCertificado() {
		HashMap<String, Object> params = new HashMap<String, Object>();
		List<Inscricao> inscritos = new ArrayList<Inscricao>();
		try {
			inscritos = InscricaoDataServices.getList(turma);
		} catch (Exception e) {
			e.printStackTrace();
		}
		getMainPanel().add(ReportFactory.getInstance().getComponent("Certificado.xml", params, new CertificadoDataSource(inscritos)));
		getMainPanel().setBorder(null);	
	}

	@Override
	protected void initComponents() {
	}
	
	@Override
	public Action getCloseAction() {
		return new AbstractAction() {

			private static final long serialVersionUID = -2040136701558742600L;

			public void actionPerformed(ActionEvent e) {
				Main.getInstance().buildPanel(CursoSearch.getInstance());
			}
		};
	}

	@Override
	public Component buildMainPanel() {
		setMainPanel(new JXTaskPaneContainer());
		
		getMainPanel().setLayout(new BorderLayout(0, 10));		
		return getMainPanel();
	}
}

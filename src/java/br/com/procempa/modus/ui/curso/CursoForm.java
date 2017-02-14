package br.com.procempa.modus.ui.curso;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import br.com.procempa.modus.entity.Curso;
import br.com.procempa.modus.services.CursoDataServices;
import br.com.procempa.modus.services.UserContext;
import br.com.procempa.modus.ui.FormPanel;
import br.com.procempa.modus.ui.IconFactory;
import br.com.procempa.modus.ui.Main;

import com.jgoodies.forms.layout.FormLayout;

public class CursoForm extends FormPanel {

	private static final long serialVersionUID = -6782962946661915946L;

	static CursoForm panel;

	Curso curso;

	JTextField nomeField;

	JTextField localField;
	
	JSpinner cargaHorariaSpinner;

	SpinnerModel cargaHorariaModel;

	JSpinner assiduidadeSpinner;

	SpinnerModel assiduidadeModel;

	JTextArea ementaField;

	JScrollPane ementaScroll;

	private CursoForm(ImageIcon icon, String title) {
		super(icon, title);
	}

	public static JComponent getInstance(Curso c) {
		ImageIcon usersIcon = IconFactory.createEquipamento();
		panel = new CursoForm(usersIcon, "Formulário de Curso");

		panel.curso = c;
		panel.buildPanel();
		return panel;
	}

	public void initComponents() {
		nomeField = new JTextField(curso.getNome());
		localField = new JTextField(curso.getLocal());
		cargaHorariaModel = new SpinnerNumberModel(curso.getCargaHorario()
				.intValue(), 0, 10000, 5);
		cargaHorariaSpinner = new JSpinner(cargaHorariaModel);
		assiduidadeModel = new SpinnerNumberModel(curso.getAssiduidade()
				.intValue(), 0, 100, 5);
		assiduidadeSpinner = new JSpinner(assiduidadeModel);
		ementaField = new JTextArea(curso.getEmenta());
		ementaField.setLineWrap(true);
		ementaScroll = new JScrollPane(ementaField,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

	}

	@Override
	public void build() {
		builder.addSeparator("Identificação", cc.xyw(1, 1, 11));
		builder.addLabel("Nome:", cc.xy(1, 3));
		builder.add(nomeField, cc.xyw(3, 3, 9));
		builder.addLabel("Local:", cc.xy(1, 5));
		builder.add(localField, cc.xyw(3, 5, 9));
		builder.addLabel("Carga Horária:", cc.xy(1, 7));
		builder.add(cargaHorariaSpinner, cc.xy(3, 7));
		builder.addLabel("Assiduidade:", cc.xy(5, 7));
		builder.add(assiduidadeSpinner, cc.xy(7, 7));
		builder.addSeparator("Ementa", cc.xyw(1, 9, 11));
		builder.add(ementaScroll, cc.xywh(1, 11, 11, 15));
	}

	@Override
	public Action getCloseAction() {
		Action actionClose = new AbstractAction("") {

			private static final long serialVersionUID = -4774630513288728748L;

			public void actionPerformed(ActionEvent e) {
				Main.getInstance().buildPanel(CursoSearch.getInstance());
			}
		};
		return actionClose;
	}

	@Override
	public FormLayout getFormLayout() {
		FormLayout layout = new FormLayout(
				"right:pref, 3dlu, 75dlu:grow, 7dlu, right:min, 3dlu, 75dlu:grow, 7dlu, right:min, 3dlu, 75dlu:grow", // cols
				"p,7dlu,p,7dlu,p,7dlu,p,7dlu,p,7dlu,p:grow,7dlu,p,7dlu,p,7dlu,p,7dlu,"
						+ "p,7dlu,p,7dlu,p,7dlu,p,7dlu"); // rows
		return layout;
	}

	@Override
	public Action getSaveAction() {
		ImageIcon saveIcon = IconFactory.createSave();
		Action actionSave = new AbstractAction("", saveIcon) {

			private static final long serialVersionUID = 0L;

			public void actionPerformed(ActionEvent e) {
				try {
					curso.setNome(nomeField.getText());
					curso.setTelecentro(UserContext.getInstance()
							.getTelecentro());
					curso.setLocal(localField.getText());
					curso.setCargaHorario((Integer)cargaHorariaSpinner
							.getValue());
					curso.setAssiduidade((Integer) assiduidadeSpinner
							.getValue());
					curso.setEmenta(ementaField.getText());

					List<String> messages = new ArrayList<String>();
					curso = CursoDataServices.persist(curso, messages);

					if (!messages.isEmpty()) {
						StringBuffer message = new StringBuffer();
						for (String m : messages) {
							message.append(m + "\n");
						}
						JOptionPane.showMessageDialog(panel,
								message.toString(), "Erro ao salvar",
								JOptionPane.WARNING_MESSAGE);
					} else {
						// atualiza a tela de Search
						CursoSearch.getInstance().refresh();

						// voltar para a lista
						Main.getInstance()
								.buildPanel(CursoSearch.getInstance());
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}

		};
		return actionSave;
	}
}

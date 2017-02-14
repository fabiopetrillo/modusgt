package br.com.procempa.modus.ui.curso.conteudo;

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

import br.com.procempa.modus.entity.Conteudo;
import br.com.procempa.modus.services.ConteudoDataServices;
import br.com.procempa.modus.ui.FormPanel;
import br.com.procempa.modus.ui.IconFactory;
import br.com.procempa.modus.ui.Main;
import br.com.procempa.modus.ui.curso.CursoSearch;

import com.jgoodies.forms.layout.FormLayout;

public class ConteudoForm extends FormPanel {

	private static final long serialVersionUID = -7183066224025317134L;

	static ConteudoForm panel;

	Conteudo conteudo;

	JTextField nomeField;

	JTextArea descricaoField;

	JScrollPane descricaoScroll;

	JSpinner quantHorasSpinner;

	SpinnerModel quantHorasModel;

	private ConteudoForm(ImageIcon icon, String title) {
		super(icon, title);
	}

	public static JComponent getInstance(Conteudo conteudo) {
		ImageIcon usersIcon = IconFactory.createEquipamento();
		panel = new ConteudoForm(usersIcon, "Formulário de Conteúdo");

		panel.conteudo = conteudo;
		panel.buildPanel();
		return panel;
	}

	public void initComponents() {
		nomeField = new JTextField(conteudo.getNome());
		descricaoField = new JTextArea(conteudo.getDescricao());
		descricaoField.setLineWrap(true);
		descricaoScroll = new JScrollPane(descricaoField,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		quantHorasModel = new SpinnerNumberModel(conteudo.getHorasAula()
				.intValue(), 0, 1000, 5);
		quantHorasSpinner = new JSpinner(quantHorasModel);
	}

	@Override
	public void build() {
		builder.addLabel("Nome:", cc.xy(1, 3));
		builder.add(nomeField, cc.xyw(3, 3, 9));
		builder.addLabel("Quant. Horas:", cc.xy(1, 5));
		builder.add(quantHorasSpinner, cc.xy(3, 5));
		builder.addLabel("Descrição:", cc.xy(1, 7));
		builder.add(descricaoScroll, cc.xywh(3, 7, 9, 14));

	}

	@Override
	public Action getCloseAction() {
		Action actionClose = new AbstractAction("") {

			private static final long serialVersionUID = -1932710788453530283L;

			public void actionPerformed(ActionEvent e) {
				Main.getInstance().buildPanel(CursoSearch.getInstance());
			}
		};
		return actionClose;
	}

	@Override
	public FormLayout getFormLayout() {
		FormLayout layout = new FormLayout(
				"right:max(5dlu;pref), 3dlu, 75dlu:grow, 7dlu, right:min, 3dlu, 75dlu:grow, 7dlu, right:min, 3dlu, 75dlu:grow", // cols
				"p,7dlu,p,7dlu,p,7dlu,p,7dlu,p,7dlu,p,7dlu,p,7dlu,p,7dlu,p,7dlu,"
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

					conteudo.setNome(nomeField.getText());
					conteudo.setHorasAula((Integer) quantHorasSpinner
							.getValue());
					conteudo.setDescricao(descricaoField.getText());

					List<String> messages = new ArrayList<String>();
					conteudo = ConteudoDataServices.persist(conteudo, messages);

					if (!messages.isEmpty()) {
						StringBuffer message = new StringBuffer();
						for (String m : messages) {
							message.append(m + "\n");
						}
						JOptionPane.showMessageDialog(panel,
								message.toString(), "Erro ao salvar",
								JOptionPane.WARNING_MESSAGE);
					} else {
						// voltar para a lista
						ConteudoSearch.getInstance().refresh();
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

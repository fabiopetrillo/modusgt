package br.com.procempa.modus.ui.visita;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.jdesktop.swingx.JXDatePicker;

import br.com.procempa.modus.entity.Equipamento;
import br.com.procempa.modus.entity.Usuario;
import br.com.procempa.modus.entity.Visita;
import br.com.procempa.modus.services.EquipamentoDataServices;
import br.com.procempa.modus.services.UserContext;
import br.com.procempa.modus.services.VisitaDataServices;
import br.com.procempa.modus.ui.FormPanel;
import br.com.procempa.modus.ui.IconFactory;
import br.com.procempa.modus.ui.Main;
import br.com.procempa.modus.ui.equipamento.EquipamentoSearch;
import br.com.procempa.modus.ui.usuario.UsuarioSearch;

import com.jgoodies.forms.layout.FormLayout;

public class VisitaForm extends FormPanel {

	private static final long serialVersionUID = 9024733843020320475L;

	static VisitaForm panel;

	Usuario usuario;

	Visita visita;

	JCheckBox emailCheck;

	JCheckBox jogoCheck;

	JCheckBox chatCheck;

	JCheckBox webCheck;

	JCheckBox escolarCheck;

	JCheckBox cursoCheck;

	JCheckBox oficinaCheck;

	JCheckBox outroCheck;

	JXDatePicker dataInicioField;

	JXDatePicker dataFimField;

	JTextArea observacaoField;

	JComboBox equipamentoCombo;

	JScrollPane observacaoScroll;

	boolean isInsert;

	private VisitaForm(ImageIcon icon, String title) {
		super(icon, title);
	}

	public static JComponent getInstance(Usuario u) {
		panel = new VisitaForm(IconFactory.createVisitaAtiva16(),
				"Registrar Visita");
		panel.usuario = u;
		panel.visita = new Visita();
		panel.isInsert = true;
		panel.buildPanel();
		return panel;
	}

	public static JComponent getInstance(Visita v) {
		panel = new VisitaForm(IconFactory.createUser16(), "Registrar Visita");
		panel.visita = v;
		panel.usuario = v.getUsuario();
		panel.isInsert = false;
		panel.buildPanel();
		return panel;
	}

	public void initComponents() {
		emailCheck = new JCheckBox("Ler e-mail");
		emailCheck.setSelected(visita.getMotivo().getEmail());

		jogoCheck = new JCheckBox("Jogar");
		jogoCheck.setSelected(visita.getMotivo().getJogo());

		chatCheck = new JCheckBox("Usar salas de bate-papo");
		chatCheck.setSelected(visita.getMotivo().getChat());

		webCheck = new JCheckBox("Navegar na Internet");
		webCheck.setSelected(visita.getMotivo().getWeb());

		escolarCheck = new JCheckBox("Trabalhos Escolares");
		escolarCheck.setSelected(visita.getMotivo().getEscolar());

		cursoCheck = new JCheckBox("Curso");
		cursoCheck.setSelected(visita.getMotivo().getCurso());

		oficinaCheck = new JCheckBox("Oficina");
		oficinaCheck.setSelected(visita.getMotivo().getOficina());

		outroCheck = new JCheckBox("Outros");
		outroCheck.setSelected(visita.getMotivo().getOutro());

		dataInicioField = new JXDatePicker(visita.getDataInicio().getTime());
		dataFimField = new JXDatePicker();
		dataFimField.setDate(visita.getDataFim());

		dataInicioField.setFormats(new String[] { "dd/MM/yyyy HH:mm" });
		dataFimField.setFormats(new String[] { "dd/MM/yyyy HH:mm" });

		observacaoField = new JTextArea();
		observacaoField.setLineWrap(true);
		observacaoField.setText(visita.getObservacao());
		observacaoScroll = new JScrollPane(observacaoField,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		try {
			EquipamentoLivreComboBoxModel model = new EquipamentoLivreComboBoxModel(
					UserContext.getInstance().getTelecentro(), isInsert);
			equipamentoCombo = new JComboBox(model);

			if (isInsert) {
				equipamentoCombo.setSelectedIndex(0);
			} else {
				equipamentoCombo.setSelectedIndex(model.indexOf(visita
						.getEquipamento()));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void build() {
		builder.addSeparator("Usuário", cc.xyw(1, 1, 7));
		builder.addLabel("RG:", cc.xy(1, 3));
		builder.addLabel(usuario.getRg(), cc.xy(3, 3));
		builder.addLabel("Nome:", cc.xy(5, 3));
		builder.addLabel(usuario.getNome(), cc.xy(7, 3));

		builder.addSeparator("Objetivos", cc.xyw(1, 5, 7));
		builder.add(emailCheck, cc.xy(3, 7));
		builder.add(webCheck, cc.xy(7, 7));
		builder.add(jogoCheck, cc.xy(3, 9));
		builder.add(chatCheck, cc.xy(7, 9));
		builder.add(cursoCheck, cc.xy(3, 11));
		builder.add(escolarCheck, cc.xy(7, 11));
		builder.add(oficinaCheck, cc.xy(3, 13));
		builder.add(outroCheck, cc.xy(7, 13));

		builder.addSeparator("Complemento", cc.xyw(1, 15, 7));
		builder.addLabel("Início:", cc.xy(1, 17));
		builder.add(dataInicioField, cc.xy(3, 17));
		builder.addLabel("Fim:", cc.xy(5, 17));
		builder.add(dataFimField, cc.xy(7, 17));

		builder.addLabel("Equipamento:", cc.xy(1, 19));
		builder.add(equipamentoCombo, cc.xy(3, 19));

		builder.addLabel("Observação:", cc.xy(1, 21));
		builder.add(observacaoScroll, cc.xywh(1, 23, 7, 3));
	}

	@Override
	public Action getCloseAction() {
		Action actionClose = new AbstractAction() {

			private static final long serialVersionUID = 3461851973571884123L;

			public void actionPerformed(ActionEvent e) {
				Main.getInstance().buildPanel(UsuarioSearch.getInstance());
			}
		};
		return actionClose;
	}

	@Override
	public FormLayout getFormLayout() {
		FormLayout layout = new FormLayout(
				"right:pref,3dlu,90dlu:grow,3dlu,right:pref,3dlu,90dlu:grow", // cols
				"p,7dlu,p,7dlu,p,7dlu,p,7dlu,p,7dlu,p,7dlu,p,7dlu,p,7dlu,p,7dlu,p,7dlu,p,7dlu,p,7dlu,50dlu"); // rows
		return layout;
	}

	@Override
	public Action getSaveAction() {
		Action actionSave = new AbstractAction("", IconFactory.createSave()) {

			private static final long serialVersionUID = -6250504881910303031L;

			public void actionPerformed(ActionEvent e) {
				try {
					visita.setUsuario(usuario);
					visita.setDataInicio(dataInicioField.getDate());
					visita.setDataFim(dataFimField.getDate());
					visita.setObservacao(observacaoField.getText());

					visita.getMotivo().setEmail(emailCheck.isSelected());
					visita.getMotivo().setChat(chatCheck.isSelected());
					visita.getMotivo().setCurso(cursoCheck.isSelected());
					visita.getMotivo().setEscolar(escolarCheck.isSelected());
					visita.getMotivo().setJogo(jogoCheck.isSelected());
					visita.getMotivo().setOficina(oficinaCheck.isSelected());
					visita.getMotivo().setOutro(outroCheck.isSelected());
					visita.getMotivo().setWeb(webCheck.isSelected());

					// Seta o telecentro do monitor
					visita.setTelecentro(UserContext.getInstance()
							.getTelecentro());

					// Se não for lista de espera, seta o equipamento,
					// senão, seta null no equipamento
					Equipamento eq = (Equipamento) equipamentoCombo
							.getSelectedItem();
					if (eq.getId() != "") {
						visita.setEquipamento(eq);
					} else {
						visita.setEquipamento(null);
					}

					List<String> messages = new ArrayList<String>();
					visita = VisitaDataServices.persist(visita, messages);

					if (!messages.isEmpty()) {
						StringBuffer message = new StringBuffer();
						for (String m : messages) {
							message.append(m + "\n");
						}
						JOptionPane.showMessageDialog(panel,
								message.toString(), "Erro ao salvar",
								JOptionPane.WARNING_MESSAGE);
					} else {
						// atualiza a tela de visitas ativas
						VisitaAtivaView.getInstance().refresh();

						if (isInsert) {
							VisitaSearch.getInstance().getTableModel().addItem(
									visita);
						} else {
							VisitaSearch.getInstance().getTableModel()
									.replaceItem(visita);
						}
						// voltar para a lista, por enquanto de usuario
						// TODO Implementar a pilha de chamadas
						Main.getInstance().buildPanel(
								UsuarioSearch.getInstance());

						// Se não for lista de espera, libera a estação
						if (!visita.isListaEspera()) {
							EquipamentoDataServices.openStation(eq);
							// Thread para atualização da lista de equipamentos
							new Thread(new Runnable() {
								public void run() {
									EquipamentoSearch.getInstance().refresh();
								}
							}).start();
						}
					}
				} catch (Exception ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				}
			}
		};
		return actionSave;
	}
}
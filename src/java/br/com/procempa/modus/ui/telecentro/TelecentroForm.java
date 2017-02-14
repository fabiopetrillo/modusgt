package br.com.procempa.modus.ui.telecentro;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import org.apache.commons.lang.StringUtils;

import br.com.procempa.modus.entity.Endereco;
import br.com.procempa.modus.entity.Perfil;
import br.com.procempa.modus.entity.Telecentro;
import br.com.procempa.modus.entity.Usuario;
import br.com.procempa.modus.services.TelecentroDataServices;
import br.com.procempa.modus.services.UserContext;
import br.com.procempa.modus.services.UsuarioDataServices;
import br.com.procempa.modus.ui.FormPanel;
import br.com.procempa.modus.ui.IconFactory;
import br.com.procempa.modus.ui.Main;

import com.jgoodies.forms.layout.FormLayout;

public class TelecentroForm extends FormPanel {

	private static final long serialVersionUID = -3319159752331669247L;

	static TelecentroForm panel;

	boolean isInsert = false;

	Telecentro telecentro;

	JTextField nomeField;

	// Endereco
	JTextField logradouroField;

	JTextField numeroField;

	JTextField complementoField;

	JTextField cepField;

	JTextField bairroField;

	JTextField cidadeField;

	JTextField ufField;

	JTextField paisField;

	JTextField emailField;

	JTextField telefoneField;

	JTextField horarioInicioField;

	JTextField horarioFimField;

	JTextField coordenadorField;

	JTextField monitor1Field;

	JTextField monitor2Field;

	JTextField monitor3Field;

	JTextField turno1Field;

	JTextField turno2Field;

	JTextField turno3Field;

	JSpinner tempoSpinner;

	JCheckBox encerraAutomatico;

	JLabel tempoLabel;

	JCheckBox umPorEquipamento;

	private TelecentroForm(ImageIcon icon, String title) {
		super(icon, title);
	}

	public static JComponent getInstance(Telecentro t) {
		panel = new TelecentroForm(IconFactory.createTelecentro16(),
				"Formulário de Telecentro");
		panel.isInsert = StringUtils.isEmpty(t.getNome());
		panel.telecentro = t;
		panel.buildPanel();
		return panel;
	}

	@Override
	protected void initComponents() {
		nomeField = new JTextField(telecentro.getNome());
		logradouroField = new JTextField(telecentro.getEndereco()
				.getLogradouro());
		numeroField = new JTextField(telecentro.getEndereco().getNumero());
		complementoField = new JTextField(telecentro.getEndereco()
				.getComplemento());
		cepField = new JTextField(telecentro.getEndereco().getLogradouro());
		bairroField = new JTextField(telecentro.getEndereco().getBairro());
		cidadeField = new JTextField(telecentro.getEndereco().getCidade());
		ufField = new JTextField(telecentro.getEndereco().getUf());
		paisField = new JTextField(telecentro.getEndereco().getPais());

		emailField = new JTextField(telecentro.getEmail());
		telefoneField = new JTextField(telecentro.getTelefone());
		horarioInicioField = new JTextField(telecentro.getHorarioInicio());
		horarioFimField = new JTextField(telecentro.getHorarioFim());

		if (telecentro.getCoordenador() != null) {
			coordenadorField = new JTextField(telecentro.getCoordenador()
					.getRg());
		} else {
			coordenadorField = new JTextField();
		}

		if (telecentro.getMonitor1() != null) {
			monitor1Field = new JTextField(telecentro.getMonitor1().getRg());
		} else {
			monitor1Field = new JTextField();
		}
		if (telecentro.getMonitor2() != null) {
			monitor2Field = new JTextField(telecentro.getMonitor2().getRg());
		} else {
			monitor2Field = new JTextField();
		}
		if (telecentro.getMonitor3() != null) {
			monitor3Field = new JTextField(telecentro.getMonitor3().getRg());
		} else {
			monitor3Field = new JTextField();
		}

		turno1Field = new JTextField(telecentro.getTurno1());
		turno2Field = new JTextField(telecentro.getTurno2());
		turno3Field = new JTextField(telecentro.getTurno3());

		if (telecentro.getTempo() == null) {
			telecentro.setTempo(30); // Default 30 min
		}

		if (telecentro.getTempo() < 5) {
			telecentro.setTempo(5); // Seta o valor para o mínimo
		}

		if (telecentro.getEncerramentoAutomatico() == null) {
			telecentro.setEncerramentoAutomatico(false);
		}

		tempoSpinner = new JSpinner(new SpinnerNumberModel(telecentro
				.getTempo().intValue(), 5, 9999, 1));
		tempoSpinner.setValue(telecentro.getTempo());
		tempoSpinner.setEnabled(telecentro.getEncerramentoAutomatico());
		tempoLabel = new JLabel("Tempo");
		tempoLabel.setEnabled(telecentro.getEncerramentoAutomatico());

		encerraAutomatico = new JCheckBox();
		Action encerraAction = new AbstractAction() {
			private static final long serialVersionUID = 4093223332334827658L;

			public void actionPerformed(ActionEvent e) {
				boolean encerra = ((JCheckBox) e.getSource()).isSelected();
				tempoSpinner.setEnabled(encerra);
				tempoLabel.setEnabled(encerra);
			}
		};
		encerraAutomatico.setSelected(telecentro.getEncerramentoAutomatico());
		encerraAutomatico.setAction(encerraAction);
		encerraAutomatico.setText("Encerram. Automático");
		encerraAutomatico
				.setToolTipText("Encerramento automático de visitas ativas");

		umPorEquipamento = new JCheckBox();
		if (telecentro.getUmUsuarioPorEquipamento() == null) {
			telecentro.setUmUsuarioPorEquipamento(true);
		}
		umPorEquipamento.setSelected(telecentro.getUmUsuarioPorEquipamento());
		umPorEquipamento.setText("Um por Equipamento");
		umPorEquipamento
				.setToolTipText("Permite que seja registrada mais de uma visita para no mesmo equipamento");
	}

	@Override
	public void build() {
		builder.addSeparator("Identificação", cc.xyw(1, 1, 11));
		builder.addLabel("Nome:", cc.xy(1, 3));
		builder.add(nomeField, cc.xyw(3, 3, 9));

		builder.addSeparator("Contato", cc.xyw(1, 5, 11));
		builder.addLabel("Endereço:", cc.xy(1, 7));
		builder.add(logradouroField, cc.xyw(3, 7, 9));

		builder.addLabel("Número:", cc.xy(1, 9));
		builder.add(numeroField, cc.xy(3, 9));
		builder.addLabel("Compl.:", cc.xy(5, 9));
		builder.add(complementoField, cc.xy(7, 9));
		builder.addLabel("Bairro:", cc.xy(9, 9));
		builder.add(bairroField, cc.xy(11, 9));

		builder.addLabel("Cidade:", cc.xy(1, 11));
		builder.add(cidadeField, cc.xy(3, 11));
		builder.addLabel("UF:", cc.xy(5, 11));
		builder.add(ufField, cc.xy(7, 11));
		builder.addLabel("CEP:", cc.xy(9, 11));
		builder.add(cepField, cc.xy(11, 11));

		builder.addLabel("País:", cc.xy(1, 13));
		builder.add(paisField, cc.xy(3, 13));
		builder.addLabel("Fone:", cc.xy(5, 13));
		builder.add(telefoneField, cc.xy(7, 13));
		builder.addLabel("Email:", cc.xy(9, 13));
		builder.add(emailField, cc.xy(11, 13));

		builder.addSeparator("Dados Administrativos", cc.xyw(1, 15, 11));
		builder.addLabel("Hor.Início:", cc.xy(1, 17));
		builder.add(horarioInicioField, cc.xy(3, 17));
		builder.addLabel("Hor.Fim:", cc.xy(5, 17));
		builder.add(horarioFimField, cc.xy(7, 17));

		builder.addLabel("Coordenador:", cc.xy(1, 19));
		builder.add(coordenadorField, cc.xy(3, 19));

		builder.add(encerraAutomatico, cc.xy(11, 17));
		builder.add(tempoLabel, cc.xy(9, 19));
		builder.add(tempoSpinner, cc.xy(11, 19));

		builder.addLabel("Monitor1:", cc.xy(1, 21));
		builder.add(monitor1Field, cc.xy(3, 21));
		builder.addLabel("Turno:", cc.xy(5, 21));
		builder.add(turno1Field, cc.xy(7, 21));

		builder.add(umPorEquipamento, cc.xy(11, 21));
		
		builder.addLabel("Monitor2:", cc.xy(1, 23));
		builder.add(monitor2Field, cc.xy(3, 23));
		builder.addLabel("Turno:", cc.xy(5, 23));
		builder.add(turno2Field, cc.xy(7, 23));

		builder.addLabel("Monitor3:", cc.xy(1, 25));
		builder.add(monitor3Field, cc.xy(3, 25));
		builder.addLabel("Turno:", cc.xy(5, 25));
		builder.add(turno3Field, cc.xy(7, 25));
	}

	@Override
	public Action getCloseAction() {
		Action actionClose = new AbstractAction("") {
			private static final long serialVersionUID = -2136668709141752110L;

			public void actionPerformed(ActionEvent e) {
				if (UserContext.getInstance().getUsuario().getPerfil() == Perfil.CIDAT) {
					Main.getInstance().buildPanel(
							TelecentroSearch.getInstance());
				} else {
					setVisible(false);
				}
			}
		};
		return actionClose;
	}

	@Override
	public FormLayout getFormLayout() {
		// 10 Columns e 32 rows
		FormLayout layout = new FormLayout(
				"right:max(5dlu;pref), 3dlu, 75dlu:grow, 7dlu, right:min, 3dlu, 75dlu:grow, 7dlu, right:min, 3dlu, 75dlu:grow", // cols
				"p,7dlu,p,7dlu,p,7dlu,p,7dlu,p,7dlu,p,7dlu,p,7dlu,p,7dlu,p,7dlu,"
						+ "p,7dlu,p,7dlu,p,7dlu,p,7dlu,p,7dlu,p,7dlu,p"); // rows
		return layout;
	}

	@Override
	public Action getSaveAction() {
		Action actionSave = new AbstractAction() {
			private static final long serialVersionUID = -6492486945303531173L;

			public void actionPerformed(ActionEvent e) {
				try {
					telecentro.setNome(nomeField.getText());
					telecentro.setEmail(emailField.getText());

					Endereco endereco = new Endereco();
					endereco.setBairro(bairroField.getText());
					endereco.setCep(cepField.getText());
					endereco.setCidade(cidadeField.getText());
					endereco.setComplemento(complementoField.getText());
					endereco.setLogradouro(logradouroField.getText());
					endereco.setNumero(numeroField.getText());
					endereco.setPais(paisField.getText());
					endereco.setUf(ufField.getText());
					telecentro.setEndereco(endereco);

					telecentro.setHorarioFim(horarioFimField.getText());
					telecentro.setHorarioInicio(horarioInicioField.getText());
					telecentro.setTelefone(telefoneField.getText());
					telecentro.setTurno1(turno1Field.getText());
					telecentro.setTurno2(turno2Field.getText());
					telecentro.setTurno3(turno3Field.getText());

					Usuario coordenador = UsuarioDataServices
							.getUsuarioByRg(coordenadorField.getText());
					telecentro.setCoordenador(coordenador);

					Usuario m1 = UsuarioDataServices
							.getUsuarioByRg(monitor1Field.getText());
					Usuario m2 = UsuarioDataServices
							.getUsuarioByRg(monitor2Field.getText());
					Usuario m3 = UsuarioDataServices
							.getUsuarioByRg(monitor3Field.getText());

					telecentro.setMonitor1(m1);
					telecentro.setMonitor2(m2);
					telecentro.setMonitor3(m3);

					telecentro.setEncerramentoAutomatico(encerraAutomatico
							.isSelected());
					telecentro.setTempo((Integer) tempoSpinner.getValue());

					telecentro.setUmUsuarioPorEquipamento(umPorEquipamento
							.isSelected());

					List<String> messages = new ArrayList<String>();
					telecentro = TelecentroDataServices.persist(telecentro,
							messages);

					if (!messages.isEmpty()) {
						StringBuffer message = new StringBuffer();
						for (String m : messages) {
							message.append(m + "\n");
						}
						JOptionPane.showMessageDialog(panel,
								message.toString(), "Erro ao salvar",
								JOptionPane.WARNING_MESSAGE);
					} else {
						if (isInsert) {
							TelecentroSearch.getInstance().getTableModel()
									.addItem(telecentro);
						} else {
							TelecentroSearch.getInstance().getTableModel()
									.replaceItem(telecentro);
						}

						// voltar para a lista
						if (UserContext.getInstance().getUsuario().getPerfil() == Perfil.CIDAT) {
							Main.getInstance().buildPanel(
									TelecentroSearch.getInstance());
						} else {
							telecentro = TelecentroDataServices
									.getTelecentro(telecentro.getId());
							UserContext.getInstance().setTelecentro(telecentro);
							setVisible(false);
						}
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}

		};
		return actionSave;
	}
}
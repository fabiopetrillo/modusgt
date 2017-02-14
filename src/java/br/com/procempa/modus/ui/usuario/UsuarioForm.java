package br.com.procempa.modus.ui.usuario;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.ComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ListDataListener;

import org.apache.commons.lang.StringUtils;

import br.com.procempa.modus.entity.Deficiencia;
import br.com.procempa.modus.entity.Endereco;
import br.com.procempa.modus.entity.Escolaridade;
import br.com.procempa.modus.entity.EstadoCivil;
import br.com.procempa.modus.entity.Perfil;
import br.com.procempa.modus.entity.Raca;
import br.com.procempa.modus.entity.Sexo;
import br.com.procempa.modus.entity.Usuario;
import br.com.procempa.modus.services.Logger;
import br.com.procempa.modus.services.UsuarioDataServices;
import br.com.procempa.modus.ui.DatePicker;
import br.com.procempa.modus.ui.FormPanel;
import br.com.procempa.modus.ui.IconFactory;
import br.com.procempa.modus.ui.Main;
import br.com.procempa.modus.utils.CryptoUtils;

import com.jgoodies.forms.layout.FormLayout;

public class UsuarioForm extends FormPanel {

	private static final long serialVersionUID = -1327393802971634806L;

	static UsuarioForm panel;

	List<String> messages = new ArrayList<String>();

	boolean isInsert = false;

	Usuario usuario;

	JTextField nomeField;

	JTextField rgField;

	JTextField emissorField;

	JComboBox perfilCombo;

	ButtonGroup sexoGroup;

	JRadioButton mascRadio;

	JRadioButton femRadio;

	JComboBox racaCombo;

	JComboBox estadoCivilCombo;

	DatePicker dataNascimentoField;

	// Endereco
	JTextField logradouroField;

	JTextField numeroField;

	JTextField complementoField;

	JTextField cepField;

	JTextField bairroField;

	JTextField cidadeField;

	JTextField ufField;

	JTextField paisField;

	JCheckBox estudanteCheck;

	JCheckBox trabalhaCheck;

	// Escolaridade
	JComboBox nivelCombo;

	JCheckBox completoCheck;

	JComboBox serieCombo;

	JTextField emailField;

	JPasswordField senhaField;

	JPasswordField senhaRetypeField;

	JTextField telefoneField;

	JTextField ocupacaoField;
	
	JTextArea observacoesField;
	
	JScrollPane observacoesScroll;
	
	JComboBox deficienciaCombo;

	private UsuarioForm(ImageIcon usersIcon, String title) {
		super(usersIcon, title);
	}

	public static JComponent getInstance(Usuario usuario) {
		ImageIcon usersIcon = IconFactory.createUser16();
		panel = new UsuarioForm(usersIcon, "Formulário de Usuário");

		// TODO Refatoracao: Criar estados dos objetos persistentes.
		// Ex: Creating, Created, Updated...
		panel.isInsert = usuario.getRg().length() == 0;

		if (panel.isInsert)
			panel.usuario = usuario;
		else
			try {
				panel.usuario = UsuarioDataServices.getUsuarioByRg(usuario
						.getRg());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		panel.buildPanel();
		return panel;
	}

	protected void initComponents() {
		rgField = new JTextField(StringUtils
				.defaultIfEmpty(usuario.getRg(), ""));
		nomeField = new JTextField(StringUtils.defaultIfEmpty(
				usuario.getNome(), ""));
		emissorField = new JTextField(StringUtils.defaultIfEmpty(usuario
				.getEmissor(), ""));

		perfilCombo = new JComboBox(new ComboBoxModel() {
			Object item;

			String[] items = Perfil.items;

			public void setSelectedItem(Object anItem) {
				item = anItem;
			}

			public Object getSelectedItem() {
				return item;
			}

			public int getSize() {
				return items.length;
			}

			public Object getElementAt(int index) {
				return items[index];
			}

			public void addListDataListener(ListDataListener l) {
				// TODO Auto-generated method stub
			}

			public void removeListDataListener(ListDataListener l) {
				// TODO Auto-generated method stub
			}
		});

		if (usuario.getPerfil() != null) {
			perfilCombo.setSelectedIndex(usuario.getPerfil());
		} else {
			perfilCombo.setSelectedIndex(Perfil.USUARIO);
		}

		sexoGroup = new ButtonGroup();
		mascRadio = new JRadioButton();
		femRadio = new JRadioButton();

		if (usuario.getSexo() == null) {
			usuario.setSexo(Sexo.MASCULINO);
		}

		mascRadio.setSelected(usuario.getSexo().equals(Sexo.MASCULINO));
		femRadio.setSelected(!usuario.getSexo().equals(Sexo.MASCULINO));

		racaCombo = new JComboBox(new ComboBoxModel() {
			Object item;

			String[] items = Raca.items;

			public void setSelectedItem(Object anItem) {
				item = anItem;
			}

			public Object getSelectedItem() {
				return item;
			}

			public int getSize() {
				return items.length;
			}

			public Object getElementAt(int index) {
				return items[index];
			}

			public void addListDataListener(ListDataListener l) {
				// TODO Auto-generated method stub
			}

			public void removeListDataListener(ListDataListener l) {
				// TODO Auto-generated method stub
			}
		});

		if (usuario.getRaca() == null) {
			usuario.setRaca(Raca.BRANCA);
		}
		racaCombo.setSelectedIndex(usuario.getRaca());

		estadoCivilCombo = new JComboBox(new ComboBoxModel() {
			Object item;

			String[] items = EstadoCivil.items;

			public void setSelectedItem(Object anItem) {
				item = anItem;
			}

			public Object getSelectedItem() {
				return item;
			}

			public int getSize() {
				return items.length;
			}

			public Object getElementAt(int index) {
				return items[index];
			}

			public void addListDataListener(ListDataListener l) {
				// TODO Auto-generated method stub
			}

			public void removeListDataListener(ListDataListener l) {
				// TODO Auto-generated method stub
			}
		});

		if (usuario.getEstadoCivil() == null) {
			usuario.setEstadoCivil(EstadoCivil.SOLTEIRO);
		}
		estadoCivilCombo.setSelectedIndex(usuario.getEstadoCivil());

		if (usuario.getDataNascimento() != null) {
			dataNascimentoField = new DatePicker(usuario.getDataNascimento()
					.getTime());
		} else {
			dataNascimentoField = new DatePicker();
		}
		dataNascimentoField.setFormats(new String[] { "dd/MM/yyyy" });
		
		// Endereco
		logradouroField = new JTextField(StringUtils.defaultIfEmpty(usuario
				.getEndereco().getLogradouro(), ""));
		numeroField = new JTextField(StringUtils.defaultIfEmpty(usuario
				.getEndereco().getNumero(), ""));
		complementoField = new JTextField(StringUtils.defaultIfEmpty(usuario
				.getEndereco().getComplemento(), ""));
		cepField = new JTextField(StringUtils.defaultIfEmpty(usuario
				.getEndereco().getCep(), ""));
		bairroField = new JTextField(StringUtils.defaultIfEmpty(usuario
				.getEndereco().getBairro(), ""));
		cidadeField = new JTextField(StringUtils.defaultIfEmpty(usuario
				.getEndereco().getCidade(), ""));
		ufField = new JTextField(StringUtils.defaultIfEmpty(usuario
				.getEndereco().getUf(), ""));
		paisField = new JTextField(StringUtils.defaultIfEmpty(usuario
				.getEndereco().getPais(), ""));

		estudanteCheck = new JCheckBox("Estuda",
				usuario.getEstudante() == null ? false : usuario.getEstudante());
		trabalhaCheck = new JCheckBox("Trabalha",
				usuario.getTrabalha() == null ? false : usuario.getTrabalha());

		// Escolaridade
		nivelCombo = new JComboBox(new ComboBoxModel() {
			Object item;

			String[] items = Escolaridade.items;

			public void setSelectedItem(Object anItem) {
				item = anItem;
			}

			public Object getSelectedItem() {
				return item;
			}

			public int getSize() {
				return items.length;
			}

			public Object getElementAt(int index) {
				return items[index];
			}

			public void addListDataListener(ListDataListener l) {
				// TODO Auto-generated method stub
			}

			public void removeListDataListener(ListDataListener l) {
				// TODO Auto-generated method stub
			}
		});

		if (usuario.getEscolaridade() == null) {
			usuario.setEscolaridade(new Escolaridade());
		}
		nivelCombo.setSelectedIndex(usuario.getEscolaridade().getNivel());

		serieCombo = new JComboBox(new String[] { "", "1ª", "2ª", "3ª", "4ª",
				"5ª", "6ª", "7ª", "8ª", "9ª", "10ª", "11ª", "12ª" });
		serieCombo.setSelectedItem(StringUtils.defaultIfEmpty(usuario
				.getEscolaridade().getSerie(), ""));

		emailField = new JTextField(StringUtils.defaultIfEmpty(usuario
				.getEmail(), ""));
		telefoneField = new JTextField(StringUtils.defaultIfEmpty(usuario
				.getTelefone(), ""));
		
		// Campos não incluidos no formulário
		senhaField = new JPasswordField();
		senhaRetypeField = new JPasswordField();
		completoCheck = new JCheckBox();
		ocupacaoField = new JTextField();
		
		observacoesField =  new JTextArea(usuario.getObservacao());
		observacoesField.setLineWrap(true);
		observacoesScroll = new JScrollPane(observacoesField,
		JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
		JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		deficienciaCombo = new JComboBox(new ComboBoxModel() {
			Object item;

			String[] items = Deficiencia.items;

			public void setSelectedItem(Object anItem) {
				item = anItem;
			}

			public Object getSelectedItem() {
				return item;
			}

			public int getSize() {
				return items.length;
			}

			public Object getElementAt(int index) {
				return items[index];
			}

			public void addListDataListener(ListDataListener l) {
				// TODO Auto-generated method stub
			}

			public void removeListDataListener(ListDataListener l) {
				// TODO Auto-generated method stub
			}
		});
		
		deficienciaCombo.setSelectedIndex(usuario.getDeficiencia());
	}

	@Override
	public Action getCloseAction() {
		Action actionClose = new AbstractAction("") {
			private static final long serialVersionUID = 1975267463075570835L;

			public void actionPerformed(ActionEvent e) {
				Main.getInstance().buildPanel(UsuarioSearch.getInstance());
			}

		};
		return actionClose;
	}

	@Override
	public Action getSaveAction() {
		ImageIcon saveIcon = IconFactory.createSave();
		Action actionSave = new AbstractAction("", saveIcon) {

			private static final long serialVersionUID = -6250504881910303031L;

			public void actionPerformed(ActionEvent e) {
				try {
					usuario.setRg(rgField.getText());
					usuario.setEmissor(emissorField.getText());
					usuario.setPerfil(perfilCombo.getSelectedIndex());
					usuario.setNome(nomeField.getText());
					usuario.setDataNascimento(dataNascimentoField.getDate());
					usuario.setSexo(mascRadio.isSelected() ? Sexo.MASCULINO
							: Sexo.FEMININO);

					Endereco endereco = new Endereco();
					endereco.setLogradouro(logradouroField.getText());
					endereco.setNumero(StringUtils.defaultIfEmpty(numeroField
							.getText(), ""));
					endereco.setComplemento(complementoField.getText());
					endereco.setBairro(bairroField.getText());
					endereco.setCidade(cidadeField.getText());
					endereco.setUf(ufField.getText());
					endereco.setCep(cepField.getText());
					endereco.setPais(paisField.getText());
					usuario.setEndereco(endereco);

					usuario.setTelefone(telefoneField.getText());

					if (StringUtils.isEmpty(emailField.getText())) {
						usuario.setEmail(null);
					} else {
						usuario.setEmail(emailField.getText());
					}

					usuario.setRaca(racaCombo.getSelectedIndex());
					usuario.setEstadoCivil(estadoCivilCombo.getSelectedIndex());

					Escolaridade escolaridade = new Escolaridade();
					escolaridade.setNivel(nivelCombo.getSelectedIndex());
					escolaridade.setSerie(serieCombo.getSelectedItem()
							.toString());
					usuario.setEscolaridade(escolaridade);
					usuario.setEstudante(estudanteCheck.isSelected());

					usuario.setTrabalha(trabalhaCheck.isSelected());

					if (StringUtils.isNotEmpty(String.valueOf(senhaField
							.getPassword()))) {
						usuario.setSenha(CryptoUtils.encripty(usuario.getRg(), senhaField.getPassword()));
					}
					usuario.setObservacao(observacoesField.getText());

					usuario.setDeficiencia(deficienciaCombo.getSelectedIndex());

					// Linha posta no topo, devido a thread
					// List<String> messages = new ArrayList<String>();

					if (!String.valueOf(senhaField.getPassword()).equals(
							String.valueOf(senhaRetypeField.getPassword()))) {
						messages.add("Senha não confere. Por favor, redigite.");
					}

					Logger.info("Iniciando a persistencia");
					usuario = UsuarioDataServices.persist(usuario, messages);
					Logger.info("Persistencia realizada com sucesso");

					if (!messages.isEmpty()) {
						StringBuffer message = new StringBuffer();
						for (String m : messages) {
							message.append(m + "\n");
						}
						JOptionPane.showMessageDialog(panel,
								message.toString(), "Erro ao salvar",
								JOptionPane.WARNING_MESSAGE);
						messages.clear();
					} else {
						// atualiza a tela de Search
						// Logger.info("Iniciando a atualizacao da lista de
						// usuarios");
						// UsuarioSearch.getInstance().refresh();
						// Logger.info("Lista atualizada com sucesso");
						// voltar para a lista

						if (isInsert) {
							UsuarioSearch.getInstance().getTableModel()
									.addItem(usuario);
						} else {
							UsuarioSearch.getInstance().getTableModel()
									.replaceItem(usuario);
						}
						
						//Salva a lista atualizada no Cache
						((UsuarioTableModel) UsuarioSearch.getInstance()
								.getTableModel()).saveCache(UsuarioSearch
								.getInstance().getTableModel().getList());

						Main.getInstance().buildPanel(
								UsuarioSearch.getInstance());
					}
				} catch (Exception ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				}
			}

		};
		return actionSave;
	}

	@Override
	public FormLayout getFormLayout() {
		// 10 Columns e 32 rows
		FormLayout layout = new FormLayout(
				"right:max(5dlu;pref), 3dlu, 75dlu:grow, 7dlu, right:min, 3dlu, 75dlu:grow, 7dlu, right:min, 3dlu, 75dlu:grow", // cols
				"p,4dlu,p,4dlu,p,4dlu,p,4dlu,p,4dlu,p,4dlu,p,4dlu,p,4dlu,p,4dlu,p,4dlu,"
						+ "p,4dlu,p,4dlu,p,4dlu,p,4dlu,p,4dlu,p,4dlu,p:grow,4dlu,p,4dlu,p,4dlu,p,4dlu,p"); // rows
		return layout;
	}

	@Override
	public void build() {
		builder.addSeparator("Identificação", cc.xyw(1, 1, 11));
		builder.addLabel("RG:", cc.xy(1, 3));
		builder.add(rgField, cc.xy(3, 3));
		builder.addLabel("Emissor:", cc.xy(5, 3));
		builder.add(emissorField, cc.xy(7, 3));
		builder.addLabel("Perfil:", cc.xy(9, 3));
		builder.add(perfilCombo, cc.xy(11, 3));

		builder.addLabel("Nome:", cc.xy(1, 5));
		builder.add(nomeField, cc.xyw(3, 5, 9));

		builder.addLabel("Data Nasc.:", cc.xy(1, 7));
		builder.add(dataNascimentoField, cc.xy(3, 7));

		builder.addLabel("Sexo:", cc.xy(5, 7));

		JPanel sexoPanel = new JPanel(new GridLayout(1, 5));
		sexoPanel.add(new JLabel("Masc."));
		sexoPanel.add(mascRadio);
		sexoPanel.add(new JLabel("Fem."));
		sexoPanel.add(femRadio);
		sexoGroup.add(mascRadio);
		sexoGroup.add(femRadio);
		builder.add(sexoPanel, cc.xy(7, 7));

		builder.addSeparator("Contato", cc.xyw(1, 9, 11));
		builder.addLabel("Endereço:", cc.xy(1, 11));
		builder.add(logradouroField, cc.xyw(3, 11, 9));

		builder.addLabel("Número:", cc.xy(1, 13));
		builder.add(numeroField, cc.xy(3, 13));
		builder.addLabel("Compl.:", cc.xy(5, 13));
		builder.add(complementoField, cc.xy(7, 13));
		builder.addLabel("Bairro:", cc.xy(9, 13));
		builder.add(bairroField, cc.xy(11, 13));

		builder.addLabel("Cidade:", cc.xy(1, 15));
		builder.add(cidadeField, cc.xy(3, 15));
		builder.addLabel("UF:", cc.xy(5, 15));
		builder.add(ufField, cc.xy(7, 15));
		builder.addLabel("CEP:", cc.xy(9, 15));
		builder.add(cepField, cc.xy(11, 15));

		builder.addLabel("País:", cc.xy(1, 17));
		builder.add(paisField, cc.xy(3, 17));
		builder.addLabel("Fone:", cc.xy(5, 17));
		builder.add(telefoneField, cc.xy(7, 17));
		builder.addLabel("Email:", cc.xy(9, 17));
		builder.add(emailField, cc.xy(11, 17));

		builder.addSeparator("Informações Sócio-econômicas", cc.xyw(1, 19, 11));
		builder.addLabel("Etnia:", cc.xy(1, 21));
		builder.add(racaCombo, cc.xy(3, 21));
		builder.addLabel("Est.Civil:", cc.xy(5, 21));
		builder.add(estadoCivilCombo, cc.xy(7, 21));
		builder.addLabel("Deficiência:", cc.xy(9, 21));
		builder.add(deficienciaCombo, cc.xy(11, 21));
		

		builder.addLabel("Escolaridade:", cc.xy(1, 23));
		builder.add(nivelCombo, cc.xy(3, 23));
		builder.addLabel("Série:", cc.xy(5, 23));
		builder.add(serieCombo, cc.xy(7, 23));

		JPanel checks = new JPanel(new GridLayout(1, 2));
		checks.add(estudanteCheck);
		checks.add(trabalhaCheck);
		builder.add(checks, cc.xyw(9, 23, 3));
		
		builder.addSeparator("Observações", cc.xyw(1, 25, 11));
		builder.add(observacoesScroll, cc.xywh(1, 27,11,11));
		
		builder.addLabel("Senha:", cc.xy(1, 41));
		builder.add(senhaField, cc.xy(3, 41));
		builder.addLabel("Redigite:", cc.xy(5, 41));
		builder.add(senhaRetypeField, cc.xy(7, 41));
	}
}
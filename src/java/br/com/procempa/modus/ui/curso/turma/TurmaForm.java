package br.com.procempa.modus.ui.curso.turma;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import br.com.procempa.modus.entity.Curso;
import br.com.procempa.modus.entity.Turma;
import br.com.procempa.modus.services.TurmaDataServices;
import br.com.procempa.modus.ui.FormPanel;
import br.com.procempa.modus.ui.IconFactory;
import br.com.procempa.modus.ui.Main;
import br.com.procempa.modus.ui.curso.CursoSearch;

import com.jgoodies.forms.layout.FormLayout;

public class TurmaForm extends FormPanel {

	private static final long serialVersionUID = 3432422123795099631L;

	static TurmaForm panel;
	    
	   Turma turma;
	   
	   private Curso curso;
	    
	    JTextField nomeField;
		JSpinner vagasSpinner;
		SpinnerModel vagasModel;
	    JTextField diaHoraField;
	    JTextField periodoField;
	    JCheckBox statusBox;
	    
	    private TurmaForm(ImageIcon icon, String title, Curso curso) {
	        super(icon, title);
	        this.curso = curso;
	    }

	    public static JComponent getInstance(Turma turma) {
	        ImageIcon usersIcon = IconFactory.createEquipamento();
	        panel = new TurmaForm(usersIcon,"Formulário de Turma", turma.getCurso()); 
	        
	        panel.turma = turma;
	        panel.buildPanel();        
	        return panel;
	    }

	    public void initComponents() {
	        nomeField  = new JTextField(turma.getNome());
			vagasModel = new SpinnerNumberModel(turma.getVagas().intValue(), 0, 1000, 1);
			vagasSpinner = new JSpinner(vagasModel);
	        diaHoraField  = new JTextField(turma.getHorario());
	        periodoField  = new JTextField(turma.getPeriodo());
	        statusBox = new JCheckBox("Turma aberta");
	        statusBox.setSelected(turma.getAberta());
	    }
	    
		@Override
		public void build() {
	        builder.addLabel("Nome:", cc.xy (1, 3));
	        builder.add(nomeField, cc.xyw(3, 3, 9));    
	        builder.addLabel("Dia/Hora:", cc.xy (1,  5));
	        builder.add(diaHoraField, cc.xyw(3, 5, 9));
	        builder.addLabel("Período:", cc.xy (1,  7));
	        builder.add(periodoField, cc.xy(3,  7));
	        builder.addLabel("Vagas:", cc.xy (5,  7));
	        builder.add(vagasSpinner, cc.xy(7,  7));
	        builder.add(statusBox, cc.xy(3,  9));
		}

		@Override
		public Action getCloseAction() {
	        Action actionClose = new AbstractAction("") {

				private static final long serialVersionUID = -2300827245255810575L;

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
	                "p,7dlu,p,7dlu,p,7dlu,p,7dlu,p,7dlu,p,7dlu,p,7dlu,p,7dlu,p,7dlu," +
	                "p,7dlu,p,7dlu,p,7dlu,p,7dlu");      // rows
			return layout;
		}

		@Override
		public Action getSaveAction() {
	        ImageIcon saveIcon = IconFactory.createSave();
	        Action actionSave = new AbstractAction("",saveIcon) {

				private static final long serialVersionUID = -2159863428977207869L;

				public void actionPerformed(ActionEvent e) {
	                try {

	                    turma.setNome(nomeField.getText());
	                    turma.setVagas((Integer)vagasSpinner.getValue());
	                    turma.setHorario(diaHoraField.getText());
	                    turma.setPeriodo(periodoField.getText());
	                    turma.setAberta(statusBox.isSelected());
	                    turma.setCurso(curso);

	                    List<String> messages = new ArrayList<String>(); 
	                    turma = TurmaDataServices.persist(turma, messages);
	                    
	                    if ( ! messages.isEmpty()) {
	                        StringBuffer message = new StringBuffer(); 
	                        for (String m : messages) {
	                            message.append(m + "\n");
	                        }
	                        JOptionPane.showMessageDialog(panel,message.toString(),"Erro ao salvar",JOptionPane.WARNING_MESSAGE);
	                    } else {
	                        //voltar para a lista
	                    		TurmaSearch.getInstance().refresh();
	                        Main.getInstance().buildPanel(CursoSearch.getInstance());                        
	                    }
	                } catch (Exception ex) {
	                    ex.printStackTrace();
	                }
	            }
	            
	        };
			return actionSave;
		}
}

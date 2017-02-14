package br.com.procempa.modus.ui.curso.encontro;

import java.awt.event.ActionEvent;
import java.text.DateFormat;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

import org.jdesktop.swingx.JXDatePicker;
import org.jdesktop.swingx.decorator.Filter;

import br.com.procempa.modus.entity.Turma;
import br.com.procempa.modus.ui.GridPanel;
import br.com.procempa.modus.ui.IconFactory;
import br.com.procempa.modus.ui.Main;
import br.com.procempa.modus.ui.SearchTableColumn;
import br.com.procempa.modus.ui.curso.CursoSearch;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class EncontroView extends GridPanel {

	private static final long serialVersionUID = 7192154424097562438L;

	private static EncontroView panel;

	private Turma turma;

	private EncontroView(ImageIcon icon, String title, Turma t) {
		super(icon, title);
		this.turma = t;
	}

	public static EncontroView getInstance(Turma turma) {
		panel = new EncontroView(IconFactory.createEncontro16(), "Encontros",
				turma);
		panel.setTableModel(new EncontroViewModel(turma));
		panel.buildPanel();
		panel.refresh();
		return panel;
	}

	@Override
	protected void initComponents() {
	}

	@Override
	public void build() {
		addTableColumn(new SearchTableColumn("conteudo.nome", "Conteúdo"));
		SearchTableColumn dataColumn = new SearchTableColumn("data",
				"Data/Hora");
		addTableColumn(dataColumn);
	}

	@Override
	public void clearFilterFields() {
		// TODO Auto-generated method stub
	}

	@Override
	public JPanel getFilterPanel() {
		FormLayout layout = new FormLayout(
				"right:min, 3dlu, 50dlu, 10dlu, right:min,3dlu, 200dlu:grow", // cols
				"p"); // rows

		PanelBuilder builder = new PanelBuilder(layout);
		builder.setDefaultDialogBorder();

		CellConstraints cc = new CellConstraints();

		builder.addLabel("Curso:", cc.xy(1, 1));
		builder.addLabel(turma.getCurso().getNome(), cc.xy(3, 1));

		builder.addLabel("Turma:", cc.xy(5, 1));
		builder.addLabel(turma.getNome(), cc.xy(7, 1));

		return builder.getPanel();
	}

	@Override
	public Filter[] getFilters() {
		return new Filter[] {};
	}

	@Override
	public void buildPanel() {
		super.buildPanel();
		getTable().getColumnModel().getColumn(1).setCellEditor(new JXDatePickerDefaultCellEditor());
	}
	
	@Override
	public Action getCloseAction() {
		return new AbstractAction() {
			private static final long serialVersionUID = -5759886329279530533L;

			public void actionPerformed(ActionEvent e) {
				Main.getInstance().buildPanel(CursoSearch.getInstance());
			}
		};
	}	
}

class JXDatePickerDefaultCellEditor extends DefaultCellEditor {

	private static final long serialVersionUID = 2888692381405782728L;

	public JXDatePickerDefaultCellEditor() {
		super(new JCheckBox());

		editorComponent = new JXDatePicker();
		final JXDatePicker datePicker = (JXDatePicker) editorComponent;
		datePicker.setFormats(new String[] {"EEE dd/MM/yyyy"});
		delegate = new EditorDelegate() {

			private static final long serialVersionUID = 1540110912280113130L;

			public Object getCellEditorValue() {
				return  DateFormat.getDateInstance(DateFormat.DATE_FIELD).format(datePicker.getDate());
			}
		};
		datePicker.addActionListener(delegate);
		datePicker.setRequestFocusEnabled(false);
	}
}

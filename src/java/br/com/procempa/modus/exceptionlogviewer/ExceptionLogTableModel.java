package br.com.procempa.modus.exceptionlogviewer;

import java.util.List;

import org.jdesktop.swingx.table.DefaultTableColumnModelExt;

import br.com.procempa.modus.entity.Persistent;
import br.com.procempa.modus.services.ExceptionLogService;
import br.com.procempa.modus.ui.SearchTableModel;

public class ExceptionLogTableModel extends SearchTableModel {

	private static final long serialVersionUID = -2287665917944608325L;
	
	public ExceptionLogTableModel() {
        super();
    }

    public ExceptionLogTableModel(DefaultTableColumnModelExt columnModel) {
        super(columnModel);
    }
    
    @SuppressWarnings("unchecked")
	@Override
    public List getList() {
        try {
        	if (list == null ) {
            	list = ExceptionLogService.getList();            	
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}

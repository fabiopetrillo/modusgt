/*
 * Created on 26/04/2006
 *
 */
package br.com.procempa.modus.ui;

import org.jdesktop.swingx.table.TableColumnExt;


/**
 * Define uma coluna usada em SearchTableModel.
 * @author bridi
 */
public class SearchTableColumn extends TableColumnExt {

    private static final long serialVersionUID = -5567224211010035799L;
    
    String fieldName;

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String field) {
        this.fieldName = field;
    }

    /**
     * @param fieldName
     * @param headerValue
     */
    public SearchTableColumn(String field, String title) {
        this.fieldName = field;
        setTitle(title);
    }
}

package br.com.procempa.modus.ui;

import javax.swing.Icon;

import com.jgoodies.uif_lite.panel.SimpleInternalFrame;

public class InternalPanel extends SimpleInternalFrame {

    private static final long serialVersionUID = -4111200194185204833L;

    public InternalPanel() {
		super("NoTitle");
	}
	
	public InternalPanel(Icon icon, String title) {
		super(icon, title);
	}

}

package br.com.procempa.modus.ui;

import org.jdesktop.swingx.JXLoginPanel;
import org.jdesktop.swingx.auth.LoginService;

import br.com.procempa.modus.services.ModusLoginService;

public class Login {

	public static void show() {
    	LoginService service = new ModusLoginService();
    	
        JXLoginPanel.Status status = JXLoginPanel.showLoginDialog(null, service);
        
        if (status == JXLoginPanel.Status.SUCCEEDED) {
            return;
        } else 		
        if (status == JXLoginPanel.Status.CANCELLED) {
            System.exit(JXLoginPanel.Status.CANCELLED.ordinal());
        }	
        
	}
}

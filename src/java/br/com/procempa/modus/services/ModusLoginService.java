package br.com.procempa.modus.services;

import org.jdesktop.swingx.auth.LoginService;


public class ModusLoginService extends LoginService {

	@Override
	public boolean authenticate(String userName, char[] password, String flags)
			throws Exception {

		UserContext uc = LoginServices.login(userName, password);
		return uc != null;
	}

}

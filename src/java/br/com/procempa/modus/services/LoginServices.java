package br.com.procempa.modus.services;

import java.util.HashMap;
import java.util.List;

import br.com.procempa.modus.entity.Perfil;
import br.com.procempa.modus.entity.Telecentro;
import br.com.procempa.modus.entity.Usuario;
import br.com.procempa.modus.session.PersistentAccess;
import br.com.procempa.modus.utils.CryptoUtils;

public class LoginServices {

	public static final int FAIL = -1;

	public static UserContext login(String login, char[] password) throws Exception {
		
		UserContext uc = null;
		
		PersistentAccess pa = PersistentAccessFactory.getInstance();
		Usuario usuario = UsuarioDataServices.getUsuarioByRg(login);
		Telecentro telecentro = null;
		
		if(usuario != null) {
			long perfil = CryptoUtils.encripty(login, password).equals(usuario.getSenha()) ? usuario.getPerfil() : FAIL;
			
			//Senha invalida
			if (perfil != FAIL) {
				HashMap<String,Object> params = new HashMap<String,Object>();  
				params.put("usuario", usuario);
	
				if (perfil == Perfil.MONITOR || perfil == Perfil.COORDENADOR) {
					List l = null;
					if (perfil == Perfil.MONITOR) {
						l = pa.search("FROM Telecentro WHERE monitor1 = :usuario OR monitor2 = :usuario OR monitor3 = :usuario", params);
						if(l.isEmpty()) {
							throw new Exception("O monitor " + usuario.getRg() + " não possui nenhum telecentro associado");
						}
					} else if (perfil == Perfil.COORDENADOR) {
						l = pa.search("FROM Telecentro WHERE coordenador = :usuario", params);
						if(l.isEmpty()) {
							Exception e = new Exception("O coordenador " + usuario.getRg() + " não possui nenhum telecentro associado");
							//TODO Construir sistema de log e controle de excecoes 
							e.printStackTrace();
							throw e;
						}
					}
					telecentro = (Telecentro) l.get(0); 
				}
				uc = UserContext.createInstance(usuario, telecentro);
			}
		}
		return uc;
	}
}
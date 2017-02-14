/**
 * 
 */
package br.com.procempa.modus.services;

import br.com.procempa.modus.entity.Telecentro;
import br.com.procempa.modus.entity.Usuario;

/**
 * Value object que contem o contexto do usuário logado.
 * @author petrillo
 *
 */
public class UserContext {

	static UserContext uc;
	
	Usuario usuario;
	Telecentro telecentro;
	
	private UserContext(Usuario u, Telecentro t) {
		this.usuario = u;
		this.telecentro = t;
	}

	public static UserContext createInstance(Usuario u, Telecentro t) {
		uc = new UserContext(u,t);
		return uc;
	}

	public static UserContext getInstance() throws RuntimeException {
		if (uc == null) {
			throw new RuntimeException("Execute o método UserContext.createInstance");
		}
		
		return uc;
	}
	
	public Telecentro getTelecentro() {
		return telecentro;
	}
	public void setTelecentro(Telecentro t) {
		this.telecentro = t;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario u) {
		this.usuario = u;
	}

}

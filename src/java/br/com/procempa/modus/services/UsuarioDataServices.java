package br.com.procempa.modus.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.apache.commons.lang.StringUtils;

import br.com.procempa.modus.entity.Persistent;
import br.com.procempa.modus.entity.Status;
import br.com.procempa.modus.entity.Usuario;
import br.com.procempa.modus.session.PersistentAccess;

public class UsuarioDataServices implements DataServices {

	public static Usuario getUsuario(String id) throws Exception {
		PersistentAccess pa = PersistentAccessFactory.getInstance();
		Usuario u = (Usuario) pa.find(Usuario.class, id);
		return u;
	}

	public static Usuario getUsuarioByRg(String rg) throws UsuarioException {
		Usuario u = null;
		try {
			PersistentAccess pa = PersistentAccessFactory.getInstance();
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("rg", rg);
			List<Persistent> l = pa.search("from Usuario where rg = :rg",
					params);
			if (!l.isEmpty()) {
				u = (Usuario) l.get(0);
			}
		} catch (Exception e) {
			throw new UsuarioException(e.getClass().getName() + ": "
					+ e.getMessage());
		}

		return u;
	}

	/**
	 * Obtém a completa de usuários
	 * 
	 * @throws UsuarioException
	 */
	public static List<Usuario> getList() throws UsuarioException {
		List<Usuario> users = new ArrayList<Usuario>();
		try {
			PersistentAccess pa = PersistentAccessFactory.getInstance();
			List<Persistent> list = pa.search("from Usuario");
			for (Persistent persistent : list) {
				users.add((Usuario) persistent);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new UsuarioException(e.getClass().getName() + ": "
					+ e.getMessage());
		}

		return users;
	}

	public static Usuario persist(Usuario usuario, List<String> messages)
			throws UsuarioException {
		try {
			PersistentAccess pa = PersistentAccessFactory.getInstance();

			if (isValid(usuario, messages)) {
				usuario = (Usuario) pa.persist(usuario);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new UsuarioException(e.getClass().getName() + ": "
					+ e.getMessage());
		}

		return usuario;
	}

	public static void removeUsuario(String id) throws Exception {
		PersistentAccess pa = PersistentAccessFactory.getInstance();
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		List<Persistent> users = pa
				.search(
						"from Usuario as u left join fetch u.visitaList where u.id = :id",
						params);

		if (!users.isEmpty()) {
			Usuario u = (Usuario) users.get(0);
			if (u.getVisitaList().isEmpty()) {
				u.setStatus(Status.EXCLUIDO);
				pa.persist(u);
			} else {
				throw new BusinessException(
						"Desculpe, mas não é possível remover um usuário com visitas registradas.");
			}
		}
	}

	private static boolean isValid(Usuario u, List<String> messages)
			throws Exception {
		boolean status = true;
		boolean isInsert = u.getId() == null || u.getId().equals("");

		if (StringUtils.isEmpty(u.getNome())) {
			messages.add("Informe o nome do usuário");
			status = false;
		}

		if (u.getDataNascimento().after(new Date())) {
			messages.add("Data de nascimento inválida");
			status = false;
		}

		if (StringUtils.isEmpty(u.getRg())) {
			messages.add("Informe o RG");
			status = false;
		} else {
			// Se isInsert, precisa testar a existência de
			// usuário com o mesmo RG
			if (isInsert) {
				PersistentAccess pa = PersistentAccessFactory.getInstance();
				HashMap<String, Object> params = new HashMap<String, Object>();
				params.put("rg", u.getRg());
				List l = pa.search("from Usuario where rg = :rg", params);

				if (!l.isEmpty()) {
					messages.add("Já existe um usuário cadastrado com o RG "
							+ u.getRg());
					status = false;
				}
			}
		}

		// Testar a existência do email, pois é chave única
		if (isInsert && StringUtils.isNotEmpty(u.getEmail())) {
			if (isValidEmail(u.getEmail())) {
				PersistentAccess pa = PersistentAccessFactory.getInstance();
				HashMap<String, Object> params = new HashMap<String, Object>();
				params.put("email", u.getEmail());
				List l = pa.search("from Usuario where email= :email", params);

				if (!l.isEmpty()) {
					messages.add("Já existe um usuário cadastrado com o email "
							+ u.getEmail());
					status = false;
				}
			} else {
				messages.add("Email " + u.getEmail() + " é inválido");
			}
		}

/*		if (StringUtils.isEmpty(u.getEmissor())) {
			messages.add("Informe o órgão emissor");
			status = false;
		}

		if (u.getDataNascimento() == null) {
			messages.add("Informe a data de nascimento");
			status = false;
		}

		if (StringUtils.isEmpty(u.getEndereco().getLogradouro())) {
			messages.add("Informe o endereço");
			status = false;
		}

		if (StringUtils.isEmpty(u.getEndereco().getNumero())) {
			messages.add("Informe o número");
			status = false;
		}

		if (StringUtils.isEmpty(u.getEndereco().getBairro())) {
			messages.add("Informe o bairro");
			status = false;
		}

		if (StringUtils.isEmpty(u.getEndereco().getCidade())) {
			messages.add("Informe a cidade");
			status = false;
		}

		if (StringUtils.isEmpty(u.getEndereco().getPais())) {
			messages.add("Informe o país");
			status = false;
		}

		if (StringUtils.isEmpty(u.getEndereco().getUf())) {
			messages.add("Informe o UF");
			status = false;
		}

		if (u.getEstudante() == null) {
			messages.add("Informe se o usuário é Estudante");
			status = false;
		}

		if (u.getSexo() == null) {
			messages.add("Informe o sexo do usuário");
			status = false;
		}

		if (u.getEstadoCivil() == null) {
			messages.add("Informe o estado civil");
			status = false;
		}

		if (u.getRaca() == null) {
			messages.add("Informe a raça");
			status = false;
		}

		if (isInsert && StringUtils.isEmpty(u.getSenha())) {
			messages.add("A senha não foi informada");
			status = false;
		}
*/
		return status;
	}

	// TODO Substituir pelo validador do Commons
	private static boolean isValidEmail(String aEmailAddress) {
		if (aEmailAddress == null)
			return false;

		boolean result = true;
		try {
			InternetAddress address = new InternetAddress(aEmailAddress);
			address.validate();
		} catch (AddressException ex) {
			result = false;
		}
		return result;
	}
}
package br.com.procempa.modus.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import br.com.procempa.modus.entity.Persistent;
import br.com.procempa.modus.entity.Status;
import br.com.procempa.modus.entity.Telecentro;
import br.com.procempa.modus.session.PersistentAccess;

public class TelecentroDataServices implements DataServices {

	public static Telecentro getTelecentro(String id) throws Exception {
		PersistentAccess pa = PersistentAccessFactory.getInstance();
		Telecentro t = (Telecentro) pa.find(Telecentro.class, id);
		return t;
	}

	public static Telecentro persist(Telecentro t, List<String> messages)
			throws Exception {
		PersistentAccess pa = PersistentAccessFactory.getInstance();

		if (isValid(t, messages)) {
			t = (Telecentro) pa.persist(t);
		}

		return t;
	}

	public static void remove(String id) throws Exception {
		try {
			PersistentAccess pa = PersistentAccessFactory.getInstance();
			Telecentro t = (Telecentro) pa.find(Telecentro.class, id);
			t.setStatus(Status.EXCLUIDO);
			pa.persist(t);
		} catch (Exception e) {
			throw new Exception(e.getClass().getName() + ": " + e.getMessage());
		}
	}

	private static boolean isValid(Telecentro t, List<String> messages)
			throws Exception {
		boolean status = true;
		if (StringUtils.isEmpty(t.getNome())) {
			messages.add("Informe o nome do telecentro");
			status = false;
		}

		if (StringUtils.isEmpty(t.getTelefone())) {
			messages.add("Informe o telefone do telecentro");
			status = false;
		}

		if (StringUtils.isEmpty(t.getHorarioInicio())
				|| StringUtils.isEmpty(t.getHorarioFim())) {
			messages.add("Informe horário de início e fim do expediente");
			status = false;
		}

		if (StringUtils.isEmpty(t.getEndereco().getLogradouro())) {
			messages.add("Informe o logradouro do telecentro");
			status = false;
		}

		if (StringUtils.isEmpty(t.getEndereco().getBairro())) {
			messages.add("Informe o bairro do telecentro");
			status = false;
		}


		if (StringUtils.isEmpty(t.getEndereco().getCep())) {
			messages.add("Informe o cep do telecentro");
			status = false;
		}


		if (StringUtils.isEmpty(t.getEndereco().getCidade())) {
			messages.add("Informe a cidade do telecentro");
			status = false;
		}


		if (StringUtils.isEmpty(t.getEndereco().getNumero())) {
			messages.add("Informe o número do telecentro");
			status = false;
		}

		if (StringUtils.isEmpty(t.getEndereco().getPais())) {
			messages.add("Informe o país do telecentro");
			status = false;
		}

		if (StringUtils.isEmpty(t.getEndereco().getUf())) {
			messages.add("Informe a UF do telecentro");
			status = false;
		}

		if (t.getCoordenador() == null) {
			messages.add("Informe a coordenador");
			status = false;
		} else {
			PersistentAccess pa = PersistentAccessFactory.getInstance();
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("coordenador", t.getCoordenador());
			
			try { 
				List l = pa.search("from Telecentro where coordenador = :coordenador",
						params);
				if (!l.isEmpty() && !((Telecentro) l.get(0)).getId().equals(t.getId())) {
					messages
							.add("O RG informado para o coordenador já está associado a outro Telecentro");
					status = false;
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	
		if (t.getMonitor1() == null) {
			messages.add("Informe o monitor 1");
			status = false;
		} else {
			PersistentAccess pa = PersistentAccessFactory.getInstance();
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("monitor", t.getMonitor1());
			
			try { 
				List l = pa.search("from Telecentro where monitor1 = :monitor",
						params);
				if (!l.isEmpty() && !((Telecentro) l.get(0)).getId().equals(t.getId())) {
					messages
							.add("O RG informado para o monitor 1 já está associado a outro Telecentro");
					status = false;
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (t.getMonitor2() == null) {
			messages.add("Informe a monitor 2");
			status = false;
		} else {
			PersistentAccess pa = PersistentAccessFactory.getInstance();
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("monitor", t.getMonitor2());
			
			try { 
				List l = pa.search("from Telecentro where monitor2 = :monitor",
						params);
				if (!l.isEmpty() && !((Telecentro) l.get(0)).getId().equals(t.getId())) {
					messages
							.add("O RG informado para o monitor 2 já está associado a outro Telecentro");
					status = false;
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (t.getMonitor3() == null) {
			messages.add("Informe a monitor 3");
			status = false;
		} else {
			PersistentAccess pa = PersistentAccessFactory.getInstance();
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("monitor", t.getMonitor3());
			
			try { 
				List l = pa.search("from Telecentro where monitor3 = :monitor",
						params);
				if (!l.isEmpty() && !((Telecentro) l.get(0)).getId().equals(t.getId())) {
					messages
							.add("O RG informado para o monitor 3 já está associado a outro Telecentro");
					status = false;
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return status;
	}

	public static List<Telecentro> getList() throws Exception {
		List<Telecentro> telecentros = new ArrayList<Telecentro>();
		PersistentAccess pa = PersistentAccessFactory.getInstance();
		List<Persistent> list = pa.search("from Telecentro");
		for (Persistent persistent : list) {
			telecentros.add((Telecentro) persistent);
		}
		return telecentros;
	}
}
package br.com.procempa.modus.services;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;

import br.com.procempa.modus.entity.Equipamento;
import br.com.procempa.modus.entity.Persistent;
import br.com.procempa.modus.entity.Status;
import br.com.procempa.modus.entity.Telecentro;
import br.com.procempa.modus.entity.Visita;
import br.com.procempa.modus.session.PersistentAccess;

public class VisitaDataServices implements DataServices {

	public static Visita getVisita(String id) throws Exception {
		PersistentAccess pa = PersistentAccessFactory.getInstance();
		Visita v = (Visita) pa.find(Visita.class, id);
		return v;
	}

	public static Visita persist(Visita visita, List<String> messages)
			throws Exception {
		PersistentAccess pa = PersistentAccessFactory.getInstance();

		if (isValid(visita, messages)) {
			visita = (Visita) pa.persist(visita);
		}
		return visita;
	}

	private static boolean isValid(Visita visita, List<String> messages)
			throws Exception {
		boolean status = true;
		if (visita.getDataInicio() == null) {
			messages.add("Informe a data/hora de início da visita");
			status = false;
		}

		if (isAllUnchecked(visita)) {
			messages.add("Informe algum motivo para a visita");
			status = false;
		}

		if (visita.getUsuario() == null) {
			messages.add("Usuário inválido");
			status = false;
		}

		if (visita.getTelecentro() == null) {
			messages.add("Telecentro inválido");
			status = false;
		}

		return status;
	}

	private static boolean isAllUnchecked(Visita visita) throws Exception {
		Method[] m = visita.getMotivo().getClass().getMethods();
		for (int i = 0; i < m.length; i++) {
			if (m[i].getReturnType().equals(Boolean.class)) {
				Boolean result = (Boolean) m[i].invoke(visita.getMotivo(),
						new Object[] {});
				if (result) {
					return false;
				}
			}
		}
		return true;
	}

	public static void remove(String id) throws Exception {
		PersistentAccess pa = PersistentAccessFactory.getInstance();
		Visita v = (Visita) pa.find(Visita.class, id);
		v.setStatus(Status.EXCLUIDO);
		pa.persist(v);
	}

	public static Visita terminate(String id) throws Exception {
		PersistentAccess pa = PersistentAccessFactory.getInstance();
		Visita v = (Visita) pa.find(Visita.class, id);
		v.setDataFim(new Date());
		v = (Visita) pa.persist(v);
		if (!v.isListaEspera()) {
			Equipamento eq = v.getEquipamento();
			EquipamentoDataServices.closeStation(eq);
		}
		return v;
	}

	public static void activeWaitList(Visita visita) {
		try {
			Telecentro telecentro = UserContext.getInstance().getTelecentro();
			List<Equipamento> eqs = EquipamentoDataServices
					.getLivres(telecentro);
			if (!eqs.isEmpty()) {
				Equipamento eq = eqs.get(0);
				if (visita.isListaEspera()) {
					visita.setEquipamento(eq);
					visita.setDataInicio(new Date());
					persist(visita, new ArrayList<String>());
					EquipamentoDataServices.openStation(eq);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static List<Visita> getList() throws Exception {
		List<Visita> items = new ArrayList<Visita>();
		PersistentAccess pa = PersistentAccessFactory.getInstance();
		List<Persistent> list = pa.search("FROM Visita");
		for (Persistent persistent : list) {
			items.add((Visita) persistent);
		}

		return items;
	}

	/**
	 * Retorna a lista das visitas cuja a diferença entre a data de inicio e fim
	 * é menor que um dia
	 * 
	 * @return lista de visitas
	 * @throws Exception
	 */
	public static List<Visita> getVisitasAtivas(Telecentro telecentro)
			throws Exception {
		List<Visita> items = new ArrayList<Visita>();
		PersistentAccess pa = PersistentAccessFactory.getInstance();

		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("telecentro", telecentro);
		List<Persistent> list = pa.search(
				"FROM Visita WHERE dataFim is null AND telecentro=:telecentro",
				params);
		for (Persistent persistent : list) {
			if (DateUtils.isSameDay(((Visita) persistent).getDataInicio(),
					new Date())) {
				items.add((Visita) persistent);
			}
		}
		return items;
	}

	public static List<Visita> getList(Telecentro telecentro) throws Exception {
		List<Visita> items = new ArrayList<Visita>();
		PersistentAccess pa = PersistentAccessFactory.getInstance();

		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("telecentro", telecentro);
		List<Persistent> list = pa.search(
				"FROM Visita WHERE telecentro=:telecentro", params);
		for (Persistent persistent : list) {
			items.add((Visita) persistent);
		}
		return items;
	}
	
	public static List<Visita> getListRelatorio(Date dataInicial, Date dataFinal, Telecentro telecentro) throws Exception {
		List<Visita> items = new ArrayList<Visita>();		
		PersistentAccess pa = PersistentAccessFactory.getInstance();
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("dataInicio", dataInicial);
		params.put("dataFim", dataFinal);
		params.put("telecentro", telecentro);

		List<Persistent> list = pa
				.search(
						"FROM Visita WHERE dataInicio >= :dataInicio AND dataFim <= :dataFim AND telecentro=:telecentro ORDER BY dataInicio",
						params);
		
		for (Persistent persistent : list) {			
			Visita visita = (Visita) persistent;			
			items.add(visita);
		}
		
		return items;
	}
	
	public static List<RelatorioVisitaVO> getListRelatorioAU(Date dataInicial, Date dataFinal, Telecentro telecentro) throws Exception {
		List<RelatorioVisitaVO> items = new ArrayList<RelatorioVisitaVO>();		
		PersistentAccess pa = PersistentAccessFactory.getInstance();
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("dataInicio", dataInicial);
		params.put("dataFim", dataFinal);
		params.put("telecentro", telecentro);
		
		//TODO Quando a serialização de Arrays estiver funcionando, substituir o VO pela query abaixo
		//"SELECT usario.nome, COUNT(visita), SUM(dataFim - dataInicio) FROM Visita visita WHERE dataInicio >= :dataInicio AND dataFim <= :dataFim",
		
		List<Persistent> list = pa
				.search(
						"FROM Visita WHERE dataInicio >= :dataInicio AND dataFim <= :dataFim AND telecentro=:telecentro ORDER BY usuario.nome",
						params);
		
		String idAnterior = "";
		
		Long totalHoras = new Long(0);
		
		Integer numVisitas = new Integer(0);
		
		if (!list.isEmpty()){
			Persistent persistent = list.get(0);
			Visita v = (Visita) persistent;
			idAnterior = v.getUsuario().getId();
		}	
				
		for (Persistent persistent : list) {			
			Visita visita = (Visita) persistent;			
			if (visita.getUsuario().getId() == idAnterior){
				numVisitas++;
				totalHoras += visita.getDataFim().getTime() - visita.getDataInicio().getTime();
			}
			else{
				RelatorioVisitaVO relatorio = new RelatorioVisitaVO();
				relatorio.setNome(UsuarioDataServices.getUsuario(idAnterior).getNome());
				relatorio.setNumeroVisitas(numVisitas); 			
				relatorio.setTotalHoras(totalHoras);
				items.add(relatorio);
				idAnterior = visita.getUsuario().getId();
				numVisitas = 1;
				totalHoras = visita.getDataFim().getTime() - visita.getDataInicio().getTime();
			}
			
		}
		
		if (!list.isEmpty()) {
			RelatorioVisitaVO relatorio = new RelatorioVisitaVO();
			relatorio.setNome(UsuarioDataServices.getUsuario(idAnterior)
					.getNome());
			relatorio.setNumeroVisitas(numVisitas);
			relatorio.setTotalHoras(totalHoras);
			items.add(relatorio);
		}
		
		return items;
	}
}
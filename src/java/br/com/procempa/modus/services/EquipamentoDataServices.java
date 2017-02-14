/*
 * Created on 25/04/2006
 *
 */
package br.com.procempa.modus.services;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.naming.NamingException;

import org.apache.commons.lang.StringUtils;

import br.com.procempa.modus.entity.Equipamento;
import br.com.procempa.modus.entity.ExceptionLog;
import br.com.procempa.modus.entity.Persistent;
import br.com.procempa.modus.entity.SituacaoEquipamento;
import br.com.procempa.modus.entity.Status;
import br.com.procempa.modus.entity.Telecentro;
import br.com.procempa.modus.entity.Visita;
import br.com.procempa.modus.session.PersistentAccess;
import br.com.procempa.modus.session.exceptions.PersistException;
import br.com.procempa.modus.stationmonitor.CommandProcessor;
import br.com.procempa.modus.utils.SocketUtils;

/**
 * Classe que contém o conjunto de métodos estáticos para manipulação de
 * Equipamentos
 * 
 * @author bridi
 */
public class EquipamentoDataServices implements DataServices {

	/**
	 * Busca um Equipamento a partir do seu identificador
	 * 
	 * @param id
	 *            identificador do equipamento
	 * @return instância de Equipamento
	 */
	public static Equipamento getEquipamento(String id) {
		Equipamento eq = null;
		try {
			PersistentAccess pa = PersistentAccessFactory.getInstance();
			eq = (Equipamento) pa.find(Equipamento.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return eq;
	}

	/**
	 * Busca lista de equipamentos
	 * 
	 * @return lista de equipamentos
	 * @throws Exception
	 */
	public static List<Equipamento> getList() throws Exception {
		List<Equipamento> equipamentos = new ArrayList<Equipamento>();
		PersistentAccess pa = PersistentAccessFactory.getInstance();
		List<Persistent> list = pa.search("FROM Equipamento");
		for (Persistent persistent : list) {
			equipamentos.add((Equipamento) persistent);
		}
		return equipamentos;
	}

	/**
	 * Busca lista de equipamentos de um telecentro
	 * 
	 * @return lista de equipamentos
	 * @throws Exception
	 */
	public static List<Equipamento> getList(Telecentro telecentro)
			throws Exception {
		List<Equipamento> equipamentos = new ArrayList<Equipamento>();
		PersistentAccess pa = PersistentAccessFactory.getInstance();
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("telecentro", telecentro);

		List<Persistent> list = pa
				.search(
						"FROM Equipamento WHERE telecentro = :telecentro ORDER BY rotulo",
						params);
		for (Persistent persistent : list) {
			equipamentos.add((Equipamento) persistent);
		}
		return equipamentos;
	}

	/**
	 * Busca lista de equipamentos controlados de um telecentro
	 * 
	 * @return lista de equipamentos controlados
	 * @throws Exception
	 */
	public static List<Equipamento> getControlados(Telecentro telecentro)
			throws Exception {
		List<Equipamento> equipamentos = new ArrayList<Equipamento>();
		List<Equipamento> list = getList(telecentro);
		for (Equipamento eq : list) {
			if (eq.isControled()) {
				equipamentos.add((eq));
			}
		}
		return equipamentos;
	}

	/**
	 * Busca lista de equipamentos NÃO controlados de um telecentro
	 * 
	 * @return lista de equipamentos
	 * @throws Exception
	 */
	public static List<Equipamento> getNaoControlados(Telecentro telecentro)
			throws Exception {
		List<Equipamento> equipamentos = new ArrayList<Equipamento>();
		List<Equipamento> list = getList(telecentro);
		for (Equipamento eq : list) {
			// Se o equipamento está controlado, testa se está ok
			if (eq.isControled()) {
				try {
					Socket socket = SocketUtils.getSocket(eq.getIpAddress(), eq
							.getPort());
					SocketUtils.sendCommand(socket,
							CommandProcessor.IS_OK_COMMAND);
				} catch (IOException e) {
					eq.setStatus(SituacaoEquipamento.DERRUBADO);
					eq = persist(eq);
					SocketUtils.removeSocket(eq.getIpAddress(), eq.getPort());
				}
			}

			// Testa novamente com o vamos atualizado.
			if (!eq.isControled()) {
				equipamentos.add((eq));
			}
		}
		return equipamentos;
	}

	/**
	 * Remove equipamento a partir do seu identificador
	 * 
	 * @param id
	 *            identificador do equipamento
	 * @throws Exception
	 */
	public static void remove(String id) throws Exception {
		try {
			PersistentAccess pa = PersistentAccessFactory.getInstance();
			Equipamento eq = (Equipamento) pa.find(Equipamento.class, id);
			if (!EquipamentoDataServices.temVisita(eq)) {
				eq.setStatus(Status.EXCLUIDO);
				pa.persist(eq);
			}
		} catch (Exception e) {
			throw new Exception(e.getClass().getName() + ": " + e.getMessage());
		}
	}

	public static boolean temVisita(Equipamento eq) throws Exception {
		PersistentAccess pa = PersistentAccessFactory.getInstance();
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("equipamento", eq);
		List<Persistent> list = pa.search(
				"FROM Visita WHERE equipamento = :equipamento", params);
		return !list.isEmpty();
	}

	public static Equipamento persist(Equipamento equipamento)
			throws PersistException, ValidationException {
		try {
			PersistentAccess pa = PersistentAccessFactory.getInstance();

			ValidationList validationList = validate(equipamento);
			if (validationList.isValid()) {
				equipamento = (Equipamento) pa.persist(equipamento);
			} else {
				throw new ValidationException(validationList);
			}
		} catch (NamingException e) {
			e.printStackTrace();
			ExceptionLog log = ExceptionLogService.log(e,
					"Falha na obtenção do PersistentAccess.");
			throw new PersistException(log);
		//TODO Trocar Exception por PersistException quando for resolvido o problema 
		//da serialização de arrays no ejb 3
		} catch (Exception e) {
			if (e instanceof ValidationException) {
				throw (ValidationException) e;
			}
			
			e.printStackTrace();
			ExceptionLog log = ExceptionLogService.log(e,
					"Falha na persistência do Equipamento.");
			throw new PersistException(log);
		}

		return equipamento;
	}

	public static ValidationList validate(Equipamento equipamento) {
		ValidationList list = new ValidationList();

		if (StringUtils.isEmpty(equipamento.getRotulo())) {
			list.add(new Validation("Informe o rótulo", "rotulo"));
		}

		return list;
	}

	/**
	 * Obtém a lista de equipamentos livres de um telecentro
	 * 
	 * @param telecentro
	 *            filtro de telecentro
	 * @return a lista de equipamento que não estão sendo ocupamentos, isto é,
	 *         não estão em uma visista ativa.
	 * @throws Exception
	 *             propaga todas as exceções
	 */
	public static List<Equipamento> getLivres(Telecentro telecentro)
			throws Exception {
		List<Equipamento> equipamentos = new ArrayList<Equipamento>();
		List<Equipamento> list = getList(telecentro);

		List<Visita> visitaList = VisitaDataServices
				.getVisitasAtivas(telecentro);

		for (Equipamento eq : list) {
			boolean found = false;
			for (Visita visita : visitaList) {
				if ((visita.getEquipamento() != null)
						&& visita.getEquipamento().getId().equals(eq.getId())) {
					found = true;
					break;
				}
			}

			boolean disponivel = eq.getDisponivel() == null ? true : eq
					.getDisponivel();
			if (!found && disponivel) {
				equipamentos.add((Equipamento) eq);
			}
		}
		return equipamentos;
	}

	private static void setStation(Equipamento equipamento, String command,
			Integer status) {
		if (equipamento.isReady()) {
			SocketUtils.removeSocket(equipamento.getIpAddress(), equipamento
					.getPort());
		}

		if (equipamento.isReady() || equipamento.isControled()) {
			try {
				try {
					SocketUtils.sendCommand(equipamento.getIpAddress(),
							equipamento.getPort(), command);
					equipamento.setStatus(status);
					if (command.equals(CommandProcessor.DOWN_COMMAND)) {
						equipamento.setIpAddress(null);
						equipamento.setPort(null);
					}
					equipamento = persist(equipamento);
				} catch (IOException e) {
					equipamento.setStatus(SituacaoEquipamento.DERRUBADO);
					equipamento = persist(equipamento);
					SocketUtils.removeSocket(equipamento.getIpAddress(),
							equipamento.getPort());
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void closeStation(Equipamento equipamento) {
		setStation(equipamento, CommandProcessor.CLOSE_COMMAND, SituacaoEquipamento.FECHADO);
	}

	public static void openStation(Equipamento equipamento) {
		setStation(equipamento, CommandProcessor.OPEN_COMMAND, SituacaoEquipamento.ABERTO);
	}

	public static void downMonitor(Equipamento equipamento) {
		setStation(equipamento, CommandProcessor.DOWN_COMMAND,
				SituacaoEquipamento.NAO_CONTROLADO);
	}

	public static Equipamento getRotulo(String rotulo) throws Exception {
		PersistentAccess pa = PersistentAccessFactory.getInstance();
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("rotulo", rotulo);
		List<Persistent> list = pa.search(
				"FROM Equipamento WHERE rotulo = :rotulo", params);

		return list.isEmpty() ? null : (Equipamento) list.get(0);
	}
}
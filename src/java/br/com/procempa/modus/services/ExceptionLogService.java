package br.com.procempa.modus.services;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import br.com.procempa.modus.entity.ExceptionLog;
import br.com.procempa.modus.entity.Persistent;
import br.com.procempa.modus.session.ExceptionLogger;
import br.com.procempa.modus.session.PersistentAccess;

public class ExceptionLogService {
	
	public  static final String STACK_DELIMITER = "\t";
	
	static ExceptionLogger logger;
	static InitialContext ctx;

	static{
		try {
			Properties props = new Properties();
			props.put(Context.INITIAL_CONTEXT_FACTORY,"org.jboss.naming.HttpNamingContextFactory");
			props.put(Context.PROVIDER_URL, "http://:8080/invoker/JNDIFactory");
			Context mainCtx = new InitialContext(props);

			logger = (ExceptionLogger) mainCtx.lookup("modus/ExceptionLoggerBean/remote");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	public static ExceptionLog log(ExceptionLog exception) {
		return logger.log(exception);
	}

	public static ExceptionLog log(Exception e, String contextMessage, String user, String telecentro) {
		ExceptionLog eLog = new ExceptionLog();
						
		eLog.setContextMessage(contextMessage);
		eLog.setException(e.getClass().getName());
		eLog.setMessage(e.getMessage());

		StringBuffer buffer = new StringBuffer();
		int length = e.getStackTrace().length;
		for (int i = 0; i < length; i++) {
			buffer.append(e.getStackTrace()[i].getClassName() + STACK_DELIMITER);
			buffer.append(e.getStackTrace()[i].getMethodName() + STACK_DELIMITER);
			
			String tmpFileName = e.getStackTrace()[i].getFileName();
			buffer.append( (tmpFileName == null ? "" : tmpFileName) + STACK_DELIMITER);

			int tmpLineNumber = e.getStackTrace()[i].getLineNumber();
			buffer.append(tmpLineNumber < 1 ? "" : tmpLineNumber);
			
			buffer.append(i < length ? "\n" : "");
		}
		
		eLog.setStackTrace(buffer.toString());

		eLog.setUser(user);
		eLog.setTelecentro(telecentro);
		
		return log(eLog);
	}
	
	public static ExceptionLog log(Exception e, String contextMessage) {
		return log(e, contextMessage, null, null);
	}
	
	public static List<RelatorioExcecaoVO> getListVO(Long dataInicial,
			Long dataFinal) throws Exception {
		List<RelatorioExcecaoVO> excecoes = new ArrayList<RelatorioExcecaoVO>();
		PersistentAccess pa = PersistentAccessFactory.getInstance();
		HashMap<String, Object> params = new HashMap<String, Object>();
		
		params.put("dataInicio", new Timestamp(dataInicial));
		params.put("dataFim", new Timestamp(dataFinal));	
		
		List<Persistent> list = pa
				.search(
						"FROM ExceptionLog WHERE timestamp >= :dataInicio AND timestamp <= :dataFim",
						params);

		for (Persistent persistent : list) {
			ExceptionLog excecao = (ExceptionLog) persistent;
			RelatorioExcecaoVO excecaoVO = new RelatorioExcecaoVO();
			excecaoVO.setId(excecao.getId());
			excecaoVO.setUsuario(excecao.getUser());
			excecaoVO.setTelecentro(excecao.getTelecentro());
			excecaoVO.setExcecao(excecao.getException());
			excecaoVO.setData(excecao.getTimestamp().getTime());
			excecaoVO.setMensagem(excecao.getMessage());
			excecoes.add(excecaoVO);
		}
		return excecoes;
	}
	
	public static List<ExceptionLog> getList() throws Exception {
		List<ExceptionLog> exceptions = new ArrayList<ExceptionLog>();
		PersistentAccess pa = PersistentAccessFactory.getInstance();
		List<Persistent> list = pa.search("FROM ExceptionLog");
		for (Persistent persistent : list) {
			exceptions.add((ExceptionLog) persistent);
		}
		return exceptions;
	}

	public static ExceptionLog updateUserDescription(ExceptionLog log, String userDesc) {
		PersistentAccess pa;
		try {
			pa = PersistentAccessFactory.getInstance();
			log = (ExceptionLog) pa.find(ExceptionLog.class, log.getId());
			log.setUserDescription(userDesc);
			log = (ExceptionLog) pa.persist(log);
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return log;
	}
}


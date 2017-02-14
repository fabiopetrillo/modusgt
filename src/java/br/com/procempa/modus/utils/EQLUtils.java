package br.com.procempa.modus.utils;

import br.com.procempa.modus.entity.Status;

/**
 * Utilitário para a manipulação de querys EJB3-QL
 * @author petrillo
 *
 */
public class EQLUtils {
	
	/**
	 * Adiciona na query o filtro de exclusao
	 * 
	 * @param query
	 *            a query original
	 * @return query com o filtro
	 */
	public static String addFilter(String query) {
		String tmpQuery = null;
		String uQuery = query.toUpperCase();
		int wherePos = uQuery.indexOf("WHERE");
		int orderPos = uQuery.indexOf("ORDER BY");
		int groupPos = uQuery.indexOf("GROUP BY");
		if (wherePos > -1) {
			tmpQuery = query.substring(0,wherePos) + "WHERE status != " + Status.EXCLUIDO + " AND" + query.substring(wherePos + 5, query.length());
		} else {
			if(groupPos > -1)
				tmpQuery = query.substring(0,groupPos) + "WHERE status != " + Status.EXCLUIDO + " GROUP BY" +query.substring(groupPos + 8, query.length());
			else if(orderPos > -1)
				tmpQuery = query.substring(0,orderPos) + "WHERE status != " + Status.EXCLUIDO + " ORDER BY" +query.substring(orderPos + 8, query.length());
			else
					tmpQuery = query + " WHERE status != " + Status.EXCLUIDO;
		}
		
		return tmpQuery;
	}	
}
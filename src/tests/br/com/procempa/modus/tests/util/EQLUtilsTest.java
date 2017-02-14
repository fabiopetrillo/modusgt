package br.com.procempa.modus.tests.utils;

import br.com.procempa.modus.entity.Status;
import br.com.procempa.modus.utils.EQLUtils;
import junit.framework.TestCase;

public class EQLUtilsTest extends TestCase {

	public void testAddFilter() {
		String q = "FROM Usuario";
		
		q = EQLUtils.addFilter(q);
		assertEquals("FROM Usuario WHERE status != " + Status.EXCLUIDO, q);
		
		q = "FROM Usuario WHERE nome = 'procempa'";
		q = EQLUtils.addFilter(q);
		assertEquals("FROM Usuario WHERE status != " + Status.EXCLUIDO + " AND " + "nome = 'procempa'"  , q);
		
		q = "FROM Usuario WHERE nome = 'procempa' ORDER BY Rg";
		q = EQLUtils.addFilter(q);
		assertEquals("FROM Usuario WHERE status != " + Status.EXCLUIDO + " AND " + "nome = 'procempa' ORDER BY Rg"  , q);
		
		q = "FROM Usuario WHERE nome = 'procempa' GROUP BY Rg";
		q = EQLUtils.addFilter(q);
		assertEquals("FROM Usuario WHERE status != " + Status.EXCLUIDO + " AND " + "nome = 'procempa' GROUP BY Rg"  , q);
		
		q = "FROM Usuario ORDER BY Rg";
		q = EQLUtils.addFilter(q);
		assertEquals("FROM Usuario WHERE status != " + Status.EXCLUIDO + " ORDER BY Rg", q);
		
		q = "FROM Usuario order by Rg";
		q = EQLUtils.addFilter(q);
		assertEquals("FROM Usuario WHERE status != " + Status.EXCLUIDO + " ORDER BY Rg", q);
		
		q = "FROM Usuario GROUP BY Rg";
		q = EQLUtils.addFilter(q);
		assertEquals("FROM Usuario WHERE status != " + Status.EXCLUIDO + " GROUP BY Rg", q);
		
		q = "FROM Usuario group by Rg";
		q = EQLUtils.addFilter(q);
		assertEquals("FROM Usuario WHERE status != " + Status.EXCLUIDO + " GROUP BY Rg", q);
		
		q = "FROM Usuario where id = 1";
		q = EQLUtils.addFilter(q);
		assertEquals("FROM Usuario WHERE status != " + Status.EXCLUIDO + " AND id = 1", q);
	}
}

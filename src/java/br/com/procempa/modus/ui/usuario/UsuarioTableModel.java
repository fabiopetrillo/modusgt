/*
 * Created on 28/04/2006
 *
 */
package br.com.procempa.modus.ui.usuario;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.lang.SerializationUtils;
import org.jdesktop.swingx.table.DefaultTableColumnModelExt;

import br.com.procempa.modus.entity.Usuario;
import br.com.procempa.modus.services.Logger;
import br.com.procempa.modus.services.UsuarioDataServices;
import br.com.procempa.modus.services.UsuarioException;
import br.com.procempa.modus.ui.SearchTableModel;

public class UsuarioTableModel extends SearchTableModel {

	private static final long serialVersionUID = 192236987217616424L;

	public UsuarioTableModel() {
		super();
	}

	public UsuarioTableModel(DefaultTableColumnModelExt columnModel) {
		super(columnModel);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Usuario> getList() {
		try {
			if (list == null) {
				list = getCache();
				if (list == null) {
					list = UsuarioDataServices.getList();
					saveCache(list);
				} else {
					// Inicializa Timer de Atualização periódica
					//TODO Definir a intervalo como configuração
					//do sistema (Options)  
					long interval = 1000 * 60 * 60 * 2; //Rodar de duas em duas hora2
					(new Timer()).schedule(new RefreshTimerTask(this),interval,interval);
				}
			}
		} catch (UsuarioException e) {
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public void refresh() {
		try {
			setList(UsuarioDataServices.getList());
			saveCache(getList());
			fireTableDataChanged();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void saveCache(List list) {
		final List l = list;

		new Thread(new Runnable() {
			public void run() {
				try {
					synchronized (l) {
						Logger
								.debug("Iniciando o salvamento do cache de usuário...");
						SerializationUtils.serialize((ArrayList) l,
								new FileOutputStream("user.dat"));
						Logger.debug("Dados salvos com sucesso.");
					}
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
	}

	private List getCache() {
		List list = null;
		try {
			list = (List) SerializationUtils.deserialize(new FileInputStream(
					"user.dat"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	class RefreshTimerTask extends TimerTask {
		UsuarioTableModel model;

		RefreshTimerTask(UsuarioTableModel m) {
			this.model = m;
		}

		@Override
		public void run() {
			Logger.debug("RefreshTimerTask - Iniciando refresh de usuário....");
			model.refresh();
			Logger.debug("RefreshTimerTask - Refresh realizado com sucesso");
		}
	}
}

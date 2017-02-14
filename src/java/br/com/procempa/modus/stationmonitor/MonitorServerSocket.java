package br.com.procempa.modus.stationmonitor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import br.com.procempa.modus.entity.Equipamento;
import br.com.procempa.modus.entity.SituacaoEquipamento;
import br.com.procempa.modus.services.EquipamentoDataServices;
import br.com.procempa.modus.services.Logger;

public class MonitorServerSocket extends ServerSocket {

	private static MonitorServerSocket serverSocket;

	private int serverPort;

	private boolean listening;

	private Equipamento equipamento;

	private MonitorServerSocket(Equipamento equipamento)
			throws IOException {
		super(0);
		this.equipamento = equipamento;
	}

	public static MonitorServerSocket createServer(Equipamento eq) {
		try {
			serverSocket = new MonitorServerSocket(eq);
			serverSocket.setServerPort(serverSocket.getLocalPort());
			serverSocket.setListening(true);

			Logger.info("Servidor escutando na porta "
					+ serverSocket.getLocalPort());

			eq.setStatus(SituacaoEquipamento.PRONTO);
			eq.setPort(serverSocket.getServerPort());
			eq = EquipamentoDataServices.persist(eq);
			
			do {
				try {
					Socket socket = serverSocket.accept();
					Logger.info("Conexão estabelecida com "
							+ socket.getRemoteSocketAddress() + ".");
					(new Thread(new CommandProcessor(socket))).start();
				} catch (IOException e) {
					serverSocket.setListening(false);
				}
			} while (serverSocket.isListening());
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return serverSocket;
	}

	public static MonitorServerSocket getServer() {
		return serverSocket;
	}

	public void downServer() {
		listening = false;
		try {
			serverSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Equipamento getEquipamento() {
		return equipamento;
	}

	public void setEquipamento(Equipamento equipamento) {
		this.equipamento = equipamento;
	}

	public boolean isListening() {
		return listening;
	}

	public void setListening(boolean listening) {
		this.listening = listening;
	}

	public int getServerPort() {
		return serverPort;
	}

	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}
}
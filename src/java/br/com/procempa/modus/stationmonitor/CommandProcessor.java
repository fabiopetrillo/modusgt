package br.com.procempa.modus.stationmonitor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import org.apache.commons.lang.StringUtils;

import br.com.procempa.modus.services.Logger;
import br.com.procempa.modus.ui.IconFactory;
import br.com.procempa.modus.ui.MessageView;
import br.com.procempa.modus.utils.SocketUtils;

public class CommandProcessor implements Runnable {

	public static final String TIME_1MIN_COMMAND = "1min";

	public static final String TIME_2MIN_COMMAND = "2min";

	public static final String END_COMMAND = "end";

	public static final String DOWN_COMMAND = "down";

	public static final String OPEN_COMMAND = "open";

	public static final String CLOSE_COMMAND = "close";

	public static final String IS_OK_COMMAND = "isOk";

	public static final String OK_RESPONSE = "ok";

	private Socket socket;

	public CommandProcessor(Socket socket) {
		this.socket = socket;
	}

	public void run() {
		BufferedReader in;
		try {
			in = new BufferedReader(new InputStreamReader(socket
					.getInputStream()));
			do {
				String command = in.readLine();

				if (!StringUtils.isEmpty(command)) {
					Logger.info("Comando recebido de "
							+ socket.getInetAddress().getHostAddress() + ": "
							+ command);
				}

				if (StringUtils.isEmpty(command) || command.equals(END_COMMAND)) {
					SocketUtils.sendCommand(socket, OK_RESPONSE);
					socket.close();
					break;
				} else if (command.equals(DOWN_COMMAND)) {
					SocketUtils.sendCommand(socket, OK_RESPONSE);
					this.socket.close();
					MonitorServerSocket.getServer().downServer();
					break;
				} else {
					process(command);
				}
				
				//Responde para o cliente "ok"
				SocketUtils.sendCommand(socket, OK_RESPONSE);
			} while (true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void process(String command) {
		if (command.equals(OPEN_COMMAND)) {
			BlockScreen.splash().setVisible(false);
			TimerScreen.splash();
		} else if (command.equals(CLOSE_COMMAND)) {
			TimerScreen.getInstance().setVisible(false);
			BlockScreen.splash();
		} else if (command.equals(IS_OK_COMMAND)) {
			//Nao precisa fazer nada
		} else if (command.equals(TIME_2MIN_COMMAND)) {
			MessageView.getInstance(IconFactory.createAbout(),"Salve seu trabalho! Você tem 2 minutos antes de acabar seu tempo.").setVisible(true);
		} else if (command.equals(TIME_1MIN_COMMAND)) {
			MessageView.getInstance(IconFactory.createAbout(),"Salve seu trabalho! Você tem 1 minutos antes de acabar seu tempo.").setVisible(true);
		} else {
			Logger.info("Comando inválido recebido: " + command);
		}
	}
}
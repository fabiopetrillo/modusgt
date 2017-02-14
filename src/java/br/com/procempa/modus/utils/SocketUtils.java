package br.com.procempa.modus.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;

import br.com.procempa.modus.stationmonitor.CommandProcessor;

public class SocketUtils {
	private static HashMap<String, Socket> sockets = new HashMap<String, Socket>();

	public static void sendCommand(String host, int port, String command)
			throws Exception {
		Socket socket = getSocket(host, port);
		sendCommand(socket, command);
		String response = getResponse(socket);
		if (response == null) {
			throw new IOException("Socket está derrubado");
		} else if (!response.equals(CommandProcessor.OK_RESPONSE)) {
			throw new Exception(
					"Falha no envio da mensagem/processamento da mensagem "
							+ "Resposta esperada era "
							+ CommandProcessor.OK_RESPONSE + ". Recebido "
							+ response);
		}
	}

	public static String getResponse(Socket socket) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(socket
				.getInputStream()));
		String response = in.readLine();
		return response;
	}

	public static void sendCommand(Socket socket, String command)
			throws IOException {
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		out.println(command);
	}

	public static Socket getSocket(String host, int port) throws IOException {
		return getSocket(host, port, false);
	}

	public static Socket getSocket(String host, int port, boolean reset)
			throws IOException {
		Socket socket = sockets.get(host + ":" + port);
		if (socket == null || socket.isClosed() || reset) {
			socket = new Socket(host, port);
			socket.setKeepAlive(true);
			sockets.put(host + ":" + port, socket);
		}
		return socket;
	}

	public static void removeSocket(String host, int port) {
		try {

			Socket socket = sockets.get(host + ":" + port);
			if (socket != null) {
				socket.close();
				sockets.remove(host + ":" + port);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
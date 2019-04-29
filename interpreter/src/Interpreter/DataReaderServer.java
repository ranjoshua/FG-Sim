package Interpreter;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class DataReaderServer implements Server {

	private int port;
	private volatile static boolean stop;
	private static int hz;

	public DataReaderServer(int port, int hz) {
		this.port = port;
		DataReaderServer.hz = hz;
		stop = false;
	}

	@Override
	public void open(int port, ClientHandler c) throws Exception {
		ServerSocket server = new ServerSocket(port);
		server.setSoTimeout(5000);
		while (!stop) {
			System.out.println("Waiting for Client to connect to My-Server");
			try {
				Socket aClient = server.accept();
				System.out.println("Client connected to My-Server successfuly!");
				try {
					InputStream in = aClient.getInputStream();
					c.handleClient(in);
					in.close();
					aClient.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (SocketTimeoutException e) {
				e.getCause();
			}
		}
		System.out.println("My server has closed");
		server.close();
	}

	@Override
	public void stop() {
		DataReaderServer.stop = true;
	}

	public void start(ClientHandler c) {
		new Thread(() -> {
			try {
				open(this.port, c);
			} catch (Exception e) {}
		}).start();
	}

	public static boolean isOpen() {
		return !stop;
	}
	public static int getHz() {
		return hz;
	}
}

package Interpreter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class DataSenderClient {

	private String ip;
	private int port;
	private PrintWriter outToServer;
	private BufferedReader inFromServer;
	private Socket FGServer;
	private volatile boolean isOpen;

	public DataSenderClient(String ip, int port) {
		this.ip = ip;
		this.port = port;
		this.isOpen = false;
	}

	public void start() {
		new Thread(() -> {
			try {
				startClientConnection(ip, port);
			} catch (Exception e) {}
		}).start();
	}

	public synchronized void startClientConnection(String ip, int port) {
		if (!isOpen) {
			System.out.println("Trying to Connect to FG-Server...");
			try {
				FGServer = new Socket(ip, port);
				isOpen = true;
				System.out.println("Connected to FG-Server Successfuly");
				outToServer = new PrintWriter(FGServer.getOutputStream());
				inFromServer = new BufferedReader(new InputStreamReader(FGServer.getInputStream()));
				//startEngine();
			} catch (UnknownHostException e) {}
			catch (IOException e) {}
		}
	}

	public synchronized void sendToServer(String command) {
		if (isOpen == false) {
			//System.out.println("Server is not open, cannot send command, THREAD IS WAITING...");
			return;
		}
		outToServer.println(command);
		outToServer.flush();
	}

	public synchronized String getFromServer(String command) {
		if (isOpen == false) {
			System.out.println("Server is not open, cannot send command, THREAD IS WAITING...");
			return null;
		}
		outToServer.println(command);
		outToServer.flush();
		try {
			//String s[] = inFromServer.readLine().split(" ");
			//System.out.println(s[0]);
			//return Double.parseDouble(s[s.length-1]);
			return inFromServer.readLine();
			//return inFromServer.readLine().replaceAll("[^0-9]", "");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public synchronized void startEngine() {
		outToServer.println("set /controls/switches/master-bat 1");
		outToServer.println("set /controls/gear/brake-parking 0");
		outToServer.println("set /controls/engines/engine/mixture 1");
		outToServer.println("set /controls/switches/magnetos 3");
		outToServer.println("set /controls/engines/engine/starter 1");
		while (!(getFromServer("get controls/engines/engine/starter").replaceAll("[^0-9]", "")).equals("1")) {
			outToServer.println("set controls/engines/engine/starter 1");
			System.out.println("starting engine");
		}
	}

	public synchronized void closeConnection() {
		if (!isOpen)
			return;
		isOpen = false;
		outToServer.flush();
		outToServer.close();
		try {
			inFromServer.close();
		} catch (IOException e1) {}
		try {
			FGServer.close();
		} catch (IOException e) {}
		System.out.println("DataSenderClient has disconnected from FG-Server");
	}

}

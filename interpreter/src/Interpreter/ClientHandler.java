package Interpreter;

import java.io.InputStream;

public interface ClientHandler {
	public void handleClient(InputStream inFromClient) throws Exception;
}

package Interpreter;

public interface Server {
	public void open(int port, ClientHandler c) throws Exception;

	public void stop();

	public void start(ClientHandler ch);
}

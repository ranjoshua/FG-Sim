package Interpreter;

public class ConnectCommand implements Command {

	String ip;
	int port;

	public ConnectCommand(String ip, int port) {
		super();
		this.ip = ip;
		this.port = port;
	}

	@Override
	public void execute() {
		DataSenderClient cli = new DataSenderClient(ip, port); // start client connection to flight gear simulator
		Address.client = cli;
		cli.start();
		//cli.startEngine();		
	}

}

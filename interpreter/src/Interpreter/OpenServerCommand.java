package Interpreter;

public class OpenServerCommand implements Command {

	int port, hz;

	public OpenServerCommand(int port, int hz) {
		super();
		this.port = port;
		this.hz = hz;
	}

	@Override
	public void execute() {
		Server s = new DataReaderServer(port, hz);	
		Main.myserver = s;
		s.start(new FGClientHandler());
	}

}

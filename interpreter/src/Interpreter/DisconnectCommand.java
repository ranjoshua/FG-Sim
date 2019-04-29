package Interpreter;

public class DisconnectCommand implements Command {

	@Override
	public void execute() {
		Address.client.sendToServer("bye");
		Address.client.closeConnection();
	}

}

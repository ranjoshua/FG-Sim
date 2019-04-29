package Interpreter;

public class DisconnectCreator implements CommandExpressionCreator {

	@Override
	public Expression createCommandExpression(String... parameters) {
		return new CommandExpression(new DisconnectCommand());
	}

}

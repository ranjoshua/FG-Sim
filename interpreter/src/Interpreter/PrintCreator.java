package Interpreter;

public class PrintCreator implements CommandExpressionCreator {

	@Override
	public Expression createCommandExpression(String... parameters) {
		return new CommandExpression(new PrintCommand(MapsHandler.getIterator().next()));
	}

}

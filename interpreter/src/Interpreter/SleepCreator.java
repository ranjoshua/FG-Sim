package Interpreter;

public class SleepCreator implements CommandExpressionCreator {

	@Override
	public Expression createCommandExpression(String... parameters) {
		return new CommandExpression(new SleepCommand(MapsHandler.getIterator().next()));
	}

}

package Interpreter;

public class SetCreator implements CommandExpressionCreator {

	@Override
	public Expression createCommandExpression(String... parameters) {
		String bindORvalue = MapsHandler.getIterator().next();
		return new CommandExpression(new SetCommand(parameters[0], bindORvalue));
	}
}

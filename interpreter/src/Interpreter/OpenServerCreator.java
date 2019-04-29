package Interpreter;

public class OpenServerCreator implements CommandExpressionCreator {

	@Override
	public Expression createCommandExpression(String... parameters) {
		int port = (int) ExpressionBuilder.BuildExpression(MapsHandler.getIterator().next()).calculate();
		int hz = (int) ExpressionBuilder.BuildExpression(MapsHandler.getIterator().next()).calculate();
		return new CommandExpression(new OpenServerCommand(port, hz));
	}

}

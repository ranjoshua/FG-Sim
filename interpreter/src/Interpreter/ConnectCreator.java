package Interpreter;

public class ConnectCreator implements CommandExpressionCreator {

	@Override
	public Expression createCommandExpression(String... parameters) {		
		return new CommandExpression(new ConnectCommand(MapsHandler.getIterator().next(), (int) ExpressionBuilder.BuildExpression(MapsHandler.getIterator().next()).calculate()));
	}

}

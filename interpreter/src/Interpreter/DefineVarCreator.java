package Interpreter;

public class DefineVarCreator implements CommandExpressionCreator {

	@Override
	public Expression createCommandExpression(String... parameters) {
		return new CommandExpression(new DefineVarCommand(MapsHandler.getIterator().next()));
	}

}

package Interpreter;

import java.util.ListIterator;

public class IfCreator implements CommandExpressionCreator {

	@Override
	public Expression createCommandExpression(String... parameters) {
		ListIterator<String> it = MapsHandler.getIterator();
		return new CommandExpression(new IfCommand(it.next(), it.next(), it.next()));
	}

}

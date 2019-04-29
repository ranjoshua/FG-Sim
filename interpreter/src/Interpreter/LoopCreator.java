package Interpreter;

import java.util.ListIterator;

public class LoopCreator implements CommandExpressionCreator {

	@Override
	public Expression createCommandExpression(String... parameters) {
		ListIterator<String> it = MapsHandler.getIterator();
		return new CommandExpression(new LoopCommand(it.next(), it.next(), it.next()));
	}
}

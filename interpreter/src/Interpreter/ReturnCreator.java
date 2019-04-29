package Interpreter;

public class ReturnCreator implements CommandExpressionCreator {

	@Override
	public Expression createCommandExpression(String... parameters) {
		try {
			Thread.sleep(250);
		} catch (InterruptedException e) {}
		return new CommandExpression(new ReturnCommand());
	}

}

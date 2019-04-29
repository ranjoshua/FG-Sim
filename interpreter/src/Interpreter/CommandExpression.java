package Interpreter;

public class CommandExpression implements Expression {

	private Command c;

	public CommandExpression(Command c) {
		super();
		this.c = c;
	}

	@Override
	public double calculate() {
		c.execute();

		if (MapsHandler.getSymbolTable().contains(new Var("toReturn")))
			return MapsHandler.getSymbolTable().get(new Var("toReturn"));
		return 0;
	}

	public Command getCommand() {
		return this.c;
	}

}

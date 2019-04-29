package Interpreter;

public class ReturnCommand implements Command {

	@Override
	public void execute() {
		String toReturn = MapsHandler.getIterator().next();
		double d;
		if (MapsHandler.getSymbolTable().containsKey(new Var(toReturn)))
			d = MapsHandler.getSymbolTable().get(new Var(toReturn));
		else 
			d = ExpressionBuilder.BuildExpression(toReturn).calculate();		
		MapsHandler.getSymbolTable().put(new Var("toReturn"), d);
	}

}

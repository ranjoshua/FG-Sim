package Interpreter;

import java.util.ListIterator;

public class DefineVarCommand implements Command {

	String varName;

	public DefineVarCommand(String varName) {
		super();
		this.varName = varName;
	}

	@Override
	public void execute() {
		Var v = new Var(varName);
		ListIterator<String> it = MapsHandler.getIterator();
		if (!MapsHandler.getSymbolTable().containsKey(v))
			MapsHandler.getSymbolTable().put(v, (double) 0);
		if (it.hasNext()) {
			if (it.next().equals("=")) {
				CommandExpression mysetcommand = (CommandExpression) MapsHandler.getCommandsMap().get("=")
						.createCommandExpression(varName);
				mysetcommand.calculate();
			} else
				it.previous();
		}
	}
}

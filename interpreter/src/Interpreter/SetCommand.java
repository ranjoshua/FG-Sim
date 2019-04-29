package Interpreter;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class SetCommand implements Command {

	Var myVar;
	String bindOrValue, myaddress;

	public SetCommand(String varName, String bindOrValue) {
		super();
		myVar = new Var(varName);
		this.bindOrValue = bindOrValue;
		if (bindOrValue.equals("bind"))
			myaddress = MapsHandler.getIterator().next();
	}

	@Override
	public void execute() {
		ConcurrentHashMap<Var, Double> symbolTable = MapsHandler.getSymbolTable();
		if (bindOrValue.equals("bind")) {
			Address a = new Address(myaddress);
			double v = symbolTable.get(myVar);
			a.addObserver(myVar);
			myVar.addObserver(a);
			symbolTable.remove(myVar);
			symbolTable.put(myVar, v);
			if (MapsHandler.getAddressMap().containsKey(a))
				MapsHandler.getAddressMap().keySet().stream().filter((b) -> b.equals(a)).findFirst().get()
						.addObserver(myVar);
			else if (!MapsHandler.getNotGenericAddress().containsKey(a))
				MapsHandler.getNotGenericAddress().put(a, v);
			else
				MapsHandler.getNotGenericAddress().keySet().stream().filter((b) -> b.equals(a)).findFirst().get()
						.addObserver(myVar);

		} else { // It's a number. put var value in symbol table.
			double varValue;
			if (symbolTable.containsKey(new Var(bindOrValue))) // bindOrValue is a Var in SymbolTable
				varValue = symbolTable.get(new Var(bindOrValue));
			else // arithmetic expression that should be evaluated
				varValue = ExpressionBuilder.BuildExpression(bindOrValue).calculate();
			if (symbolTable.containsKey(myVar)) {
				Optional<Var> key = symbolTable.keySet().stream().filter((v) -> v.equals(myVar)).findFirst();
				myVar = key.get();
			}
			myVar.setValue(varValue);
		}
	}

}

package Interpreter;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.concurrent.ConcurrentHashMap;

public abstract class ConditionParser implements Command {

	ArrayList<Command> commands;
	String left, right, sign;
	Var leftVar, rightVar;

	public ConditionParser(String left, String sign, String right) {
		super();
		this.left = left;
		this.right = right;
		this.sign = sign;
		this.leftVar = new Var(left);
		this.rightVar = new Var(right);

		buildCondition();
		createCommandsList();
	}

	private void createCommandsList() {
		commands = new ArrayList<>();
		ListIterator<String> it = MapsHandler.getIterator();
		String s = it.next();

		if (!s.equals("{")) // fix this later! it's a loop with 1 command, without {}.
			return;

		CommandExpression c = null;
		s = it.next();
		while (!s.equals("}")) { // until we receive all the commands. from the start at '{' to the finish at '}'
			if (MapsHandler.getSymbolTable().containsKey(new Var(s))) // for commands like: throttle = 1. in this case
																		// there is no var at the start of the line.
				c = (CommandExpression) MapsHandler.getCommandsMap().get(it.next()).createCommandExpression(s);
			else if (MapsHandler.getCommandsMap().containsKey(s))
				c = (CommandExpression) MapsHandler.getCommandsMap().get(s).createCommandExpression();
			else {
				System.out.println("Wrong input in ConditionParser");
				c = null;
			}
			if (c != null)
				commands.add(c.getCommand());

			s = it.next();
		}
	}

	protected Boolean isTrue() {
		ConcurrentHashMap<Var, Double> symbolTable = MapsHandler.getSymbolTable();
		if (sign.equals(">") && (symbolTable.get(leftVar) > symbolTable.get(rightVar)))
			return true;
		if (sign.equals("<") && (symbolTable.get(leftVar) < symbolTable.get(rightVar)))
			return true;
		if (sign.equals(">=") && (symbolTable.get(leftVar) >= symbolTable.get(rightVar)))
			return true;
		if (sign.equals("<=") && (symbolTable.get(leftVar) <= symbolTable.get(rightVar)))
			return true;
		if (sign.equals("==") && (symbolTable.get(leftVar) == symbolTable.get(rightVar)))
			return true;
		if (sign.equals("!=") && (symbolTable.get(leftVar) != symbolTable.get(rightVar)))
			return true;

		return false;
	}

	private void buildCondition() {
		ConcurrentHashMap<Var, Double> symbolTable = MapsHandler.getSymbolTable();
		Double leftVarValue, rightVarValue;
		if (!symbolTable.containsKey(leftVar)) { // its an expression
			leftVarValue = ExpressionBuilder.BuildExpression(left).calculate();
			symbolTable.put(leftVar, leftVarValue);
		}
		if (!symbolTable.containsKey(rightVar)) { // its an expression
			rightVarValue = ExpressionBuilder.BuildExpression(right).calculate();
			symbolTable.put(rightVar, rightVarValue);
		}
	}

}

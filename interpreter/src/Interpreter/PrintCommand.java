package Interpreter;

import java.util.ArrayList;
import java.util.Arrays;

public class PrintCommand implements Command {

	String toprint;

	public PrintCommand(String toprint) {
		super();
		this.toprint = toprint;
	}

	@Override
	public void execute() {
		ArrayList<String> printables = new ArrayList<String>(Arrays.asList(toprint.split("\\+")));
		StringBuilder sb = new StringBuilder();
		for (String s : printables) {
			if (MapsHandler.getSymbolTable().containsKey(new Var(s))) // if it's a var. get his value from SymbolTable
																		// and append.
				sb.append(MapsHandler.getSymbolTable().get(new Var(s)));
			else
				sb.append(s);
		}	
		System.out.println(sb.toString());		
	}

}

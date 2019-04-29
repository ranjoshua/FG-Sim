package Interpreter;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.ConcurrentHashMap;

public class Main {

	static Server myserver = null;
	
	public static double main(String[] args) {
		
		System.out.println("Starting Interpreter");
		if (args.length == 0) {
			System.out.println("Arguments has not provided. Closing program...");
			return 0;
		}

		String script;
		File f = new File(args[0]);
		if (f.exists()) 
			script = readScriptFile(args[0]);	
		else
			script = readConsoleCommands(args);

		MapsHandler.createCommandsMap();
		MapsHandler.createAddressMap();
		Lexer(script);

		double ret = 0;
		if (MapsHandler.getSymbolTable().containsKey(new Var("toReturn")))
			ret = MapsHandler.getSymbolTable().get(new Var("toReturn"));
		//MapsHandler.getSymbolTable().clear();
		if (myserver != null)
			myserver.stop();
		myserver = null;
		
		return ret;
	}

	private static String readConsoleCommands(String[] args) {
		StringBuilder sb = new StringBuilder();
		for (String s : args)
			if (s.equals("bind"))
				sb.append(s + '\n');
			else
				sb.append(s + " ");
		return sb.toString();
	}

	private static String readScriptFile(String filePathString) {
		if (filePathString == null)
			return null;
		if (!Files.exists(Paths.get(filePathString))) {
			System.out.println("Error: file is not exist!");
			return null;
		}
		StringBuilder sb = new StringBuilder();

		try {
			Files.lines(Paths.get(filePathString), Charset.defaultCharset()).forEach((l) -> sb.append(l));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return sb.toString();
	}

	private static void Lexer(String script) {
		String[] fixedInput = script.replaceAll(" *([+]|[-]|[*]|[/]|[(]|[)]) *", "$1")
				.replaceAll(" *((<=)|(>=)|(==)|(=)) *", " $1 ").split("\\s+");

		List<String> expressions = Arrays.asList(fixedInput);
		MapsHandler.setIterator(expressions.listIterator());
		Parser(expressions);
	}

	protected static void Parser(List<String> exp) {
		CommandExpression c = null;
		String s;
		ListIterator<String> it = MapsHandler.getIterator();
		HashMap<String, CommandExpressionCreator> commandsMap = MapsHandler.getCommandsMap();
		ConcurrentHashMap<Var, Double> symbolTable = MapsHandler.getSymbolTable();
		while (it.hasNext()) {
			s = it.next();
			if (commandsMap.containsKey(s))
				c = (CommandExpression) commandsMap.get(s).createCommandExpression();
			else if (symbolTable.containsKey(new Var(s))) // for commands like: throttle = 1. in this case there is no
															// var at the start of the line.
				c = (CommandExpression) commandsMap.get(it.next()).createCommandExpression(s);
			else {
				System.out.println("Wrong input in Main parsing");
				c = null;
			}

			if (c != null)
				c.calculate();

		}
	}

}

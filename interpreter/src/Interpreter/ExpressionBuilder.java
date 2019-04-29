package Interpreter;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class ExpressionBuilder {

	public static Expression BuildExpression(String exp) {		
		Queue<String> qu = shuntingYard(exp);
		Stack<Expression> st = prefixToExpressionStack(qu);
		return new Number(st.pop().calculate());
		//return new Number(Math.floor((st.pop().calculate() * 1000)) / 1000);
	}

	private static boolean isDouble(String sumString) { // returns true if sumString is a number.
		if (sumString.charAt(0) == '-' && sumString.length() > 1)
			sumString = sumString.substring(1, sumString.length());
		return sumString.chars().filter((c) -> c != '.').allMatch(Character::isDigit);
	}

	public static Queue<String> shuntingYard(String expression) {
		LinkedList<String> qu = new LinkedList<>();
		Stack<String> st = new Stack<>();
		String[] tokens = expression.split("(?<=[-+*/()])|(?=[-+*/()])");

		for (String token : tokens) {
			if (MapsHandler.getSymbolTable().containsKey(new Var(token))) 
				qu.add(String.valueOf(MapsHandler.getSymbolTable().get(new Var(token))));		
			
			else if (isDouble(token))
				qu.add(token);

			else {
				switch (token) {
				case "+":
					while (!st.empty() && (st.peek().equals("*") || st.peek().equals("/")))
						qu.add(st.pop());
					st.push(token);
					break;
				case "-":
					while (!st.empty() && (st.peek().equals("*") || st.peek().equals("/")))
						qu.add(st.pop());
					st.push(token);
					break;
				case "*":
					st.push(token);
					break;
				case "/":
					st.push(token);
					break;
				case "(":
					st.push(token);
					break;
				case ")":
					while (!st.empty() && !st.peek().equals("("))
						qu.add(st.pop());
					st.pop();
					break;
				default:
					break;
				}
			}
		}

		while (!st.empty())
			qu.add(st.pop());
		
		return qu;
	}

	private static Stack<Expression> prefixToExpressionStack(Queue<String> queue) {			
		String token = null;
		Stack<Expression> exp = new Stack<>();
		Expression left = null, right = null;
		while (!queue.isEmpty()) {
			token = queue.poll();

			if (isDouble(token))
				exp.push(new Number(Double.parseDouble(token)));

			else {
				if (exp.size() == 1) {
					left = null;
					right = exp.pop();
				} else {
					right = exp.pop();
					left = exp.pop();
				}
				switch (token) {
				case "/":
					exp.push(new Div(left, right));
					break;
				case "*":
					exp.push(new Mul(left, right));
					break;
				case "+":
					exp.push(new Plus(left, right));
					break;
				case "-":
					exp.push(new Minus(left, right));
					break;
				default:
					break;
				}
			}
		}
		return exp;
	}

}

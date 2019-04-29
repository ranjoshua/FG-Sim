package Interpreter;

public class Minus extends BinaryExpression {

	public Minus(Expression left, Expression right) {
		super(left, right);
	}

	@Override
	public double calculate() {
		if (left == null) 
			return (-1) * right.calculate();
		else if (right == null)
			return (-1) * left.calculate();

		return left.calculate() - right.calculate();
	}

}

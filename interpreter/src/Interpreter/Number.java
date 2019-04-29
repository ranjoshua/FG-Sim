package Interpreter;

public class Number implements Expression {

	private double value;
	
	public Number(double value) {
		super();
		this.value = value;
	}

	@Override
	public double calculate() {
		return value;
	}

}

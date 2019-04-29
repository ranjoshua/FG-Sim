package Interpreter;

public class SleepCommand implements Command {

	String timeToSleep;

	public SleepCommand(String timeToSleep) {
		super();
		this.timeToSleep = timeToSleep;
	}

	@Override
	public void execute() {
		Var v = new Var(timeToSleep);
		if (MapsHandler.getSymbolTable().containsKey(v)) {
			try {
				Thread.sleep(MapsHandler.getSymbolTable().get(v).longValue());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		Double value = ExpressionBuilder.BuildExpression(timeToSleep).calculate();

		try {
			Thread.sleep(value.longValue());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}

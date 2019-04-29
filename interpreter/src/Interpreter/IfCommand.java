package Interpreter;

public class IfCommand extends ConditionParser {

	public IfCommand(String left, String sign, String right) {
		super(left, sign, right);
	}

	@Override
	public void execute() {
		if (isTrue()) {
			for (Command c : commands)
				c.execute();
		}
	}

}

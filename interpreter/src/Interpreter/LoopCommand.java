package Interpreter;

public class LoopCommand extends ConditionParser {

	public LoopCommand(String left, String sign, String right) {
		super(left, sign, right);
	}

	@Override
	public void execute() {
		while (isTrue()) {
			for (Command c : commands) 
				c.execute();			
		}
	}
}

package Interpreter;

public interface CommandExpressionCreator {

	Expression createCommandExpression(String... parameters);
	// Right now, I don't use the 'Varargs' optional parameters.
	// But I added them for the future, if i'd need Creator that get arguments.
}

package Interpreter;

import java.util.Observable;
import java.util.Observer;

public class Var extends Observable implements Observer {

	String varName;

	public Var(String varName) {
		super();
		this.varName = varName;
	}

	public double getValue() {
		return MapsHandler.getSymbolTable().get(this);
	}

	public void setValue(double value) {
		if (getValue() == value)
			return;
		MapsHandler.getSymbolTable().put(this, value);
		setChanged();
		notifyObservers(value);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((varName == null) ? 0 : varName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Var))
			return false;
		Var other = (Var) obj;
		if (varName == null) {
			if (other.varName != null)
				return false;
		} else if (!varName.equals(other.varName))
			return false;
		return true;
	}

	@Override
	public void update(Observable o, Object arg) {
		if (getValue() == (double) arg)
			return;
		MapsHandler.getSymbolTable().put(this, (double) arg);
	}
}

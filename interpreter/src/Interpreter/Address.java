package Interpreter;

import java.util.Observable;
import java.util.Observer;

public class Address extends Observable implements Observer {

	static DataSenderClient client;
	String address;

	public Address(String address) {
		super();
		if (address.charAt(0) == '"')
			address = address.substring(1, address.length() - 1);
		this.address = address;
	}

	@Override
	public void update(Observable o, Object arg) {
		double val = (double) arg;
		if (getValue() == val)
			return;
		setValue(val);
		client.sendToServer("set " + address + " " + val);
	}

	public double getValue() {
		if (MapsHandler.getAddressMap().containsKey(this))
			return MapsHandler.getAddressMap().get(this);
		return MapsHandler.getNotGenericAddress().get(this);
	}

	public void setValue(double value) {
		if (getValue() == value)
			return;
		if (MapsHandler.getAddressMap().containsKey(this))
			MapsHandler.getAddressMap().put(this, value);
		else
			MapsHandler.getNotGenericAddress().put(this, value);
		setChanged();
		notifyObservers(value);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Address))
			return false;
		Address other = (Address) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		return true;
	}

}

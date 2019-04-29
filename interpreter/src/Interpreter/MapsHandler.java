package Interpreter;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MapsHandler {

	private static ConcurrentHashMap<Var, Double> SymbolTable = new ConcurrentHashMap<>();
	private static HashMap<String, CommandExpressionCreator> CommandsMap = new HashMap<>();
	private static Map<Address, Double> AddressMap = Collections.synchronizedMap(new LinkedHashMap<>());
	private static Map<Address, Double> NotGenericAddress = Collections.synchronizedMap(new LinkedHashMap<>());
	private static ListIterator<String> it;

	protected static ListIterator<String> getIterator() {
		return MapsHandler.it;
	}

	protected static void setIterator(ListIterator<String> it) {
		MapsHandler.it = it;
	}

	// CommandFactory //
	public static void createCommandsMap() {
		CommandsMap.put("openDataServer", new OpenServerCreator());
		CommandsMap.put("connect", new ConnectCreator());
		CommandsMap.put("var", new DefineVarCreator());
		CommandsMap.put("=", new SetCreator());
		CommandsMap.put("while", new LoopCreator());
		CommandsMap.put("if", new IfCreator());
		CommandsMap.put("sleep", new SleepCreator());
		CommandsMap.put("print", new PrintCreator());
		CommandsMap.put("disconnect", new DisconnectCreator());
		CommandsMap.put("return", new ReturnCreator());
	}

	public synchronized static void createAddressMap() {
		AddressMap.put(new Address("/instrumentation/airspeed-indicator/indicated-speed-kt"), (double) 0);
		AddressMap.put(new Address("/instrumentation/altimeter/indicated-altitude-ft"), (double) 0);
		AddressMap.put(new Address("/instrumentation/altimeter/pressure-alt-ft"), (double) 0);
		AddressMap.put(new Address("/instrumentation/attitude-indicator/indicated-pitch-deg"), (double) 0);
		AddressMap.put(new Address("/instrumentation/attitude-indicator/indicated-roll-deg"), (double) 0);
		AddressMap.put(new Address("/instrumentation/attitude-indicator/internal-pitch-deg"), (double) 0);
		AddressMap.put(new Address("/instrumentation/attitude-indicator/internal-roll-deg"), (double) 0);
		AddressMap.put(new Address("/instrumentation/encoder/indicated-altitude-ft"), (double) 0);
		AddressMap.put(new Address("/instrumentation/encoder/pressure-alt-ft"), (double) 0);
		AddressMap.put(new Address("/instrumentation/gps/indicated-altitude-ft"), (double) 0);
		AddressMap.put(new Address("/instrumentation/gps/indicated-ground-speed-kt"), (double) 0);
		AddressMap.put(new Address("/instrumentation/gps/indicated-vertical-speed"), (double) 0);
		AddressMap.put(new Address("/instrumentation/heading-indicator/indicated-heading-deg"), (double) 0);
		AddressMap.put(new Address("/instrumentation/magnetic-compass/indicated-heading-deg"), (double) 0);
		AddressMap.put(new Address("/instrumentation/slip-skid-ball/indicated-slip-skid"), (double) 0);
		AddressMap.put(new Address("/instrumentation/turn-indicator/indicated-turn-rate"), (double) 0);
		AddressMap.put(new Address("/instrumentation/vertical-speed-indicator/indicated-speed-fpm"), (double) 0);
		AddressMap.put(new Address("/controls/flight/aileron"), (double) 0);
		AddressMap.put(new Address("/controls/flight/elevator"), (double) 0);
		AddressMap.put(new Address("/controls/flight/rudder"), (double) 0);
		AddressMap.put(new Address("/controls/flight/flaps"), (double) 0);
		AddressMap.put(new Address("/controls/engines/current-engine/throttle"), (double) 0);
		AddressMap.put(new Address("/engines/engine/rpm"), (double) 0);
	}

	public synchronized static void updateAddressMap(List<Double> values) {
		int i = 0;
		Iterator<Address> it = AddressMap.keySet().iterator();
		while (it.hasNext() && i < values.size()) {
			it.next().setValue(values.get(i));
			i++;
		}
	}
	
	public static void updateNotGenericAddress(List<Double> values) {
		//while (values.size() != NotGenericAddress.keySet().size()) {
		//	try {
		//		Thread.sleep(100);
		//	} catch (InterruptedException e) {}
		//}
		
		int i = 0;
		Iterator<Address> it = NotGenericAddress.keySet().iterator();
		while (it.hasNext() && i < values.size()) {
			if (values.get(i) != 0)
				it.next().setValue(values.get(i));
			else 
				it.next();
			i++;
		}
	}

	public static ConcurrentHashMap<Var, Double> getSymbolTable() {
		return SymbolTable;
	}

	public static HashMap<String, CommandExpressionCreator> getCommandsMap() {
		return CommandsMap;
	}

	public static Map<Address, Double> getAddressMap() {
		return AddressMap;
	}

	public static Map<Address, Double> getNotGenericAddress() {
		return NotGenericAddress;
	}

}

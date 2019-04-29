package Interpreter;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FGClientHandler implements ClientHandler { // this ClientHandler should handle MatrixMaze problems.

	@Override
	public void handleClient(InputStream inFromClient) throws Exception {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(inFromClient));
			try {
				String line;
				List<Double> values;

				while (DataReaderServer.isOpen()) { // read input problem from Flight-Gear Simulator.
					//System.out.println("Getting Data from FG-Server to update addressMap"); 
					line = in.readLine();
					if (line != null) {
						values = Arrays.asList(line.split(",")).stream().map(Double::parseDouble)
								.collect(Collectors.toList());
						
						if (values.size() == 23)
							MapsHandler.updateAddressMap(values);
						else
							MapsHandler.updateNotGenericAddress(values);
					}
					Thread.sleep(1000 / DataReaderServer.getHz());
				}		
				in.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

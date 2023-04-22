package org.concordia.RegionMap;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StateRegionMap {
	private static final String CSV_FILE_PATH = Paths.get("src", "main", "resources", "us-regions.csv").toString();
	private static final String DELIMITER = ",";
	private static final Map<String, String> regionMapping = new HashMap<>();

	static {
		try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE_PATH))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] values = line.split(DELIMITER);
				String state = values[0];
				String region = values[1];
				regionMapping.put(state, region);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String findRegion(String state) {
		return regionMapping.get(state);
	}

	public static void main(String[] args) {
		try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE_PATH))) {
			// Skip the header
			br.readLine();

			// Create a map of region to states
			Map<String, List<String>> regionToStates = new HashMap<>();
			String line;
			while ((line = br.readLine()) != null) {
				String[] values = line.split(DELIMITER);
				String state = values[0];
				String region = values[1];

				List<String> states = regionToStates.getOrDefault(region, new ArrayList<>());
				states.add(state);
				regionToStates.put(region, states);
			}

			// Print out the regions and their states
			for (Map.Entry<String, List<String>> entry : regionToStates.entrySet()) {
				String region = entry.getKey();
				List<String> states = entry.getValue();

				System.out.println("Region: " + region);
				System.out.println("States: " + String.join(", ", states));
				System.out.println();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

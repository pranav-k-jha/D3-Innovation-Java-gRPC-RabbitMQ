package org.concordia.DAO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.concordia.MongoDB.MongoDBUtil;
import org.concordia.RegionMap.StateRegionMap;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;

public class QueryFiveDAO {
	private static final String COLLECTION_NAME = "EduCostStat";
	private static final String QUERY_COLLECTION_NAME = "EduCostStatQueryFive";

	public static Map<String, Double> query(int year, String type, String length) {
		MongoCollection<Document> collection = MongoDBUtil.getCollection(COLLECTION_NAME);

		// Check if query result already exists for the specified year, type, and length
		// combination
		Document existingQueryResult = MongoDBUtil.getCollection(QUERY_COLLECTION_NAME)
				.find(new Document("year", year).append("type", type).append("length", length)).first();
		if (existingQueryResult != null) {
			System.out.println("Query result already exists in the collection.");
			System.out.println("Region-wise average overall expense for year: " + year + ", type: " + type
					+ ", and length: " + length);
			@SuppressWarnings("unchecked")
			Map<String, Double> regionCostMap = (Map<String, Double>) existingQueryResult.get("result");
			for (Map.Entry<String, Double> entry : regionCostMap.entrySet()) {
				System.out.println(entry.getKey() + ": " + entry.getValue());
			}
			return regionCostMap;
		}
		List<Bson> pipeline = new ArrayList<>();
		pipeline.add(Aggregates.match(Filters.eq("year", year)));
		pipeline.add(Aggregates.match(Filters.eq("type", type)));
		pipeline.add(Aggregates.match(Filters.eq("length", length)));
		pipeline.add(Aggregates.group("$state", Accumulators.avg("cost", "$value")));
		pipeline.add(Aggregates.sort(new Document("cost", -1)));

		List<Document> stateResults = collection.aggregate(pipeline).into(new ArrayList<>());

		if (stateResults.isEmpty()) {
			System.out.println("No data found.");
			return new HashMap<>();
		}

		Map<String, Double> regionCostMap = new HashMap<>();
		Map<String, Integer> regionCountMap = new HashMap<>();
		for (Document doc : stateResults) {
			String state = doc.getString("_id");
			double avgCost = doc.getDouble("cost");
			String region = StateRegionMap.findRegion(state);

			if (region != null) {
				double totalRegionCost = regionCostMap.getOrDefault(region, 0.0);
				int regionCount = regionCountMap.getOrDefault(region, 0);

				totalRegionCost += avgCost;
				regionCount++;

				regionCostMap.put(region, totalRegionCost);
				regionCountMap.put(region, regionCount);
			}
		}

		if (regionCostMap.isEmpty()) {
			System.out.println("No data found.");
			return regionCostMap;
		}

		for (String region : regionCostMap.keySet()) {
			double totalRegionCost = regionCostMap.get(region);
			int regionCount = regionCountMap.get(region);
			double avgRegionCost = totalRegionCost / regionCount;
			regionCostMap.put(region, avgRegionCost);
		}

		Document queryResult = new Document();
		queryResult.append("year", year);
		queryResult.append("type", type);
		queryResult.append("length", length);
		queryResult.append("result", regionCostMap);
		MongoDBUtil.getCollection(QUERY_COLLECTION_NAME).insertOne(queryResult);

		System.out.println("Query executed successfully.");
		System.out.println("Region-wise average overall expense for year: " + year + ", type: " + type
				+ ", and length: " + length);
		for (Map.Entry<String, Double> entry : regionCostMap.entrySet()) {
			System.out.println(entry.getKey() + ": " + entry.getValue());
		}
		return regionCostMap;
	}

	public static void main(String[] args) {
		query(2014, "Public Out-of-State", "4-year");
	}
}

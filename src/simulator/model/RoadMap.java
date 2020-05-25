package simulator.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;


public class RoadMap {

	private List<Junction> junctionsList;
	private List<Road> roadsList;
	private List<Vehicle> vehiclesList;
	private Map<String, Junction> junctionsMap;
	private Map<String, Road> roadsMap;
	private Map<String, Vehicle> vehiclesMap;

	protected RoadMap() {
		junctionsList = new ArrayList<Junction>();
		roadsList = new ArrayList<>();
		vehiclesList = new ArrayList<>();
		junctionsMap = new HashMap<>();
		roadsMap = new HashMap<>();
		vehiclesMap = new HashMap<>();
	}

	protected void addJunction(Junction j) {
		boolean found = false;
		for (int i = 0; !found && i < junctionsList.size(); i++) {
			if (j.getId() == junctionsList.get(i).getId()) {
				found = true;
			}
		}
		if (!found) {
			junctionsList.add(j);
			junctionsMap.put(j._id, j);
		}
	}

	protected void addRoad(Road r) throws IOException {
		boolean idFound = false;
		boolean srcFound = false;
		boolean destFound = false;
		
		for (int i = 0; !idFound && i < roadsList.size(); i++) {
			if (r.getId() == roadsList.get(i).getId())
				idFound = true;
		}
		
		for (int i = 0; i< junctionsList.size();i++) {
			if (r.srcJunc == junctionsList.get(i))
				srcFound = true;
			else if ((r.destJunc == junctionsList.get(i)))
				destFound = true;
		}

		if (!srcFound || !destFound)
			throw new IOException("Junction not found");

		else if (!idFound) {
			roadsList.add(r);
			roadsMap.put(r._id, r);
		}
	}

	protected void addVehicle(Vehicle v) throws IOException {

		for (int i = 0; i < vehiclesList.size(); i++) {
			if (v.getId() == vehiclesList.get(i).getId())
				throw new IOException("Vehicle id already exists");
		}
		for (int j = 0; j < v.itinerary.size()-1; j++) {
			if (v.itinerary.get(j).roadTo(v.itinerary.get(j+1)) == null)
				throw new IOException("No road assigned to junction");
		}
		vehiclesList.add(v);
		vehiclesMap.put(v._id, v);
	}

	public Junction getJunction(String id) {
		for (int i = 0; i < junctionsList.size(); i++) {
			if (junctionsList.get(i)._id.equals(id))
				return junctionsList.get(i);
		}
		return null;
	}

	public Road getRoad(String id) {
		for (int i = 0; i < roadsList.size(); i++) {
			if (roadsList.get(i)._id.equals(id))
				return roadsList.get(i);
		}
		return null;
	}

	public Vehicle getVehicle(String id) {
		for (int i = 0; i < vehiclesList.size(); i++) {
			if (vehiclesList.get(i)._id.equals(id))
				return vehiclesList.get(i);
		}
		return null;
	}

	public List<Junction> getJunctions() {
		List<Junction> jList = junctionsList;
		return jList;
	}

	public List<Road> getRoads() {
		List<Road> rList = roadsList;
		return rList;
	}

	public List<Vehicle> getVehicles() {
		List<Vehicle> vList = vehiclesList;
		return vList;
	}

	protected void reset() {
		vehiclesList.clear();
		vehiclesMap.clear();
		roadsList.clear();
		roadsMap.clear();
		junctionsList.clear();
		junctionsList.clear();
	}

	public JSONObject report() {

		JSONObject jObject = new JSONObject();
		JSONArray jJArray = new JSONArray();
		JSONArray jRArray = new JSONArray();
		JSONArray jVArray = new JSONArray();

		for (int i = 0; i < junctionsList.size(); i++)
			jJArray.put(junctionsList.get(i).report());

		jObject.put("junctions", jJArray);
		
		for (int i = 0; i < roadsList.size(); i++)
			jRArray.put(roadsList.get(i).report());
		
		jObject.put("roads", jRArray);
		
		for (int i = 0; i < vehiclesList.size(); i++)
			jVArray.put(vehiclesList.get(i).report());
		
		jObject.put("vehicles", jVArray);

		return jObject;
	}

}

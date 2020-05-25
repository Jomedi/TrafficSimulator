package simulator.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class Junction extends SimulatedObject {

	private List<Road> roads;
	private Map<Junction, Road> roadMap;
	private Map<Road, List<Vehicle>> vehicleRoadMap; // HEMOS AÑADIDO NOSOTROS
	private List<List<Vehicle>> qs;
	private int currGreen = -1; // indice del semáforo en verde
	private int lastSwitchingTime = 0;
	private LightSwitchStrategy lsStrategy;
	private DequeingStrategy dqStrategy;
	private int xCoor;
	private int yCoor;

	protected Junction(String id, LightSwitchStrategy lsStrategy, DequeingStrategy dqStrategy, int xCoor, int yCoor)
			throws IOException {
		super(id);

		if (lsStrategy == null || dqStrategy == null)
			throw new IOException("The strategy can't be null");
		if (xCoor < 0 || yCoor < 0)
			throw new IOException("X and Y coordinates must be positive.");

		roads = new ArrayList<Road>();
		roadMap = new HashMap<Junction, Road>();
		vehicleRoadMap = new HashMap<Road, List<Vehicle>>();
		qs = new ArrayList<List<Vehicle>>();

		this.lsStrategy = lsStrategy;
		this.dqStrategy = dqStrategy;
		this.xCoor = xCoor;
		this.yCoor = yCoor;
	}

	protected void addIncommingRoad(Road r) throws IOException {
		if (!r.destJunc.equals(this))
			throw new IOException("Destiny junction doesnt match");
		else {
			roads.add(r);
			List<Vehicle> queue = new LinkedList();
			Collections.sort(queue);
			
			qs.add(queue);
			vehicleRoadMap.put(r, queue);
		}
	}

	protected void addOutGoingRoad(Road r) throws IOException {
		if (r.srcJunc.roadTo(r.destJunc) != null)
			throw new IOException("Outgoing road already exists");
		else if (r.srcJunc != this)
			throw new IOException("Road is not outgoing from the junction");
		else
			roadMap.put(r.destJunc, r);
	}

	protected void enter(Vehicle v) throws IOException {
		vehicleRoadMap.get(v.road).add(v);
	}

	Road roadTo(Junction j) {
		Road roadToJ = roadMap.get(j);
		return roadToJ;
	}

	@Override
	protected void advance(int time) throws IOException {
		if (!roads.isEmpty()) {
			List<Vehicle> listaV = new ArrayList<>();
			List<Vehicle> listaVqs = new ArrayList<>();
			if (currGreen >= 0) {
				listaVqs = qs.get(currGreen);
				listaV = dqStrategy.dequeue(qs.get(currGreen));

				for (int i = 0; i < listaV.size(); i++) {
					if (listaV.get(i).state != VehicleStatus.ARRIVED) {
						listaV.get(i).moveToNextRoad();
						listaVqs.remove(listaV.get(i));
					}
				}
			}

			int index = lsStrategy.chooseNextGreen(roads, qs, currGreen, lastSwitchingTime, time);

			if (currGreen != index) {
				currGreen = index;
				lastSwitchingTime = time;
			}
		}
	}

	@Override
	public JSONObject report() {
		JSONObject jObject = new JSONObject();
		JSONArray jArray = new JSONArray();
		JSONObject jRoad = new JSONObject();
		JSONArray jVArray = new JSONArray();

		jObject.put("id", _id);
		
		if (currGreen == -1)
			jObject.put("green", "none");
		else
			jObject.put("green", roads.get(currGreen)._id);
	
		for (Map.Entry<Road, List<Vehicle>> entry : vehicleRoadMap.entrySet()) {
			jVArray = new JSONArray();
			jRoad = new JSONObject();
			Road road = entry.getKey();
			jRoad.put("road", road._id);

			for (Vehicle v : entry.getValue()) {
				jVArray.put(v);
			}
			
			jRoad.put("vehicles", jVArray);
			jArray.put(jRoad);
		}

		jObject.put("queues", jArray);

		return jObject;
	}

}

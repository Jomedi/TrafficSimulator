package simulator.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

public class TrafficSimulator implements Observable<TrafficSimObserver> {

	private RoadMap roadMap;
	private List<Event> eventList;
	private int simulationTime; // el paso de la simulación
	private TrafficSimObserver tSimObser;

	public TrafficSimulator() {
		roadMap = new RoadMap();
		eventList = new ArrayList<>();
		simulationTime = 0;
	}

	public void addEvent(Event e) {
		eventList.add(e);
		tSimObser.onEventAdded(roadMap, eventList, e, simulationTime);
	}

	public void advance() throws IOException {
		simulationTime++;
		tSimObser.onAdvanceStart(roadMap, eventList, simulationTime);

		int i = 0;
		int j = 0;

		int listSize = eventList.size();

		while (j < listSize) {
			if (eventList.get(i)._time == simulationTime) {
				eventList.get(i).execute(roadMap);
				eventList.remove(i);
			} else
				i++;
			j++;
		}
		try {
			List<Junction> jList = roadMap.getJunctions();
			for (int k = 0; k < jList.size(); k++) {
				jList.get(k).advance(simulationTime);
			}

			List<Road> rList = roadMap.getRoads();
			for (int k = 0; k < rList.size(); k++) {
				rList.get(k).advance(simulationTime);
			}
		} catch (IOException e) {
			tSimObser.onError(e.getMessage());
		}

		tSimObser.onAdvanceEnd(roadMap, eventList, simulationTime);
	}

	public void reset() {
		roadMap.reset();
		eventList.clear();
		simulationTime = 0;
		tSimObser.onReset(roadMap, eventList, simulationTime);
	}

	public JSONObject report() {
		JSONObject jObject = new JSONObject();

		jObject.put("time", simulationTime);
		jObject.put("state", roadMap.report());

		return jObject;
	}

	@Override
	public void addObserver(TrafficSimObserver o) {
		// hacer una lista de observers
		tSimObser.onRegister(roadMap, eventList, simulationTime);
	}

	@Override
	public void removeObserver(TrafficSimObserver o) {
		// TODO Auto-generated method stub

	}

}

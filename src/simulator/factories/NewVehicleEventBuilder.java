package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.model.Event;
import simulator.model.NewVehicleEvent;

public class NewVehicleEventBuilder extends Builder<Event> {

	private int time;
	private String id;
	private int maxSpeed;
	private int contClass;
	private List<String> itinerary;

	public NewVehicleEventBuilder() {
		super("new_vehicle");
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		time = data.getInt("time");
		id = data.getString("id");
		maxSpeed = data.getInt("maxspeed");
		contClass = data.getInt("class");
		itinerary = new ArrayList<>();
		JSONArray jL = data.getJSONArray("itinerary");
		for (int i = 0; i < jL.length(); i++) {
			itinerary.add(jL.getString(i));
		}

		return new NewVehicleEvent(time, id, maxSpeed, contClass, itinerary);
	}

}

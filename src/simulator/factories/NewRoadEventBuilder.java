package simulator.factories;

import org.json.JSONObject;

import simulator.model.Event;
import simulator.model.Weather;

public abstract class NewRoadEventBuilder extends Builder<Event>{

	protected int time;
	protected String id;
	protected String srcJunc;
	protected String destJunc;
	protected int length;
	protected int co2limit;
	protected int maxSpeed;
	protected Weather weather;
	
	public NewRoadEventBuilder(String type) {
		super(type);
	}
	
	protected void getData(JSONObject data) {
		time = data.getInt("time");
		id = data.getString("id");
		srcJunc = data.getString("src");
		destJunc = data.getString("dest");
		length = data.getInt("length");
		co2limit = data.getInt("co2limit");
		maxSpeed = data.getInt("maxspeed");
		weather = Weather.valueOf(data.getString("weather"));
	}
	
}

package simulator.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public abstract class Road extends SimulatedObject {

	protected Junction srcJunc;
	protected Junction destJunc;
	protected int length;
	protected int maxSpeed;
	protected int speedLimit;
	protected int contLimit;
	protected Weather weather;
	protected int totContamination;
	protected List<Vehicle> vehicles;

	protected Road(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length,
			Weather weather) throws IOException {
		super(id);
		if (maxSpeed < 0)
			throw new IOException("Speed must be a positive value");
		if (contLimit < 0)
			throw new IOException("Speed limit must be a positive value");
		if (length < 0)
			throw new IOException("Length must be a positive value");
		if (srcJunc == null || destJunc == null || weather == null)
			throw new IOException("Null Junction");

		vehicles = new ArrayList<>();
		
		speedLimit = maxSpeed;

		this.srcJunc = srcJunc;
		this.destJunc = destJunc;
		this.maxSpeed = maxSpeed;
		this.contLimit = contLimit;
		this.length = length;
		this.weather = weather;

		this.srcJunc.addOutGoingRoad(this);
		this.destJunc.addIncommingRoad(this);
	}

	protected void enter(Vehicle v) throws IOException {
		if (v.location != 0)
			throw new IOException("Wrong location of the vehicle");
		else if (v.actSpeed != 0)
			throw new IOException("Vehicles speed must be 0");
		else
			vehicles.add(v);
	}

	protected void exit(Vehicle v) {
		vehicles.remove(v); 
	}

	protected void setWeather(Weather w) throws IOException {
		if (w == null)
			throw new IOException("NULL weather");
		this.weather = w;
	}

	protected void addContamination(int c) throws IOException {
		if (c < 0)
			throw new IOException("Contamination must be positive");
		this.totContamination += c;
	}

	abstract void reduceTotalContamination();

	abstract void updateSpeedLimit();

	abstract int calculateVehicleSpeed(Vehicle v);

	@Override
	protected void advance(int time) throws IOException {
		reduceTotalContamination();
		updateSpeedLimit();
		
		for (int i = 0; i < vehicles.size(); i++) {
			if (vehicles.get(i).state != VehicleStatus.WAITING) {
				int speed = calculateVehicleSpeed(vehicles.get(i));
				vehicles.get(i).setSpeed(speed);
				vehicles.get(i).advance(time);
			}
		}
		Collections.sort(vehicles);
	}

	@Override
	public JSONObject report() {
		JSONObject jObject = new JSONObject();
		JSONArray jArray = new JSONArray();

		jObject.put("id", _id);
		jObject.put("speedlimit", speedLimit);
		jObject.put("weather", weather);
		jObject.put("co2", totContamination);

		for (int i = 0; i < vehicles.size(); i++)
			jArray.put(vehicles.get(i)._id);

		jObject.put("vehicles", jArray);

		return jObject;
	}
}

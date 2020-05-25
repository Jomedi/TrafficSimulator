package simulator.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONObject;

public class Vehicle extends SimulatedObject implements Comparable<Vehicle> {

	protected List<Junction> itinerary;
	protected Junction srcJunc;
	protected int index;
	protected int maxSpeed;
	protected int actSpeed;
	protected VehicleStatus state;
	protected Road road;
	protected int location;
	protected int contClass;
	protected int totCont;
	protected int distance;

	protected Vehicle(String id, int maxSpeed, int contClass, List<Junction> itinerary) throws IOException {
		super(id);

		if (maxSpeed < 0)
			throw new IOException("Speed must be a positive value");
		else if (contClass < 0 || contClass > 10)
			throw new IOException("Contamination class must be between this values: 0 - 10");
		else if (itinerary.size() < 2)
			throw new IOException("list size must be 2 or longer");

		this.itinerary = Collections.unmodifiableList(new ArrayList<>(itinerary));

		state = VehicleStatus.PENDING;
		road = null;
		this.maxSpeed = maxSpeed;
		this.contClass = contClass;
		this.itinerary = itinerary;
		srcJunc = this.itinerary.get(0);
	}

	protected void setSpeed(int s) throws IOException {

		if (s < 0)
			throw new IOException("Speed set can not be negative");

		if (maxSpeed < s)
			actSpeed = maxSpeed;
		else
			actSpeed = s;
	}

	protected void setContaminationClass(int c) throws IOException {
		if (c < 0 && c > 10)
			throw new IOException("Contamination must be between 0 and 10");

		contClass = c;
	}

	@Override
	protected void advance(int time) throws IOException {
		int advanced = actSpeed; // lo que avanzas
		int oldLocation = location;
		int newLocation = location + advanced;

		if (state == VehicleStatus.TRAVELING) {
			if ((newLocation) < road.length) {
				location = newLocation;
				distance += advanced;
			} else {
				location = road.length;
				distance += road.length - oldLocation;
			}

			int l = location - oldLocation;
			int c = l * contClass;
			totCont = c + totCont;
			road.addContamination(c);

			if (location == road.length) {
				road.destJunc.enter(this);
				actSpeed = 0;
				state = VehicleStatus.WAITING;
				srcJunc = road.destJunc;
			}
		}
	}

	protected void moveToNextRoad() throws IOException {
		if (state != VehicleStatus.WAITING && state != VehicleStatus.PENDING) {
			throw new IOException("Vehicle state isn't WAITING or PENDING");
		}

		Junction nextJunc;
		location = 0;
		actSpeed = 0;

		if (state == VehicleStatus.WAITING) {
			road.exit(this);
			srcJunc = road.destJunc;
		} else
			srcJunc = itinerary.get(0);

		int nextJuncIndex = itinerary.indexOf(srcJunc) + 1;
		if (nextJuncIndex < itinerary.size()) {
			nextJunc = itinerary.get(nextJuncIndex);
			road = srcJunc.roadTo(nextJunc);
			road.enter(this);
			state = VehicleStatus.TRAVELING;
		} else 
			state = VehicleStatus.ARRIVED;
	}

	@Override
	public JSONObject report() {
		JSONObject jObject = new JSONObject();

		jObject.put("id", _id);
		jObject.put("speed", actSpeed);
		jObject.put("distance", distance);
		jObject.put("co2", totCont);
		jObject.put("class", contClass);
		if (state == VehicleStatus.TRAVELING) {
			jObject.put("status", VehicleStatus.TRAVELING.name());
		} else if (state == VehicleStatus.PENDING) {
			jObject.put("status", VehicleStatus.PENDING.name());
		} else if (state == VehicleStatus.ARRIVED) {
			jObject.put("status", VehicleStatus.ARRIVED.name());
		} else if (state == VehicleStatus.WAITING) {
			jObject.put("status", VehicleStatus.WAITING.name());
		}
		if (state == VehicleStatus.TRAVELING || state == VehicleStatus.WAITING) {
			jObject.put("road", road.getId());
			jObject.put("location", location);
		}

		return jObject;
	}

	@Override
	public int compareTo(Vehicle v) {
		return v.location - location;
	}

}

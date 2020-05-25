package simulator.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NewVehicleEvent extends Event {

	private String id;
	private int maxSpeed;
	private int contClass;
	private List<String> sItinerary;
	private List<Junction> itinerary;

	public NewVehicleEvent(int time, String id, int maxSpeed, int contClass, List<String> itinerary) {
		super(time);

		this.id = id;
		this.maxSpeed = maxSpeed;
		this.contClass = contClass;
		this.sItinerary = itinerary;

		this.itinerary = new ArrayList<Junction>();
	}

	@Override
	protected void execute(RoadMap map) throws IOException {
		for (int i = 0; i < sItinerary.size(); i++) {
			itinerary.add(map.getJunction(sItinerary.get(i)));
		}
		Vehicle vehicle = new Vehicle(id, maxSpeed, contClass, itinerary);
		map.addVehicle(vehicle);
		vehicle.moveToNextRoad();
	}

	@Override
	public String toString() {
		return "New Vehicle '" + id + "'";
	}
}

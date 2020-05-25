package simulator.model;

import java.io.IOException;

public class NewInterCityRoadEvent extends NewRoadEvent {

    public NewInterCityRoadEvent(int time, String id, String srcJunc, String destJunc, int length, int CO2Limit,
            int maxSpeed, Weather weather) {
        super(time, id, srcJunc, destJunc, length, CO2Limit, maxSpeed, weather);
    }

    @Override
    protected void execute(RoadMap map) throws IOException {
    	getJunctions(map);
        InterCityRoad road = new InterCityRoad(id, srcJ, destJ, maxSpeed, CO2Limit, length, weather);
        map.addRoad(road);
    }
    
    @Override
	public String toString() {
		return "New Inter City '" + id + "'";
	}
}

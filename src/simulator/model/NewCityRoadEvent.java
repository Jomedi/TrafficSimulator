package simulator.model;

import java.io.IOException;

public class NewCityRoadEvent extends NewRoadEvent {

    public NewCityRoadEvent(int time, String id, String srcJunc, String destJunc, int length, int CO2Limit,
            int maxSpeed, Weather weather) {
        super(time, id, srcJunc, destJunc, length, CO2Limit, maxSpeed, weather);
    }

    @Override
    protected void execute(RoadMap map) throws IOException {
        getJunctions(map);
        CityRoad road = new CityRoad(id, srcJ, destJ, maxSpeed, CO2Limit, length, weather);
        map.addRoad(road);
    }
    
    @Override
	public String toString() {
		return "New City Road '" + id + "'";
	}
}
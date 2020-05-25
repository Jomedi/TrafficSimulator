package simulator.model;

import java.io.IOException;

public class InterCityRoad extends Road {

	protected InterCityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length,
			Weather weather) throws IOException {
		super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
	}

	@Override
	protected void reduceTotalContamination() {
		double x = 0;
		int tc = totContamination;

		if (weather == Weather.SUNNY)
			x = 2.0;
		else if (weather == Weather.CLOUDY)
			x = 3.0;
		else if (weather == Weather.RAINY)
			x = 10.0;
		else if (weather == Weather.WINDY)
			x = 15.0;
		else if (weather == Weather.STORM)
			x = 20.0;

		totContamination = ((int)(((100.0 - x) / 100.0) * tc));
	}

	@Override
	protected void updateSpeedLimit() {
		if (totContamination > contLimit)
			speedLimit = (int) Math.ceil((maxSpeed*0.5));
		else
			speedLimit = maxSpeed;
	}

	@Override
	protected int calculateVehicleSpeed(Vehicle v) {
		v.actSpeed = speedLimit;

		if (weather == Weather.STORM)
			v.actSpeed = (int) Math.ceil(speedLimit * 0.8);

		return v.actSpeed; 
	}
}

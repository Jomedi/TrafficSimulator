package simulator.model;

import java.io.IOException;

public class CityRoad extends Road {

	protected CityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length,
			Weather weather) throws IOException {
		super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
	}

	@Override
	protected void reduceTotalContamination() {
		int x = 0;

		if (weather == Weather.WINDY || weather == Weather.STORM)
			x = 10;
		else
			x = 2;

		if ((totContamination - x) < 0)
			totContamination = 0;
		else
			totContamination = totContamination - x;
	}

	@Override
	protected void updateSpeedLimit() {
		speedLimit = maxSpeed;
	}

	@Override
	protected int calculateVehicleSpeed(Vehicle v) {
		int s = speedLimit;
		int f = v.contClass;

		v.actSpeed = (int)Math.ceil((((11.0 - f) / 11.0) * s));

		return v.actSpeed;
	}

}

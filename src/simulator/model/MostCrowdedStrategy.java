package simulator.model;

import java.util.List;

public class MostCrowdedStrategy implements LightSwitchStrategy {

	private int timeSlot;

	public MostCrowdedStrategy(int timeSlot) {

		this.timeSlot = timeSlot;
	}

	@Override
	public int chooseNextGreen(List<Road> roads, List<List<Vehicle>> qs, int currGreen, int lastSwitchingTime,
			int currTime) {
		if (roads.size() == 0)
			return -1;
		else if (currGreen == -1) {
			List<Vehicle> v = null;
			int index = -1;
			for (int i = 0; i < qs.size(); i++) {
				if (qs.get(i).size() > v.size()) {
					v = qs.get(i);
					index = i;
				}
			}
			return index;
		} else if (currTime - lastSwitchingTime < timeSlot)
			return currGreen;
		else {
			List<Vehicle> v = null;
			int index = -1;
			for (int i = (currGreen + 1) % roads.size(); i < qs.size(); i++) {
				if (qs.get(i).size() > v.size()) {
					v = qs.get(i);
					index = i;
				}
			}	
			return index;
		}
	}

}

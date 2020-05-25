package simulator.factories;

import org.json.JSONObject;

import simulator.model.LightSwitchStrategy;
import simulator.model.MostCrowdedStrategy;

public class MostCrowdedStrategyBuilder extends Builder<LightSwitchStrategy> {

	private int timeSlot = 1;

	public MostCrowdedStrategyBuilder() {
		super("most_crowded_lss");
	}

	@Override
	protected LightSwitchStrategy createTheInstance(JSONObject data) {
		timeSlot = data.getInt("timeslot");
		return new MostCrowdedStrategy(timeSlot);
	}

}

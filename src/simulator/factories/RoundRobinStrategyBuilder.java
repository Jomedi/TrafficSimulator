package simulator.factories;

import org.json.JSONObject;

import simulator.model.LightSwitchStrategy;
import simulator.model.RoundRobinStrategy;

public class RoundRobinStrategyBuilder extends Builder<LightSwitchStrategy> {

	private int timeSlot = 1;

	public RoundRobinStrategyBuilder() {
		super("round_robin_lss");
	}

	@Override
	protected LightSwitchStrategy createTheInstance(JSONObject data) {
		timeSlot = data.getInt("timeslot");
		return new RoundRobinStrategy(timeSlot);
	}

}

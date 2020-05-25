package simulator.model;

import java.io.IOException;

public class NewJunctionEvent extends Event {

	private String id;
	private LightSwitchStrategy lsStrategy;
	private DequeingStrategy dqStrategy;
	private int xCoor;
	private int yCoor;

	public NewJunctionEvent(int time, String id, LightSwitchStrategy lsStrategy, DequeingStrategy dqStrategy, int xCoor,
			int yCoor) {
		super(time);

		this.id = id;
		this.lsStrategy = lsStrategy;
		this.dqStrategy = dqStrategy;
		this.xCoor = xCoor;
		this.yCoor = yCoor;

	}

	@Override
	protected void execute(RoadMap map) throws IOException {
		Junction junction = new Junction(id, lsStrategy, dqStrategy, xCoor, yCoor);
		map.addJunction(junction);
	}
	
	@Override
	public String toString() {
		return "New Junction '" + id + "'";
	}
}

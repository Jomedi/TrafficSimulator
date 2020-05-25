package simulator.model;

import java.io.IOException;

public abstract class Event implements Comparable<Event> {

	protected int _time;

	Event(int time) {
		if (time < 1)
			throw new IllegalArgumentException("Time must be positive (" + time + ")");
		else
			_time = time;
	}

	protected int getTime() {
		return _time;
	}

	@Override
	public int compareTo(Event o) {
		return _time - o._time;
	}

	abstract void execute(RoadMap map) throws IOException;
}

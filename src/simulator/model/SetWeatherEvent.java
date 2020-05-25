package simulator.model;

import java.io.IOException;
import java.util.List;

import simulator.misc.Pair;

public class SetWeatherEvent extends Event {

	private List<Pair<String, Weather>> ws;

	public SetWeatherEvent(int time, List<Pair<String, Weather>> ws) throws IOException {
		super(time);

		if (ws == null)
			throw new IOException("ws can't be null"); // revisar el comentario de la excepción (ws)

		this.ws = ws;
	}

	@Override
	protected void execute(RoadMap map) throws IOException {

		for (int i = 0; i < ws.size(); i++) {
			Road r = map.getRoad(ws.get(i).getFirst());
			if (r == null)
				throw new IOException("Road doesn't exist");
			else
				r.setWeather(ws.get(i).getSecond());
		}
	}
	
	@Override
	public String toString() {
		return "New Weather '" + id + "'";
	}

}

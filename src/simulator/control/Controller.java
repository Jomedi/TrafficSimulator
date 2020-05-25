package simulator.control;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import simulator.factories.Factory;
import simulator.model.Event;
import simulator.model.TrafficSimObserver;
import simulator.model.TrafficSimulator;

public class Controller {

	private TrafficSimulator sim;
	private Factory<Event> eventsFactory;

	public Controller(TrafficSimulator sim, Factory<Event> eventsFactory) throws IOException {
		this.sim = sim;
		this.eventsFactory = eventsFactory;

		if (sim == null && eventsFactory == null)
			throw new IOException("The params can't be a null value");
	}

	public void loadEvents(InputStream in) throws JSONException, IOException {
		JSONObject jo = new JSONObject(new JSONTokener(in));

		JSONArray events = jo.getJSONArray("events");

		for (int i = 0; i < events.length(); i++) {
			sim.addEvent(eventsFactory.createInstance(events.getJSONObject(i)));
		}
	}

	public void run(int n, OutputStream out) throws IOException{

		if (out == null) {
			out = new OutputStream() {
				@Override
				public void write(int b) throws IOException {}
			};
		}
		PrintStream p = new PrintStream(out);
		p.println("{");
		p.println(" \"states\": [");
		
		for(int i = 0; i < n-1; i++) {
			sim.advance(); 
			p.print(sim.report()); 
			p.println(",");
		}
		
		sim.advance();
		p.print(sim.report()); 
		p.println();
		p.println("]");
		p.println("}");
	}

	public void reset() {
		sim.reset();
	}
	
	public void addObserver(TrafficSimObserver o) {
		sim.addObserver(o);
	}
	
	void removeObserver(TrafficSimObserver o) {
		sim.removeObserver(o);
	}
	
	void addEvent(Event e) {
		sim.addEvent(e);
	}
}

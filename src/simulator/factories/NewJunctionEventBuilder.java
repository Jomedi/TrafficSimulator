package simulator.factories;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import simulator.model.DequeingStrategy;
import simulator.model.Event;
import simulator.model.LightSwitchStrategy;
import simulator.model.NewJunctionEvent;


public class NewJunctionEventBuilder extends Builder<Event> {

	private int time;
	private String id;
	private LightSwitchStrategy lsStrategy;
	private Factory<LightSwitchStrategy> lssFactory;
	private DequeingStrategy dqStrategy;
	private Factory<DequeingStrategy> dqsFactory;
	private int xCoor;
	private int yCoor;

    public NewJunctionEventBuilder(Factory<LightSwitchStrategy> lssFactory, Factory<DequeingStrategy> dqsFactory) {
    	super("new_junction");
    	this.lssFactory = lssFactory;
    	this.dqsFactory = dqsFactory;
    }

    @Override
    protected Event createTheInstance(JSONObject data) throws JSONException, IOException {
        time = data.getInt("time");
        id = data.getString("id");
        xCoor = data.getJSONArray("coor").getInt(0);
        yCoor = data.getJSONArray("coor").getInt(1);

        lsStrategy = lssFactory.createInstance(data.getJSONObject("ls_strategy"));
        dqStrategy = dqsFactory.createInstance(data.getJSONObject("dq_strategy"));
        
        return new NewJunctionEvent(time, id, lsStrategy, dqStrategy, xCoor, yCoor);
    }

}

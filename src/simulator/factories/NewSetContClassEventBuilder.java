package simulator.factories;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.NewSetContClassEvent;

public class NewSetContClassEventBuilder extends Builder<Event>{
	
	private List<Pair<String, Integer>> pairList;
    private JSONArray jsArray;

	public NewSetContClassEventBuilder() {
		super("set_cont_class");
	}

	@Override
	protected Event createTheInstance(JSONObject data) throws JSONException, IOException {
		pairList = new ArrayList<Pair<String, Integer>>();
        jsArray = data.getJSONArray("info");

        for (int i = 0; i < jsArray.length(); i++) {
        	JSONObject j = jsArray.getJSONObject(i);
            pairList.add(
                    new Pair<String, Integer>(j.getString("vehicle"), j.getInt("class")));
        }
        return new NewSetContClassEvent(data.getInt("time"), pairList);
	}

}

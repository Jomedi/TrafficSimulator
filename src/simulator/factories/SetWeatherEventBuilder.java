package simulator.factories;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.SetWeatherEvent;
import simulator.model.Weather;

public class SetWeatherEventBuilder extends Builder<Event> {

	private List<Pair<String, Weather>> pairList;
	private JSONArray jsArray;

    public SetWeatherEventBuilder() {
        super("set_weather");
    }

    @Override
    protected Event createTheInstance(JSONObject data) throws JSONException, IOException {
        pairList = new ArrayList<Pair<String, Weather>>();
        jsArray = data.getJSONArray("info");

        for (int i = 0; i < jsArray.length(); i++) {
            JSONObject j = jsArray.getJSONObject(i);
            pairList.add(
                    new Pair<String, Weather>(j.getString("road"), Weather.valueOf(j.getString("weather"))));
        }
        
        return new SetWeatherEvent(data.getInt("time"), pairList);
    }

}

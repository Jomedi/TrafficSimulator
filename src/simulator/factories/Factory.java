package simulator.factories;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

public interface Factory<T> {
	public T createInstance(JSONObject info) throws JSONException, IOException;
}

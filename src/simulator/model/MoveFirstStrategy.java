package simulator.model;

import java.util.ArrayList;
import java.util.List;

public class MoveFirstStrategy implements DequeingStrategy {

	@Override
	public List<Vehicle> dequeue(List<Vehicle> q) {
		List<Vehicle> d = new ArrayList<Vehicle>();
		Vehicle v;

		if (!q.isEmpty()) {
			v = q.get(0);
			d.add(v);
		}

		return d;
	}

}

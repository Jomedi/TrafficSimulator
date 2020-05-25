package simulator.model;

import java.io.IOException;
import java.util.List;

import simulator.misc.Pair;

public class NewSetContClassEvent extends Event {

	private List<Pair<String, Integer>> cs;

	public NewSetContClassEvent(int time, List<Pair<String, Integer>> cs) throws IOException {
		super(time);

		if (cs == null)
			throw new IOException("cs can't be null"); // revisar el comentario de la excepción (cs)

		this.cs = cs;
	}

	
	@Override
	protected void execute(RoadMap map) throws IOException {
		for (int i = 0; i < cs.size(); i++) {
			Vehicle v = map.getVehicle(cs.get(i).getFirst());
			if (v == null)
				throw new IOException("Vehicle doesn't exist");
			else
				v.setContaminationClass(cs.get(i).getSecond());
		}
	}
	
	
	@Override
	public String toString() {
		for(int i = 0; i < cs.size(); i++) {
			String idVehiculo = cs.get(i).getFirst();
			Integer contClass = cs.get(i).getSecond();
			
			return "New contamination class of'" cs.get() ;
		}
		
	}

}

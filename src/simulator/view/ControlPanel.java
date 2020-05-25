package simulator.view;

import java.util.List;

import javax.swing.*;

import Prueba.Iconos;
import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class ControlPanel extends JPanel implements TrafficSimObserver {

	private Controller ctrl;
	private JButton load; 
	private JButton contamination; 
	private JButton condWeather;
	private JButton run; 
	private JButton stop; 
	private JButton ticks;

	public ControlPanel(Controller ctrl) {
		this.ctrl = ctrl;
		
		this.load = new JButton("Load File Chooser");
		this.contamination = new JButton("Contamination");
		this.condWeather = new JButton("Weather");
		this.run = new JButton("Run");
		this.stop = new JButton("Stop");
		this.ticks = new JButton("Ticks");
		
		//imagen
		ImageIcon exit = null;
		java.net.URL path = null;
		path = Iconos.class.getResource("./images/exit.png");
		if (path != null){
			exit = new ImageIcon(path);
		}
		
	}


	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub

	}

}

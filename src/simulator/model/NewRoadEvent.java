package simulator.model;

public abstract class NewRoadEvent extends Event{

    protected String id;
    protected String srcJunc, destJunc;
    protected Junction srcJ, destJ;
    protected int length;
    protected int CO2Limit;
    protected int maxSpeed;
    protected Weather weather;

    NewRoadEvent(int time, String id, String srcJunc, String destJunc, int length, int CO2Limit,
            int maxSpeed, Weather weather) {
        super(time);

        this.id = id;
        this.srcJunc = srcJunc;
        this.destJunc = destJunc;
        this.length = length;
        this.CO2Limit = CO2Limit;
        this.maxSpeed = maxSpeed;
        this.weather = weather;
        
    }
    
    protected void getJunctions(RoadMap map) {
    	srcJ = map.getJunction(srcJunc);
        destJ = map.getJunction(destJunc);
    }
}
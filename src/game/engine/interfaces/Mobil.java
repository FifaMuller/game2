package game.engine.interfaces;

public interface Mobil
{
	int getDistance();

	void setDistance(int distance);

	int getSpeed();

	void setSpeed(int speed);
	
	
	public default boolean hasReachedTarget() {
		if(getDistance() == 0)
			return true;
		
		else
			return false;
	}
	
	public default boolean move() {		
		
		if(getDistance() < 0)
			return true;
		else {
			setDistance(getDistance() - getSpeed());
			return false;}
	}

}

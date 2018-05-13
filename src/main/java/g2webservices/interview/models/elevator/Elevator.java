package g2webservices.interview.models.elevator;

import java.util.Set;

public interface Elevator {

	String getName();
	ElevatorState getState();
	boolean openDoor();
	boolean closeDoor();
	void up();
	void down();
	void stop();
	void alarm();
	int getMaxCapacity();
	Set<Integer> getRestrictedFloors(); 
}

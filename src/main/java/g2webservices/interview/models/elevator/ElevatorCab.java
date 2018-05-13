package g2webservices.interview.models.elevator;

import g2webservices.interview.enums.DirectionEnum;
import g2webservices.interview.notifier.FloorChangeObserver;

public class ElevatorCab implements FloorChangeObserver{

	private final String name;

	public ElevatorCab(String name) {
		this.name = name;
	}

	public void floorChanged(int floor, DirectionEnum direction) {
		System.out.println("Notification received in " + name + " .Floor changed to " + floor + " Moving in direction " + direction );
	}
}

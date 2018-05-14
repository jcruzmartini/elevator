package g2webservices.interview.models.elevator;

import static g2webservices.interview.utils.TimeUtils.simulate;

import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import g2webservices.interview.enums.StatusEnum;
import g2webservices.interview.notifier.FloorChangeObserver;

public class ElevatorImpl implements Elevator {

	private String name;
	private ElevatorState state;
	private int capacity; // in tons
	List<FloorChangeObserver> subscribers = new ArrayList<>(); // floors and cabs to be notified when new floor is reached
	Set<Integer> securized = new HashSet<>();

	public ElevatorImpl(String name, ElevatorState state, Integer capacity, List<FloorChangeObserver> floors,
			Set<Integer> securized, ElevatorCab cab) {
		this.name = name;
		this.state = state;
		this.capacity = capacity;
		this.subscribers = floors;
		this.securized = securized;
		addObserver(cab);
	}

	@Override
	public ElevatorState getState() {
		return state;
	}

	@Override
	public boolean openDoor() {
		state.setStatus(StatusEnum.OPEN_DOOR);
		System.out.println("Opening Door");
		simulate(2);
		System.out.println("Door Opened");
		return true;
	}

	@Override
	public boolean closeDoor() {
		state.setStatus(StatusEnum.IDLE);
		System.out.println("Closing Door");
		simulate(2);
		System.out.println("Door Closed");
		return true;
	}

	public void up() {
		final int nextFloor = state.getCurrent() + 1;
		state.setCurrent(nextFloor);
		move(nextFloor);
	}

	public void down() {
		final int nextFloor = state.getCurrent() - 1;
		state.setCurrent(nextFloor);
		move(nextFloor);
	}

	private void move(int floor) {
		simulate(1);
		notifyFloorChange();// notify screen in all floors
	}

	@Override
	public Integer getMaxCapacity() {
		return capacity;
	}
	
	@Override
	public void notifyFloorChange() {
		subscribers.forEach(f -> f.floorChanged(state.getCurrent(), state.getDirection()));
	}

	@Override
	public Set<Integer> getRestrictedFloors() {
		return securized;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void addObserver(FloorChangeObserver observer) {
		subscribers.add(observer);
	}

	@Override
	public void stop() {
		state.setStatus(StatusEnum.STOPPED);
	}

	@Override
	public void alarm() {
		System.out.println("weight limit is exceeded, please call maintenance service");
		Toolkit.getDefaultToolkit().beep();
	}

}

package g2webservices.interview.handlers;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import g2webservices.interview.enums.DirectionEnum;
import g2webservices.interview.enums.StatusEnum;
import g2webservices.interview.models.elevator.Elevator;
import g2webservices.interview.models.elevator.ElevatorRequest;
import g2webservices.interview.models.elevator.ElevatorState;

/**
 * simple abstract request handler for controlling elevator requests
 * 
 * @author jmartini
 *
 */
public abstract class ElevatorRequestHandlerAbstract implements ElevatorRequestHandler {
	
	private final Set<Integer> intermediateStops = Collections.synchronizedSet(new HashSet<>());
	private final Elevator elevator;
	
	public ElevatorRequestHandlerAbstract(Elevator elevator) {
		this.elevator = elevator;
	}

	@Override
	public void process(ElevatorRequest request) {
		if (!isValid(request)) {
			return;
		}
		Elevator elevator = getElevator();
		ElevatorState state = elevator.getState();
		
		state.setStatus(StatusEnum.RUNNING);

		if (request.getTarget() == state.getCurrent()) {
			openDoor();
		}

		final int difference = request.getTarget() - state.getCurrent();
		final DirectionEnum direction = (difference > 0) ? DirectionEnum.UP : DirectionEnum.DOWN;
		
		doMovement(elevator, direction, Math.abs(difference));
		
		final boolean isOpened = openDoor();
		if (isOpened) {
			closeDoor();
		}
		state.setStatus(StatusEnum.IDLE);
	}

	private void doMovement(Elevator elevator, DirectionEnum direction, int difference) {
		ElevatorState state = elevator.getState();
		state.setDirection(direction);
		
		for (int i = 0; i < difference; i++) {
			if (direction == DirectionEnum.DOWN) {
				elevator.down();
			} else {
				elevator.up();
			}
			if (intermediateStops.contains(state.getCurrent())) {
				removeStop(state.getCurrent());
				openDoor();
				closeDoor();
			}
		}
	}

	@Override
	public boolean isValid(ElevatorRequest request) {
		ElevatorState state = getElevator().getState();
		if (state.getStatus() == StatusEnum.STOPPED) {
			System.out.println("Elevator under maintenance service");
			return false;
		}
		if (maxReached(request)) {
			elevator.stop();
			elevator.alarm();
			return false;
		}
		return true;
	}

	

	private boolean maxReached(ElevatorRequest request) {
		return request.getWeight() >= getElevator().getMaxCapacity();
	}

	@Override
	public Elevator getElevator() {
		return elevator;
	}
	
	@Override
	public boolean openDoor() {
		return elevator.openDoor();
	}

	@Override
	public void closeDoor() {
		elevator.closeDoor();
	}

	@Override
	public void addStop(Integer floor) {
		intermediateStops.add(floor);
	}
	
	@Override
	public void removeStop(Integer floor) {
		intermediateStops.remove(floor);
	}

}

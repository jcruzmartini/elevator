package g2webservices.interview.manager;

import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;

import g2webservices.interview.enums.DirectionEnum;
import g2webservices.interview.handlers.ElevatorRequestHandler;
import g2webservices.interview.models.elevator.Elevator;
import g2webservices.interview.models.elevator.ElevatorRequest;
import g2webservices.interview.models.elevator.ElevatorState;

public class ElevatorRequestManagerImpl implements ElevatorRequestManager {

	private final ElevatorRequestHandler handler;
	private final ConcurrentLinkedDeque<ElevatorRequest> requests;
	private final Elevator elevator;

	
	public ElevatorRequestManagerImpl(Elevator elevator, ElevatorRequestHandler handler) {
		this.handler = handler;
		this.requests = new ConcurrentLinkedDeque<ElevatorRequest>();
		this.elevator = elevator;
	}

	@Override
	public void send(ElevatorRequest request) {
		System.out.println("Adding new request " + request);
		final ElevatorState state = elevator.getState();
		if (state.isRunning() && (request.getTarget() > state.getCurrent() && state.getDirection() == DirectionEnum.UP)
				|| (request.getTarget() < state.getCurrent() && state.getDirection() == DirectionEnum.DOWN)) {
			handler.addStop(request.getTarget());
		} else {
			requests.add(request);
		}
	}

	@Override
	public void run() {
		while (true) {
			if (!requests.isEmpty()) {
				handler.process(requests.poll());
			}
		}
	}

	@Override
	public Deque<ElevatorRequest> getRequests() {
		return requests;
	}

}

package g2webservices.interview.manager;

import g2webservices.interview.models.elevator.ElevatorRequest;

public interface ElevatorRequestManager extends Runnable{

	void send(ElevatorRequest request);

}

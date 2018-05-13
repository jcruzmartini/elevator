package g2webservices.interview.handlers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import g2webservices.interview.keycard.KeyCardAccessSystem;
import g2webservices.interview.models.elevator.Elevator;
import g2webservices.interview.models.elevator.ElevatorRequest;

/**
 * simple request handler for controlling only Max Weight
 * @author jmartini
 *
 */
public class ElevatorSecuredRequestHandler extends ElevatorRequestHandlerAbstract {
	
    private static final Logger LOGGER = LogManager.getLogger(ElevatorSecuredRequestHandler.class);
    
	private final KeyCardAccessSystem keyCardSystem;
	
	public ElevatorSecuredRequestHandler(Elevator elevator, KeyCardAccessSystem keyCard) {
		super(elevator);
		this.keyCardSystem = keyCard;
	}

	@Override
	public void process(ElevatorRequest request) {
		super.process(request);
	}

	@Override
	public boolean openDoor() {
		final int current = getElevator().getState().getCurrent();
		if (getElevator().getRestrictedFloors().contains(current)){
			if (!keyCardSystem.validate()){
				System.out.println("Access Key Denied");
				return false;
			}
		}
		return super.openDoor();
	}

	
}

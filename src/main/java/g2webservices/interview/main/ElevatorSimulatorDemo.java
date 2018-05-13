package g2webservices.interview.main;

import static g2webservices.interview.utils.TimeUtils.simulate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import g2webservices.interview.enums.StatusEnum;
import g2webservices.interview.handlers.ElevatorRequestHandler;
import g2webservices.interview.handlers.ElevatorSimpleRequestHandler;
import g2webservices.interview.handlers.ElevatorKeyCardRequestHandler;
import g2webservices.interview.keycard.DummyCardAccessSystem;
import g2webservices.interview.keycard.KeyCardAccessSystem;
import g2webservices.interview.manager.ElevatorRequestManager;
import g2webservices.interview.manager.ElevatorRequestManagerImpl;
import g2webservices.interview.models.Building;
import g2webservices.interview.models.Floor;
import g2webservices.interview.models.elevator.Elevator;
import g2webservices.interview.models.elevator.ElevatorCab;
import g2webservices.interview.models.elevator.ElevatorImpl;
import g2webservices.interview.models.elevator.ElevatorRequest;
import g2webservices.interview.models.elevator.ElevatorState;
import g2webservices.interview.notifier.FloorChangeObserver;

/**
 * Hello world!
 *
 */
public class ElevatorSimulatorDemo {
	final private static int MAX_FLOOR = 50;
	final private static int MIN_FLOOR = -1;

	public static void main(String[] args) {
		final ExecutorService executor = Executors.newFixedThreadPool(2);

		List<FloorChangeObserver> observersForPublic = getFloorsToBeNotified();
		Building bulding = new Building(MAX_FLOOR, MIN_FLOOR);

		ElevatorCab cabin = new ElevatorCab("Cab Public Elevator");
		Elevator elevator = new ElevatorImpl("Public", new ElevatorState(null, 0, StatusEnum.IDLE), 1,
				observersForPublic, Stream.of(MIN_FLOOR, MAX_FLOOR).collect(Collectors.toSet()), cabin);
		bulding.addElevator(elevator);

		KeyCardAccessSystem keyCard = new DummyCardAccessSystem();
		ElevatorRequestHandler handler = new ElevatorKeyCardRequestHandler(elevator, keyCard);
		ElevatorRequestManager pManager = new ElevatorRequestManagerImpl(elevator, handler);
		executor.submit(pManager);

		ElevatorRequest request = new ElevatorRequest(16, 0);
		pManager.send(request);

		simulate(2);

		ElevatorRequest request2 = new ElevatorRequest(-1, 0);
		pManager.send(request2);

		simulate(2);

		ElevatorRequest request3 = new ElevatorRequest(5, 0);
		pManager.send(request3);

		simulate(2);

		ElevatorRequest request4 = new ElevatorRequest(50, 0);
		pManager.send(request4);

		simulate(2);

		ElevatorRequest request5 = new ElevatorRequest(5, 2);
		pManager.send(request5);

		ElevatorRequest request6 = new ElevatorRequest(30, 2);
		pManager.send(request6);

		ElevatorRequest request7 = new ElevatorRequest(45, 2);
		pManager.send(request7);

		List<FloorChangeObserver> observersForFreight = getFloorsToBeNotified();
		ElevatorCab cabinFreight = new ElevatorCab("Cab Freight Elevator");
		Elevator freightElevator = new ElevatorImpl("Freight", new ElevatorState(null, 0, StatusEnum.IDLE), 3,
				observersForFreight, null, cabinFreight);
		bulding.addElevator(freightElevator);

		ElevatorRequestHandler handlerFr = new ElevatorSimpleRequestHandler(freightElevator);
		ElevatorRequestManager fRManager = new ElevatorRequestManagerImpl(freightElevator, handlerFr);
		executor.submit(fRManager);

		ElevatorRequest request8 = new ElevatorRequest(16, 0);
		fRManager.send(request8);
		
		simulate(2);

		ElevatorRequest request9 = new ElevatorRequest(-1, 0);
		fRManager.send(request9);

		simulate(2);

		ElevatorRequest request10 = new ElevatorRequest(5, 0);
		fRManager.send(request10);
		
		simulate(2);

		ElevatorRequest request11 = new ElevatorRequest(5, 2);
		fRManager.send(request11);

	}

	private static List<FloorChangeObserver> getFloorsToBeNotified() {
		List<FloorChangeObserver> observers = new ArrayList<>();
		for (int i = -1; i <= 50; i++) {
			observers.add(new Floor(String.valueOf(i), i));
		}
		return observers;
	}
}

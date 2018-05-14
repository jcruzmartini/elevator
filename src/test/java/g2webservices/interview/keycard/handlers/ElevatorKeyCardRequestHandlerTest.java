package g2webservices.interview.keycard.handlers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import g2webservices.interview.enums.StatusEnum;
import g2webservices.interview.handlers.ElevatorKeyCardRequestHandler;
import g2webservices.interview.handlers.ElevatorRequestHandler;
import g2webservices.interview.keycard.DummyCardAccessSystem;
import g2webservices.interview.models.elevator.Elevator;
import g2webservices.interview.models.elevator.ElevatorRequest;
import g2webservices.interview.models.elevator.ElevatorState;

@RunWith(MockitoJUnitRunner.class)
public class ElevatorKeyCardRequestHandlerTest {
	
	private ElevatorRequestHandler handler;
	@Mock
	private Elevator elevator;

	@Mock
	private DummyCardAccessSystem keyCard;
	
	@Before
	public void setUp() throws Exception {
		handler = new ElevatorKeyCardRequestHandler(elevator, keyCard);
	}

	@After
	public void tearDown() throws Exception {
		elevator = null;
		handler = null;
		keyCard = null;
	}

	@Test
	public void testRequestForRestrictedWithAuthenticationOK() {
		final ElevatorState state = new ElevatorState(null, 0, StatusEnum.IDLE);
		final ElevatorRequest request = new ElevatorRequest(-1, 1);
		when(elevator.getRestrictedFloors()).thenReturn(new HashSet<>(Arrays.asList(-1, 0 ,50)));
		when(elevator.getState()).thenReturn(state);
		when(elevator.getMaxCapacity()).thenReturn(2);
		when(keyCard.validate()).thenReturn(true);
		when(elevator.openDoor()).thenReturn(true);
		
		handler.process(request);

		verify(elevator, times(1)).down();
		verify(keyCard, times(1)).validate();
		verify(elevator, times(1)).openDoor();
		verify(elevator, times(1)).closeDoor();
		
		assertEquals(elevator.getState().getStatus(), StatusEnum.IDLE);
	}
	
	@Test
	public void testRequestForRestrictedWithAuthenticationFailed() {
		final ElevatorState state = new ElevatorState(null, 0, StatusEnum.IDLE);
		final ElevatorRequest request = new ElevatorRequest(-1, 1);
		when(elevator.getRestrictedFloors()).thenReturn(new HashSet<>(Arrays.asList(-1, 0 ,50)));
		when(elevator.getState()).thenReturn(state);
		when(elevator.getMaxCapacity()).thenReturn(2);
		when(keyCard.validate()).thenReturn(false);
		
		handler.process(request);

		verify(elevator, times(1)).down();
		verify(keyCard, times(1)).validate();
		verify(elevator, never()).openDoor();
		verify(elevator, never()).closeDoor();
		
		assertEquals(elevator.getState().getStatus(), StatusEnum.IDLE);
	}

}

package g2webservices.interview.models.elevator;

import g2webservices.interview.enums.DirectionEnum;

public class ElevatorRequest {

	private int target;
	private DirectionEnum direction;
	private int weight;
	
	
	public ElevatorRequest(int target, DirectionEnum direction, int weight) {
		super();
		this.target = target;
		this.direction = direction;
		this.weight = weight;
	}


	public int getTarget() {
		return target;
	}


	public DirectionEnum getDirection() {
		return direction;
	}


	public int getWeight() {
		return weight;
	}




	@Override
	public String toString() {
		return "ElevatorRequest [target=" + target + ", direction=" + direction + ", weight=" + "weight ]";
	}
	
}

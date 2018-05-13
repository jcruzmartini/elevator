package g2webservices.interview.models.elevator;

public class ElevatorRequest {

	private int target;
	private int weight;
	
	
	public ElevatorRequest(int target, int weight) {
		this.target = target;
		this.weight = weight;
	}


	public int getTarget() {
		return target;
	}




	public int getWeight() {
		return weight;
	}


	@Override
	public String toString() {
		return "ElevatorRequest [target=" + target + ", weight=" + "weight ]";
	}
	
}

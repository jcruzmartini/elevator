package g2webservices.interview.notifier;

public interface FloorChangeNotifier {
	
	void addObserver(FloorChangeObserver observer);
    void notifyFloorChange();
    
}

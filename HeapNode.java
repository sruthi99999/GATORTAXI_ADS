//A class representing a node in the heap.
public class HeapNode {
    //Each node has a reference to an RBTreeNode and a Ride object.
	private RBTreeNode ref;
	private Ride ride;
	//Constructor to create a new HeapNode
	public HeapNode(Ride ride,RBTreeNode ref){
		this.setRbtReference(ref);
		this.setRide(ride);
			
	}
    //getter and setters
	
	public RBTreeNode getRbtReference() {
        return ref;
    }

    private void setRbtReference(RBTreeNode node) {
        this.ref = node;
    }
    public Ride getRide() {
        return ride;
    }

    public void setRide(Ride ride) {
        this.ride = ride;
    }
}

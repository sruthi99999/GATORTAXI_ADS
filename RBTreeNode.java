public class RBTreeNode {
    //The attributes of the class RBTreenode
    private Ride ride;
    private boolean isRed;
    private RBTreeNode left;
    private RBTreeNode right;
    private RBTreeNode parent;
// Constructor for creating a new node with a given ride object and color.
    public RBTreeNode(Ride ride, boolean isRed) {
        this.ride = ride;
        this.isRed = isRed;
    }
//getters and setters for the objects stored in this node.
    public Ride getRide() {
        return ride;
    }

    public void setRide(Ride ride) {
        this.ride = ride;
    }

    public boolean isRed() {
        return isRed;
    }

    public void setRed(boolean isRed) {
        this.isRed = isRed;
    }

    public RBTreeNode getLeft() {
        return left;
    }

    public void setLeft(RBTreeNode left) {
        this.left = left;
    }

    public RBTreeNode getRight() {
        return right;
    }

    public void setRight(RBTreeNode right) {
        this.right = right;
    }

    public RBTreeNode getParent() {
        return parent;
    }

    public void setParent(RBTreeNode parent) {
        this.parent = parent;
    }

    public int getColor() {
		return isRed ? 0 : 1;
	}
	
	public void setColor(int color) {
		if(color == 1)
            isRed = false;
        else    
            isRed = true;
	}

    public int getRideNumber(){
        return ride.getRideNumber();
    }
}
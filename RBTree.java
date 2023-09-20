import java.util.List;

public class RBTree {
	
private static RBTreeNode rootNode;
	
	//Codes for Red and Black Colour of the nodes
	private static final int COLOR_RED   = 0;
    private static final int COLOR_BLACK = 1;
    
	//Getter function for rootnode of the tree
    public RBTreeNode getRootNode() {
		return rootNode;
	}

	//Setter function for rootnode of the tree
	public void setRootNode(RBTreeNode rootNode) {
		RBTree.rootNode = rootNode;
	}
    
	//Method for right rotation along a node .
	//Performs a simple ClockWise rotation around the given node passed as input
	//Input is of type RBTreeNode and is the node along which rotation is to be performed.
    public void rotateRight(RBTreeNode node) {
    	
    	RBTreeNode tmpry = node.getLeft();
    	node.setLeft(tmpry.getRight());
    	if(tmpry.getRight() != null) {
    		tmpry.getRight().setParent(node);
    	}
    	tmpry.setParent(node.getParent());
    	if(node.getParent() == null) {
    		RBTree.rootNode = tmpry;
    	}
    	else {
    		if(node.getParent().getRight() == node) {
    			node.getParent().setRight(tmpry);
    		}
    		else {
    			node.getParent().setLeft(tmpry);
    		}
    	}
    	tmpry.setRight(node);
    	node.setParent(tmpry);
    }
    
	//Method for left rotation along a node .
	//Performs a simple Anti ClockWise rotation around the given node passed as input
	//Input is of type RBTreeNode and is the node along which rotation is to be performed.
    public void rotateLeft(RBTreeNode node) {
    	
    	RBTreeNode tmpry = node.getRight();
    	node.setRight(tmpry.getLeft());
    	if(tmpry.getLeft() != null) {
    		tmpry.getLeft().setParent(node);
    	}
    	tmpry.setParent(node.getParent());
    	if(node.getParent() == null) {
    		RBTree.rootNode = tmpry;
    	}else {
    		if(node.getParent().getLeft() == node) {
    			node.getParent().setLeft(tmpry);
    		}
    		else {
    			node.getParent().setRight(tmpry);
    		}
    	}
    	tmpry.setLeft(node);
    	node.setParent(tmpry);
    	
    }
    
	//Method for inserting a new node in redblack tree. 
	//Inserts a new node in similar fashion to BST to the redblack tree with no regards for maintenance of redblack property
	//Contains a call to method balanceRBL which is responsible for maintaining the sanctity of redblack property across all nodes 
	//affected by the addition of new node.
	//Input is of type RBTreeNode and is the node which is to be inserted.
    public void insert(RBTreeNode node) {
    	
    	RBTreeNode rootCopy = RBTree.rootNode;
    	RBTreeNode temp = null;
    	node.setColor(RBTree.COLOR_RED);
    	
    	while(rootCopy != null) {
    		temp = rootCopy;
    		if(node.getRideNumber() < rootCopy.getRideNumber()) {
        		rootCopy = rootCopy.getLeft();
        	}
        	else {
        		rootCopy = rootCopy.getRight();
        	}
    	}
    	node.setParent(temp);
    	if(temp != null) {
    		if(node.getRideNumber() < temp.getRideNumber()) {
    			temp.setLeft(node);
    		}
    		else {
    			temp.setRight(node);
    		}
    	}
    	else {
    		RBTree.rootNode = node;
    	}
    	balanceRBLInsert(node);
    }
    
	//Method responsible for maintaining the sanctity of red black property across all the nodes affected by the newly imnserted nodes.
	//Performs roatations based on the color of parent, grand parent and uncle of the affected node.
	//Input is of type RBTreeNode and is the node which was inserted in the insert method.
    public void balanceRBLInsert(RBTreeNode node) {
    	
    	RBTreeNode par, gpar;
    	par = node.getParent();
    	while(par != null && par.getColor() == RBTree.COLOR_RED)
    	{
    		gpar = par.getParent();
    		if(par == gpar.getLeft()) {
    			RBTreeNode uncle = gpar.getRight();
    			if(null != uncle && uncle.getColor() == RBTree.COLOR_RED) {
    				uncle.setColor(RBTree.COLOR_BLACK);
    				par.setColor(RBTree.COLOR_BLACK);
    				gpar.setColor(RBTree.COLOR_RED);
    				node = gpar;
    				continue;
    			}
    			if(par.getRight() == node) {
    				RBTreeNode tmpry;
    				rotateLeft(par);
    				tmpry = par;
    				par = node;
    				node = tmpry;
    			}
    			par.setColor(RBTree.COLOR_BLACK);
    			gpar.setColor(RBTree.COLOR_RED);
    			rotateRight(gpar);
    		}
    		else {
    			RBTreeNode uncle = gpar.getLeft();
    			if(null != uncle && uncle.getColor() == RBTree.COLOR_RED) {
    				uncle.setColor(RBTree.COLOR_BLACK);
    				par.setColor(RBTree.COLOR_BLACK);
    				gpar.setColor(RBTree.COLOR_RED);
    				node = gpar;
    				continue;
    			}
    			if(par.getLeft() == node) {
    				RBTreeNode tmpry;
    				rotateRight(par);
    				tmpry = par;
    				par = node;
    				node = tmpry;
    			}
    			par.setColor(RBTree.COLOR_BLACK);
    			gpar.setColor(RBTree.COLOR_RED);
    			rotateLeft(gpar);
    		}
    	}
    	RBTree.rootNode.setColor(RBTree.COLOR_BLACK);
    }
    
	//Method is responsible for deletion of the input node.
	//Similar to insert method, deletion performed is with no regards of maintaining the redblack tree property.
	//Contains call to method rebalanceRBLDelete which is responsible for maintaining the redblack property after deletion of a node.
	//Input is of type RBTreeNode and is the node which is to be deleted.
    public void delete(RBTreeNode node) {
    	RBTreeNode chi, par;
    	int tempCol;
    	
    	if(node.getLeft() != null && node.getRight() != null) {
    		RBTreeNode tmpry = node;
    		tmpry = tmpry.getRight();
    		
    		while(tmpry.getLeft() != null) {
    			tmpry = tmpry.getLeft();
    		}
    		
    		if(node.getParent() != null) {
    			if(node.getParent().getLeft() == node){
    				node.getParent().setLeft(tmpry);
    			}
	    		else {
	    			node.getParent().setRight(tmpry);
	    		}
    		}
    		else {
    			RBTree.rootNode = tmpry;
    		}
    		
    		chi = tmpry.getRight();
    		par = tmpry.getParent();
    		tempCol = tmpry.getColor();
    		
    		if(par == node) {
    			par = tmpry;
    		}else {
    			if(chi != null) {
    				chi.setParent(par);
    			}
    			par.setLeft(chi);
    			tmpry.setRight(node.getRight());
    			node.getRight().setParent(tmpry);
    		}
    		
    		tmpry.setParent(node.getParent());
    		tmpry.setColor(node.getColor());
    		tmpry.setLeft(node.getLeft());
    		node.getLeft().setParent(tmpry);
    		
    		if(tempCol == COLOR_BLACK) {
    			rebalanceRBLDelete(chi, par);
    		}
    		
    		node = null;
    		return; 
    	}
    	
    	
    	if(node.getLeft() != null) {
    		chi = node.getLeft();
    	}
    	else {
    		chi = node.getRight();
    	}
    	
    	par = node.getParent();
    	tempCol = node.getColor();
    	
    	if(chi != null) {
    		chi.setParent(par);
    	}
    	
    	if(par != null) {
			if(par.getLeft() != null && par.getLeft() == node){
				par.setLeft(chi);
			}
    		else {
    			par.setRight(chi);
    		}
		}
		else {
			RBTree.rootNode = chi;
		}
    	
    	if(tempCol == COLOR_BLACK) {
			rebalanceRBLDelete(chi, par);
		}
		node = null;
    }
    
	//Method responsible for maintaining the sanctity of red black property across all the nodes affected by the deleted nodes.
	//Performs roatations based on the color of parent, grand parent and uncle of the affected node.
	//Input is of type RBTreeNode and is the node which was deleted in the delete method.
    private void rebalanceRBLDelete(RBTreeNode chi, RBTreeNode par) {
    	RBTreeNode tmpry;
    	while(((chi == null || (chi.getColor() == COLOR_BLACK))) 
    			&& chi != RBTree.rootNode) {
    		
    		if(par.getLeft() == chi) {
    			tmpry = par.getRight();
    			
    			if(tmpry.getColor() == COLOR_RED) {
    				tmpry.setColor(COLOR_BLACK);
    				par.setColor(COLOR_RED);
    				rotateLeft(par);
    				tmpry = par.getRight();
    			}
    			
    			if((tmpry.getLeft() == null || (tmpry.getLeft().getColor() == COLOR_BLACK))
    					&& (tmpry.getRight() == null || (tmpry.getRight().getColor() == COLOR_BLACK))) {
    				tmpry.setColor(COLOR_RED);
    				chi = par;
    				par = chi.getParent();
    			}else {
    				
    				if(tmpry.getRight() == null || (tmpry.getRight().getColor() == COLOR_BLACK)) {
    					tmpry.getLeft().setColor(COLOR_BLACK);
    					tmpry.setColor(COLOR_RED);
    					rotateRight(tmpry);
    					tmpry = par.getRight();
    				}
    				
    				tmpry.setColor(par.getColor());
    				par.setColor(COLOR_BLACK);
    				tmpry.getRight().setColor(COLOR_BLACK);
    				rotateLeft(par);
    				chi = RBTree.rootNode;
    				break;
    			}
    		}else {
    			
    			tmpry = par.getLeft();
    			if(tmpry.getColor() == COLOR_RED) {
    				tmpry.setColor(COLOR_BLACK);
    				par.setColor(COLOR_RED);
    				rotateRight(par);
    				tmpry = par.getLeft();
    			}
    			
    			if((tmpry.getLeft() == null || (tmpry.getLeft().getColor() == COLOR_BLACK))
    					&& (tmpry.getRight() == null || (tmpry.getRight().getColor() == COLOR_BLACK))) {
    				tmpry.setColor(COLOR_RED);
    				chi = par;
    				par = chi.getParent();
    			}else {
    				
    				if((tmpry.getLeft() == null || (tmpry.getLeft().getColor() == COLOR_BLACK))) {
    					tmpry.getRight().setColor(COLOR_BLACK);
    					tmpry.setColor(COLOR_RED);
    					rotateLeft(tmpry);
    					tmpry = par.getLeft();
    				}
    				
    				tmpry.setColor(par.getColor());
    				par.setColor(COLOR_BLACK);
    				tmpry.getLeft().setColor(COLOR_BLACK);
    				rotateRight(par);
    				chi = RBTree.rootNode;
    				break;
    			}	
    		}
    	}
    	if(chi != null) {
    		chi.setColor(COLOR_BLACK);
    	}
    }
	// this is a function to serach for a particular node with the given ridenumber 
    public RBTreeNode search(int rideNumber) {
    	RBTreeNode current = rootNode;
        while (current != null) {
            if (current.getRide().getRideNumber() == rideNumber) {
                return current;
            } else if (current.getRide().getRideNumber() > rideNumber) {
                current = current.getLeft();
            } else {
                current = current.getRight();
            }
        }
        return null; // rideNumber not found in tree
    }
	//function to print the ride details of a particular ridenumber
    public RBTreeNode Print(RBTreeNode rNode, int rideNumber) {
    	
    	if(rNode == null || (rNode.getRide().getRideNumber() == rideNumber)) {
    		return rNode;
    	}
    	if(rideNumber < rNode.getRide().getRideNumber()) {
    		return Print(rNode.getLeft(), rideNumber);
    	}
    	return Print(rNode.getRight(), rideNumber);
    	
    }
	//function to print details of ridenumbers within a range
    public List<RBTreeNode> Print(RBTreeNode rtNode, int rideNumber1, int rideNumber2, List<RBTreeNode> triplets) {
	
        if(rtNode == null) {
            return null;
        }
        
        if(rideNumber1 < rtNode.getRide().getRideNumber()) {
            Print(rtNode.getLeft(), rideNumber1, rideNumber2, triplets);
        }
        
        if(rideNumber1 <= rtNode.getRide().getRideNumber() && rtNode.getRide().getRideNumber() <= rideNumber2) {
            triplets.add(rtNode);
        }
        
        if(rideNumber2 > rtNode.getRide().getRideNumber()) {
            Print(rtNode.getRight(), rideNumber1, rideNumber2, triplets);
        }
        return triplets;
        
    }
	//function to update the tripdetails for a particular ride.
    public int UpdateTrip(RBTreeNode rNode, int rideNumber,int new_tripDuration ) {
	
        if(rNode != null ) {
    
            if((new_tripDuration<=rNode.getRide().getTripDuration() )) {
                return 1;
    //    		rNode.getRide().setTripDuration(new_tripDuration);
            }
            else if(rNode.getRide().getTripDuration() < new_tripDuration && new_tripDuration<=2*rNode.getRide().getTripDuration()) {
                return 2;
            }
            else if(new_tripDuration>2*rNode.getRide().getTripDuration())
                return 3;
        }
        return 0;
        
    }

    
}
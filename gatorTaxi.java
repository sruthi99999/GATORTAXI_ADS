import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class gatorTaxi{
	public static void main(String[] args) throws IOException {
        // Create a new instance of RideMinHeap to hold rides with minimum trip duration
		RideMinHeap minheap=new RideMinHeap();
        // Create a new instance of RBTree to hold all rides in the system
		RBTree rbt=new RBTree();
		String inputPath = null;

		if(args.length != 0) {
			inputPath = args[0];
		}

		//Take input from file. File name is taken input as a command line arg.
		File inputFile = new File(args[0]);// Create a new File object based on the input file name provided as a command line argument
		Scanner scanner = new Scanner(inputFile);// Create a new Scanner object to read from the input file
		BufferedWriter out = new BufferedWriter(new FileWriter("output.txt"));// Create a new BufferedWriter object to write to the output file
		String line = null;
        int count = 0;
		int i;
		do {
			
			String [] words = null;
			String Command = null;
			line = null;
			if(scanner.hasNext() && line == null) {
				line = scanner.nextLine();// Read the next line of input from the file
			}
			if(line != null) {
                //Split the line into individual words
				words = line.split("[,?.@:()]"); 
                //Extract first word which is the command
				Command = words[0].trim();
                //If the command is insert add a new ride
				if("Insert".equals(Command)) {
					int ridenum,ridecost,tripdur;
					RBTreeNode rbtn;
                    //Extract the ride details
					ridenum=Integer.parseInt(words[1].trim());
					ridecost=Integer.parseInt(words[2].trim());
					tripdur=Integer.parseInt(words[3].trim());
                    //search the RBTree for a ride with the given number
					rbtn=rbt.search(ridenum);
                    //if a ride already exists then it is duplicate
					if(rbtn!=null) {
						String op = "Duplicate RideNumber";
						out.write(op);
						out.newLine();
						out.close();
						return;
					}
                    //if the ride is a new ride then insert it.
					else {
					Ride r=new Ride(ridenum,ridecost,tripdur);
					rbtn = new RBTreeNode(r,true);
					HeapNode hn = new HeapNode(r,rbtn);
					rbt.insert(rbtn);
					minheap.insert(hn);
					
					}
				
				}
                //If the command is print 
                //Print all the numbers in a given range
				else if("Print".equals(Command) && words.length==3) {
					int num1,num2;
                    //Extract the lower and upper bound
					num1 = Integer.parseInt(words[1].trim());
					num2 = Integer.parseInt(words[2].trim());
                    // store a list of all the rides within the range
					List<RBTreeNode> range=new ArrayList<RBTreeNode>();
					range=rbt.Print(rbt.getRootNode(), num1, num2, range);
					String op = "";
                    //Print the rides within the range
					if(range != null && range.size() != 0){
					for(RBTreeNode res : range) {
							op = op + ("("+res.getRide().getRideNumber()+","+res.getRide().getRideCost()+","+res.getRide().getTripDuration()+"),");
						}
					
					op = op.substring(0, op.length()-1);
					}
                    //if there are no rides within this range print 0,0,0
					else{
						op = "(0,0,0)";
					}
					out.write(op);
					out.newLine();
					op = null;
				}
                //if the command is to print
				else if("Print".equals(Command) && words.length==2) {
					int num = Integer.parseInt(words[1].trim());
					RBTreeNode rbtn;
                    //print the ride with a given ridenumber 
					rbtn=rbt.Print(rbt.getRootNode(),num);
                    //if the returned value is not null then print the ride details
					if(rbtn!=null) {
						String op = ("("+rbtn.getRide().getRideNumber()+","+rbtn.getRide().getRideCost()+","+rbtn.getRide().getTripDuration()+")");
						out.write(op);
						out.newLine();
					}
                    //if there is no ride with a particular ride number print 0,0,0
					else {
						String op = ("("+0+","+0+","+0+")");
						out.write(op);
						out.newLine();
					}
				}
                //if the command is getnextride then we will get thhe ride details which has minimum cost 
                // we need to delete this ride from the min heap and rbtree using the reference
				else if("GetNextRide".equals(Command)) {
					HeapNode next;
					next=minheap.GetNextRide();
					if(next != null){
						rbt.delete(next.getRbtReference());
						out.write("("+next.getRide().getRideNumber()+","+next.getRide().getRideCost()+","+next.getRide().getTripDuration()+")\n");
					}
					else{
                        //if there are no more rides 
						out.write("No active ride requests\n");
					}
				}
                //to cancel a ride with a particular ridenumber
				else if("CancelRide".equals(Command)) {
					int num = Integer.parseInt(words[1].trim());
					RBTreeNode rbtn=rbt.search(num);
					if(rbtn!=null)
					{
                        //it will delete the rbtree node with the ridenumber and the corresponding minheap
					rbt.delete(rbtn);
					i = 0;
					for(i=1;i<=minheap.getsize();i++) {
						if(minheap.heap[i].getRbtReference()==rbtn)
							break;
					}
					minheap.delete(i);
					}
				}
                //if the command is Update trip 
				else if("UpdateTrip".equals(Command)) {
					int ridenum = Integer.parseInt(words[1].trim());
					int new_tripDuration = Integer.parseInt(words[2].trim());
					RBTreeNode rbtn=rbt.search(ridenum);
                    //we need to update the ride deatils in the datastructure
					int k=rbt.UpdateTrip(rbtn, ridenum, new_tripDuration);
					i =0;
					for(i=1;i<=minheap.getsize();i++) {
						if(minheap.heap[i].getRbtReference()==rbtn)
							break;
					}
					RBTreeNode temp=rbtn;
                    //if the new_tripDuration <= existing tripDuration, then update the existing trip duration
                    //with the new trip duration
                    //here the old nodes are delted and new nodes are inserted with the new duration
					if(k==1) {
						rbt.delete(rbtn);
						minheap.delete(i);
						Ride newride=new Ride(temp.getRide().getRideNumber(),temp.getRide().getRideCost(),new_tripDuration);
						RBTreeNode newrbtn = new RBTreeNode(newride,true);
						HeapNode newhn = new HeapNode(newride,newrbtn);
						rbt.insert(newrbtn);
						minheap.insert(newhn);
					}
                    //f the existing_tripDuration < new_tripDuration <= 2*(existing tripDuration)
					else if(k==2)
					{
						// we should delte the existing ride
						rbt.delete(rbtn);
						minheap.delete(i);
                        //insert a new ride with new ride cost
						Ride newride=new Ride(temp.getRide().getRideNumber(),temp.getRide().getRideCost()+10,new_tripDuration);
						RBTreeNode newrbtn = new RBTreeNode(newride,true);
						HeapNode newhn = new HeapNode(newride,newrbtn);
						rbt.insert(newrbtn);
						minheap.insert(newhn);
					}
                    //if new_tripDuration > 2*(existing tripDuration), 
					else if(k==3)
					{
                        //we need to delte the ride in both datastructures
						i = 0;
						for(i=1;i<=minheap.getsize();i++) {
							if(minheap.heap[i].getRbtReference()==rbtn)
								break;
						}
						rbt.delete(rbtn);
						minheap.delete(i);
					}
					
				}
			}
		}while(line != null&&scanner.hasNext());
		out.close();
        

}
}

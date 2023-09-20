public class RideMinHeap {
    public HeapNode[] heap;// an array to store the rides
    private int capacity;// maximum number of rides that can be stored in the heap
    private int size;// current number of rides in the heap

    

    public RideMinHeap() {
        this.capacity = 2000;
        this.size = 0;
        heap = new HeapNode[capacity + 1];// adding 1 to the capacity for convenience
    
    }
    public int getsize() {
    	return size;
    }
    // returns the index of the parent node
    private int getParent(int index) {
        return index / 2;
    }
    // returns the index of the left child node
    private int getLeftChild(int index) {
        return index * 2;
    }
    // returns the index of the right child node
    private int getRightChild(int index) {
        return index * 2 + 1;
    }
    // swaps the rides at the two specified indexes in the heap array
    private void swap(int index1, int index2) {
    	HeapNode temp = heap[index1];
        heap[index1] = heap[index2];
        heap[index2] = temp;
    }
    // checks if the node at the specified index is a leaf node
    private boolean isLeaf(int index) {
        return index > size / 2 && index <= size;
    }
    // moves the ride at the specified index up the heap until its ride cost is greater than or equal to its parent's ride cost
    private void heapifyUp(int index) {
        while (index > 1 && heap[index].getRide().getRideCost() < heap[getParent(index)].getRide().getRideCost()) {
            swap(index, getParent(index));
            index = getParent(index);
        }
        // if two rides have the same ride cost, move the one with the shorter trip duration up the heap
        while (index > 1 && heap[index].getRide().getRideCost() == heap[getParent(index)].getRide().getRideCost() &&
                heap[index].getRide().getTripDuration() < heap[getParent(index)].getRide().getTripDuration()) {
            swap(index, getParent(index));
            index = getParent(index);
        }
    }

    private void heapifyDown(int index) {
        // moves the ride at the specified index down the heap until its ride cost is less than or equal to its children's ride costs
        
        while (!isLeaf(index)) {
            int left = getLeftChild(index);
            int right = getRightChild(index);
            int minChild = left;

            if (right <= size && heap[right].getRide().getRideCost() < heap[left].getRide().getRideCost()) {
                minChild = right;
            }
            // if the ride cost of the minimum child is equal to the ride cost of the ride at the specified index,
            // move the ride with the shorter trip duration down the heap
            
            if (heap[minChild].getRide().getRideCost() == heap[index].getRide().getRideCost() &&
                    heap[minChild].getRide().getTripDuration() < heap[index].getRide().getTripDuration()) {
                swap(minChild, index);
                index = minChild;
            } else if (heap[minChild].getRide().getRideCost() < heap[index].getRide().getRideCost()) {
                swap(minChild, index);
                index = minChild;
            } else {
                break;
            }
        }
    }
    //Function to insert a ride
    public void insert(HeapNode ride) {
        if (size >= capacity) {
            throw new IndexOutOfBoundsException("Heap is full");
        }
        heap[++size] = ride;
        heapifyUp(size);
    }

    public Ride peek() {
        if (size == 0) {
            return null;
        }
        return heap[1].getRide();
    }
    //function to delete a ride of particular index
    public void delete(int index) {
        if (size == 0) {
            return;
        }
        
        if (index < 1 || index > size) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
        
        HeapNode deletedRide = heap[index];
        heap[index] = heap[size--];
        
        if (index == size+1) {
            // the deleted element was the last element, no need to heapify
            return ;
        }
        
        // check if the element is smaller than its parent
        if (index > 1 && heap[index].getRide().getRideCost() < heap[getParent(index)].getRide().getRideCost()) {
            heapifyUp(index);
        } else {
            heapifyDown(index);
        }
        
        
    }
    
//extractMin
//function to get the next ride 
//This extracts the ride with min cost
    public HeapNode GetNextRide() {
        if (size == 0) {
            return null;
        }
        
        HeapNode min = heap[1];
        heap[1] = heap[size--];
        if (size > 0) {
            heapifyDown(1);
        }
        return min;
    }

    public boolean isEmpty() {
        return size == 0;
    }
}
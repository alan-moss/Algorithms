
//Min heap class 
public class MinHeap 
{
    int[] A;
    int heapsize;
    int i;

    public MinHeap(int heapsize)
    {
        this.heapsize = heapsize;
        this.i = 0;
        A = new int[heapsize];
    }

    //Minheapify method takes index parameter
    public void minHeapify(int i)
    {
        //check if leaf node
        if(leaf(i) != true)
        {
            //Current node greater than children
            if(A[i] > A[getleftchild(i)] || A[i] > A[getrightchild(i)])
            {
                //right child greater than left child
                if(A[getleftchild(i)] < A[getrightchild(i)])
                {
                    //Swap current node with left child
                    swap(i, getleftchild(i));
                }
                //Left child greater than right child
                else
                {
                    //Swap current node with right child
                    swap(i, getrightchild(i));
                    //Recursively call minHeapify
                    minHeapify(getrightchild(i));
                }
            }
        }
    }

    //Method for inserting elements into minheap
    public void insertElement(int num)
    {
        //Index has reached end of heap
        if(i >= heapsize)
        {
            return;
        }

        //Assign array index num
        A[i] = num;
        //store index in current value
        int temp = i;

        while(A[temp] < A[getparent(temp)])
        {
            swap(temp, getparent(temp));
            temp = getparent(temp);
        }
        i++;
    }

    //Pops min value
    public int removeMin()
    {
        int min = A[0];
        A[0] = A[heapsize - 1];
        heapsize--;
        minHeapify(0);
        return min;
    }

     //Swaps array places
     private void swap(int i, int j)
     {
         int temp;
         temp = A[i];
         A[i] = A[j];
         A[j] = temp;
     }

    //Returns left child
    private int getleftchild(int i)
    {
        return i*2+1;
    }

    //Returns right child
    private int getrightchild(int i)
    {
        return i*2+2;
    }

    //Returns parent
    private int getparent(int i)
    {
        return (i-1)/2;
    }

    //Checks if current value is a leaf node
    private boolean leaf(int i)
    {   
        if(getrightchild(i) >= heapsize || getleftchild(i) >= heapsize)
        {
            return true;
        }
        return false;
    }

     // Function to print the contents of the heap
	public void printHeap() {
		for (int j = 0; j < (i / 2); i++) {
			System.out.print("Parent : " + A[i]);
			if (getleftchild(j) < i)
				System.out.print(" Left : " + A[getleftchild(i)]);
			if (getrightchild(i) < i)
				System.out.print(" Right :" + A[getrightchild(i)]);
			System.out.println();
		}
	}
}
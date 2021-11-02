import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class Richest
{
	public static void main(String[] args) throws Exception
	{
        //Get arguments from command line
		String file = args[0];

		File fileEntry = new File(file);


        // Scanner sc = new Scanner(fileEntry);
        // A[0] = sc.nextInt();
        // System.out.println(A[0]);

		try(Scanner sc = new Scanner(fileEntry))
		{
            //Create arraylist for integers from file
			ArrayList<Integer> arraylist = new ArrayList<Integer>();
            int heapsize = 0;
			while(sc.hasNextInt())
			{
                arraylist.add(sc.nextInt());
                heapsize++;
            }

			//Create minheap of 10,000 integers from file
            MinHeap minheap = new MinHeap(10000);

            //Fill minheap using insertElement
            for(int j=0; j < heapsize-1; j++)
            {
                minheap.insertElement(arraylist.get(j).intValue());
            }

            //Prints values
            for(int k = 0; k < heapsize-1; k++)
            {
                System.out.println(minheap.A[k]);
            }

            write("richest.output", minheap.A);
        }   
        //Print stack trace in case of filenotfound exception
	    catch(FileNotFoundException e)
	    {
	    	e.printStackTrace();
	    }
    }

    //Method for writing array to file
    public static void write(String name, int[]A) throws IOException
    {
        BufferedWriter w = null;
        w = new BufferedWriter(new FileWriter(name));
        w.write(Arrays.toString(A));
        w.flush();
        w.close();
    }
}


//Min heap class 
class MinHeap 
{
    //Variables for MinHeap class
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
        //Update heapsize
        heapsize--;
        //Heapify from top
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
	public void printMinHeap() {
		for (int j = 0; j < heapsize; ++j) 
        {
            System.out.print(A[i]+ " ");
            System.out.println();
		}
	}
}
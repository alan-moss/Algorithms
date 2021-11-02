/*
Alan Moss
CSCD 320
Prog 1
This program finds the ith order statistic in an unsorted array.
It utilizes an algorithm whose time complexity is Θ(n)
*/

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;

public class OS_Finding 
{
	public static void main(String[] args) throws Exception
	{
        //Get arguments from command line
		String file = args[0];
        //ith order is simply the index - 1 of (ascending) sorted array
        //In other words, first order of sorted array A is A[0]
        int ith = Integer.parseInt(args[1]);

		File fileEntry = new File(file);
		
		try(Scanner sc = new Scanner(fileEntry))
		{
			//Create arraylist for integers from file
			ArrayList<Integer> arraylist = new ArrayList<Integer>();
			while(sc.hasNext())
			{
				if(sc.hasNextInt())
				{
					arraylist.add(sc.nextInt());
				}
				
				//Check for characters that are not integers
				else if(sc.hasNext() && !sc.hasNextInt())
				{
					System.out.println("Invalid input in file");
					return;
				}
			}
			
			//Check if arraylist is empty
			if(arraylist.isEmpty())
			{
				System.out.println("List is empty");
				return;
            }

            // Convert arraylist to array
            int[] A = new int[arraylist.size()];
            int n = A.length;

            //Check that ith OS is in bounds
            if(ith > n || ith <= 0)
            {
                System.out.println("Null");
            }

            else
            {
                for(int i=0; i < n; i++)
                {
                    A[i] = arraylist.get(i).intValue();
                }

                int output = RandomizedSelect(A, 0, n-1, ith);

                System.out.println("ith order static of array is: " + output);
            }
        }
        //Print stack trace in case of filenotfound exception
	    catch(FileNotFoundException e)
	    {
	    	e.printStackTrace();
	    }
    } //End of main




    //Method for swapping array elements
    static void swap(int[] A, int i, int j)
    {
        int temp = A[i];
        A[i] = A[j];
        A[j] = temp;
    }

    //Method for generating random int within range [min...max]
    //java.util.Random is less biased than Math.random
    static int random(int min, int max)
    {
        Random num = new Random();
        return num.nextInt((max - min) + 1) + min;
    }

    //Partition only affects array within the bounds left -> right
    static int partition(int[] A, int left, int right)
    {
        int pivot = A[right];
        int index = left-1;
        for(int i = left; i < right; i++)
        {
            if(A[i] < pivot)
            {
                index++;
                swap(A, index, i);
            }
        }
        swap(A, index+1, right);
        return index+1;
    }

    //Partition using randomly generated pivot
    //Calls deterministic partition method
    static int randomized_partition(int[]A, int left, int right)
    {
        //A number randomly picked from the range [left...right]
        int i = random(left, right);

        //swap(A, right, left);
        swap(A, i, right);
        return partition(A, left, right); 
    }

    //Randomized algorithm for finding the ith order statistic of any general working area A[p...r]
    static int RandomizedSelect(int[] A, int p, int r, int i)
    {
        //Working area only has one number, i must be equal to 1
        if(p == r)
        {
            return A[p];
        }

        //A[q] is the pivot after partition
        int q = randomized_partition(A, p, r);
        int k = q - p + 1;

        if(i == k)
        {
            return A[q];
        }

        else if(i < k)
        {
            return RandomizedSelect(A, p, q-1, i);
        }

        else
        {
            return RandomizedSelect(A, q+1, r, i-k);
        }
    }
}
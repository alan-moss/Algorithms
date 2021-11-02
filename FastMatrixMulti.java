import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


public class FastMatrixMulti 
{
    public static void main(String[] args) throws Exception
	{
        //Get arguments from command line
		String file = args[0];

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

            //Populate array
            for(int i=0; i < n; i++)
            {
                A[i] = arraylist.get(i).intValue();
            }

            int optimal = matrix_chain_order(A);
            System.out.println("Minimum time cost: " + optimal);
        }
        //Print stack trace in case of filenotfound exception
	    catch(FileNotFoundException e)
	    {
	    	e.printStackTrace();
	    }
    } //End of main

    static int matrix_chain_order(int[] p)
    {
        //Number of matrices
        int n = p.length;
        int inf = Integer.MAX_VALUE;

        //Store optimal time cost for mutliplying subchains
        int[][] m = new int[n][n];
        //Store position of optimal outmost pair for subchain
        int[][] s = new int[n][n];

        //Initialize time cost to zero
        for(int q = 1; q < n; q++)
        {
            m[q][q] = 0;
        }

        //Bottom to top, find best way to multiply all matrices
        //Subchain of size 2 to begin, then subchain of size 3, then 4, etc
        //h represents the size of subchain
        for(int h = 2; h < n; h++)
        {
            //i represents index of starting matrix of subchain size h
            for(int i = 1; i < n-h+1; i++)
            {
                int j = i+h-1;
                //Assign infinite value
                m[i][j] = inf;
                //try different positions for outermost pair
                //for subchain
                for(int k = i; k < j; k++)
                {
                    int x = m[i][k] + m[k+1][j] +
                    (p[i-1] * p[k] * p[j]);
                    if(x < m[i][j])
                    {
                        //x is optimal time cost
                        m[i][j] = x;
                        //position of outmost parentheses
                        s[i][j] = k;
                    }
                }
            }
        }
        //Call while we have access to array s
        print_optimal_parens(s, 1, n-1);
        //Least amount of time to multiply all matrices
        return m[1][n-1];
    }

    static void print_optimal_parens(int[][] s, int i, int j)
    {
        if(i == j)
        {
            System.out.print("A" + i);
        }
        //print left and right parentheses and recursively call
        else
        {
            System.out.print("(");
            print_optimal_parens(s, i, s[i][j]);
            print_optimal_parens(s, s[i][j]+1, j);
            System.out.print(")");
        }
    }
}

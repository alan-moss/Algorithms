/*
Alan Moss
CSCD 320
Prog 5

This program utilizes Dijkstra's shortest path algorithm to find the shortest path
from a given node to all other nodes in a directed graph

For this program I implemented the following structures: 
node, linked list, min heap, edge, and graph

I created a graph class that holds an array of singly linked lists. Each linked list holds
Edge objects, and each Edge object holds two nodes, the source node and the destination node, and the 
weight of the edge between the nodes. I was able to store all the information like I wanted to.

I was unable to get Dijkstra's algorithm working properly, but I know most of the logic is present.
I ran into an issue with the implementation of the Min Heap, something I also didn't get the hang
of in program 2. I reworked my min heap from prog 2 and got it working for the prog 2 implementation,
but I couldn't get it working here.
*/
import java.io.*;
import java.util.*;

public class Dijkstra {
    public static void main(String[] args) throws Exception 
    {
        try
        {
            String file = args[0];
            //Node we travel from
            int sourceNode = Integer.parseInt(args[1]);

			Scanner sc = new Scanner(new FileInputStream(file));
			
			//Length is number of array elements
			int length = getLineCount(sc);
			//Reset scanner so can read from top of file
			sc = new Scanner(new FileInputStream(file));

            graph G = populateGraph(sc, length);

            for(int i = 0; i < G.adj.length; i++)
            {
                Edge source = G.adj[i].head;
                for(int j = 0; j < G.adj[i].size; j++)
                {

                    System.out.println("Src: " + source.src.id + " Dest: " + source.dest.id + " Weight: " + source.edgeweight);
                    source = source.next;
                }
            }
            //Perform Dijkstra shortest path algorithm with user selected vertex
            doTheDijkstra(G, sourceNode);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    //Dijkstra's algorithm implementation takes a graph and integer arguments
    //v is the vertex we want to start from
    public static void doTheDijkstra(graph G, int v)
    {
        //Checks size of linked list to see if graph has only vertex v
        //or if you cannot reach any other vertexes from v
        if(G.adj[v].size == 0)
        {
            return;
        }

        //Set all v.d to infinity and v.p to null
        for(int i = 0; i < G.size; i++)
        {
            Edge source = G.adj[i].head;
            for(int j = 0; j < G.adj[i].size; j++)
                {
                    source.src.totalWeight = Integer.MAX_VALUE;
                    source.dest.totalWeight = Integer.MAX_VALUE;
                    source = source.next;
                }
        }

        //set total weights of source nodes to 0
        Edge temp = G.adj[v].head;
        for(int i = 0; i < G.adj[v].size; i++)
        {
            temp.src.totalWeight = 0;
            temp = temp.next;
        }

        //Create minheap
        MinHeap q = new MinHeap(new int[G.size]);
        //Populate minheap
        for(int i = 0; i < G.size; i++)
        {
            q.insertElement(G.adj[i].head);
        }

        while(q.heapsize != 0)
        {
            int x = q.removeMin();

            for(int i = 0; i < G.size; i++)
            {
                Edge temp2 = G.adj[i].head;
                for(int j = 0; j < G.adj[i].size; j++)
                {
                    if(temp2.src.totalWeight > temp2.next.src.totalWeight + temp2.src.totalWeight)
                    {
                        temp2.src.totalWeight = temp2.next.src.totalWeight + temp2.src.totalWeight;
                        q.min_Heapify(A, heapsize, parent);
                    }
                }
            }
        }
    }

    //Populate graph from scanner file
    public static graph populateGraph(Scanner file, int size)
    {
        graph G = new graph(size);
        //Entirity of the file
		while(file.hasNextLine())
		{
			//Load the next line 
			String line = file.nextLine();
			//First number in line is index for array
			int index = Integer.parseInt(String.valueOf(line.charAt(0)));
			//This is the character directly after the colon
			int j = 2;
            int k = j+2;
			while(j < line.length())
			{
				if(Character.isDigit(line.charAt(j)))
				{
					int dest = Integer.parseInt(String.valueOf(line.charAt(j)));
                    int weight = Integer.parseInt(String.valueOf(line.charAt(k)));

					//Add the edges to linked list at given array index
					G.adj[index].addLast(new Edge(new Node(index), new Node(dest), weight));
				}
                //update j and k
				j = j + 4;
                k = k + 4;
			}
		}
        return G;
    }

    //Adjacency list representation is an array of linked lists
    public static class graph
	{
		//adjacency list is an array of linked lists of edges
		private SLinkedList[] adj;
		private int size;

		//Constructor
		public graph(int size)
		{
			this.size = size;
			adj = new SLinkedList[size];
			//Create a singly linked list at every array location
			for(int i = 0; i < size; i++)
			{
				adj[i] = new SLinkedList();
			}
		}

		//Getter
		public int getSize()
		{
			return size;
		}
	}


    //Method to count lines in a file
    public static int getLineCount(Scanner sc)
    {
        int count = 0;
        while(sc.hasNextLine())
        {
            sc.nextLine();
            count++;
        }
        return count;
    }

    //Track edges between vertices, along with their weights.
    public static class Edge
    {
        //Node src is starting node, Node dest is finishing node
        //We create an edge between the two nodes
        Node src;
        Node dest;
        int edgeweight;
        Edge next;

        public Edge(Node src, Node dest, int edgeweight)
        {
            this.src = src;
            this.dest = dest;
            this.edgeweight = edgeweight;
            next = null;
        }
    }

    //Node class for singly linked list
	public static class Node
	{
		public int id;
	    public Node next;
        public Node previousHop;
        //Tracks the weight from starting node to ending node
	    public int totalWeight;
        //Linked list (adjacency list)
        public SLinkedList adj;

		//Constructor
	    public Node(int id)
        {
            this.id = id;
            next = null;
            //To be used for Dijkstra's algorithm
            previousHop = null;
            //Initialize weight to infinity (or in this case max integer value)
            //This value is updated if we can reach the node
            totalWeight = Integer.MAX_VALUE;
            
            //Create adjacency list, all values null, size 0
            adj = new SLinkedList();
	    }

        public void createEdge(Node a, Node b, int i)
        {
            //Create edge between nodes a and b, of weight i
            Edge edge = new Edge(a, b, i);
            //Add edge to adjacency list
            adj.addLast(edge);
        }

        public void setNext(Node n)
        {
            next = n;
        }
	}

	//Singly linked list class
    //List of edges
	public static class SLinkedList
	{
	    protected Edge head; 
		protected Edge tail;
        protected Edge next;
	    protected int size; 
	    
        //No arg constructor
	    public SLinkedList(){
			head = null; 
			tail = null;
            next = null;
			size = 0;
	    }
	    
        
		//Adds edge to end of linked list
		public void addLast(Edge e)
		{
            //If no edges in linked list yet
			if(size == 0)
			{
				head = e;
			}

			else
			{
				tail.next = e;
			}
			tail = e;
			size++;
		}

		//Getter for head
		public Edge headNode()
		{
			return head;
		}
	}

    //Min heap class 
    public static class MinHeap 
    {
        int[] A;
        int heapsize;
        int max;

        //Constructor, heapsize is zero until elements are added
        public MinHeap(int[] A)
        {
            this.A = A;
            heapsize = A.length;
            max = heapsize - 1;
        }

        //Min_heapify method p. 154 max-heapify pseudocode
        public void min_Heapify(int[] A, int heapsize, int parent)
        {
            int smallest = parent;
            //Left child less than parent
            if(getleftchild(parent) < heapsize && A[getleftchild(parent)] < A[smallest])
            {
                smallest = getleftchild(parent);
            }
            //Right child less than parent
            if(getrightchild(parent) < heapsize && A[getrightchild(parent)] < A[smallest])
            {
                smallest = getrightchild(parent);
            }
            //If we have children less than parent, we swap that child with parent
            //and recursively call min_Heapify
            if(smallest != parent)
            {
                swap(parent, smallest);
                min_Heapify(A, heapsize, parent);
            }
        }

        //Builds min heap p. 157 build-max-heap pseudocode
        //slides_heap_1.pdf p. 11
        public void build_Min_Heap(int[] A)
        {
            this.heapsize = A.length;
            for(int i = (int) Math.floor(A.length/2); i >= 1; i--)
            {
                min_Heapify(A, heapsize, i);
            }
        }

        //Pops min value
        public int removeMin()
        {
            int min = A[0];
            A[0] = A[heapsize - 1];
            heapsize--;
            min_Heapify(A, heapsize, A[0]);
            return min;
        }

        //Method for inserting elements into minheap
        public void insertElement(int num)
        {
            //Index has reached end of heap
            if(max >= heapsize)
            {
                return;
            }

            //Assign array index num
            A[max] = num;
            //store index in current value
            int temp = max;

            while(A[temp] < A[getparent(temp)])
            {
                swap(temp, getparent(temp));
                temp = getparent(temp);
            }
            max++;
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
    }
}
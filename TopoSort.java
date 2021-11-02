/*
Alan Moss
CSCD 320
Program 4
This program is a topological sort of a directed acyclic graph using
depth first search
*/

import java.io.*;
import java.util.*;
import java.lang.*;

public class TopoSort
{
	//Global time variable
	static int time = 0;
	static SLinkedList sorted = new SLinkedList();
    public static void main(String args[]) throws Exception
    {
		try
		{
			String file = args[0];
			Scanner sc = new Scanner(new FileInputStream(file));
			
			//Length is number of array elements
			int length = getLineCount(sc);
			//Reset scanner so can read from top of file
			sc = new Scanner(new FileInputStream(file));

			//Create dag representation using file and length
			Dag dag = new Dag(length);
			//Populate Dag
			fillDag(dag, sc, length);
			//Call DFS on DAG
			DFS(dag);
			//Print the .f values of each 
			printDag(dag);


			Node n = sorted.head;
			for(int i = 0; i < sorted.size; i++)
			{

				System.out.print(" " + n.getID());
				n = n.next;
			}
			// SLinkedList sorted = topologicalSort(dag);
			// //Print list
			// printSLinkedList(sorted);

        }

        catch(Exception e)
		{
			e.printStackTrace();
		}
    }

	// public static void printSLinkedList(SLinkedList l)
	// {

	// }

	// public static SLinkedList topologicalSort(Dag G)
	// {
	// 	SLinkedList sorted = new SLinkedList();
	// 	for(int i = 0; i < G.size; i++)
	// 	{
	// 		sorted.addLast(G.adj[i].headNode().getID());
	// 	}
	// 	return sorted;
	// }

	//Prints dag
	public static void printDag(Dag g)
	{
		for(int i = 0; i < g.size; i++)
		{
			System.out.println("Array location: " + i + " Finishing time:" +g.adj[i].headNode().getF());
		}

		// for(int j = 0; j < g.size; j++)
		// {
		// 	Node n = g.adj[j].headNode();
		// 	for(int k = 0; k < g.adj[j].size; k++)
		// 	{
		// 		System.out.println(n.getID());
		// 		n = n.next;
		// 	}
		// 	System.out.println("----");
		// 	//System.out.println(g.adj[j].headNode().getID());
		// }
	}

	public static Dag fillDag(Dag dag, Scanner file, int size)
	{
		//Entirity of the file
		while(file.hasNextLine())
		{
			//Load the next line 
			String line = file.nextLine();
			//First number in line is index for array
			int index = Integer.parseInt(String.valueOf(line.charAt(0)));
			//This is the character directly after the colon
			int j = 2;
			while(j < line.length())
			{
				if(Character.isDigit(line.charAt(j)))
				{
					int value = Integer.parseInt(String.valueOf(line.charAt(j)));
					//Add the nodes to linked list at given array index
					dag.adj[index].addLast(value);
				}
				j++;
			}
		}
		return dag;
	}

	//Depth first search of dag
	public static void DFS(Dag G)
	{
		for(int j = 0; j < G.getSize(); j++)
		{
			G.adj[j].headNode().setColor("white");
			G.adj[j].headNode().setP(null);
		}

		//Timestamp
		time = 0;
		//For each u in G.v
		for(int i = 0; i < G.getSize(); i++)
		{
			Node u = G.adj[i].headNode();
			
			if(u.getColor() == "white")
			{
				DFS_Graph(G, u);
			}
			//Add to sorted list
			sorted.addLast(u.getID());
		}
		
	}

	//Graph depth first search
	public static void DFS_Graph(Dag G, Node u)
	{
		time++;
		u.setD(time);
		u.setColor("gray");

		//HERE WE NEED TO USE THE LINKED LIST
		for(int i = 0; i < G.getSize(); i++)
		{
			Node n = G.adj[i].headNode();

			for(int j = 0; j < G.adj[i].size; j++)
			{
				if(n.getColor() == "white")
				{
					G.adj[i].headNode().setP(u);
					DFS_Graph(G, n);
				}
				//Update N
				n = n.getNext();
			}
		}
		time++;
		u.setF(time);
		u.setColor("black");
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

	//Dag class
	//A dag is an array of singly linked list objects
	public static class Dag
	{
		//adj stands for adjacency list
		private SLinkedList[] adj;
		private int size;

		//Constructor
		public Dag(int size)
		{
			this.size = size;
			adj = new SLinkedList[size];
			//Create a singly linked list at every array location
			//First node in array has ID value of i
			for(int i = 0; i < size; i++)
			{
				adj[i] = new SLinkedList();
				adj[i].addLast(i);
			}
		}

		//Getter
		public int getSize()
		{
			return size;
		}
	}

	//Node class for singly linked list
	public static class Node
	{
		private int id;
		private String color;
		private int d;
		private int f;
		private Node p;
	    private Node next;  //the link referencing to the next node in the link list.
	    
		//Constructor
	    public Node(int id){
	    this.id = id;
		//White means node has not been visited yet
		//When we construct we want color to be white
		color = "white";
		d = 0;
		f = 0;
		p = null;
		next = null; 
	    }

		//Getters
	    public int getID() { return id; }
	    public Node getNext() { return next; }
		public String getColor() { return color; }
		public int getD() { return d; }
		public int getF() { return f; }
		public Node getP() { return p; }
		//Setters
	    public void setID(int id) { this.id = id; }
	    public void setNext(Node next) { this.next = next; }
		public void setColor(String color) { this.color = color; }
		public void setD(int d) { this.d = d; }
		public void setF(int f) { this.f = f; }
		public void setP(Node p) { this.p = p; }
	}

	//Singly linked list class
	public static class SLinkedList
	{
	    protected Node head; 
		protected Node tail;
	    protected long size; 
	    
	    public SLinkedList(){
			head = null; 
			tail = null;
			size = 0;
	    }
	    

		//Adds node to end of linked list
		public void addLast(int i)
		{
			Node n = new Node(i);
			if(size == 0)
			{
				head = n;
			}

			else
			{
				tail.setNext(n);
			}
			tail = n;
			size++;
		}

		// /* add node v at the beginning of the list */
	    // public void addFirst(Node v){
		// 	if(v == null)
		// 		return; 

		// 	v.setNext(head);
		// 	head = v; 
		// 	size ++;
	    // }
	    
	    // /* add node v at the end of the list */
	    // public void addLast(Node v){
		// 	if(size == 0)
		// 		addFirst(v);
		// 	else{
		// 		Node cur; 
		// 		for(cur = head; cur.getNext() != null; cur = cur.getNext())
		// 		{
		// 			v.setNext(null);
		// 			cur.setNext(v); 
		// 			size ++; 
		// 		}
				
		// 	}
	    // }

		//Getter for head node
		public Node headNode()
		{
			return head;
		}
	}
}
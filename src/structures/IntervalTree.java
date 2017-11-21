package structures;

import java.util.ArrayList;

/**
 * Encapsulates an interval tree.
 * 
 * @author runb-cs112
 */
public class IntervalTree {
	
	/**
	 * The root of the interval tree
	 */
	IntervalTreeNode root;
	
	/**
	 * Constructs entire interval tree from set of input intervals. Constructing the tree
	 * means building the interval tree structure and mapping the intervals to the nodes.
	 * 
	 * @param intervals Array list of intervals for which the tree is constructed
	 */
	public IntervalTree(ArrayList<Interval> intervals) {
		
		// make a copy of intervals to use for right sorting
		ArrayList<Interval> intervalsRight = new ArrayList<Interval>(intervals.size());
		for (Interval iv : intervals) {
			intervalsRight.add(iv);
		}
		
		// rename input intervals for left sorting
		ArrayList<Interval> intervalsLeft = intervals;
		
		// sort intervals on left and right end points
		sortIntervals(intervalsLeft, 'l');
		sortIntervals(intervalsRight,'r');
		
		// get sorted list of end points without duplicates
		ArrayList<Integer> sortedEndPoints = 
							getSortedEndPoints(intervalsLeft, intervalsRight);
		
		// build the tree nodes
		root = buildTreeNodes(sortedEndPoints);
		
		// map intervals to the tree nodes
		mapIntervalsToTree(intervalsLeft, intervalsRight);
	}
	
	/**
	 * Returns the root of this interval tree.
	 * 
	 * @return Root of interval tree.
	 */
	public IntervalTreeNode getRoot() {
		return root;
	}
	
	/**
	 * Sorts a set of intervals in place, according to left or right endpoints.  
	 * At the end of the method, the parameter array list is a sorted list. 
	 * 
	 * @param intervals Array list of intervals to be sorted.
	 * @param lr If 'l', then sort is on left endpoints; if 'r', sort is on right endpoints
	 */
	public static void sortIntervals(ArrayList<Interval> intervals, char lr) {
		// COMPLETE THIS METHOD
		ArrayList<Interval> sorted = new ArrayList<Interval>();
		
		ArrayList<Interval> c = intervals;  
		
		//Interval min = new Interval(0,0,null);
		Interval min =c.get(0);
	
		int minIndex =0; 
		if(lr=='l')
		{
		while(!(c.isEmpty()))
		{
			min= c.get(0);
			minIndex=0;
		//	System.out.println(min);
			for(int i =0; i<c.size(); i++)
			{
				Interval temp= c.get(i);
				if(temp.leftEndPoint<min.leftEndPoint)
				{
					min=temp;
					
					minIndex=i;
					
				}
			}
			c.remove(minIndex);
			sorted.add(min);
		
		
		}
		
		}
		else
		{
		while(!(c.isEmpty()))
		{
		
			min= c.get(0);
			minIndex=0;
			for(int i =0; i<c.size(); i++)
			{
				Interval temp= c.get(i);
				if(temp.rightEndPoint<min.rightEndPoint)
				{
					min=temp;
					minIndex=i;
				}
			}
			c.remove(minIndex);
			sorted.add(min);
			
			
		}
		}
		intervals.clear();
		for(int i=0; i<sorted.size(); i++)
		{
			intervals.add(sorted.get(i));
		}
	

		
	}
	
	/**
	 * Given a set of intervals (left sorted and right sorted), extracts the left and right end points,
	 * and returns a sorted list of the combined end points without duplicates.
	 * 
	 * @param leftSortedIntervals Array list of intervals sorted according to left endpoints
	 * @param rightSortedIntervals Array list of intervals sorted according to right endpoints
	 * @return Sorted array list of all endpoints without duplicates
	 */
	public static ArrayList<Integer> getSortedEndPoints(ArrayList<Interval> leftSortedIntervals, ArrayList<Interval> rightSortedIntervals) {
		// COMPLETE THIS METHOD
		// THE FOLLOWING LINE HAS BEEN ADDED TO MAKE THE PROGRAM COMPILE
		ArrayList<Integer> points=new ArrayList<Integer>();
		//System.out.println("Size "+rightSortedIntervals.size());
		//System.out.println("Size "+leftSortedIntervals.size());
		//System.out.println("Size "+points.size());
		for(int i=0; i<leftSortedIntervals.size(); i++)
		{
			if(points.size()==0)
			{
				//System.out.println(points.get(0));
				points.add(leftSortedIntervals.get(i).leftEndPoint);
				
				
			}
			if(!(points.contains(leftSortedIntervals.get(i).leftEndPoint)))
			{
				points.add(leftSortedIntervals.get(i).leftEndPoint);
			}
		}
		System.out.println(points);
		for(int i=0; i<rightSortedIntervals.size(); i++)
		{
			
			if(!(points.contains(rightSortedIntervals.get(i).rightEndPoint)))
			{
				//System.out.println(points);
				 int s= points.size();
				for(int x=0; x< s; x++)
				{
					
					if(points.get(x)>rightSortedIntervals.get(i).rightEndPoint)
					{
						points.add(x, rightSortedIntervals.get(i).rightEndPoint);
						s++;
						break;
					}
					if (x==points.size()-1){
						points.add(x+1, rightSortedIntervals.get(i).rightEndPoint);
					}
				}
			}
			System.out.println(points);
		}
		
		return points;

	}
	
	/**
	 * Builds the interval tree structure given a sorted array list of end points
	 * without duplicates.
	 * 
	 * @param endPoints Sorted array list of end points
	 * @return Root of the tree structure
	 */
	public static IntervalTreeNode buildTreeNodes(ArrayList<Integer> endPoints) {
		// COMPLETE THIS METHOD
		// THE FOLLOWING LINE HAS BEEN ADDED TO MAKE THE PROGRAM COMPILE
			Queue<IntervalTreeNode> t = new Queue<IntervalTreeNode>();
		      for (int i = 0; i < endPoints.size(); i++)
				{
					IntervalTreeNode temp = new IntervalTreeNode(endPoints.get(i), endPoints.get(i), endPoints.get(i));
					t.enqueue(temp);
				}
				
				while (true)
				{	
					if (t.size() == 1)
					{
						break;
					}
				
					int temps = t.size();
					while(temps > 1)
					{
						IntervalTreeNode  t1 = t.dequeue();
						IntervalTreeNode t2  = t.dequeue();
						
						float splitValue = (((float) t1.maxSplitValue+(float) t2.minSplitValue)/2);
						
						IntervalTreeNode newNode = new IntervalTreeNode(splitValue, t1.minSplitValue, t2.maxSplitValue);
						newNode.leftChild = t1;
						newNode.rightChild = t2;
						
						t.enqueue(newNode);
						
						temps = temps-2;
					}
					
					if (temps == 1)
						t.enqueue(t.dequeue());
				}
				return t.dequeue();
				

		

	}
	
	/**
	 * Maps a set of intervals to the nodes of this interval tree. 
	 * 
	 * @param leftSortedIntervals Array list of intervals sorted according to left endpoints
	 * @param rightSortedIntervals Array list of intervals sorted according to right endpoints
	 */
	public void mapIntervalsToTree(ArrayList<Interval> leftSortedIntervals, ArrayList<Interval> rightSortedIntervals) {
		// mapping left sorted intervals
				IntervalTreeNode pos = root;
				boolean keepGoing = true;
				int loc = 0;
				while (loc < leftSortedIntervals.size()){
					keepGoing = true;
					pos = root;
					while (keepGoing){
						if (pos == null){
							keepGoing = false;
						}
						else{
							if (leftSortedIntervals.get(loc).leftEndPoint <= pos.splitValue && leftSortedIntervals.get(loc).rightEndPoint >= pos.splitValue){
								if (pos.leftIntervals == null)
									pos.leftIntervals = new ArrayList<Interval>();
								keepGoing = false;
								pos.leftIntervals.add(leftSortedIntervals.get(loc));
							}
							else if(leftSortedIntervals.get(loc).leftEndPoint > pos.splitValue)
								pos = pos.rightChild;
							else
								pos = pos.leftChild;
						}
					}
					loc++;
				}
				// mapping right sorted intervals
				pos = root;
				keepGoing = true;
				loc = 0;
				while (loc < rightSortedIntervals.size()){
					keepGoing = true;
					pos = root;
					while (keepGoing){
						if (pos == null){
							keepGoing = false;
						}
						else{
							if (rightSortedIntervals.get(loc).leftEndPoint <= pos.splitValue && rightSortedIntervals.get(loc).rightEndPoint >= pos.splitValue){
								if (pos.rightIntervals == null)
									pos.rightIntervals = new ArrayList<Interval>();
								keepGoing = false;
								pos.rightIntervals.add(rightSortedIntervals.get(loc));
							}
							else if(rightSortedIntervals.get(loc).leftEndPoint > pos.splitValue)
								pos = pos.rightChild;
							else
								pos = pos.leftChild;
						}
					}
					loc++;
				}
	}
	
	/**
	 * Gets all intervals in this interval tree that intersect with a given interval.
	 * 
	 * @param q The query interval for which intersections are to be found
	 * @return Array list of all intersecting intervals; size is 0 if there are no intersections
	 */
	ArrayList<Interval> resultList;
	Queue<IntervalTreeNode> node = new Queue<IntervalTreeNode>();
	public ArrayList<Interval> findIntersectingIntervals(Interval q) {
		IntervalTreeNode r;
		if (node.size == 0){
			r = root;
			resultList = new ArrayList<Interval>();
		}
		else{
			r = (IntervalTreeNode)node.dequeue();
		}
		
		if (r.leftIntervals == null && r.rightIntervals == null)
			return resultList;
		if (q.leftEndPoint <= r.splitValue && q.rightEndPoint >= r.splitValue){
			for (int cnt = 0; cnt < r.leftIntervals.size(); cnt++)
				resultList.add(r.leftIntervals.get(cnt));
			node.enqueue(r.leftChild);
			findIntersectingIntervals(q);
			node.enqueue(r.rightChild);
			findIntersectingIntervals(q);
		}
		else if(r.splitValue < q.leftEndPoint){
			int pos = r.rightIntervals.size() - 1;
			while (pos >= 0 && r.rightIntervals.get(pos).rightEndPoint >= q.leftEndPoint){
				resultList.add(r.rightIntervals.get(pos));
				pos--;
			}
			node.enqueue(r.rightChild);
			findIntersectingIntervals(q);
		}
		else if(r.splitValue > q.rightEndPoint){
			int pos = 0;
			while (pos < r.leftIntervals.size() && r.leftIntervals.get(pos).leftEndPoint <= q.rightEndPoint){
				resultList.add(r.leftIntervals.get(pos));
				pos++;
			}
			node.enqueue(r.leftChild);
			findIntersectingIntervals(q);
		}
		
		return resultList;
	}

}
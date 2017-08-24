package scheduler.structures;

import java.util.ArrayList;
import java.util.List;

import scheduler.Scheduler;

public class TreeNode implements Comparable<TreeNode> {	
	
	private TreeNode parent;
	private Node node;
	private int recentProcessor;
	private int recentStartTime;
	private int heuristic;
	
	/**
	 * All branches of the search tree are intended to be created using this constructor. It takes as input a TreeNode
	 * as it's parent, and creates a new TreeNode instance one level deeper, that is, with another node added at the earliest
	 * possible legal point.
	 * 
	 * @param parent The TreeNode instance that is intended to be the parent of this TreeNode.
	 * @param node The Node instance that is being added to this branch of the tree.
	 * @param procNum An int representing the processor the Node is to be added to.
	 */
	public TreeNode(TreeNode parent, Node node, int procNum) {		
		int maxStart = 0; 
		TreeNode current = parent;
		
		//traverse up through the tree, finding the latest of all the earliest allowable times at which this Node can be added
		//If we have reached the top of the tree, either the contained Node object or the TreeNode object itself will be null.
		//it depends on the use of a completely empty placeholder TreeNode used for multiple entry points. 
		while (!(current == null || current.node == null)) {
			
			int time = 0;
			if (procNum == current.recentProcessor) {
				time = current.recentStartTime + current.node.getWeight();
			} else {
				try {
					time = current.recentStartTime + current.node.getWeight() + node.getParentEdgeWeights().get(current.node);
				} catch(NullPointerException e) {
					//this nullpointer is expected to be thrown whenever a node on a different processor is not a parent
					//of the node being added - in this case node.parentEdgeWeights.get(current.recentNode) returns a null
				}
			}
			
			//get the latest allowable start time
			if (time > maxStart) {
				maxStart = time;
			}
			
			//traverse up the tree
			current = current.parent;
		}
		
		// Perfect load balance stuff start
		int currentBalance = Scheduler.getTotal();
		int[] startTimes = new int[Scheduler.getInstance().getNumProc()];
		
		startTimes[procNum] = maxStart;
		
		current = parent;
		while (!(current == null || current.node == null)) {
			int proc = current.recentProcessor;
			if (startTimes[proc] != 0) {
				currentBalance += startTimes[proc] - current.recentStartTime - current.node.getWeight();
			}
			startTimes[proc] = current.recentStartTime;
			current = current.parent;
		}
		for (int i : startTimes) {
			currentBalance += i;
		}
		
		heuristic = (int) Math.floor(currentBalance/Scheduler.getInstance().getNumProc());
		//System.out.println(heuristic);
		// Perfect load balance stuff end
		
		//initialise the fields for this object
		this.parent = parent;
		this.node = node;
		this.recentProcessor = procNum;
		this.recentStartTime = maxStart;
		
		// Finalise heuristic
		if (node.getBottomLevel() + recentStartTime > heuristic) {
			heuristic = node.getBottomLevel() + recentStartTime;
		}
	}
	
	/**
	 * Default constructor for TreeNode. Intended to be called only for the root node of the tree
	 */
	public TreeNode() {
		this.parent = null;
		this.node = null;
		this.recentProcessor = -1;
		this.recentStartTime = -1;
	}

	/**
	 * Comparator override used in order to compare heuristic values of TreeNode, intended to be used in determining which branch
	 * of the tree to expand on next
	 */
	@Override
	public int compareTo(TreeNode other) {
		if (heuristic < other.heuristic) {
			return -1;
		} else if (heuristic == other.heuristic) {
			return 0;
		} else {
			return 1;
		}
	}
	
	public Node getNode() {
		return node;
	}
	
	public TreeNode getParent() {
		return parent;
	}
	
	public int getProcessor() {
		return recentProcessor;
	}
	
	public int getStartTime() {
		return recentStartTime;
	}
	
	@Override
	public boolean equals(Object obj) {
		TreeNode tn = (TreeNode) obj;

		List<Node> otherNodes = new ArrayList<Node>();
		int[] thisFinish = new int[Scheduler.getInstance().getNumProc()];
		int[] otherFinish = new int[Scheduler.getInstance().getNumProc()];
		int count = 0;
		
		TreeNode current = tn;
		while (current.getNode() != null) {
			otherNodes.add(current.getNode());
			int time = current.getStartTime() + current.getNode().getWeight();
			if (otherFinish[current.getProcessor()] < time) {
				otherFinish[current.getProcessor()] = time;
			}
			current = current.getParent();
		}
		
		current = this;
		while (current.getNode() != null) {
			if (!otherNodes.contains(current.getNode())) {
				return false;
			}
			count++;
			int time = current.getStartTime() + current.getNode().getWeight();
			if (thisFinish[current.getProcessor()] < time) {
				thisFinish[current.getProcessor()] = time;
			}
			current = current.getParent();
		}
		
		if (count != otherNodes.size()) {
			return false;
		}
		
		for (int i = 0; i < thisFinish.length; i++) {
			boolean boo = false;
			for (int j = 0; j < otherFinish.length; j++) {
				if (thisFinish[i] == otherFinish[j]) {
					boo = true;
				}
			}
			if (!boo) {
				return false;
			}
		}
		
		return true;
	}
	
	public int hashCode() {
		return (int) System.currentTimeMillis();
	}
	
}

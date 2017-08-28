package scheduler.structures;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import scheduler.Scheduler;

/**
 * Provides a representation of the search tree.
 * 
 * @author Luke Tudor
 */
public class TreeNode implements Comparable<TreeNode> {	
	
	// TreeNode instance that is the parent of this TreeNode
	private TreeNode parent;
	// The node element instance of a graph object
	private Node node;
	// The most recent processor instance
	private int recentProcessor;
	// The most recent start time instance
	private int recentStartTime;
	// The heuristic instance of the algorithm
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
		
		//Traverse up through the tree, finding the latest of all the earliest allowable times at which this Node can be added.
		//If we have reached the top of the tree, either the contained Node object or the TreeNode object itself will be null.
		//It depends on the use of a completely empty placeholder TreeNode used for multiple entry points. 
		while (!(current == null || current.node == null)) {
			
			int time = 0;
			if (procNum == current.recentProcessor) {
				time = current.recentStartTime + current.node.getWeight();
			} else {
				try {
					time = current.recentStartTime + current.node.getWeight() + node.getParentEdgeWeights().get(current.node);
				} catch(NullPointerException e) {
					//This nullpointer is expected to be thrown whenever a node on a different processor is not a parent
					//of the node being added - in this case node.parentEdgeWeights.get(current.recentNode) returns a null
				}
			}
			
			//Get the latest allowable start time
			if (time > maxStart) {
				maxStart = time;
			}
			
			//Traverse up the tree
			current = current.parent;
		}
		
		// Perfect load balance stuff start
		int currentBalance = Scheduler.getInstance().getTotal();
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
		// Perfect load balance stuff end
		
		// Initialise the fields for this object
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
	
	// Acquires the node of the directed graph ADT
	public Node getNode() {
		return node;
	}
	
	// Acquires the parent of this TreeNode
	public TreeNode getParent() {
		return parent;
	}
	
	// Acquires the most recent processor for this TreeNode
	public int getProcessor() {
		return recentProcessor;
	}
	
	// Acquires the most recent start time for this TreeNode
	public int getStartTime() {
		return recentStartTime;
	}
	
	public int getHeuristic() {
		return heuristic;
	}
	
	public String getString() {
		List<TreeNode> treeNodes = new ArrayList<>();
		TreeNode current = this;
		// Collect all the tree nodes in a list
		while (!(current == null || current.node == null)) {
			treeNodes.add(current);
			current = current.parent;
		}
		// Put the tree nodes in alphanumeric order of the nodes in their schedule
		Collections.sort(treeNodes, new Comparator<TreeNode>() {

			@Override
			public int compare(TreeNode o1, TreeNode o2) {
				return o1.node.getName().compareTo(o2.node.getName());
			}
			
		});
		StringBuilder sb = new StringBuilder();
		// Store cache for processor number normalisation
		Map<Integer, Integer> newProcNums = new HashMap<Integer, Integer>();
		int newProcNum = 0;
		for (TreeNode tn : treeNodes) {
			// If we haven't seen it before, it must be a different processor
			if (!newProcNums.containsKey(tn.recentProcessor)) {
				newProcNums.put(tn.recentProcessor, newProcNum++);
			}
			// Identify a schedule by all the nodes such that each node has name:startTime:normalisedProcessorNumber;
			sb.append(tn.node.getName() + ":" + tn.recentStartTime + ":" + newProcNums.get(tn.recentProcessor) + ";");
		}
		return sb.toString();
	}
	
	// Digests the data stored in an instance of the TreeNode class into a single hash value (a 32-bit signed integer).
	public int hashCode() {
		return (int) System.currentTimeMillis();
	}
	
}

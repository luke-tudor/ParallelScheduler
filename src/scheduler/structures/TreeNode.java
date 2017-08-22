package scheduler.structures;

import java.util.ArrayList;
import java.util.List;

public class TreeNode implements Comparable<TreeNode> {	
	
	private TreeNode parent;
	private Node node;
	private int recentProcessor;
	private int recentStartTime;
	
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
		
		//initialise the fields for this object
		this.parent = parent;
		this.node = node;
		this.recentProcessor = procNum;
		this.recentStartTime = maxStart;
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
		int node1Heuristic = node.getBottomLevel() + recentStartTime;
		int node2Heuristic = other.node.getBottomLevel() + other.recentStartTime;
		if (node1Heuristic < node2Heuristic) {
			return -1;
		} else if (node1Heuristic == node2Heuristic) {
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
}

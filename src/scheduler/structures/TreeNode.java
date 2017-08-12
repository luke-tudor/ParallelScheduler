package scheduler.structures;

import java.util.Set;

public class TreeNode implements Comparable<TreeNode> {	
	public TreeNode parent;
	public Node recentNode;
	public int recentProcessor;
	public int recentStartTime;
	public int height;
	
	//stores the int value of the earliest available time for a node to start on a given processor. 
	//Each processor 
	public int[] _processorEndTimes;
	
	public TreeNode(TreeNode tn, Node recent, int procNum) {
		TreeNode newTN = tn.addNode(recent, procNum);
		parent = newTN.parent;
		recentNode = newTN.recentNode;
		recentProcessor = newTN.recentProcessor;
		height = newTN.height;
	}
	
	public TreeNode(int numOfProcessors) {
		//values in processorArray set to 0 by default, exactly what we want for an empty TreeNode
		this(null, null, -1, -1, 0, new int[numOfProcessors]);
	}
	
	public TreeNode(TreeNode parent, Node recentNode, int recentProcessor, int recentStartTime, int height, int[] processorEndTimes) {
		this.parent = parent;
		this.recentNode = recentNode;
		this.recentProcessor = recentProcessor;
		this.recentStartTime = recentStartTime;
		this.height = height;
		this._processorEndTimes = processorEndTimes;
	}

	@Override
	public int compareTo(TreeNode other) {
		if (hashCode() < other.hashCode()) {
			return -1;
		} else if (hashCode() == other.hashCode()) {
			return 0;
		} else {
			return 1;
		}
	}
	
	/**
	 * Returns the earliest possible start time of the given node on the given processor as defined by its parents.
	 * 
	 * @param node The Node object to add to the TreeNode
	 * @param processor The processor the Node is to be added to
	 * @return An int value representing the earliest start time of the given node on the specified processor 
	 * allowable by it's parents. This doesn't guarantee adding the node at that time results in a legal solution.
	 */
	private int calculateDiagram(Node node, int processor) {
		
		int parentEarliest = 0;
		
		//if this treeNode has a parent, find out their shortest time 
		//this is done first to minimize the amount of idling memory in this call
		if(parent != null) {
			parentEarliest = parent.calculateDiagram(node, processor);
		}
		
		//earliestStart initialised to 0
		//if this is the first treeNode, then earliestStart will be 0 and the upcoming conditionals will fail
		int earliestStart = 0;
		
		//if this node is a parent of the node to add, get the int value of its earliest start time
		if(node.parentEdgeWeights.keySet().contains(recentNode)) {
			earliestStart = recentStartTime + recentNode.getWeight();
			
			//if they're on different processors, add the edge weight
			if(processor != recentProcessor) {
				earliestStart += node.parentEdgeWeights.get(node);
			}
		}
		
		//return the largest of this treeNode's earliest start and their parent treeNode's earliest start
		return Math.max(earliestStart, parentEarliest);
	}
	

	/**
	 * Creates a new Treenode, with the current TreeNode as it's parent, containing the given node on the given 
	 * processor.
	 * 
	 * It assumes that all the parents of the given node are already in the graph, that is, the node is legally 
	 * able to be added to the TreeNode.
	 * 
	 * @param nodeToAdd the Node object to add to the TreeNode
	 * @param processor	the processor to add the Node object to
	 * @return	A new TreeNode containing the added node
	 */
	public TreeNode addNode(Node nodeToAdd, int processor) {
		//find the earliest start time for that node on that processor
		int startTime = Math.max(calculateDiagram(nodeToAdd, processor), _processorEndTimes[processor-1]);
		
		//update the processorEndTimes array
		_processorEndTimes[processor-1] = startTime + nodeToAdd.getWeight();
		
		//create the new TreeNode
		return new TreeNode(this, nodeToAdd, processor, startTime, height+1, _processorEndTimes);
	}
}

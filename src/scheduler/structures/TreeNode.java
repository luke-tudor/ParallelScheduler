package scheduler.structures;

public class TreeNode implements Comparable<TreeNode> {	
	public TreeNode parent;
	public Node recentNode;
	public int recentProcessor;
	public int recentStartTime;
	public int height;
	
	public TreeNode(TreeNode tn, Node recent, int procNum) {		
		int maxStart = 0; 
		TreeNode current = tn;
		
		while (current != null && current.recentNode != null) {
			
			int time = 0;
			if (procNum == current.recentProcessor) {
				time = current.recentStartTime + current.recentNode.getWeight();
			} else {
				try {
					time = current.recentStartTime + current.recentNode.getWeight() + recent.parentEdgeWeights.get(current.recentNode);
				} catch(NullPointerException e) {
					
				}
			}
			
			if (time > maxStart) {
				maxStart = time;
			}
			
			current = current.parent;
		}
		
		this.parent = tn;
		this.recentNode = recent;
		this.recentProcessor = procNum;
		this.recentStartTime = maxStart;
		this.height = tn.height + 1;
		
	}
	
	public TreeNode() {
		this.parent = null;
		this.recentNode = null;
		this.recentProcessor = -1;
		this.recentStartTime = -1;
		this.height = 0;
	}

	@Override
	public int compareTo(TreeNode other) {
		if (recentNode.getHeuristic() < other.recentNode.getHeuristic()) {
			return -1;
		} else if (recentNode.getHeuristic() == other.recentNode.getHeuristic()) {
			return 0;
		} else {
			return 1;
		}
	}
}

package scheduler.structures;

public class TreeNode implements Comparable<TreeNode> {	
	public TreeNode parent;
	public Node recentNode;
	public int recentProcessor;
	public int recentStartTime;
	public int height;
	
	public TreeNode() {
		this(null, null, -1, -1, 0);
	}
	
	public TreeNode(TreeNode parent, Node recentNode, int recentProcessor, int recentStartTime, int height) {
		this.parent = parent;
		this.recentNode = recentNode;
		this.recentProcessor = recentProcessor;
		this.recentStartTime = recentStartTime;
		this.height = height;
	}

	@Override
	public int compareTo(TreeNode other) {
		if (recentNode.hvalue + recentStartTime < other.recentNode.hvalue + other.recentStartTime) {
			return -1;
		} else if (recentNode.hvalue + recentStartTime == other.recentNode.hvalue + recentStartTime) {
			return 0;
		} else {
			return 1;
		}
	}
}

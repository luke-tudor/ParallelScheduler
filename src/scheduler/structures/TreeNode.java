package scheduler.structures;

public class TreeNode implements Comparable<TreeNode> {	
	public TreeNode parent;
	public Node recentNode;
	public int recentProcessor;
	public int recentStartTime;
	public int height;
	
	public TreeNode(TreeNode tn, Node recent, int procNum) {
		
	}
	
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
		if (hashCode() < other.hashCode()) {
			return -1;
		} else if (hashCode() == other.hashCode()) {
			return 0;
		} else {
			return 1;
		}
	}
}

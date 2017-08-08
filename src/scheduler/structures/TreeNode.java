package scheduler.structures;

public class TreeNode {	
	TreeNode parent;
	Node recentNode;
	int recentProcessor;
	int recentStartTime;
	
	public TreeNode() {
		this(null, null, -1, -1);
	}
	
	public TreeNode(TreeNode parent, Node recentNode, int recentProcessor, int recentStartTime) {
		this.parent = parent;
		this.recentNode = recentNode;
		this.recentProcessor = recentProcessor;
		this.recentStartTime = recentStartTime;
	}
}

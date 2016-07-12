package pw.tdekk.util;

import java.util.ArrayList;

/**
 * Created by TimD on 7/12/2016.
 * This will create a parent-child relationship between nodes.
 * [1 parent - infinite children]
 */
public class TreeNode {
    private TreeNode parent;
    private ArrayList<TreeNode> children = new ArrayList<>();


    public void setParent(TreeNode parent) {
        this.parent = parent;
    }

    public TreeNode getParent() {
        return parent;
    }

    public ArrayList<TreeNode> getChildren() {
        return children;
    }


}

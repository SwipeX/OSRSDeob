package pw.tdekk.deob.cfg;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.MethodNode;
import pw.tdekk.util.TreeNode;

import java.util.Arrays;

/**
 * Created by TimD on 7/12/2016.
 * This class encompasses a tree using a node based system to
 * keep track of the flow of blocks throughout the method.
 */
public class ControlFlowGraph {

    private final BasicBlock head;
    private final MethodNode method;
    private final BasicBlock[] blocks;

    public ControlFlowGraph(MethodNode mn) {
        this.method = mn;
        blocks = method.blocks;
        head = blocks[0];
        head.setType(BlockType.HEADER);
    }

    /**
     * Builds the resulting graph based on block flow.
     * This uses a Depth-First-Search (DFS) algorithm to trace the block execution.
     */
    public final void generate() {
        traceBlock(null, head);
    }

    /**
     * @param parent the parent of the current block.
     * @param block  - the current block being traced in the DFS.
     */
    private final void traceBlock(TreeNode parent, BasicBlock block) {
        if (block == null) {
            return;
        }
        block.setParent(parent);
        if (parent != null) {
            parent.getChildren().add(block);
        }
        AbstractInsnNode last = block.getLast();
        if (last != null) {
            if (last instanceof JumpInsnNode) {
                block.setType(last.getOpcode() == Opcodes.GOTO ? BlockType.GOTO : BlockType.JUMP);
                traceBlock(block, blockFrom(((JumpInsnNode) last).label));
                if (last.getOpcode() != Opcodes.GOTO) {
                    //Jumps also can keep going down the tree, GOTOs cannot.
                    traceBlock(block, successor(block));
                }
            } else {
                //a return would halt, all other types of blocks continue the tree.
                if (block.getType() != BlockType.RETURN) {
                    traceBlock(block, successor(block));
                }
            }
        }
    }

    private final BasicBlock blockFrom(AbstractInsnNode ain) {
        return blockFrom(method.instructions.indexOf(ain));
    }

    private final BasicBlock blockFrom(int index) {
        for (BasicBlock block : blocks) {
            for (AbstractInsnNode ain : block.getInstructions()) {
                if (method.instructions.indexOf(ain) == index) return block;
            }
        }
        return null;
    }

    public BasicBlock getHead() {
        return head;
    }

    /**
     * @param block - the block to search for the successor of.
     * @return the successor block if it exists.
     */
    private final BasicBlock successor(BasicBlock block) {
        for (int i = 0; i < blocks.length - 1; i++) {
            if (blocks[i].equals(block))
                return blocks[i + 1];
        }
        return null;
    }
    public String toString(){
       printChildren(head);
        return null;
    }

    private void printChildren(BasicBlock block){
        for(TreeNode b : block.getChildren()){
            BasicBlock bb = (BasicBlock)b;
            System.out.println(bb.getChildren().size());
            if(bb.getChildren().size() == 0)
                System.out.println("---END---");
        }
    }
}

package pw.tdekk.deob.cfg;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodNode;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by TimD on 7/12/2016.
 * This class encompasses a tree using a node based system to
 * keep track of the flow of blocks throughout the method.
 */
public class ControlFlowGraph extends DirectedGraph<BasicBlock, BasicBlock> {

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
        for (BasicBlock block : blocks) {
            addVertex(block);
            AbstractInsnNode last = block.getLastInstruction();
            if (last != null) {
                if (last instanceof JumpInsnNode) {
                    addEdge(block, blockFrom(((JumpInsnNode) last).label));
                    if (last.getOpcode() != Opcodes.GOTO) {
                        //Jumps also can keep going down the tree, GOTOs cannot.
                        addEdge(block, successor(block));
                    }
                } else {
                    //a return would halt, all other types of blocks continue the tree.
                    if (block.getType().equals(BlockType.NORMAL)) {
                        addEdge(block, successor(block));
                    }
                }
            }
        }
    }

    /**
     * @param ain - the AbstractInsnNode to use to determine the block it is contained in.
     * @return the corresponding block if it exists.
     */
    private final BasicBlock blockFrom(AbstractInsnNode ain) {
        int offset = ain instanceof LabelNode ? 1 : 0;
        int idx = method.instructions.indexOf(ain);
        //offset accounts for label + line number
        if (method.instructions.get(idx + 1).getOpcode() < 0) {
            offset++;
        }
        return blockFrom(idx + offset);
    }

    /**
     * @param index - index used to determine the block it is contained in.
     * @return the corresponding block if it exists.
     */
    private final BasicBlock blockFrom(int index) {
        for (BasicBlock block : blocks) {
            AbstractInsnNode start = block.getInstructions().get(0);
            AbstractInsnNode end = block.getLastInstruction();
            //if the instruction range resides in this block
            if (index >= method.instructions.indexOf(start) && index <= method.instructions.indexOf(end))
                return block;
        }
        return null;
    }

    /**
     * @return The block that begins the method.
     */
    public BasicBlock getHead() {
        return head;
    }

    /**
     * @return All blocks that will exit the method
     */
    public List<BasicBlock> getExits() {
        return Arrays.stream(blocks).filter(b -> b.getType().equals(BlockType.RETURN)).collect(Collectors.toList());
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

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("digraph {\r\n");
        for (BasicBlock block : blocks) {
            edgesFrom(block).forEach(e -> {
                builder.append(block.getIdentifier() + " -> " + e.getIdentifier());
                if (e.getType().equals(BlockType.RETURN))
                    builder.append("[color=red,penwidth=3.0]");
                builder.append(";\r\n");
            });
        }
        return builder.append("}").toString();
    }

}

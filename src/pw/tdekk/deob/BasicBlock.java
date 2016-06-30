package pw.tdekk.deob;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import java.util.ArrayList;
import java.util.IllegalFormatException;

/**
 * Created by TimD on 6/30/2016.
 */
public class BasicBlock {

    private MethodNode owner;
    private Label initial;
    private InsnList instructions;
    private int index;

    public BasicBlock(MethodNode owner, Label initial) {
        this.owner = owner;
        this.initial = initial;
        owner.instructions.indexOf(getLabelNode(initial));
    }


    protected LabelNode getLabelNode(final Label l) {
        if (!(l.info instanceof LabelNode)) {
            l.info = new LabelNode();
        }
        return (LabelNode) l.info;
    }

    public static BasicBlock[] getBlocks(MethodNode owner) {
        ArrayList<BasicBlock> blocks = new ArrayList<>();
        AbstractInsnNode first = owner.instructions.getFirst();
        if (!(first instanceof LabelNode)) {
            throw new IllegalStateException("All methods must start with a LabelNode");
        }
        LabelNode current = (LabelNode) first;
        BasicBlock block = new BasicBlock(owner, current.getLabel());
        AbstractInsnNode node = current.getNext();
        while (node != null) {
            //Handle end of blocks
            if (isExit(node)) {
                blocks.add(block);
            } else if (node instanceof JumpInsnNode) {
                //end of block
                //usually insert new label
                blocks.add(block);
                LabelNode newLabel = new LabelNode(new Label());
                block.instructions.insertBefore(node, newLabel);
            } else if (node instanceof LabelNode) {
                //end of block
                //new label not needed
                blocks.add(block);
            }
            block.instructions.add(node);
            node = node.getNext();
        }
        return blocks.toArray(new BasicBlock[blocks.size()]);
    }

    private static boolean isExit(AbstractInsnNode node) {
        int op = node.getOpcode();
        return (op == Opcodes.RETURN ||
                op == Opcodes.ARETURN ||
                op == Opcodes.IRETURN ||
                op == Opcodes.DRETURN ||
                op == Opcodes.FRETURN ||
                op == Opcodes.LRETURN ||
                op == Opcodes.ATHROW);
    }
}

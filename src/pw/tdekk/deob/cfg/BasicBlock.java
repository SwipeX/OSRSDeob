package pw.tdekk.deob.cfg;

import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.MethodNode;

import java.util.ArrayList;

/**
 * Created by TimD on 7/11/2016.
 * Encapsulates a list of instructions that have only an entry on the first instruction and an exit on the last instruction.
 * A block may have infinite entry points, and up to two exit paths.
 */
public class BasicBlock {

    private final ArrayList<AbstractInsnNode> instructions = new ArrayList();
    private final MethodNode owner;

    public BasicBlock(MethodNode owner) {
        this.owner = owner;
    }

    public ArrayList<AbstractInsnNode> getInstructions() {
        return instructions;
    }
}

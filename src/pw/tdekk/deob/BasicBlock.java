package pw.tdekk.deob;

import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by TimD on 6/30/2016.
 * Only standing questions besides optimization would be if ASM's line numbers affect blocks in any way?
 */
public class BasicBlock {

    private MethodNode owner;
    private AbstractInsnNode initial;
    private ArrayList<AbstractInsnNode> instructions;
    private int index;
    private int position;
    ArrayList<BasicBlock> referenced;

    public BasicBlock(MethodNode owner, AbstractInsnNode initial) {
        this.owner = owner;
        this.initial = initial;
        if (initial != null)
            position = owner.instructions.indexOf(initial);
        instructions = new ArrayList<>();
        referenced = new ArrayList<>();
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public MethodNode getOwner() {
        return owner;
    }

    public ArrayList<AbstractInsnNode> getInstructions() {
        return instructions;
    }

    public int getPosition() {
        return position;
    }

    public String toString() {
        final String[] s = {""};
        instructions.forEach(i -> s[0] += i + "\r\n");
        return "   " + owner.owner.name + "." + owner.name + owner.desc + " @ " + getPosition() + " #" + getIndex() + "\r\n" +
                "  {" + "\r\n" + s[0] + "  }";
    }

    public boolean contains(int index) {
        return position <= index && position + instructions.size() > index;
    }


    /**
     * BELOW THIS POINT ARE STATIC 'BUILDERS' FOR BASIC BLOCKS.
     */

    @NotNull
    public static BasicBlock[] getBlocks(MethodNode owner) {
        HashMap<Integer, Integer> jumps = new HashMap<>();//target, initial
        //This will populate the jump map, since ASM's labels are not the most reliable thing for building blocks.
        Arrays.stream(owner.instructions.toArray()).filter(insn -> insn instanceof JumpInsnNode || insn instanceof TableSwitchInsnNode || insn instanceof LookupSwitchInsnNode)
                .forEach(node -> {
                    if (node instanceof JumpInsnNode) {
                        jumps.put(owner.instructions.indexOf(((JumpInsnNode) node).label), owner.instructions.indexOf(node));
                    } else {
                        if (node instanceof TableSwitchInsnNode) {
                            TableSwitchInsnNode tsin = (TableSwitchInsnNode) node;
                            jumps.put(owner.instructions.indexOf(tsin.dflt), owner.instructions.indexOf(tsin));
                            tsin.labels.forEach(label -> jumps.put(owner.instructions.indexOf(label), owner.instructions.indexOf(tsin)));
                        } else if (node instanceof LookupSwitchInsnNode) {
                            LookupSwitchInsnNode lsin = (LookupSwitchInsnNode) node;
                            jumps.put(owner.instructions.indexOf(lsin.dflt), owner.instructions.indexOf(lsin));
                            lsin.labels.forEach(label -> jumps.put(owner.instructions.indexOf(label), owner.instructions.indexOf(lsin)));
                        }
                    }
                });
        owner.tryCatchBlocks.stream().forEach(t -> {
            jumps.put(owner.instructions.indexOf(t.end), owner.instructions.indexOf(t.start));
            jumps.put(owner.instructions.indexOf(t.handler), owner.instructions.indexOf(t.start));
        });
        //Now we can begin building the basic blocks of code
        ArrayList<BasicBlock> blocks = new ArrayList<>();
        BasicBlock block = new BasicBlock(owner, owner.instructions.getFirst());
        for (int i = 0; i < owner.instructions.size(); i++) {
            AbstractInsnNode node = owner.instructions.get(i);
            if (block == null) {
                block = new BasicBlock(owner, node);
                AbstractInsnNode prev = node.getPrevious();
                if (prev instanceof JumpInsnNode && prev.getOpcode() != Opcodes.GOTO)
                    blocks.get(blocks.size() - 1).referenced.add(block);
                block.setIndex(blocks.size());
            } else if (jumps.containsKey(i)) { //code can enter here
                blocks.add(block);
                block = new BasicBlock(owner, node);
                block.setIndex(blocks.size());
                block.instructions.add(node);//insn in new block
            } else if (endsBlock(node)) { //end of block
                block.instructions.add(node);//insn ends old block
                blocks.add(block);
                block = null;
            } else {
                if (node.getOpcode() >= 0)
                    block.instructions.add(node);//add to current block
            }
        }
       //set jump refs
        loop:
        for (Map.Entry<Integer, Integer> entry : jumps.entrySet()) {
            for (BasicBlock blox : blocks) {
                if (blox.contains(entry.getValue())) {
                    for (BasicBlock bb : blocks) {
                        if (bb.contains(entry.getKey())) {
                            blox.referenced.add(bb);
                            continue loop;
                        }
                    }
                }
            }
        }
        return blocks.toArray(new BasicBlock[blocks.size()]);
    }

    public static BasicBlock containing(BasicBlock[] blocks, int index){
        for(BasicBlock block : blocks){
            if(block.contains(index)) {
                return block;
            }
        }
        return null;
    }


    private static boolean endsBlock(AbstractInsnNode node) {
        int op = node.getOpcode();
        return op == Opcodes.RETURN || op == Opcodes.ARETURN || op == Opcodes.IRETURN ||
                op == Opcodes.DRETURN || op == Opcodes.FRETURN || op == Opcodes.LRETURN ||
                op == Opcodes.ATHROW || node instanceof JumpInsnNode;
    }

}

package pw.tdekk.deob.cfg;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.tree.*;

import java.util.ArrayList;

/**
 * Created by TimD on 7/11/2016.
 */
public class BlockAssembler extends MethodVisitor {

    private final MethodNode node;
    private final ArrayList<BasicBlock> blocks = new ArrayList<>();
    private final ArrayList<Integer> targets = new ArrayList<>();
    private BasicBlock currentBlock;

    /**
     * @param node - the method from which the blocks will be generated.
     */
    public BlockAssembler(MethodNode node) {
        this.node = node;
        buildTargets();
        currentBlock = new BasicBlock(node);
        node.accept(this);
    }

    /**
     * @return the list of blocks, post computation.
     */
    public ArrayList<BasicBlock> getBlocks() {
        return blocks;
    }

    /**
     * Constructs an index of each possible target jump location.
     * The reason this must be used is that a basic block cannot contain any entry or exit except for the first and last instruction.
     */
    private final void buildTargets() {
        node.accept(new MethodVisitor() {
            @Override
            public void visitTableSwitchInsn(TableSwitchInsnNode tsin) {
                targets.add(node.instructions.indexOf(tsin.dflt));
                tsin.labels.forEach(l -> targets.add(node.instructions.indexOf(l)));
            }

            @Override
            public void visitLookupSwitchInsn(LookupSwitchInsnNode lsin) {
                targets.add(node.instructions.indexOf(lsin.dflt));
                lsin.labels.forEach(l -> targets.add(node.instructions.indexOf(l)));
            }

            @Override
            public void visitJumpInsn(JumpInsnNode jin) {
                targets.add(node.instructions.indexOf(jin.label));
            }

            @Override
            public void visitTryCatchBlock(TryCatchBlockNode tcbn) {
                targets.add(node.instructions.indexOf(tcbn.start));
                targets.add(node.instructions.indexOf(tcbn.end));
                targets.add(node.instructions.indexOf(tcbn.handler));
            }
        });
    }

    public void visitAbstractInsn(AbstractInsnNode ain){
        if(targets.contains(node.instructions.indexOf(ain))){
            blocks.add(currentBlock);
            currentBlock = new BasicBlock(node);
        }
    }

    @Override
    public void visitInsn(InsnNode in) {
        currentBlock.getInstructions().add(in);
    }

    @Override
    public void visitIntInsn(IntInsnNode iin) {
        currentBlock.getInstructions().add(iin);
    }

    @Override
    public void visitVarInsn(VarInsnNode vin) {
        currentBlock.getInstructions().add(vin);
    }

    @Override
    public void visitTypeInsn(TypeInsnNode tin) {
        currentBlock.getInstructions().add(tin);
    }

    @Override
    public void visitFieldInsn(FieldInsnNode fin) {
        currentBlock.getInstructions().add(fin);
    }

    @Override
    public void visitMethodInsn(MethodInsnNode min) {
        currentBlock.getInstructions().add(min);
    }

    @Override
    public void visitInvokeDynamicInsn(InvokeDynamicInsnNode idin) {
        currentBlock.getInstructions().add(idin);
    }

    @Override
    public void visitJumpInsn(JumpInsnNode jin) {
        currentBlock.getInstructions().add(jin);
        blocks.add(currentBlock);
        currentBlock = new BasicBlock(node);
    }

    @Override
    public void visitLdcInsn(LdcInsnNode ldc) {
        currentBlock.getInstructions().add(ldc);
    }

    @Override
    public void visitIincInsn(IincInsnNode iin) {
        currentBlock.getInstructions().add(iin);
    }

    @Override
    public void visitTableSwitchInsn(TableSwitchInsnNode tsin) {
        currentBlock.getInstructions().add(tsin);
    }

    @Override
    public void visitLookupSwitchInsn(LookupSwitchInsnNode lsin) {
        currentBlock.getInstructions().add(lsin);
    }

    @Override
    public void visitMultiANewArrayInsn(MultiANewArrayInsnNode manain) {
        currentBlock.getInstructions().add(manain);
    }

    public void visitEnd() {
        if (currentBlock != null && currentBlock.getInstructions().size() > 0) {
            blocks.add(currentBlock);
        }
    }
}

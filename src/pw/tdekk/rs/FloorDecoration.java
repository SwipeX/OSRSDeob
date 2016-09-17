package pw.tdekk.rs;


import org.objectweb.asm.tree.ClassNode;


public class FloorDecoration extends AbstractIdentifier {

    @Override
    public boolean validate(ClassNode cn) {
        return cn.ownerless() && cn.getFieldTypeCount() == 2 && cn.fieldCount("I") == 5 &&
                cn.fieldCount(internalDesc("RenderableNode")) == 1;
    }
}

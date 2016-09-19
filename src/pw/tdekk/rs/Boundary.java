package pw.tdekk.rs;

import org.objectweb.asm.tree.ClassNode;

public class Boundary extends AbstractIdentifier {

    @Override
    public boolean validate(ClassNode cn) {
        return cn.ownerless() && cn.getFieldTypeCount() == 2 && cn.fieldCount("I") == 7 &&
                cn.fieldCount(internalDesc("Entity")) == 2;
    }
}

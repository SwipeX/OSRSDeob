package pw.tdekk.rs;

import org.objectweb.asm.tree.ClassNode;


public class PlayerDefinition extends AbstractIdentifier {

    @Override
    public boolean validate(ClassNode cn) {
        return cn.ownerless() && cn.getFieldTypeCount() == 4 && cn.fieldCount("J") == 2;
    }
}

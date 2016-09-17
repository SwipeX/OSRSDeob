package pw.tdekk.rs;

import org.objectweb.asm.tree.ClassNode;


public class IgnoredPlayer extends AbstractIdentifier {

    @Override
    public boolean validate(ClassNode cn) {
        return cn.ownerless() && cn.getFieldTypeCount() == 1 && cn.fieldCount("Ljava/lang/String;") == 2;
    }
}

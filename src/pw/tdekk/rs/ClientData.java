package pw.tdekk.rs;

import org.objectweb.asm.tree.ClassNode;

public class ClientData extends AbstractIdentifier {

    @Override
    public boolean validate(ClassNode cn) {
        return cn.ownerless() && cn.getFieldTypeCount() == 0 && cn.fieldCount("Ljava/lang/String;", false) >= 7 &&
                cn.fieldCount("Z", false) >= 1 && cn.fieldCount("[I", false) >= 1;
    }
}

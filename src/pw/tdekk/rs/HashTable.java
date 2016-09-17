package pw.tdekk.rs;

import org.objectweb.asm.tree.ClassNode;

public class HashTable extends AbstractIdentifier {

    @Override
    public boolean validate(ClassNode cn) {
        String desc = internalDesc("Node");
        return cn.ownerless() && cn.fieldCount(desc) == 2 && cn.fieldCount("[" + desc) == 1;
    }
}

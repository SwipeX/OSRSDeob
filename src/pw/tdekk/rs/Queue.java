package pw.tdekk.rs;

import org.objectweb.asm.tree.ClassNode;


public class Queue extends AbstractIdentifier {

    @Override
    public boolean validate(ClassNode cn) {
        return cn.ownerless() && cn.fields.size() == 1 && cn.fieldCount(internalDesc("CacheableNode")) == 1 &&
                !cn.interfaces.contains("java/lang/Iterable");
    }
}

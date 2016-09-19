package pw.tdekk.rs;

import org.objectweb.asm.tree.ClassNode;

public class ChatboxMessage extends AbstractIdentifier {

    @Override
    public boolean validate(ClassNode cn) {
        return cn.superName.equals(getMapping("CacheableNode")) && cn.fieldCount("Ljava/lang/String;") == 3;
    }
}

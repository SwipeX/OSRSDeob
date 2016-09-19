package pw.tdekk.rs;

import org.objectweb.asm.tree.ClassNode;

public class Shell extends AbstractIdentifier {

    @Override
    public boolean validate(ClassNode cn) {
        return cn.name.equals(getMappedNode("Client").superName);
    }
}

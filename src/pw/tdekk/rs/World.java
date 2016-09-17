package pw.tdekk.rs;


import org.objectweb.asm.tree.ClassNode;

public class World extends AbstractIdentifier {

    @Override
    public boolean validate(ClassNode cn) {
        return cn.ownerless() && cn.fieldCount("Ljava/lang/String;") == 2 && cn.fieldCount("I") == 5;
    }
}

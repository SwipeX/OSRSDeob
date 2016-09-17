package pw.tdekk.rs;


import org.objectweb.asm.tree.ClassNode;

public class Friend extends AbstractIdentifier {

    @Override
    public boolean validate(ClassNode cn) {
        return cn.ownerless() && cn.getAbnormalFieldCount() == 0 && cn.fieldCount("Ljava/lang/String;") == 2 &&
                cn.fieldCount("Z") == 2 && cn.fieldCount("I") == 2;
    }
}

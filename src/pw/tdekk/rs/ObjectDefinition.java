package pw.tdekk.rs;


import org.objectweb.asm.tree.ClassNode;


public class ObjectDefinition extends AbstractIdentifier {

    @Override
    public boolean validate(ClassNode cn) {
        return cn.getFieldTypeCount() == 6 && cn.fieldCount("[S") == 4 && cn.fieldCount("[I") == 4;
    }
}

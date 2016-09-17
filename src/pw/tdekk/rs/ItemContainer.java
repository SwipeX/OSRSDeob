package pw.tdekk.rs;


import org.objectweb.asm.tree.ClassNode;


public class ItemContainer extends AbstractIdentifier {

    @Override
    public boolean validate(ClassNode cn) {
        return cn.getFieldTypeCount() == 1 && cn.fieldCount("[I") == 2 && cn.superName.equals(internalName("Node"));
      }}

package pw.tdekk.rs;


import org.objectweb.asm.tree.ClassNode;

public class ItemLayer extends AbstractIdentifier {

    @Override
    public boolean validate(ClassNode cn) {
        return cn.ownerless() && cn.getFieldTypeCount() == 2 && cn.fieldCount(internalDesc("RenderableNode")) == 3;
      }}

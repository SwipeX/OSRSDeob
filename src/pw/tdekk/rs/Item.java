package pw.tdekk.rs;

import org.objectweb.asm.tree.ClassNode;

public class Item extends AbstractIdentifier {

    @Override
    public boolean validate(ClassNode cn) {
        return cn.superName.equals(getMapping("RenderableNode")) && cn.getFieldTypeCount() == 1 && cn.fieldCount("I") == 2;
      }}

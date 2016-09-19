package pw.tdekk.rs;

import org.objectweb.asm.tree.ClassNode;


public class WidgetNode extends AbstractIdentifier {

    @Override
    public boolean validate(ClassNode cn) {
        return cn.superName.equals(getMapping("Node")) && cn.fieldCount("I") == 2 && cn.fieldCount("Z") == 1;
      }}

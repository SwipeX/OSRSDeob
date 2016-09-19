package pw.tdekk.rs;


import org.objectweb.asm.tree.ClassNode;


public class Widget extends AbstractIdentifier {

    @Override
    public boolean validate(ClassNode cn) {
        return cn.superName.equals(getMapping("Node")) && cn.fieldCount("[Ljava/lang/Object;") > 10;
      }}

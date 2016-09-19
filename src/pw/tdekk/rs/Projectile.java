package pw.tdekk.rs;


import org.objectweb.asm.tree.ClassNode;


public class Projectile extends AbstractIdentifier {

    @Override
    public boolean validate(ClassNode cn) {
        return cn.superName.equals(getMapping("Entity")) && cn.getFieldTypeCount() == 4 &&
                cn.fieldCount("Z") == 1 && cn.fieldCount(internalDesc("AnimationSequence")) == 1;
      }}

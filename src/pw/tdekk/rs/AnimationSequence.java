package pw.tdekk.rs;


import org.objectweb.asm.tree.ClassNode;

public class AnimationSequence extends AbstractIdentifier {

    @Override
    public boolean validate(ClassNode cn) {
        return cn.superName.equals(internalName("CacheableNode")) && cn.getFieldTypeCount() == 3 && cn.fieldCount("Z") == 1;
      }}

package pw.tdekk.rs;

import org.objectweb.asm.tree.ClassNode;

public class AnimableGameObject extends AbstractIdentifier {

    @Override
    public boolean validate(ClassNode cn) {
        return cn.superName.equals(internalName("RenderableNode")) & cn.fieldCount(internalDesc("AnimationSequence")) == 1 &&
                cn.fieldCount("Z") == 1 && cn.fieldCount("I") == 8;
      }}

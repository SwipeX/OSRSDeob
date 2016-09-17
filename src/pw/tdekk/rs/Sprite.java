package pw.tdekk.rs;

import org.objectweb.asm.tree.ClassNode;

/**
 * @author Tyler Sedlar
 * @since 3/11/15.
 */

public class Sprite extends AbstractIdentifier {

    @Override
    public boolean validate(ClassNode cn) {
        return !cn.ownerless() && cn.getFieldTypeCount() == 2 && cn.fieldCount("[I") == 1;
      }}

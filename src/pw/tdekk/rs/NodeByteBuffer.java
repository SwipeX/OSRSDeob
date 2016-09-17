package pw.tdekk.rs;


import org.objectweb.asm.tree.ClassNode;

/**
 * @author Tim Dekker
 * @since 7/9/15
 */

public class NodeByteBuffer extends AbstractIdentifier {

    @Override
    public boolean validate(ClassNode cn) {
        return cn.superName.equals(internalName("Node")) && cn.getFieldTypeCount() == 2 && cn.fieldCount("[B") > 0 &&
                cn.fieldCount("I") > 0;
      }}

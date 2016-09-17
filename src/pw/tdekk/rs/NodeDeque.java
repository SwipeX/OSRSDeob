package pw.tdekk.rs;


import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;

public class NodeDeque extends AbstractIdentifier {

    @Override
    public boolean validate(ClassNode cn) {
        return cn.ownerless() && cn.fields.size() == 2 && cn.fieldCount(internalDesc("Node")) == 2;
      }}

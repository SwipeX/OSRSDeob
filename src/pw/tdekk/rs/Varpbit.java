package pw.tdekk.rs;

import org.objectweb.asm.tree.ClassNode;

/**
 * @author Tyler Sedlar
 */

public class Varpbit extends AbstractIdentifier {

    @Override
    public boolean validate(ClassNode cn) {
        return cn.superName.equals(internalName("CacheableNode")) && cn.getFieldTypeCount() == 1 && cn.fieldCount("I") == 3;
      }}

package pw.tdekk.rs;

import org.objectweb.asm.tree.ClassNode;

/**
 * Created by $ Tim Dekker on 7/16/2016.
 */
public class NpcDefinition extends AbstractIdentifier {
    @Override
    public boolean validate(ClassNode cn) {
        return cn.superName.equals(getMapping("CacheableNode")) && cn.fieldCount("Z") >= 4 && cn.fieldCount("Z") < 7;
    }
}

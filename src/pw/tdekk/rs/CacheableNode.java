package pw.tdekk.rs;

import org.objectweb.asm.tree.ClassNode;

/**
 * Created by $ Tim Dekker on 7/16/2016.
 */
public class CacheableNode extends AbstractIdentifier {
    @Override
    public boolean validate(ClassNode cn) {
        return cn.superName.equals(getMapping("Node")) && cn.fieldCount("L" + cn.name + ";") == 2;
    }
}

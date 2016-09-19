package pw.tdekk.rs;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;

/**
 * Created by $ Tim Dekker on 7/16/2016.
 */
public class Entity extends AbstractIdentifier {
    @Override
    public boolean validate(ClassNode cn) {
        return (cn.access & Opcodes.ACC_ABSTRACT) == Opcodes.ACC_ABSTRACT && cn.superName.equals(getMapping("CacheableNode"));
    }

    public void process(){
        super.process();
    }
}

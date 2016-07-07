package pw.tdekk.rs;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

/**
 * Created by timde on 7/2/2016.
 */
public abstract class AbstractIdentifier {
    ClassNode identified = null;

    public abstract ClassNode Identify();

    public void setIdentified(ClassNode cn) {
        identified = cn;
    }

    public void Process() {
        if (identified != null && getVisitors() != null) {
            for (MethodVisitor visitor : getVisitors()) {
                for (MethodNode mn : identified.methods) {
                    mn.accept(visitor);
                }
            }
        }
    }


    public MethodVisitor[] getVisitors() {
        return null;
    }
}

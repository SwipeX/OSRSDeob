package pw.tdekk.rs;

import org.objectweb.asm.tree.ClassNode;
import pw.tdekk.Application;

/**
 * Created by $ Tim Dekker on 7/16/2016.
 */
public class Character extends AbstractIdentifier {
    @Override
    public boolean validate(ClassNode cn) {
        return cn.superName.equals(internalName("RenderableNode"))  && cn.fieldCount("Z") >= 1 &&
                cn.fieldCount("Ljava/lang/String;") == 1;
    }
}

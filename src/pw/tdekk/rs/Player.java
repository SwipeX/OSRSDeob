package pw.tdekk.rs;

import org.objectweb.asm.tree.ClassNode;
import pw.tdekk.Application;

/**
 * Created by $ Tim Dekker on 7/16/2016.
 */
public class Player extends AbstractIdentifier {

    @Override
    public boolean validate(ClassNode cn) {
        return cn.superName.equals(internalName("Character")) && cn.fieldCount("Ljava/lang/String;") >= 1 &&
                cn.fieldCount("Z") >= 1 && cn.getAbnormalFieldCount() == 2;
    }
}

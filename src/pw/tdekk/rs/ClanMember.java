package pw.tdekk.rs;

import org.objectweb.asm.tree.ClassNode;

/**
 * Created by TimD on 9/17/2016.
 */
public class ClanMember extends AbstractIdentifier {
    @Override
    public boolean validate(ClassNode cn) {
        return cn.superName.equals(internalName("Node")) && cn.fieldCount("Ljava/lang/String;") == 2 &&
                cn.fieldCount("B") > 0 && cn.fieldCount("I") > 0;
    }
}

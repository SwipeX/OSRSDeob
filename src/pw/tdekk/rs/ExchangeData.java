package pw.tdekk.rs;

import org.objectweb.asm.tree.ClassNode;

/**
 * Created by TimD on 9/17/2016.
 */
public class ExchangeData extends AbstractIdentifier {
    @Override
    public boolean validate(ClassNode cn) {
        return cn.ownerless() && cn.getFieldTypeCount() == 2 && cn.fieldCount("B") == 1 && cn.fieldCount("I") > 3;
    }
}

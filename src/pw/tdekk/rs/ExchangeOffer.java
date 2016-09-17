package pw.tdekk.rs;


import org.objectweb.asm.tree.ClassNode;

;

/**
 * @author Tim Dekker
 * @since 4/13/15
 */
public class ExchangeOffer extends AbstractIdentifier {

    @Override
    public boolean validate(ClassNode cn) {
        return cn.ownerless() && cn.getFieldTypeCount() == 2 && cn.fieldCount("B") == 1 && cn.fieldCount("I") > 3;
    }
}

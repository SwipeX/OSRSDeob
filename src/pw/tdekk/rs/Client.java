package pw.tdekk.rs;

import org.objectweb.asm.tree.ClassNode;

/**
 * Created by TimD on 9/17/2016.
 */
public class Client  extends AbstractIdentifier {
    @Override
    public boolean validate(ClassNode cn) {
        return cn.name.equals("client");
    }
}

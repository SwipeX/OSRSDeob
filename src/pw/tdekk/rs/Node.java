package pw.tdekk.rs;

import org.objectweb.asm.tree.ClassNode;
import pw.tdekk.Application;

import java.util.Collection;

/**
 * Created by timde on 7/2/2016.
 */
public class Node extends AbstractIdentifier {
    @Override
    public boolean validate(ClassNode cn) {
        return cn.ownerless() && cn.fields.size() == 3 && cn.fieldCount("J") == 1 && cn.fieldCount("L" + cn.name + ";") == 2;
    }

    public void process() {
        super.process();
        easyHook("uid", "J");
    }
}

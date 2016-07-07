package pw.tdekk.rs;

import org.objectweb.asm.tree.ClassNode;
import pw.tdekk.Application;

/**
 * Created by timde on 7/2/2016.
 */
public class Node extends AbstractIdentifier {
    @Override
    public ClassNode Identify() {
        for (ClassNode cn : Application.getClasses().values()) {
            if (cn.ownerless() && cn.fields.size() == 3 && cn.fieldCount("J") == 1 && cn.fieldCount("L" + cn.name + ";") == 2)
                return cn;
        }
        return null;
    }
    public void Process(){
        super.Process();
        System.out.println(getClass().getName() + " -> " +identified.name);
    }
}

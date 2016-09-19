package pw.tdekk.rs;

import org.objectweb.asm.tree.ClassNode;

/**
 * Created by $ Tim Dekker on 7/16/2016.
 */
public class Npc extends AbstractIdentifier {
    @Override
    public boolean validate(ClassNode cn) {
        return cn.fieldCount("L" + getMapping("NpcDefinition") + ";") == 1;
    }

    public void process(){
        super.process();
        easyHook("definition", "L" + getMapping("NpcDefinition") + ";");
    }
}

package pw.tdekk.rs;


import org.objectweb.asm.tree.ClassNode;


public class Tile extends AbstractIdentifier {

    @Override
    public boolean validate(ClassNode cn) {
        return cn.superName.equals(internalName("Node")) && cn.fieldCount("Z") == 3;
    }
}

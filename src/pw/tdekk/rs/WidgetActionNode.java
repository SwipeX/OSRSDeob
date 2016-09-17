package pw.tdekk.rs;


import org.objectweb.asm.tree.ClassNode;

public class WidgetActionNode extends AbstractIdentifier {

    @Override
    public boolean validate(ClassNode cn) {
        return cn.superName.equals(internalName("Node")) && cn.fieldCount(internalDesc("Widget")) == 2;
    }
}

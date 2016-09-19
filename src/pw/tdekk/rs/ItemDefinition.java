package pw.tdekk.rs;


import org.objectweb.asm.tree.*;

public class ItemDefinition extends AbstractIdentifier {

    @Override
    public boolean validate(ClassNode cn) {
        return cn.superName.equals(getMapping("CacheableNode")) && cn.getFieldTypeCount() == 6 &&
                cn.fieldCount("[Ljava/lang/String;") == 2;
      }}

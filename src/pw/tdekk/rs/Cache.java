package pw.tdekk.rs;


import org.objectweb.asm.tree.ClassNode;


public class Cache extends AbstractIdentifier {

    @Override
    public boolean validate(ClassNode cn) {
        return cn.getFieldTypeCount() == 4 && cn.fieldCount(internalDesc("CacheableNode")) == 1 &&
                cn.fieldCount(internalDesc("HashTable")) == 1;
      }}

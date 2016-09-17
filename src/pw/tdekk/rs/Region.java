package pw.tdekk.rs;

import org.objectweb.asm.tree.ClassNode;

public class Region extends AbstractIdentifier {

    @Override
    public boolean validate(ClassNode cn) {
        return cn.ownerless() && cn.getAbnormalFieldCount() == 2 &&
                cn.fieldCount("[[[" + internalDesc("Tile")) == 1 && cn.fieldCount("[" + internalDesc("InteractableObject")) == 1;
      }}

package pw.tdekk.rs;


import org.objectweb.asm.tree.ClassNode;


public class ChatboxChannel extends AbstractIdentifier {

    @Override
    public boolean validate(ClassNode cn) {
        return cn.fieldCount("[" + internalDesc("ChatboxMessage")) > 0;
      }}

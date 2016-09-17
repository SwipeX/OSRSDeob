package pw.tdekk.rs;


import org.objectweb.asm.tree.ClassNode;


public class Canvas extends AbstractIdentifier {


    @Override
    public boolean validate(ClassNode cn) {
        return cn.superName.equals("java/awt/Canvas") && cn.fieldCount("Ljava/awt/Component;") == 1;
      }}

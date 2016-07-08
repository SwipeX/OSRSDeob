package pw.tdekk.deob;

import org.objectweb.asm.Handle;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;
import pw.tdekk.Application;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by TimD on 6/21/2016.
 */
public class UnusedMembers implements Mutator {
    private ArrayList<Handle> usedMethods = new ArrayList<>();
    private ArrayList<Handle> usedFields = new ArrayList<>();
    private int removedCount = 0;
    private int removedFields = 0;

    @Override
    public void mutate() {
        long startTime = System.currentTimeMillis();
        getEntryPoints().forEach(this::visit);
        ArrayList<MethodNode> toRemove = new ArrayList<>();
        ArrayList<FieldNode> fields = new ArrayList<>();
        Application.getClasses().values().forEach(c -> {
            c.methods.forEach(m -> {
                if (!usedMethods.contains(m.getHandle())) {
                    toRemove.add(m);
                    removedCount++;
                }
            });
            c.fields.forEach(f -> {
                if (!usedFields.contains(f.getHandle())) {
                    fields.add(f);
                    removedFields++;
                }
            });

        });
        toRemove.forEach(m -> m.owner.methods.remove(m));
        fields.forEach(f -> f.owner.fields.remove(f));
        System.out.println(usedMethods.size()+ "methods");
        System.out.println(String.format("Removed %s methods and %s fields in %s ms", removedCount, removedFields, (System.currentTimeMillis() - startTime)));
    }

    private void visit(MethodNode mn) {
        Handle handle = mn.getHandle();
        if (usedMethods.contains(handle)) return;
        usedMethods.add(handle);
        //subclass methods
        if ((mn.access & Opcodes.ACC_ABSTRACT) == Opcodes.ACC_ABSTRACT) {
            Application.getClasses().values().stream().filter(node -> node.superName.equals(mn.owner.name)).forEach(node -> {
                MethodNode sub = node.getMethod(mn.name, mn.desc);
                if (sub != null)
                    visit(sub);
            });
        }
        mn.accept(new MethodVisitor(Opcodes.ASM5) {
            @Override
            public void visitFieldInsn(int opcode, String owner, String name, String desc) {
                Handle handle = new Handle(0, owner, name, desc, false);
                if (!usedFields.contains(handle)) {
                    usedFields.add(handle);
                    ClassNode node = Application.getClasses().get(owner);
                    if (node != null) {
                        String superName = node.superName;
                        if (Application.getClasses().containsKey(superName)) {
                            ClassNode superClass = Application.getClasses().get(superName);
                            if (superClass.getField(name, desc) != null)
                                usedFields.add(new Handle(0, superName, name, desc, false));
                        }
                    }
                    getSubClasses(owner).forEach(sub -> usedFields.add(new Handle(0, sub.name, name, desc, false)));
                }
            }

            @Override
            public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
                if (Application.getClasses().containsKey(owner)) {
                    ClassNode node = Application.getClasses().get(owner);
                    MethodNode method = node.getMethod(name, desc);
                    if (method != null) {
                        String superName = node.superName;
                        if (Application.getClasses().containsKey(superName)) {
                            ClassNode superClass = Application.getClasses().get(superName);
                            MethodNode superMethod = superClass.getMethod(name, desc);
                            if (superMethod != null) {
                                visit(superMethod);
                            }
                        }
                        getSubClasses(node.name).forEach(
                                sub -> {
                                    MethodNode superMethod = sub.getMethod(name, desc);
                                    if (superMethod != null) {
                                        visit(superMethod);
                                    }
                                }
                        );
                        visit(method);
                    }
                }
            }
        });
    }

    public List<ClassNode> getSubClasses(String superName) {
        return Application.getClasses().values().stream().filter(node -> node.superName.equals(superName)).collect(Collectors.toList());
    }

    public List<MethodNode> getEntryPoints() {
        ArrayList<MethodNode> entry = new ArrayList<>();
        Application.getClasses().values().forEach(node -> {
            //All methods > 2 length for osrs obfuscator
            entry.addAll(node.methods.stream().filter(m -> m.name.length() > 2).collect(Collectors.toList()));
            //Any subclass methods should be added
            String superName = node.superName;
            if (Application.getClasses().containsKey(superName)) {
                for (MethodNode method : node.methods) {
                    entry.addAll(Application.getClasses().get(superName).methods.stream().filter(m ->
                            m.name.equals(method.name) && m.desc.equals(method.desc)).collect(Collectors.toList()));
                }
            }
            //interface methods
            List<String> interfaces = node.interfaces;
            for (String iface : interfaces) {
                ClassNode impl = Application.getClasses().get(iface);
                if (impl != null) {
                    for (MethodNode mn : node.methods) {
                        impl.methods.stream().filter(imn -> mn.name.equals(imn.name) && mn.desc.equals(imn.desc)).forEach(imn -> {
                            entry.add(mn);
                            entry.add(imn);
                        });
                    }
                }
            }
        });
        return entry;
    }
}

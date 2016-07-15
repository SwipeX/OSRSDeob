package pw.tdekk.deob.usage;

import org.objectweb.asm.Handle;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;
import pw.tdekk.Application;
import pw.tdekk.deob.Mutator;

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
        mn.accept(new MethodVisitor() {
            @Override
            public void visitFieldInsn(FieldInsnNode fin) {
                Handle handle = new Handle(0, fin.owner, fin.name, fin.desc, false);
                if (!usedFields.contains(handle)) {
                    usedFields.add(handle);
                    ClassNode node = Application.getClasses().get(fin.owner);
                    if (node != null) {
                        String superName = node.superName;
                        if (Application.getClasses().containsKey(superName)) {
                            ClassNode superClass = Application.getClasses().get(superName);
                            if (superClass.getField(fin.name, fin.desc) != null)
                                usedFields.add(new Handle(0, superName, fin.name, fin.desc, false));
                        }
                    }
                    getSubClasses(fin.owner).forEach(sub -> usedFields.add(new Handle(0, sub.name, fin.name, fin.desc, false)));
                }
            }

            @Override
            public void visitMethodInsn(MethodInsnNode mn) {
                if (Application.getClasses().containsKey(mn.owner)) {
                    ClassNode node = Application.getClasses().get(mn.owner);
                    MethodNode method = node.getMethod(mn.name, mn.desc);
                    if (method != null) {
                        String superName = node.superName;
                        if (Application.getClasses().containsKey(superName)) {
                            ClassNode superClass = Application.getClasses().get(superName);
                            MethodNode superMethod = superClass.getMethod(mn.name, mn.desc);
                            if (superMethod != null) {
                                visit(superMethod);
                            }
                        }
                        getSubClasses(node.name).forEach(
                                sub -> {
                                    MethodNode superMethod = sub.getMethod(mn.name, mn.desc);
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

package pw.tdekk.rs;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;
import pw.tdekk.Application;
import pw.tdekk.rs.hook.Hook;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Created by timde on 7/2/2016.
 * Classes should extend this in order to identfy a new internal class.
 */
public abstract class AbstractIdentifier {
    private static HashMap<String, String> mapping = new HashMap<>();
    private ClassNode identified = null;
    private List<Hook> hooks = new ArrayList<>();

    public List<Hook> getHooks(){
        return hooks;
    }

    protected void addHook(String alias, String owner, String name, String desc) {
        hooks.add(new Hook(alias, owner, name, desc, Hook.Type.FIELD));
    }

    protected void addHook(String alias, FieldNode fn) {
        addHook(alias, fn.owner.name, fn.name, fn.desc);
    }

    protected void easyHook(String alias, String desc) {
        Optional<FieldNode> optional = identified.fields.stream().filter(fn -> fn.desc.equals(desc)).findFirst();
        if (optional.isPresent()) {
            FieldNode fn = optional.get();
            addHook(alias, fn);
        }
    }

    /**
     * @param cn ClassNode to test against the validation.
     * @return true if the ClassNode passes the validation boolean.
     */
    public abstract boolean validate(ClassNode cn);

    /**
     * @param classes - the Collection of ClassNodes obtained from an Archive.
     * @return the first ClassNode that matches the #validate method.
     */
    public ClassNode identify(Collection<ClassNode> classes) {
        Optional<ClassNode> optional = classes.stream().filter(this::validate).findFirst();
        if (optional.isPresent()) {
            ClassNode node = optional.get();
            mapping.put(getClass().getSimpleName(), node.name);
            return identified = node;
        }
        return null;
    }

    public ClassNode getIdentified() {
        return identified;
    }

    public void setIdentified(ClassNode cn) {
        identified = cn;
    }


    public String internalDesc(String external) {
        return "L" + getMapping(external) + ";";
    }

    /**
     * Allows the set visitors to run through the identified class.
     */
    public void process() {
        if (identified != null && getVisitors() != null) {
            for (MethodVisitor visitor : getVisitors()) {
                for (MethodNode mn : identified.methods) {
                    mn.accept(visitor);
                }
            }
        }
    }

    public static String getMapping(String internal) {
        return mapping.get(internal);
    }

    public static ClassNode getMappedNode(String internal) {
        return Application.getClasses().get(getMapping(internal));
    }

    public MethodVisitor[] getVisitors() {
        return null;
    }
}

package pw.tdekk.rs;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;
import pw.tdekk.Application;

import java.util.Collection;

/**
 * Created by timde on 7/2/2016.
 * Classes should extend this in order to identfy a new internal class.
 */
public abstract class AbstractIdentifier {
    ClassNode identified = null;

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
        return classes.stream().filter(this::validate).findFirst().get();
    }

    public ClassNode getIdentified() {
        return identified;
    }

    public void setIdentified(ClassNode cn) {
        identified = cn;
    }

    public static ClassNode internalClass(String external) {
        for (AbstractIdentifier a : Application.getIdentifiers()) {
            if (a.getClass().getSimpleName().equals(external))
                return a.getIdentified();
        }
        return null;
    }

    /**
     * @param external - the name of an external identifier that has already been processed. ex: 'Node'
     * @return - the internal, identified class name IF it has been located and processed.
     */
    public String internalName(String external) {
        ClassNode identified = internalClass(external);
        return identified == null ? null : identified.name;
    }

    public String internalDesc(String external) {
        return "L" + internalName(external) + ";";
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


    public MethodVisitor[] getVisitors() {
        return null;
    }
}

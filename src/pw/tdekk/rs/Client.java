package pw.tdekk.rs;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.FieldNode;
import pw.tdekk.Application;

/**
 * Created by TimD on 9/17/2016.
 */
public class Client extends AbstractIdentifier {
    @Override
    public boolean validate(ClassNode cn) {
        return cn.name.equals("client");
    }

    public void process() {
        super.process();
        addStaticHook("localPlayer", internalDesc("Player"));
        addStaticHook("players", "["+internalDesc("Player"));
        addStaticHook("npcs", "["+internalDesc("Npc"));
        addStaticHook("clanMembers", "["+internalDesc("ClanMember"));
        addStaticHook("groundItems", "[[["+internalDesc("NodeDeque"));
        addStaticHook("exchangeOffers", "["+internalDesc("ExchangeOffer"));
        addStaticHook("worlds", "["+internalDesc("World"));
        addStaticHook("widgets", "[[" + internalDesc("Widget"));
    }

    public MethodVisitor[] getVisitors() {
        return new MethodVisitor[]{};
    }

    public void addStaticHook(String alias, String desc) {
        for (ClassNode classNode : Application.getClasses().values()) {
            for (FieldNode fn : classNode.fields) {
                if (fn.desc.equals(desc)) {
                    addHook(alias, fn);
                    return;
                }
            }
        }
    }
}

package pw.tdekk;


import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;
import pw.tdekk.deob.Mutator;
import pw.tdekk.deob.usage.UnusedMembers;
import pw.tdekk.deob.usage.UnusedParameters;
import pw.tdekk.deob.cfg.ControlFlowGraph;
import pw.tdekk.rs.*;
import pw.tdekk.rs.Character;
import pw.tdekk.util.Archive;
import pw.tdekk.util.Crawler;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.jar.JarFile;


/**
 * Created by TimD on 6/16/2016.
 */
public class Application {
    private static JarFile OSRS;
    private static Mutator[] mutators = new Mutator[]{new UnusedMembers(), new UnusedParameters()};
    private static AbstractIdentifier[] identifiers = {new Node(), new CacheableNode(), new RenderableNode(), new HashTable(),
            new NodeDeque(), new Queue(), new Cache(), new NodeByteBuffer(), new Tile(), new AnimationSequence(),
            new Character(), new NpcDefinition(), new Npc(), new Player(), new PlayerDefinition(),
            new Projectile(), new Item(), new ItemDefinition(), new ItemLayer(), new InteractableObject(),
            new ObjectDefinition(), new Region(), new Friend(), new IgnoredPlayer(), new ClientData(),
            new ClanMember(), new Canvas(), new Boundary(), new AnimableGameObject(), new FloorDecoration(),
            new WallDecoration(), new WidgetNode(), new Widget(), new WidgetActionNode(), new Varpbit(),
            new World(), new ChatboxMessage(), new ChatboxChannel(), new Sprite(), new ExchangeOffer(),
            new ItemContainer(), new Client(), new Shell()
    };
    private static ConcurrentHashMap<String, ClassNode> classes;

    public static void main(String[] args) {
        try {
            Crawler crawler = new Crawler();
            if (crawler.outdated()) {
                crawler.download();
            }
            OSRS = new JarFile(new File("os_pack.jar"));
            long startTime = System.currentTimeMillis();
            classes = Archive.build(OSRS);
            VersionVisitor versionVisitor = new VersionVisitor();
            classes.get("client").getMethod("init", "()V").accept(versionVisitor);
            int version = versionVisitor.getVersion();
            System.out.println("Running on OSRS #" + version);
            Arrays.stream(mutators).forEach(Mutator::mutate);
            Arrays.stream(identifiers).forEach(i -> {
                ClassNode identified = i.identify(classes.values());
                if (identified != null) {
                    i.process();
                }
            });
            Arrays.sort(identifiers, (a, b) -> a.getClass().getSimpleName().compareTo(b.getClass().getSimpleName()));
            Arrays.stream(identifiers).forEach(i-> {
                System.out.println(i.getIdentified().name + " -> "+i.getClass().getSimpleName());
                i.getHooks().forEach(System.out::println);
            });
            System.out.println("Executed in: " + (System.currentTimeMillis() - startTime));
            //collapse blocks
            for (ClassNode c : classes.values()) {
                //  c.methods.stream().filter(m -> (Opcodes.ACC_ABSTRACT & m.access) != Opcodes.ACC_ABSTRACT).forEach(m -> new ControlFlowGraph(m).generate());
                c.methods.forEach(MethodNode::collapseBlocks);
            }
            Archive.write(new File("test.jar"), classes);

            // ClassNode A = classes.get("a");
            //  MethodNode f = A.methods.get(0);
            // ControlFlowGraph cfg = new ControlFlowGraph(f).generate();
            // System.out.println(cfg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ConcurrentHashMap<String, ClassNode> getClasses() {
        return classes;
    }

    public static AbstractIdentifier[] getIdentifiers() {
        return identifiers;
    }
}

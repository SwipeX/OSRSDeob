package pw.tdekk;


import org.objectweb.asm.tree.*;
import pw.tdekk.deob.Mutator;
import pw.tdekk.deob.UnusedMembers;
import pw.tdekk.deob.UnusedParameters;
import pw.tdekk.deob.cfg.BlockAssembler;
import pw.tdekk.rs.AbstractIdentifier;
import pw.tdekk.rs.Node;
import pw.tdekk.util.Archive;
import pw.tdekk.util.Crawler;

import java.io.File;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;
import java.util.jar.JarFile;


/**
 * Created by TimD on 6/16/2016.
 */
public class Application {
    private static JarFile OSRS;
    private static Mutator[] mutators = new Mutator[]{new UnusedMembers(), new UnusedParameters()};
    private static AbstractIdentifier[] identifiers = new AbstractIdentifier[]{new Node()};
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
            Arrays.stream(identifiers).forEach(i ->{ i.setIdentified(i.Identify()); i.Process();});
            System.out.println("Executed in: " + (System.currentTimeMillis() - startTime));
            Archive.write(new File("test.jar"), classes);

            ClassNode A = classes.get("a");
            MethodNode f = A.getMethod("f","(I)Z");
            System.out.println(new BlockAssembler(f).getBlocks().size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ConcurrentHashMap<String, ClassNode> getClasses() {
        return classes;
    }
}

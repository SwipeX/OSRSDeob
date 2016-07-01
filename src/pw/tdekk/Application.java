package pw.tdekk;


import org.objectweb.asm.Label;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;
import pw.tdekk.deob.BasicBlock;
import pw.tdekk.deob.Mutator;
import pw.tdekk.deob.UnusedMethods;
import pw.tdekk.deob.UnusedParameters;
import pw.tdekk.util.Archive;
import pw.tdekk.util.Crawler;

import java.io.File;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;
import java.util.jar.JarFile;

import static org.objectweb.asm.Opcodes.INVOKEVIRTUAL;


/**
 * Created by TimD on 6/16/2016.
 */
public class Application {
    private static JarFile OSRS;
    private static Mutator[] mutators = new Mutator[]{new UnusedMethods(), new UnusedParameters()};
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
            System.out.println("Executed in: " + (System.currentTimeMillis() - startTime));
            BasicBlock.getBlocks(classes.get("a").getMethod("f","(I)Z"));
            Archive.write(new File("test.jar"), classes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ConcurrentHashMap<String, ClassNode> getClasses() {
        return classes;
    }
}

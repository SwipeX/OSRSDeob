package com.rs;

import EDU.purdue.cs.bloat.context.PersistentBloatContext;
import EDU.purdue.cs.bloat.file.ClassFile;
import EDU.purdue.cs.bloat.file.ClassFileLoader;
import EDU.purdue.cs.bloat.reflect.ClassInfo;
import com.rs.data.ClassData;
import com.rs.data.MethodData;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.zip.ZipFile;

/**
 * Created by TimD on 7/7/2016.
 */
public class Deobfuscator {
    private static ClassFileLoader loader;
    private static PersistentBloatContext context;
    private static HashMap<String, ClassData> classes = new HashMap<>();

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        loader = new ClassFileLoader();
        context = new PersistentBloatContext(loader);
        ClassInfo[] infoArray = loader.loadClassesFromZipFile(new ZipFile("./os_pack.jar"));

        Arrays.stream(infoArray).forEach(info -> {
            if (info == null || info.name().equals(null))
                return;
            classes.put(info.name(), new ClassData(info, context.editClass(info)));
        });
        loader.setOutputDir(new File("./rsout/"));
        write(new File("bloat.jar"));
    }

    public static void write(File target) {
        HashMap<String, byte[]> processed = new HashMap<>();
        //make the following threaded to account for slowness of bloat.
        //Check assertEquals
        try (JarOutputStream output = new JarOutputStream(new FileOutputStream(target))) {
            for (Map.Entry<String, ClassData> entry : classes.entrySet()) {
                output.putNextEntry(new JarEntry(entry.getKey().replaceAll("\\.", "/") + ".class"));
                ClassData data = entry.getValue();
                for (MethodData m : data.methods()) {
                    m.releaseGraph();
                    m.editor().commit();
                }
                data.editor.commit();
                data.info.commit();
                output.write(((ClassFile) data.editor.classInfo()).commitToBytes());
                output.closeEntry();
            }
            output.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

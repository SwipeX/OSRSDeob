package com.rs.data;

import EDU.purdue.cs.bloat.editor.ClassEditor;
import EDU.purdue.cs.bloat.reflect.ClassInfo;
import EDU.purdue.cs.bloat.reflect.FieldInfo;
import EDU.purdue.cs.bloat.reflect.MethodInfo;
import com.rs.visitor.AbstractVisitor;

public class ClassData {
    public final ClassInfo info;
    public final ClassEditor editor;

    private FieldData[] fields;
    private MethodData[] methods;

    public ClassData(ClassInfo info, ClassEditor editor) {
        this.info = info;
        this.editor = editor;

        fields = new FieldData[info.fields().length];
        for (int i = 0; i < fields.length; i++) {
            FieldInfo fieldInfo = info.fields()[i];
            fields[i] = new FieldData(this, fieldInfo, editor.context().editField(fieldInfo));
        }

        methods = new MethodData[info.methods().length];
        for (int i = 0; i < methods.length; i++) {
            MethodInfo methodInfo = info.methods()[i];
            methods[i] = new MethodData(this, methodInfo, editor.context().editMethod(methodInfo));
        }
    }

    public String name() {
        return info.name();
    }

    public FieldData[] fields() {
        return fields;
    }

    public MethodData[] methods() {
        return methods;
    }

    public void accept(AbstractVisitor visitor) {
        visitor.visitClass(this);
        for (MethodData method : methods) {
            method.accept(visitor);
        }
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof ClassData) {
            ClassData n2 = (ClassData) object;
            if (n2.name().equals(name())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return name().hashCode();
    }
}
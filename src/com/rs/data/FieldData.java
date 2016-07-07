package com.rs.data;

import EDU.purdue.cs.bloat.editor.FieldEditor;
import EDU.purdue.cs.bloat.reflect.FieldInfo;

public class FieldData {
	public final ClassData owner;
	public final FieldInfo info;
	public final FieldEditor editor;
	
	public FieldData(ClassData owner, FieldInfo info, FieldEditor editor) {
		this.owner = owner;
		this.info = info;
		this.editor = editor;
	}
	
	public String name() {
		return editor.name();
	}
	
	public String signature() {
		return editor.nameAndType().type().descriptor();
	}
	
	@Override
	public String toString() {
		return owner.name() + "." + name() + signature();
	}
	
	@Override
	public boolean equals(Object object) {
		if(object instanceof FieldData) {
			FieldData n2 = (FieldData) object;
			if(n2.owner.equals(owner) && n2.name().equals(name()) && n2.signature().equals(signature())) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return (name().hashCode() & 0xffff) << 16 | signature().hashCode(); 
	}
}
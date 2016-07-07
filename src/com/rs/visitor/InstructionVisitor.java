package com.rs.visitor;

import java.util.List;

import EDU.purdue.cs.bloat.editor.Instruction;
import EDU.purdue.cs.bloat.editor.Label;
import EDU.purdue.cs.bloat.editor.MethodEditor;
import com.rs.data.ClassData;
import com.rs.data.MethodData;


public abstract class InstructionVisitor extends AbstractVisitor {
	protected Instruction next(MethodData m, Instruction insn) {
		MethodEditor editor = m.editor();

		List code = editor.code();
		int index = code.indexOf(insn);
		int offset = 1;

		Object e = null;
		while((code.size() > (index + offset)) && !((e = code.get(index + offset++)) instanceof Instruction) && e != null);
		return e instanceof Instruction ? ((Instruction) e) : null;
	}
	
	protected Instruction prev(MethodData m, Instruction insn) {
		MethodEditor editor = m.editor();
		
		@SuppressWarnings("rawtypes")
		List code = editor.code();
		int index = code.indexOf(insn);
		int offset = 1;

		Object e = null;
		while((index - offset) >= 0 && !((e = code.get(index - offset++)) instanceof Instruction) && e != null);
		return e instanceof Instruction ? ((Instruction) e) : null;
	}
	
	public void visitInsn(ClassData c, MethodData m, Instruction insn) {
	}
	
	public void visitLabel(ClassData c, MethodData m, Label label) {
	}
	
	public void visitLoadInsn(ClassData c, MethodData m, Instruction insn) {
	}
	
	public void visitStoreInsn(ClassData c, MethodData m, Instruction insn) {
	}
	
	public void visitIncInsn(ClassData c, MethodData m, Instruction insn) {
	}
	
	public void visitThrowInsn(ClassData c, MethodData m, Instruction insn) {
	}
	
	public void visitInvokeInsn(ClassData c, MethodData m, Instruction insn) {
	}
	
	public void visitRetInsn(ClassData c, MethodData m, Instruction insn) {
	}
	
	public void visitReturnInsn(ClassData c, MethodData m, Instruction insn) {
	}
	
	public void visitSwitchInsn(ClassData c, MethodData m, Instruction insn) {
	}
	
	public void visitJumpInsn(ClassData c, MethodData m, Instruction insn) {
	}
	
	public void visitJsrInsn(ClassData c, MethodData m, Instruction insn) {
	}
}
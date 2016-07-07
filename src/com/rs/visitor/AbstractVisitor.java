package com.rs.visitor;

import com.rs.data.ClassData;
import com.rs.data.MethodData;

public abstract class AbstractVisitor {
	public void onStart() {
	}
	
	public void visitClass(ClassData c) {
	}
	
	public void visitMethod(ClassData c, MethodData m) {
	}
	
	public void onFinish() {
	}
}
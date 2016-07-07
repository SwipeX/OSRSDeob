package com.rs.data;

import java.util.ArrayList;

import EDU.purdue.cs.bloat.cfg.FlowGraph;
import EDU.purdue.cs.bloat.codegen.CodeGenerator;
import EDU.purdue.cs.bloat.editor.*;
import EDU.purdue.cs.bloat.reflect.MethodInfo;
import EDU.purdue.cs.bloat.tree.*;
import com.rs.visitor.InstructionVisitor;
import com.rs.visitor.AbstractVisitor;
import com.rs.visitor.TreeVisitor;


public class MethodData {
	public final ClassData owner;
	public final MethodInfo info;
	
	private final MethodEditor editor;
	private FlowGraph graph = null;
	
	private boolean editorInUse = false;
	private boolean graphInUse = false;
	
	public MethodData(ClassData owner, MethodInfo info, MethodEditor editor) {
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
	
	public MethodEditor editor() {
		if(graphInUse) {
			throw new IllegalStateException("Cannot use editor while a flow-graph is in use!");
		}
		editorInUse = true;
		return editor;
	}
	
	public void releaseEditor() {
		editorInUse = false;
	}
	
	public FlowGraph graph() {
		if(editorInUse) {
			throw new IllegalStateException("Cannot use flow-graph while a editor is in use!");
		}
		graphInUse = true;
		if(graph == null) {
			graph = new FlowGraph(editor);
		}
		return graph;
	}
	
	public void releaseGraph() {
		if(graphInUse) {
			CodeGenerator codegen = new CodeGenerator(editor);
			
			codegen.replacePhis(graph);
			codegen.simplifyControlFlow(graph);
			
			editor.clearCode();
			graph.visit(codegen);

			graph = null;
			graphInUse = false;
		}
	}
	public void accept(AbstractVisitor visitor) {
		releaseEditor();
		if(visitor instanceof InstructionVisitor) {
			visitor.visitMethod(owner, this);
			
			InstructionVisitor insnVisitor = (InstructionVisitor) visitor;
			
			MethodEditor editor = editor();
			for(Object n : new ArrayList(editor.code())) {
				if(n instanceof Instruction) {
					Instruction i = (Instruction) n;
					
					insnVisitor.visitInsn(owner, this, i);
					
					if(i.isLoad()) {
						insnVisitor.visitLoadInsn(owner, this, i);
					} else if(i.isStore()) {
						insnVisitor.visitStoreInsn(owner, this, i);
					} else if(i.isInc()) {
						insnVisitor.visitIncInsn(owner, this, i);
					} else if(i.isThrow()){
						insnVisitor.visitThrowInsn(owner, this, i);
					} else if(i.isInvoke()) {
						insnVisitor.visitInvokeInsn(owner, this, i);
					} else if(i.isRet()) {
						insnVisitor.visitRetInsn(owner, this, i);
					} else if(i.isReturn()) {
						insnVisitor.visitReturnInsn(owner, this, i);
					} else if(i.isSwitch()) {
						insnVisitor.visitSwitchInsn(owner, this, i);
					} else if(i.isJump()) {
						insnVisitor.visitJumpInsn(owner, this, i);
					} else if(i.isJsr()) {
						insnVisitor.visitJsrInsn(owner, this, i);
					}
				} else if(n instanceof Label) {
					insnVisitor.visitLabel(owner, this, (Label) n);
				}
			}

		} else if(visitor instanceof TreeVisitor) {
			visitor.visitMethod(owner, this);
			
			final TreeVisitor v = (TreeVisitor) visitor;
			
			final MethodData m = this;
			FlowGraph cfg = graph();
			cfg.visit(new EDU.purdue.cs.bloat.tree.TreeVisitor() {
				@Override
				public void visitExprStmt(final ExprStmt stmt) {
					visitStmt(stmt);
					v.visitExprStmt(owner, m, stmt);
				}
				
				@Override
				public void visitIfStmt(final IfStmt stmt) {
					visitStmt(stmt);
					v.visitIfStmt(owner, m, stmt);
				}

				@Override
				public void visitIfCmpStmt(final IfCmpStmt stmt) {
					visitIfStmt(stmt);
					v.visitIfCmpStmt(owner, m, stmt);
				}

				@Override
				public void visitIfZeroStmt(final IfZeroStmt stmt) {
					visitIfStmt(stmt);
					v.visitIfZeroStmt(owner, m, stmt);
				}

				@Override
				public void visitInitStmt(final InitStmt stmt) {
					visitStmt(stmt);
					v.visitInitStmt(owner, m, stmt);
				}

				@Override
				public void visitGotoStmt(final GotoStmt stmt) {
					visitStmt(stmt);
					v.visitGotoStmt(owner, m, stmt);
				}

				@Override
				public void visitLabelStmt(final LabelStmt stmt) {
					visitStmt(stmt);
					v.visitLabelStmt(owner, m, stmt);
				}

				@Override
				public void visitMonitorStmt(final MonitorStmt stmt) {
					visitStmt(stmt);
					v.visitMonitorStmt(owner, m, stmt);
				}

				@Override
				public void visitPhiStmt(final PhiStmt stmt) {
					visitStmt(stmt);
					v.visitPhiStmt(owner, m, stmt);
				}

				@Override
				public void visitCatchExpr(final CatchExpr expr) {
					visitExpr(expr);
					v.visitCatchExpr(owner, m, expr);
				}

				@Override
				public void visitDefExpr(final DefExpr expr) {
					visitExpr(expr);
					v.visitDefExpr(owner, m, expr);
				}

				@Override
				public void visitStackManipStmt(final StackManipStmt stmt) {
					visitStmt(stmt);
					v.visitStackManipStmt(owner, m, stmt);
				}

				@Override
				public void visitPhiCatchStmt(final PhiCatchStmt stmt) {
					visitPhiStmt(stmt);
					v.visitPhiCatchStmt(owner, m, stmt);
				}

				@Override
				public void visitPhiJoinStmt(final PhiJoinStmt stmt) {
					visitPhiStmt(stmt);
					v.visitPhiJoinStmt(owner, m, stmt);
				}

				@Override
				public void visitRetStmt(final RetStmt stmt) {
					visitStmt(stmt);
					v.visitRetStmt(owner, m, stmt);
				}

				@Override
				public void visitReturnExprStmt(final ReturnExprStmt stmt) {
					visitStmt(stmt);
					v.visitReturnExprStmt(owner, m, stmt);
				}

				@Override
				public void visitReturnStmt(final ReturnStmt stmt) {
					visitStmt(stmt);
					v.visitReturnStmt(owner, m, stmt);
				}

				@Override
				public void visitAddressStoreStmt(final AddressStoreStmt stmt) {
					visitStmt(stmt);
					v.visitAddressStoreStmt(owner, m, stmt);
				}

				@Override
				public void visitStoreExpr(final StoreExpr expr) {
					visitExpr(expr);
					v.visitStoreExpr(owner, m, expr);
				}

				@Override
				public void visitJsrStmt(final JsrStmt stmt) {
					visitStmt(stmt);
					v.visitJsrStmt(owner, m, stmt);
				}

				@Override
				public void visitSwitchStmt(final SwitchStmt stmt) {
					visitStmt(stmt);
					v.visitSwitchStmt(owner, m, stmt);
				}

				@Override
				public void visitThrowStmt(final ThrowStmt stmt) {
					visitStmt(stmt);
					v.visitThrowStmt(owner, m, stmt);
				}

				@Override
				public void visitStmt(final Stmt stmt) {
					v.visitStmt(owner, m, stmt);
					stmt.visitChildren(this);
				}

				@Override
				public void visitSCStmt(final SCStmt stmt) {
					visitStmt(stmt);
					v.visitSCStmt(owner, m, stmt);
				}

				@Override
				public void visitSRStmt(final SRStmt stmt) {
					visitStmt(stmt);
					v.visitSRStmt(owner, m, stmt);
				}

				@Override
				public void visitArithExpr(final ArithExpr expr) {
					visitExpr(expr);
					v.visitArithExpr(owner, m, expr);
				}

				@Override
				public void visitArrayLengthExpr(final ArrayLengthExpr expr) {
					visitExpr(expr);
					v.visitArrayLengthExpr(owner, m, expr);
				}

				@Override
				public void visitMemExpr(final MemExpr expr) {
					visitDefExpr(expr);
					v.visitMemExpr(owner, m, expr);
				}

				@Override
				public void visitMemRefExpr(final MemRefExpr expr) {
					visitMemExpr(expr);
					v.visitMemRefExpr(owner, m, expr);
				}

				@Override
				public void visitArrayRefExpr(final ArrayRefExpr expr) {
					visitMemRefExpr(expr);
					v.visitArrayRefExpr(owner, m, expr);
				}

				@Override
				public void visitCallExpr(final CallExpr expr) {
					visitExpr(expr);
					v.visitCallExpr(owner, m, expr);
				}

				@Override
				public void visitCallMethodExpr(final CallMethodExpr expr) {
					visitCallExpr(expr);
					v.visitCallMethodExpr(owner, m, expr);
				}

				@Override
				public void visitCallStaticExpr(final CallStaticExpr expr) {
					visitCallExpr(expr);
					v.visitCallStaticExpr(owner, m, expr);
				}

				@Override
				public void visitCastExpr(final CastExpr expr) {
					visitExpr(expr);
					v.visitCastExpr(owner, m, expr);
				}

				@Override
				public void visitConstantExpr(final ConstantExpr expr) {
					visitExpr(expr);
					v.visitConstantExpr(owner, m, expr);
				}

				@Override
				public void visitFieldExpr(final FieldExpr expr) {
					visitMemRefExpr(expr);
					v.visitFieldExpr(owner, m, expr);
				}

				@Override
				public void visitInstanceOfExpr(final InstanceOfExpr expr) {
					visitExpr(expr);
					v.visitInstanceOfExpr(owner, m, expr);
				}

				@Override
				public void visitLocalExpr(final LocalExpr expr) {
					visitVarExpr(expr);
					v.visitLocalExpr(owner, m, expr);
				}

				@Override
				public void visitNegExpr(final NegExpr expr) {
					visitExpr(expr);
					v.visitNegExpr(owner, m, expr);
				}

				@Override
				public void visitNewArrayExpr(final NewArrayExpr expr) {
					visitExpr(expr);
					v.visitNewArrayExpr(owner, m, expr);
				}

				@Override
				public void visitNewExpr(final NewExpr expr) {
					visitExpr(expr);
					v.visitNewExpr(owner, m, expr);
				}

				@Override
				public void visitNewMultiArrayExpr(final NewMultiArrayExpr expr) {
					visitExpr(expr);
					v.visitNewMultiArrayExpr(owner, m, expr);
				}

				@Override
				public void visitCheckExpr(final CheckExpr expr) {
					visitExpr(expr);
					v.visitCheckExpr(owner, m, expr);
				}
				
				@Override
				public void visitZeroCheckExpr(final ZeroCheckExpr expr) {
					visitCheckExpr(expr);
					v.visitZeroCheckExpr(owner, m, expr);
				}

				@Override
				public void visitRCExpr(final RCExpr expr) {
					visitCheckExpr(expr);
					v.visitRCExpr(owner, m, expr);
				}

				@Override
				public void visitUCExpr(final UCExpr expr) {
					visitCheckExpr(expr);
					v.visitUCExpr(owner, m, expr);
				}

				@Override
				public void visitReturnAddressExpr(final ReturnAddressExpr expr) {
					visitExpr(expr);
					v.visitReturnAddressExpr(owner, m, expr);
				}

				@Override
				public void visitShiftExpr(final ShiftExpr expr) {
					visitExpr(expr);
					v.visitShiftExpr(owner, m, expr);
				}

				@Override
				public void visitStackExpr(final StackExpr expr) {
					visitVarExpr(expr);
					v.visitStackExpr(owner, m, expr);
				}

				@Override
				public void visitVarExpr(final VarExpr expr) {
					visitMemExpr(expr);
					v.visitVarExpr(owner, m, expr);
				}

				@Override
				public void visitStaticFieldExpr(final StaticFieldExpr expr) {
					visitMemRefExpr(expr);
					v.visitStaticFieldExpr(owner, m, expr);
				}

				@Override
				public void visitExpr(final Expr expr) {
					v.visitExpr(owner, m, expr);
					expr.visitChildren(this);
				}
			});
		} else {
			visitor.visitMethod(owner, this);
		}
	}
	
	@Override
	public String toString() {
		return owner.name() + "." + name() + signature();
	}
	
	@Override
	public boolean equals(Object object) {
		if(object instanceof MethodData) {
			MethodData n2 = (MethodData) object;
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
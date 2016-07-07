package com.rs.visitor;

import EDU.purdue.cs.bloat.tree.AddressStoreStmt;
import EDU.purdue.cs.bloat.tree.ArithExpr;
import EDU.purdue.cs.bloat.tree.ArrayLengthExpr;
import EDU.purdue.cs.bloat.tree.ArrayRefExpr;
import EDU.purdue.cs.bloat.tree.CallExpr;
import EDU.purdue.cs.bloat.tree.CallMethodExpr;
import EDU.purdue.cs.bloat.tree.CallStaticExpr;
import EDU.purdue.cs.bloat.tree.CastExpr;
import EDU.purdue.cs.bloat.tree.CatchExpr;
import EDU.purdue.cs.bloat.tree.CheckExpr;
import EDU.purdue.cs.bloat.tree.ConstantExpr;
import EDU.purdue.cs.bloat.tree.DefExpr;
import EDU.purdue.cs.bloat.tree.Expr;
import EDU.purdue.cs.bloat.tree.ExprStmt;
import EDU.purdue.cs.bloat.tree.FieldExpr;
import EDU.purdue.cs.bloat.tree.GotoStmt;
import EDU.purdue.cs.bloat.tree.IfCmpStmt;
import EDU.purdue.cs.bloat.tree.IfStmt;
import EDU.purdue.cs.bloat.tree.IfZeroStmt;
import EDU.purdue.cs.bloat.tree.InitStmt;
import EDU.purdue.cs.bloat.tree.InstanceOfExpr;
import EDU.purdue.cs.bloat.tree.JsrStmt;
import EDU.purdue.cs.bloat.tree.LabelStmt;
import EDU.purdue.cs.bloat.tree.LocalExpr;
import EDU.purdue.cs.bloat.tree.MemExpr;
import EDU.purdue.cs.bloat.tree.MemRefExpr;
import EDU.purdue.cs.bloat.tree.MonitorStmt;
import EDU.purdue.cs.bloat.tree.NegExpr;
import EDU.purdue.cs.bloat.tree.NewArrayExpr;
import EDU.purdue.cs.bloat.tree.NewExpr;
import EDU.purdue.cs.bloat.tree.NewMultiArrayExpr;
import EDU.purdue.cs.bloat.tree.PhiCatchStmt;
import EDU.purdue.cs.bloat.tree.PhiJoinStmt;
import EDU.purdue.cs.bloat.tree.PhiStmt;
import EDU.purdue.cs.bloat.tree.RCExpr;
import EDU.purdue.cs.bloat.tree.RetStmt;
import EDU.purdue.cs.bloat.tree.ReturnAddressExpr;
import EDU.purdue.cs.bloat.tree.ReturnExprStmt;
import EDU.purdue.cs.bloat.tree.ReturnStmt;
import EDU.purdue.cs.bloat.tree.SCStmt;
import EDU.purdue.cs.bloat.tree.SRStmt;
import EDU.purdue.cs.bloat.tree.ShiftExpr;
import EDU.purdue.cs.bloat.tree.StackExpr;
import EDU.purdue.cs.bloat.tree.StackManipStmt;
import EDU.purdue.cs.bloat.tree.StaticFieldExpr;
import EDU.purdue.cs.bloat.tree.Stmt;
import EDU.purdue.cs.bloat.tree.StoreExpr;
import EDU.purdue.cs.bloat.tree.SwitchStmt;
import EDU.purdue.cs.bloat.tree.ThrowStmt;
import EDU.purdue.cs.bloat.tree.UCExpr;
import EDU.purdue.cs.bloat.tree.VarExpr;
import EDU.purdue.cs.bloat.tree.ZeroCheckExpr;
import com.rs.data.ClassData;
import com.rs.data.MethodData;


public class TreeVisitor extends AbstractVisitor {
	public void visitExprStmt(ClassData c, MethodData m, ExprStmt stmt) {
	}

	public void visitIfStmt(ClassData c, MethodData m, IfStmt stmt) {
	}

	public void visitIfCmpStmt(ClassData c, MethodData m, IfCmpStmt stmt) {
	}

	public void visitIfZeroStmt(ClassData c, MethodData m, IfZeroStmt stmt) {
	}

	public void visitInitStmt(ClassData c, MethodData m, InitStmt stmt) {
	}

	public void visitGotoStmt(ClassData c, MethodData m, GotoStmt stmt) {
	}

	public void visitLabelStmt(ClassData c, MethodData m, LabelStmt stmt) {
	}

	public void visitMonitorStmt(ClassData c, MethodData m, MonitorStmt stmt) {
	}

	public void visitPhiStmt(ClassData c, MethodData m, PhiStmt stmt) {
	}

	public void visitCatchExpr(ClassData c, MethodData m, CatchExpr expr) {
	}

	public void visitDefExpr(ClassData c, MethodData m, DefExpr expr) {
	}

	public void visitStackManipStmt(ClassData c, MethodData m, StackManipStmt stmt) {
	}

	public void visitPhiCatchStmt(ClassData c, MethodData m, PhiCatchStmt stmt) {
	}

	public void visitPhiJoinStmt(ClassData c, MethodData m, PhiJoinStmt stmt) {
	}

	public void visitRetStmt(ClassData c, MethodData m, RetStmt stmt) {
	}

	public void visitReturnExprStmt(ClassData c, MethodData m, ReturnExprStmt stmt) {
	}

	public void visitReturnStmt(ClassData c, MethodData m, ReturnStmt stmt) {
	}

	public void visitAddressStoreStmt(ClassData c, MethodData m, AddressStoreStmt stmt) {
	}

	public void visitStoreExpr(ClassData c, MethodData m, StoreExpr expr) {
	}

	public void visitJsrStmt(ClassData c, MethodData m, JsrStmt stmt) {
	}

	public void visitSwitchStmt(ClassData c, MethodData m, SwitchStmt stmt) {
	}

	public void visitThrowStmt(ClassData c, MethodData m, ThrowStmt stmt) {
	}

	public void visitStmt(ClassData c, MethodData m, Stmt stmt) {
	}

	public void visitSCStmt(ClassData c, MethodData m, SCStmt stmt) {
	}

	public void visitSRStmt(ClassData c, MethodData m, SRStmt stmt) {
	}

	public void visitArithExpr(ClassData c, MethodData m, ArithExpr expr) {
	}

	public void visitArrayLengthExpr(ClassData c, MethodData m, ArrayLengthExpr expr) {
	}

	public void visitMemExpr(ClassData c, MethodData m, MemExpr expr) {
	}

	public void visitMemRefExpr(ClassData c, MethodData m, MemRefExpr expr) {
	}

	public void visitArrayRefExpr(ClassData c, MethodData m, ArrayRefExpr expr) {
	}

	public void visitCallExpr(ClassData c, MethodData m, CallExpr expr) {
	}

	public void visitCallMethodExpr(ClassData c, MethodData m, CallMethodExpr expr) {
	}

	public void visitCallStaticExpr(ClassData c, MethodData m, CallStaticExpr expr) {
	}

	public void visitCastExpr(ClassData c, MethodData m, CastExpr expr) {
	}

	public void visitConstantExpr(ClassData c, MethodData m, ConstantExpr expr) {
	}

	public void visitFieldExpr(ClassData c, MethodData m, FieldExpr expr) {
	}

	public void visitInstanceOfExpr(ClassData c, MethodData m, InstanceOfExpr expr) {
	}

	public void visitLocalExpr(ClassData c, MethodData m, LocalExpr expr) {
	}

	public void visitNegExpr(ClassData c, MethodData m, NegExpr expr) {
	}

	public void visitNewArrayExpr(ClassData c, MethodData m, NewArrayExpr expr) {
	}

	public void visitNewExpr(ClassData c, MethodData m, NewExpr expr) {
	}

	public void visitNewMultiArrayExpr(ClassData c, MethodData m, NewMultiArrayExpr expr) {
	}

	public void visitCheckExpr(ClassData c, MethodData m, CheckExpr expr) {
	}

	public void visitZeroCheckExpr(ClassData c, MethodData m, ZeroCheckExpr expr) {
	}

	public void visitRCExpr(ClassData c, MethodData m, RCExpr expr) {
	}

	public void visitUCExpr(ClassData c, MethodData m, UCExpr expr) {
	}

	public void visitReturnAddressExpr(ClassData c, MethodData m, ReturnAddressExpr expr) {
	}

	public void visitShiftExpr(ClassData c, MethodData m, ShiftExpr expr) {
	}

	public void visitStackExpr(ClassData c, MethodData m, StackExpr expr) {
	}

	public void visitVarExpr(ClassData c, MethodData m, VarExpr expr) {
	}

	public void visitStaticFieldExpr(ClassData c, MethodData m, StaticFieldExpr expr) {
	}

	public void visitExpr(ClassData c, MethodData m, Expr expr) {
	}

}
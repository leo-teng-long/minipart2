package mini;

import mini.analysis.*;
import mini.node.*;
import mini.AdapterUtility;
import mini.SymbolTable;
import java.io.*;

public class CodeEmitter extends DepthFirstAdapter {

  private PrintWriter out;

  public CodeEmitter() {
    /* Constructor */
    try {
      out = new PrintWriter(new FileWriter("a.c"));
    } catch (Exception ex) {
      System.out.println("Exception: failed to emit code.");
      System.exit(0);
    }
  }

  /* Program */
  public void inAProgramProg(AProgramProg node) {
    emit("IN_PROG", null);
  }

  public void outAProgramProg(AProgramProg node) {
    emit("OUT_PROG", null);
    out.close();
  }

  /* Declaration */
  public void outADeclareDecl(ADeclareDecl node) {
    String code = "";
    PType type = node.getType();

    if (AdapterUtility.isAIntType(type)) {
      code += "int ";
    } else if (AdapterUtility.isAFloatType(type)) {
      code += "float ";
    }
    code += node.getId().getText() + ";";

    emit("OUT_DECL", code);
  }

  /* Assignment */
  public void outAAssignStmt(AAssignStmt node) {
    String code = node.getId().getText() + " = ";
    code += buildExprCode(node.getExpr());
    code += ";";

    emit("OUT_ASSIGN", code);
  }

  /* While statement */
  public void inAWhileStmt(AWhileStmt node) {
    PExpr expr = node.getExpr();
    String code = "while (" + buildExprCode(expr) + ") {";

    emit("IN_WHILE", code);
  }

  public void outAWhileStmt(AWhileStmt node) {
    emit("OUT_WHILE", null);
  }

  /* Read statement */
  public void outAReadStmt(AReadStmt node) {
    String id = node.getId().getText();
    String code = "scanf(";

    if (AdapterUtility.isAIntType(SymbolTable.getVariableType(id))) {
      code += "\"%d\", &" + id;
    } else if (AdapterUtility.isAFloatType(SymbolTable.getVariableType(id))) {
      code += "\"%f\", &" + id;
    }
    code += ");";

    emit("OUT_READ", code);
  }

  /* Print statement */
  public void outAPrintStmt(APrintStmt node) {
    PExpr expr = node.getExpr();
    String code = "printf(";

    if (AdapterUtility.isAIdExpr(expr)) {
      String id = ((AIdExpr) expr).getId().getText();
      if (AdapterUtility.isExprTypeInt(expr)) {
        code += "\"%d\\n\", " + id;
      } else if (AdapterUtility.isExprTypeFloat(expr)) {
        code += "\"%f\\n\", " + id;
      }
    }
    code += ");";

    emit("OUT_PRINT", code);
  }

  /**********************************************
  * Private methods *****************************
  **********************************************/

  private void emit(String type, String code) {
    switch (type) {
      case "IN_PROG":
        out.println("#include <stdio.h>");
        out.println("int main() {");
        break;
      case "OUT_PROG":
        out.println("return 0;");
        out.println("}");
        break;
      case "OUT_DECL":
        out.println(code);
        break;
      case "OUT_ASSIGN":
        out.println(code);
        break;
      case "IN_WHILE":
        out.println(code);
        break;
      case "OUT_WHILE":
        out.println("}");
        break;
      case "OUT_READ":
        out.println(code);
        break;
      case "OUT_PRINT":
        out.println(code);
        break;
      default:
        break;
    }
  }

  private String buildExprCode(PExpr expr) {
    /* Plus, minus, times and divide expressions */
    if (AdapterUtility.isAPlusExpr(expr) || AdapterUtility.isAMinusExpr(expr) || AdapterUtility.isATimesExpr(expr) || AdapterUtility.isADivideExpr(expr)) {
      PExpr left;
      PExpr right;
      char operator;

      if (AdapterUtility.isAPlusExpr(expr)) {
        /* Plus operator */
        left = ((APlusExpr) expr).getLeft();
        right = ((APlusExpr) expr).getRight();
        operator = '+';
      } else if (AdapterUtility.isAMinusExpr(expr)) {
        /* Minus operator */
        left = ((AMinusExpr) expr).getLeft();
        right = ((AMinusExpr) expr).getRight();
        operator = '-';
      } else if (AdapterUtility.isATimesExpr(expr)) {
        /* Times operator */
        left = ((ATimesExpr) expr).getLeft();
        right = ((ATimesExpr) expr).getRight();
        operator = '*';
      } else {
        left = ((ADivideExpr) expr).getLeft();
        right = ((ADivideExpr) expr).getRight();
        operator = '/';
      }

      return '(' + buildExprCode(left) + operator + buildExprCode(right) + ')';
    }

    if (AdapterUtility.isAUnaryExpr(expr)) {

    }

    /* Base cases: id, int, float or string */
    return " " + expr.toString();
  }

}

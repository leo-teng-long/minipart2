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
    String id = node.getId().getText();
    PType type = node.getType();

    String code = "";
    if (AdapterUtility.isAIntType(type)) {
      /* Declare int var */
      code += "int " + id + ";";
    } else if (AdapterUtility.isAFloatType(type)) {
      /* Declare float var */
      code += "float " + id + ";";
    } else {
      /* Declare string var */
      // ......
    }

    emit("OUT_DECL", code);
  }

  /* Assignment */
  public void outAAssignStmt(AAssignStmt node) {
    String id = node.getId().getText();
    PExpr expr = node.getExpr();

    String code = id + " = ";
    if (AdapterUtility.isExprTypeString(expr)) {
      /* Assign to string var */
      // ......
    } else {
      /* Assign to int/float var */
      code += buildNumericalExprCode(expr) + ";";
    }

    emit("OUT_ASSIGN", code);
  }

  /* If statement */
  public void inAIfStmt(AIfStmt node) {
    PExpr expr = node.getExpr();
    String code = "if (" + buildNumericalExprCode(expr) + ") {";

    emit("IN_IF", code);
  }

  public void outAIfStmt(AIfStmt node) {
    emit("OUT_IF", null);
  }

  /* If-else statement */
  public void inAIfelseStmt(AIfelseStmt node) {
    // ......

    emit("IN_IFELSE", null);
  }

  public void outAIfelseStmt(AIfelseStmt node) {
    // ......

    emit("OUT_IFELSE", null);
  }

  /* While statement */
  public void inAWhileStmt(AWhileStmt node) {
    PExpr expr = node.getExpr();
    String code = "while (" + buildNumericalExprCode(expr) + ") {";

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
      /* Read int from stdin */
      code += "\"%d\", &" + id + ");";
    } else if (AdapterUtility.isAFloatType(SymbolTable.getVariableType(id))) {
      /* Read float from stdin */
      code += "\"%f\", &" + id + ");";
    } else {
      /* Read string from stdin */
      // ......
    }

    emit("OUT_READ", code);
  }

  /* Print statement */
  public void outAPrintStmt(APrintStmt node) {
    PExpr expr = node.getExpr();
    PExpr exprType = AdapterUtility.getExprType(expr);

    String code = "printf(";
    if (AdapterUtility.isExprTypeInt(exprType)) {
      /* Print int to stdout */
      code += "\"%d\\n\", " + buildNumericalExprCode(expr) + ");";
    } else if (AdapterUtility.isExprTypeFloat(exprType)) {
      /* Print float to stdout */
      code += "\"%f\\n\", " + buildNumericalExprCode(expr) + ");";
    } else {
      /* Print string to stdout */
      // ......
    }

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
      case "IN_IF":
        out.println(code);
        break;
      case "OUT_IF":
        out.println("}");
        break;
      case "IN_IFELSE":
        // ......
        break;
      case "OUT_IFELSE":
        // ......
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

  private String buildNumericalExprCode(PExpr expr) {
    /* Plus, minus, times and divide expressions */
    if (AdapterUtility.isAPlusExpr(expr) || AdapterUtility.isAMinusExpr(expr) || AdapterUtility.isATimesExpr(expr) || AdapterUtility.isADivideExpr(expr)) {
      PExpr left;
      PExpr right;
      String operator;

      if (AdapterUtility.isAPlusExpr(expr)) {
        /* Plus operator */
        left = ((APlusExpr) expr).getLeft();
        right = ((APlusExpr) expr).getRight();
        operator = "+";
      } else if (AdapterUtility.isAMinusExpr(expr)) {
        /* Minus operator */
        left = ((AMinusExpr) expr).getLeft();
        right = ((AMinusExpr) expr).getRight();
        operator = "-";
      } else if (AdapterUtility.isATimesExpr(expr)) {
        /* Times operator */
        left = ((ATimesExpr) expr).getLeft();
        right = ((ATimesExpr) expr).getRight();
        operator = "*";
      } else {
        left = ((ADivideExpr) expr).getLeft();
        right = ((ADivideExpr) expr).getRight();
        operator = "/";
      }

      return "(" + buildNumericalExprCode(left) + operator + buildNumericalExprCode(right) + ")";
    }

    /* Unary minus */
    if (AdapterUtility.isAUnaryExpr(expr)) {
      AUnaryExpr unary = (AUnaryExpr) expr;
      return "-" + buildNumericalExprCode(unary.getExpr());
    }

    /* Base cases: id, int, float or string */
    return " " + expr.toString();
  }

  private String buildStringExprCode(PExpr expr) {
    /* Plus and Minus string operations */
    if (AdapterUtility.isAPlusExpr(expr) || AdapterUtility.isAMinusExpr(expr)) {
      PExpr left;
      PExpr right;

      if (AdapterUtility.isAPlusExpr(expr)) {
        left = ((APlusExpr) expr).getLeft();
        right = ((APlusExpr) expr).getRight();

        // ......
      } else {
        left = ((AMinusExpr) expr).getLeft();
        right = ((AMinusExpr) expr).getRight();

        // ......
      }
    }

    /* Reverse string */
    if (AdapterUtility.isAUnaryExpr(expr)) {
      AUnaryExpr unary = (AUnaryExpr) expr;

      // ......
    }

    /* string literals or variables */
    return null;
  }

}

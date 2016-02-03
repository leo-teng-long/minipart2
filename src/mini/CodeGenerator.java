package mini;

import mini.analysis.*;
import mini.node.*;
import mini.AdapterUtility;
import mini.SymbolTable;
import java.io.*;

public class CodeGenerator extends DepthFirstAdapter {

  private PrintWriter out;
  private int tabs;

  public CodeGenerator() {
    /* Constructor */
    try {
      out = new PrintWriter(new FileWriter("a.c"));
      tabs = 0;
    } catch (Exception ex) {
      System.out.println("Exception: failed to emit code.");
      System.exit(0);
    }
  }

  /* Program */
  public void inAProgramProg(AProgramProg node) {
    emit("IN_PROG", null);
    tabs++;
  }

  public void outAProgramProg(AProgramProg node) {
    tabs--;
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
      code += "int " + id + " = 0;";
    } else if (AdapterUtility.isAFloatType(type)) {
      /* Declare float var */
      code += "float " + id + " = 0.0;";
    } else {
      /* Declare string var */
      code += "char " + id + "[128]" + " = \"\";";
    }

    emit("OUT_DECL", code);
  }

  /* Assignment */
  public void outAAssignStmt(AAssignStmt node) {
    String id = node.getId().getText();
    PExpr expr = node.getExpr();
    PExpr exprType = AdapterUtility.getExprType(expr);

    String code = id + " = ";
    if (AdapterUtility.isExprTypeString(exprType)) {
      /* Assign to string var */
      code += buildStringExprCode(expr) + ";";
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
    tabs++;
  }

  public void outAIfStmt(AIfStmt node) {
    tabs--;
    emit("OUT_IF", null);
  }

  /* If-else statement */
  public void inAIfelseStmt(AIfelseStmt node) {
    PExpr expr = node.getExpr();
    String code = "if (" + buildNumericalExprCode(expr) + ") {";

    emit("IN_IFELSE", code);
    tabs++;
  }

  public void inAElseList(AElseList node) {
    tabs--;

    emit("IN_ELSE", null);
    tabs++;
  }

  public void outAIfelseStmt(AIfelseStmt node) {
    tabs--;
    emit("OUT_IFELSE", null);
  }

  /* While statement */
  public void inAWhileStmt(AWhileStmt node) {
    PExpr expr = node.getExpr();
    String code = "while (" + buildNumericalExprCode(expr) + ") {";

    emit("IN_WHILE", code);
    tabs++;
  }

  public void outAWhileStmt(AWhileStmt node) {
    tabs--;
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
      code += "\"%s\", " + id + ");";
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
      code += "\"%s\\n\", " + buildStringExprCode(expr) + ");";
    }

    emit("OUT_PRINT", code);
  }

  /**********************************************
  * Private methods *****************************
  **********************************************/

  private void emit(String type, String code) {
    switch (type) {
      case "IN_PROG":
        emitIncludes();
        emitPrototypes();
        emitBeginMain();
        break;
      case "OUT_PROG":
        emitEndMain();
        emitScatFunction();
        emitSrevFunction();
        break;
      case "OUT_DECL":
        addTabs();
        out.println(code);
        break;
      case "OUT_ASSIGN":
        addTabs();
        out.println(code);
        break;
      case "IN_IF":
        addTabs();
        out.println(code);
        break;
      case "OUT_IF":
        addTabs();
        addEndBracket();
        break;
      case "IN_IFELSE":
        addTabs();
        out.println(code);
        break;
      case "IN_ELSE":
        addTabs();
        emitElse();
        break;
      case "OUT_IFELSE":
        addTabs();
        addEndBracket();
        break;
      case "IN_WHILE":
        addTabs();
        out.println(code);
        break;
      case "OUT_WHILE":
        addTabs();
        addEndBracket();
        break;
      case "OUT_READ":
        addTabs();
        out.println(code);
        break;
      case "OUT_PRINT":
        addTabs();
        out.println(code);
        break;
      default:
        break;
    }
  }

  private void emitIncludes() {
    out.println("#include <stdio.h>");
    out.println("#include <stdlib.h>");
    out.println("#include <string.h>");
    out.println();
  }

  private void emitPrototypes() {
    out.println("char* scat(char* s1, char* s2);");
    out.println("char* srev(char* s);");
    out.println();
  }

  private void emitBeginMain() {
    out.println("int main() {");
  }

  private void emitEndMain() {
    out.println("\treturn 0;");
    out.println("}");
    out.println();
  }

  private void emitScatFunction() {
    out.println("char* scat(char* s1, char* s2) {");
    out.println("\tint l1 = strlen(s1);");
    out.println("\tint l2 = strlen(s2);");
    out.println("\tchar* n = malloc(l1 + l2 + 1);");
    out.println();
    out.println("\tint i = 0;");
    out.println("\twhile (i < l1) {");
    out.println("\t\tn[i] = s1[i];");
    out.println("\t\ti++;");
    out.println("\t}");
    out.println("\twhile (i < l1 + l2) {");
    out.println("\t\tn[i] = s2[i - l1];");
    out.println("\t\ti++;");
    out.println("\t}");
    out.println("\tn[i] = '\\0';");
    out.println();
    out.println("\treturn n;");
    out.println("}");
    out.println();
  }

  private void emitSrevFunction() {
    out.println("char* srev(char* s) {");
    out.println("\tint i = 0;");
    out.println("\tint j = strlen(s) - 1;");
    out.println();
    out.println("\twhile (i < j) {");
    out.println("\t\tchar temp = s[i];");
    out.println("\t\ts[i] = s[j];");
    out.println("\t\ts[j] = temp;");
    out.println("\t\ti++;");
    out.println("\t\tj--;");
    out.println("\t}");
    out.println();
    out.println("\treturn s;");
    out.println("}");
    out.println();
  }

  private void emitElse() {
    out.println("} else {");
  }

  private void addTabs() {
    for (int i = 0; i < tabs; i++) {
      out.print("\t");
    }
  }

  private void addEndBracket() {
    out.println("}");
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
        operator = " + ";
      } else if (AdapterUtility.isAMinusExpr(expr)) {
        /* Minus operator */
        left = ((AMinusExpr) expr).getLeft();
        right = ((AMinusExpr) expr).getRight();
        operator = " - ";
      } else if (AdapterUtility.isATimesExpr(expr)) {
        /* Times operator */
        left = ((ATimesExpr) expr).getLeft();
        right = ((ATimesExpr) expr).getRight();
        operator = " * ";
      } else {
        left = ((ADivideExpr) expr).getLeft();
        right = ((ADivideExpr) expr).getRight();
        operator = " / ";
      }

      return "(" + buildNumericalExprCode(left) + operator + buildNumericalExprCode(right) + ")";
    }

    /* Unary minus */
    if (AdapterUtility.isAUnaryExpr(expr)) {
      AUnaryExpr unary = (AUnaryExpr) expr;
      return "(" + "-" + buildNumericalExprCode(unary.getExpr()) + ")";
    }

    /* Base cases: id, int or float */
    if (AdapterUtility.isAIdExpr(expr)) {
      return ((AIdExpr) expr).getId().getText();
    } else if (AdapterUtility.isAIntExpr(expr)) {
      return ((AIntExpr) expr).getIntconst().getText();
    } else {
      return ((AFloatExpr) expr).getFloatconst().getText();
    }
  }

  private String buildStringExprCode(PExpr expr) {
    /* Plus and Minus string operations */
    if (AdapterUtility.isAPlusExpr(expr) || AdapterUtility.isAMinusExpr(expr)) {
      PExpr left;
      PExpr right;
      if (AdapterUtility.isAPlusExpr(expr)) {
        left = ((APlusExpr) expr).getLeft();
        right = ((APlusExpr) expr).getRight();
        return "scat(" + buildStringExprCode(left) + ", " + buildStringExprCode(right) + ")";
      } else {
        left = ((AMinusExpr) expr).getLeft();
        right = ((AMinusExpr) expr).getRight();
        return "scat(" + buildStringExprCode(left) + ", srev(" + buildStringExprCode(right) + ")" + ")";
      }
    }

    /* Reverse string */
    if (AdapterUtility.isAUnaryExpr(expr)) {
      AUnaryExpr unary = (AUnaryExpr) expr;
      return "srev(" + buildStringExprCode(unary.getExpr()) + ")";
    }

    /* string literals or variables */
    if (AdapterUtility.isAIdExpr(expr)) {
      return ((AIdExpr) expr).getId().getText();
    } else {
      return ((AStringExpr) expr).getStringconst().getText();
    }
  }

}

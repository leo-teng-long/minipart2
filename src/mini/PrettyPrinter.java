package mini;

import mini.analysis.*;
import mini.node.*;
import mini.AdapterUtility;
import mini.SymbolTable;
import java.io.*;

public class PrettyPrinter extends DepthFirstAdapter {

  private PrintWriter out;
  private String fileName;
  private int tabs;

  /* Constructor */
  public PrettyPrinter(String fileName) {
    this.fileName = fileName;
    tabs = 0;
  }

  /* Program */
  public void inAProgramProg(AProgramProg node) {
    try {
      out = new PrintWriter(new FileWriter(fileName + ".pretty.min"));
    } catch (Exception ex) {
      System.out.println("Exception: failed to pretty print.");
      System.exit(1);
    }
  }

  public void outAProgramProg(AProgramProg node) {
    out.close();
  }

  /* Declaration */
  public void outADeclareDecl(ADeclareDecl node) {
    String  id = node.getId().getText();
    PType type = node.getType();
    String str = "var " + id + " : ";
    if (AdapterUtility.isAIntType(type)) {
      /* Declare int */
      str += ((AIntType) type).getInt().getText() + ";";
    } else if (AdapterUtility.isAFloatType(type)) {
      /* Declare float */
      str += ((AFloatType) type).getFloat().getText() + ";";
    } else {
      /* Declare string */
      str += ((AStringType) type).getString().getText() + ";";
    }

    out.println(str);
  }

  /* Assignment */
  public void outAAssignStmt(AAssignStmt node) {
    addTabs();

    String id = node.getId().getText();
    PExpr expr = node.getExpr();

    String str = id + " = " + buildMinilangExpr(expr) + ";";
    out.println(str);
  }

  /* If statement */
  public void inAIfStmt(AIfStmt node) {
    addTabs();

    PExpr expr = node.getExpr();
    String str = "if " + buildMinilangExpr(expr) + " then";

    out.println(str);
    tabs++;
  }

  public void outAIfStmt(AIfStmt node) {
    tabs--;
    addTabs();

    String str = "endif";
    out.println(str);
  }

  /* If-else statement */
  public void inAIfelseStmt(AIfelseStmt node) {
    addTabs();

    PExpr expr = node.getExpr();
    String str = "if " + buildMinilangExpr(expr) + " then";

    out.println(str);
    tabs++;
  }

  public void inAElseList(AElseList node) {
    tabs--;
    addTabs();

    String str = "else";
    out.println(str);
    tabs++;
  }

  public void outAIfelseStmt(AIfelseStmt node) {
    tabs--;
    addTabs();

    String str = "endif";
    out.println(str);
  }

  /* While statement */
  public void inAWhileStmt(AWhileStmt node) {
    addTabs();

    PExpr expr = node.getExpr();
    String str = "while " + buildMinilangExpr(expr) + " do";

    out.println(str);
    tabs++;
  }

  public void outAWhileStmt(AWhileStmt node) {
    tabs--;
    addTabs();

    String str = "done";
    out.println(str);
  }

  /* Read statement */
  public void outAReadStmt(AReadStmt node) {
    addTabs();
    String id = node.getId().getText();

    String str = "read " + id + ";";
    out.println(str);
  }

  /* Print statement */
  public void outAPrintStmt(APrintStmt node) {
    addTabs();

    PExpr expr = node.getExpr();
    String str = "print " + buildMinilangExpr(expr) + ";";
    out.println(str);
  }

  /**********************************************
  * Private methods *****************************
  **********************************************/

  private String buildMinilangExpr(PExpr expr) {
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

      return "(" + buildMinilangExpr(left) + operator + buildMinilangExpr(right) + ")";
    }

    /* Unary minus */
    if (AdapterUtility.isAUnaryExpr(expr)) {
      AUnaryExpr unary = (AUnaryExpr) expr;
      return "(" + "-" + buildMinilangExpr(unary.getExpr()) + ")";
    }

    /* Base cases: id, int, float and string */
    if (AdapterUtility.isAIdExpr(expr)) {
      return ((AIdExpr) expr).getId().getText();
    } else if (AdapterUtility.isAIntExpr(expr)) {
      return ((AIntExpr) expr).getIntconst().getText();
    } else if (AdapterUtility.isAFloatExpr(expr)) {
      return ((AFloatExpr) expr).getFloatconst().getText();
    } else {
      return ((AStringExpr) expr).getStringconst().getText();
    }
  }

  private void addTabs() {
    for (int i = 0; i < tabs; i++) {
      out.print("\t");
    }
  }

}

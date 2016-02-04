package mini;

import mini.analysis.*;
import mini.node.*;
import mini.AdapterUtility;
import mini.SymbolTable;
import java.io.*;

public class TypeChecker extends DepthFirstAdapter {

  private String fileName;

  /* Constructor */
  public TypeChecker(String fileName) {
    this.fileName = fileName;
  }

  /* Program */
  public void outAProgramProg(AProgramProg node) {
    outputSymbolTable();
  }

  /* Declaration */
  public void outADeclareDecl(ADeclareDecl node) {
    TId id = node.getId();
    PType type = node.getType();
    String key = id.getText();

    if (!SymbolTable.declareVariable(key, type)) {
      System.out.println("Error: [" + id.getLine() + "," + id.getPos() + "] Identifier " + key + " already declared.");
      outputSymbolTable();
      System.exit(1);
    }
  }

  /* Assignment */
  public void outAAssignStmt(AAssignStmt node) {
    TId id = node.getId();
    String key = id.getText();

    PType type = SymbolTable.getVariableType(key);
    PExpr expr = AdapterUtility.getExprType(node.getExpr());
    /* int var */
    if (AdapterUtility.isAIntType(type) && !AdapterUtility.isExprTypeInt(expr)) {
      System.out.println("Error: [" + id.getLine() + "," + id.getPos() + "] Assigned type does not match identifier type.");
      outputSymbolTable();
      System.exit(1);
    }
    /* float var */
    if (AdapterUtility.isAFloatType(type) && !(AdapterUtility.isExprTypeInt(expr) || AdapterUtility.isExprTypeFloat(expr))) {
      System.out.println("Error: [" + id.getLine() + "," + id.getPos() + "] Assigned type does not match identifier type.");
      outputSymbolTable();
      System.exit(1);
    }
    /* string var */
    if (AdapterUtility.isAStringType(type) && !AdapterUtility.isExprTypeString(expr)) {
      System.out.println("Error: [" + id.getLine() + "," + id.getPos() + "] Assigned type does not match identifier type.");
      outputSymbolTable();
      System.exit(1);
    }
  }

  /* If statment */
  public void inAIfStmt(AIfStmt node) {
    PExpr expr = AdapterUtility.getExprType(node.getExpr());

    if (!AdapterUtility.isExprTypeInt(expr)) {
      System.out.println("Error: Condition does not evaluate to type int.");
      outputSymbolTable();
      System.exit(1);
    }
  }

  /* If-else statement */
  public void inAIfelseStmt(AIfelseStmt node) {
    PExpr expr = AdapterUtility.getExprType(node.getExpr());

    if (!AdapterUtility.isExprTypeInt(expr)) {
      System.out.println("Error: Condition does not evaluate to type int.");
      outputSymbolTable();
      System.exit(1);
    }
  }

  /* While statement */
  public void inAWhileStmt(AWhileStmt node) {
    PExpr expr = AdapterUtility.getExprType(node.getExpr());

    if (!AdapterUtility.isExprTypeInt(expr)) {
      System.out.println("Error: Condition does not evaluate to type int.");
      outputSymbolTable();
      System.exit(1);
    }
  }

  /* Plus expression */
  public void outAPlusExpr(APlusExpr node) {
    PExpr leftExpr = AdapterUtility.getExprType(node.getLeft());
    PExpr rightExpr = AdapterUtility.getExprType(node.getRight());

    if (AdapterUtility.isExprTypeString(leftExpr) && !AdapterUtility.isExprTypeString(rightExpr)) {
      System.out.println("Error: string | non-string addition not allowed.");
      outputSymbolTable();
      System.exit(1);
    }
    if (!AdapterUtility.isExprTypeString(leftExpr) && AdapterUtility.isExprTypeString(rightExpr)) {
      System.out.println("Error: string | non-string addition not allowed.");
      outputSymbolTable();
      System.exit(1);
    }
  }

  /* Minus expression */
  public void outAMinusExpr(AMinusExpr node) {
    PExpr leftExpr = AdapterUtility.getExprType(node.getLeft());
    PExpr rightExpr = AdapterUtility.getExprType(node.getRight());

    if (AdapterUtility.isExprTypeString(leftExpr) && !AdapterUtility.isExprTypeString(rightExpr)) {
      System.out.println("Error: string | non-string subtraction not allowed.");
      outputSymbolTable();
      System.exit(1);
    }
    if (!AdapterUtility.isExprTypeString(leftExpr) && AdapterUtility.isExprTypeString(rightExpr)) {
      System.out.println("Error: string | non-string subtraction not allowed.");
      outputSymbolTable();
      System.exit(1);
    }
  }

  /* Times expression */
  public void outATimesExpr(ATimesExpr node) {
    PExpr leftExpr = AdapterUtility.getExprType(node.getLeft());
    PExpr rightExpr = AdapterUtility.getExprType(node.getRight());

    if (AdapterUtility.isExprTypeString(leftExpr) || AdapterUtility.isExprTypeString(rightExpr)) {
      System.out.println("Error: * cannot be applied to string type.");
      outputSymbolTable();
      System.exit(1);
    }
  }

  /* Divide expression */
  public void outADivideExpr(ADivideExpr node) {
    PExpr leftExpr = AdapterUtility.getExprType(node.getLeft());
    PExpr rightExpr = AdapterUtility.getExprType(node.getRight());

    if (AdapterUtility.isExprTypeString(leftExpr) || AdapterUtility.isExprTypeString(rightExpr)) {
      System.out.println("Error: / cannot be applied to string type.");
      outputSymbolTable();
      System.exit(1);
    }
  }

  /* Id expression */
  public void outAIdExpr(AIdExpr node) {
    TId id = node.getId();
    String key = id.getText();

    if (!SymbolTable.containVariable(key)) {
      System.out.println("Error: [" + id.getLine() + "," + id.getPos() + "] Identifier " + key + " not declared.");
      outputSymbolTable();
      System.exit(1);
    }
  }

  /**********************************************
  * Private methods *****************************
  **********************************************/

  /* Output symbolTable to file */
  private void outputSymbolTable() {
    try  {
      PrintWriter out = new PrintWriter(new FileWriter(fileName + ".symbol.txt"));
      out.print(SymbolTable.getStringRepresentation());
      out.close();
    } catch (Exception ex) {
      System.out.println("Exception: failed to output symbol table.");
      System.exit(1);
    }
  }

}

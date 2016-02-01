package mini;

import mini.analysis.*;
import mini.node.*;
import mini.AdapterUtility;
import mini.SymbolTable;
import java.io.*;

public class TypeChecker extends DepthFirstAdapter {

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
      System.exit(0);
    }
  }

  /* Assignment */
  public void outAAssignStmt(AAssignStmt node) {
    TId id = node.getId();
    String key = id.getText();

    PType type = SymbolTable.getVariableType(key);
    PExpr expr = getExprType(node.getExpr());

    /* int var */
    if (AdapterUtility.isAIntType(type) && !AdapterUtility.isExprTypeInt(expr)) {
      System.out.println("Error: [" + id.getLine() + "," + id.getPos() + "] Assigned type does not match identifier type.");
      outputSymbolTable();
      System.exit(0);
    }
    /* float var */
    if (AdapterUtility.isAFloatType(type) && !(AdapterUtility.isExprTypeInt(expr) || AdapterUtility.isExprTypeFloat(expr))) {
      System.out.println("Error: [" + id.getLine() + "," + id.getPos() + "] Assigned type does not match identifier type.");
      outputSymbolTable();
      System.exit(0);
    }
    /* string var */
    if (AdapterUtility.isAStringType(type) && !AdapterUtility.isExprTypeString(expr)) {
      System.out.println("Error: [" + id.getLine() + "," + id.getPos() + "] Assigned type does not match identifier type.");
      outputSymbolTable();
      System.exit(0);
    }
  }

  /* If statment */
  public void inAIfStmt(AIfStmt node) {
    PExpr expr = getExprType(node.getExpr());

    if (!AdapterUtility.isExprTypeInt(expr)) {
      System.out.println("Error: Condition does not evaluate to type int.");
      outputSymbolTable();
      System.exit(0);
    }
  }

  /* If-else statement */
  public void inAIfelseStmt(AIfelseStmt node) {
    PExpr expr = getExprType(node.getExpr());

    if (!AdapterUtility.isExprTypeInt(expr)) {
      System.out.println("Error: Condition does not evaluate to type int.");
      outputSymbolTable();
      System.exit(0);
    }
  }

  /* While statement */
  public void inAWhileStmt(AWhileStmt node) {
    PExpr expr = getExprType(node.getExpr());

    if (!AdapterUtility.isExprTypeInt(expr)) {
      System.out.println("Error: Condition does not evaluate to type int.");
      outputSymbolTable();
      System.exit(0);
    }
  }

  /* Plus expression */
  public void outAPlusExpr(APlusExpr node) {
    PExpr leftExpr = getExprType(node.getLeft());
    PExpr rightExpr = getExprType(node.getRight());

    if (AdapterUtility.isExprTypeString(leftExpr) && !AdapterUtility.isExprTypeString(rightExpr)) {
      System.out.println("Error: string | non-string addition not allowed.");
      outputSymbolTable();
      System.exit(0);
    }
    if (!AdapterUtility.isExprTypeString(leftExpr) && AdapterUtility.isExprTypeString(rightExpr)) {
      System.out.println("Error: string | non-string addition not allowed.");
      outputSymbolTable();
      System.exit(0);
    }
  }

  /* Minus expression */
  public void outAMinusExpr(AMinusExpr node) {
    PExpr leftExpr = getExprType(node.getLeft());
    PExpr rightExpr = getExprType(node.getRight());

    if (AdapterUtility.isExprTypeString(leftExpr) && !AdapterUtility.isExprTypeString(rightExpr)) {
      System.out.println("Error: string | non-string subtraction not allowed.");
      outputSymbolTable();
      System.exit(0);
    }
    if (!AdapterUtility.isExprTypeString(leftExpr) && AdapterUtility.isExprTypeString(rightExpr)) {
      System.out.println("Error: string | non-string subtraction not allowed.");
      outputSymbolTable();
      System.exit(0);
    }
  }

  /* Times expression */
  public void outATimesExpr(ATimesExpr node) {
    PExpr leftExpr = getExprType(node.getLeft());
    PExpr rightExpr = getExprType(node.getRight());

    if (AdapterUtility.isExprTypeString(leftExpr) || AdapterUtility.isExprTypeString(rightExpr)) {
      System.out.println("Error: * cannot be applied to string type.");
      outputSymbolTable();
      System.exit(0);
    }
  }

  /* Divide expression */
  public void outADivideExpr(ADivideExpr node) {
    PExpr leftExpr = getExprType(node.getLeft());
    PExpr rightExpr = getExprType(node.getRight());

    if (AdapterUtility.isExprTypeString(leftExpr) || AdapterUtility.isExprTypeString(rightExpr)) {
      System.out.println("Error: / cannot be applied to string type.");
      outputSymbolTable();
      System.exit(0);
    }
  }

  /* Id expression */
  public void outAIdExpr(AIdExpr node) {
    TId id = node.getId();
    String key = id.getText();

    if (!SymbolTable.containVariable(key)) {
      System.out.println("Error: [" + id.getLine() + "," + id.getPos() + "] Identifier " + key + " not declared.");
      outputSymbolTable();
      System.exit(0);
    }
  }

  /**********************************************
  * Private methods *****************************
  **********************************************/

  private PExpr getExprType(PExpr expr) {
    /* Plus, minus, times and divide */
    if (AdapterUtility.isAPlusExpr(expr) || AdapterUtility.isAMinusExpr(expr) || AdapterUtility.isATimesExpr(expr) || AdapterUtility.isADivideExpr(expr)) {
      PExpr left;
      PExpr right;

      if (AdapterUtility.isAPlusExpr(expr)) {
        /* Plus expression */
        APlusExpr plus = (APlusExpr) expr;
        left = getExprType(plus.getLeft());
        right = getExprType(plus.getRight());
      } else if (AdapterUtility.isAMinusExpr(expr)) {
        /* Minus expression */
        AMinusExpr minus = (AMinusExpr) expr;
        left = getExprType(minus.getLeft());
        right = getExprType(minus.getRight());
      } else if (AdapterUtility.isATimesExpr(expr)) {
        /* Times expression */
        ATimesExpr times = (ATimesExpr) expr;
        left = getExprType(times.getLeft());
        right = getExprType(times.getRight());
      } else {
        /* Divide expression */
        ADivideExpr divide = (ADivideExpr) expr;
        left = getExprType(divide.getLeft());
        right = getExprType(divide.getRight());
      }

      /* Handle string */
      if (AdapterUtility.isExprTypeString(left) || AdapterUtility.isExprTypeString(right)) {
        return AdapterUtility.isExprTypeString(left) ? left : right;
      }
      /* Handle float */
      if (AdapterUtility.isExprTypeFloat(left) || AdapterUtility.isExprTypeFloat(right)) {
        return AdapterUtility.isExprTypeFloat(left) ? left : right;
      }

      /* Handle int */
      return left;
    }

    /* Unary minus expression */
    if (AdapterUtility.isAUnaryExpr(expr)) {
      AUnaryExpr unary = (AUnaryExpr) expr;
      return getExprType(unary.getExpr());
    }

    /* All base cases */
    return expr;
  }

  /* Output symbolTable to file */
  private void outputSymbolTable() {
    try  {
      PrintWriter out = new PrintWriter(new FileWriter("a.symbol.txt"));
      out.print(SymbolTable.getStringRepresentation());
      out.close();
    } catch (Exception ex) {
      System.out.println("Exception: failed to output symbol table.");
      System.exit(0);
    }
  }

}

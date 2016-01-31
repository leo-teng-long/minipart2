package mini;

import mini.analysis.*;
import mini.node.*;
import mini.AdapterUtility;
import java.util.*;

public class TypeChecker extends DepthFirstAdapter {

  private HashMap<String, PType> symbolTable = new HashMap<String, PType>();
  private AdapterUtility utility = new AdapterUtility();

  public TypeChecker() {
    /* Constructor */
  }

  public void outADeclareDecl(ADeclareDecl node) {
    TId id = node.getId();
    PType type = node.getType();
    String key = id.getText();

    if (symbolTable.containsKey(key)) {
      System.out.println("Error: [" + id.getLine() + "," + id.getPos() + "] Indentifier \"" + key + "\" already declared.");
      System.exit(0);
    } else {
      symbolTable.put(key, type);
    }
  }

  private PExpr getExprType(PExpr node) {
    /* Terminal node */
    if (utility.isAIdExpr(node) || utility.isAIntExpr(node) || utility.isAFloatExpr(node) || utility.isAStringExpr(node)) {
      return node;
    }

    /* Handle '+' | '-' */
    if (utility.isAPlusExpr(node) || utility.isAMinusExpr(node)) {
      PExpr leftExpr;
      PExpr rightExpr;
      if (utility.isAPlusExpr(node)) {
        leftExpr = getExprType(((APlusExpr) node).getLeft());
        rightExpr = getExprType(((APlusExpr) node).getRight());
      } else {
        leftExpr = getExprType(((AMinusExpr) node).getLeft());
        rightExpr = getExprType(((AMinusExpr) node).getRight());
      }

      if (utility.isAStringExpr(leftExpr) && utility.isAStringExpr(rightExpr)) {
        return (PExpr) new AStringExpr();
      }

      return (PExpr) new AIntExpr();
    }

    /* Handle '*' | '/' */
    if (utility.isATimesExpr(node) || utility.isADivideExpr(node)) {
      PExpr leftExpr;
      PExpr rightExpr;
      if (utility.isATimesExpr(node)) {
        leftExpr = getExprType(((ATimesExpr) node).getLeft());
        rightExpr = getExprType(((ATimesExpr) node).getRight());
      } else {
        leftExpr = getExprType(((ADivideExpr) node).getLeft());
        rightExpr = getExprType(((ADivideExpr) node).getRight());
      }

      if (utility.isAStringExpr(leftExpr)) {
        TStringconst leftStr = ((AStringExpr) leftExpr).getStringconst();
        printStringExprTypeError(leftStr.getLine(), leftStr.getPos(), "\"*\" and \"/\"");
        System.exit(0);
      } else if (utility.isAStringExpr(rightExpr)) {
        TStringconst rightStr = ((AStringExpr) rightExpr).getStringconst();
        printStringExprTypeError(rightStr.getLine(), rightStr.getPos(), "\"*\" and \"/\"");
        System.exit(0);
      }

      if (utility.isAIdExpr(leftExpr)) {
        TId leftId = ((AIdExpr) leftExpr).getId();
        String key = leftId.getText();

        if (utility.isAStringType(symbolTable.get(key))) {
          printStringExprTypeError(leftId.getLine(), leftId.getPos(), "\"*\" and \"/\"");
          System.exit(0);
        }
      } else if (utility.isAIdExpr(rightExpr)) {
        TId rightId = ((AIdExpr) rightExpr).getId();
        String key = rightId.getText();

        if (utility.isAStringType(symbolTable.get(key))) {
          printStringExprTypeError(rightId.getLine(), rightId.getPos(), "\"*\" and \"/\"");
          System.exit(0);
        }
      }

      if (utility.isAFloatExpr(leftExpr)) {
        return (PExpr) new AFloatExpr();
      } else if (utility.isAFloatExpr(rightExpr)) {
        return (PExpr) new AFloatExpr();
      }

      if (utility.isAIdExpr(leftExpr)) {
        TId leftId = ((AIdExpr) leftExpr).getId();
        String key = leftId.getText();
        if (utility.isAFloatType(symbolTable.get(key))) {
          return (PExpr) new AFloatExpr();
        }
      } else if (utility.isAIdExpr(rightExpr)) {
        TId rightId = ((AIdExpr) rightExpr).getId();
        String key = rightId.getText();
        if (utility.isAFloatType(symbolTable.get(key))) {
          return (PExpr) new AFloatExpr();
        }
      }

      return (PExpr) new AIntExpr();
    }

    /* Handle unary '-' */
    if (utility.isAUnaryExpr(node)) {
      return getExprType(((AUnaryExpr) node).getExpr());
    }

    return node;
  }

  private void printStringExprTypeError(int line, int pos, String message) {
    System.out.println("Error: [" + line + "," + pos + "] " + message + " cannot be applied to string.");
  }

  public void outAPlusExpr(APlusExpr node) {
    PExpr left = node.getLeft();
    PExpr right = node.getRight();
  }

  public void outAMinusExpr(AMinusExpr node) {
    PExpr left = node.getLeft();
    PExpr right = node.getRight();
  }

  public void outATimesExpr(ATimesExpr node) {
    getExprType(node.getLeft());
    getExprType(node.getRight());
  }

  public void outADivideExpr(ADivideExpr node) {
    getExprType(node.getLeft());
    getExprType(node.getRight());
  }

  public String toString() {
    return symbolTable.toString();
  }

}

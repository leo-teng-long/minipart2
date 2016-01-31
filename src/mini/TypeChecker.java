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

  /* Declaration */
  public void outADeclareDecl(ADeclareDecl node) {
    TId id = node.getId();
    PType type = node.getType();
    String key = id.getText();

    if (symbolTable.containsKey(key)) {
      System.out.println("Error: [" + id.getLine() + "," + id.getPos() + "] Identifier " + key + " already declared.");
      System.exit(0);
    } else {
      symbolTable.put(key, type);
    }
  }

  /* Assignment */
  public void outAAssignStmt(AAssignStmt node) {
    TId id = node.getId();
    String key = id.getText();

    PType type = symbolTable.get(key);
    PExpr expr = getExprType(node.getExpr());

    /* int var */
    if (utility.isAIntType(type) && !isExprTypeInt(expr)) {
      System.out.println("Error: [" + id.getLine() + "," + id.getPos() + "] Assigned type does not match identifier type.");
      System.exit(0);
    }
    /* float var */
    if (utility.isAFloatType(type) && !(isExprTypeInt(expr) || isExprTypeFloat(expr))) {
      System.out.println("Error: [" + id.getLine() + "," + id.getPos() + "] Assigned type does not match identifier type.");
      System.exit(0);
    }
    /* string var */
    if (utility.isAStringType(type) && !isExprTypeString(expr)) {
      System.out.println("Error: [" + id.getLine() + "," + id.getPos() + "] Assigned type does not match identifier type.");
      System.exit(0);
    }
  }

  /* If statment */
  public void outAIfStmt(AIfStmt node) {
    PExpr expr = getExprType(node.getExpr());

    if (!isExprTypeInt(expr)) {
      System.out.println("Error: Condition does not evaluate to type int.");
      System.exit(0);
    }
  }

  /* If-else statement */
  public void outAIfelseStmt(AIfelseStmt node) {
    PExpr expr = getExprType(node.getExpr());

    if (!isExprTypeInt(expr)) {
      System.out.println("Error: Condition does not evaluate to type int.");
      System.exit(0);
    }
  }

  /* While statement */
  public void outAWhileStmt(AWhileStmt node) {
    PExpr expr = getExprType(node.getExpr());

    if (!isExprTypeInt(expr)) {
      System.out.println("Error: Condition does not evaluate to type int.");
      System.exit(0);
    }
  }

  /* Plus expression */
  public void outAPlusExpr(APlusExpr node) {
    PExpr leftExpr = getExprType(node.getLeft());
    PExpr rightExpr = getExprType(node.getRight());

    if (isExprTypeString(leftExpr) && !isExprTypeString(rightExpr)) {
      System.out.println("Error: string | non-string addition not allowed.");
      System.exit(0);
    }
    if (!isExprTypeString(leftExpr) && isExprTypeString(rightExpr)) {
      System.out.println("Error: string | non-string addition not allowed.");
      System.exit(0);
    }
  }

  /* Minus expression */
  public void outAMinusExpr(AMinusExpr node) {
    PExpr leftExpr = getExprType(node.getLeft());
    PExpr rightExpr = getExprType(node.getRight());

    if (isExprTypeString(leftExpr) && !isExprTypeString(rightExpr)) {
      System.out.println("Error: string | non-string subtraction not allowed.");
      System.exit(0);
    }
    if (!isExprTypeString(leftExpr) && isExprTypeString(rightExpr)) {
      System.out.println("Error: string | non-string subtraction not allowed.");
      System.exit(0);
    }
  }

  /* Times expression */
  public void outATimesExpr(ATimesExpr node) {
    PExpr leftExpr = getExprType(node.getLeft());
    PExpr rightExpr = getExprType(node.getRight());

    if (isExprTypeString(leftExpr) || isExprTypeString(rightExpr)) {
      System.out.println("Error: * cannot be applied to string type.");
      System.exit(0);
    }
  }

  /* Divide expression */
  public void outADivideExpr(ADivideExpr node) {
    PExpr leftExpr = getExprType(node.getLeft());
    PExpr rightExpr = getExprType(node.getRight());

    if (isExprTypeString(leftExpr) || isExprTypeString(rightExpr)) {
      System.out.println("Error: / cannot be applied to string type.");
      System.exit(0);
    }
  }

  /* Id expression */
  public void outAIdExpr(AIdExpr node) {
    TId id = node.getId();
    String key = id.getText();

    if (!symbolTable.containsKey(key)) {
      System.out.println("Error: [" + id.getLine() + "," + id.getPos() + "] Identifier " + key + " not declared.");
      System.exit(0);
    }
  }

  /**********************************************
  * Private methods *****************************
  **********************************************/

  private boolean isExprTypeInt(PExpr expr) {
    return utility.isAIntExpr(expr) || isIdTypeInt(expr);
  }

  private boolean isExprTypeFloat(PExpr expr) {
    return utility.isAFloatExpr(expr) || isIdTypeFloat(expr);
  }

  private boolean isExprTypeString(PExpr expr) {
    return utility.isAStringExpr(expr) || isIdTypeString(expr);
  }

  private boolean isIdTypeInt(PExpr expr) {
    if (!utility.isAIdExpr(expr)) {
      return false;
    }

    String key = ((AIdExpr) expr).getId().getText();
    return utility.isAIntType(symbolTable.get(key));
  }

  private boolean isIdTypeFloat(PExpr expr) {
    if (!utility.isAIdExpr(expr)) {
      return false;
    }

    String key = ((AIdExpr) expr).getId().getText();
    return utility.isAFloatType(symbolTable.get(key));
  }

  private boolean isIdTypeString(PExpr expr) {
    if (!utility.isAIdExpr(expr)) {
      return false;
    }

    String key = ((AIdExpr) expr).getId().getText();
    return utility.isAStringType(symbolTable.get(key));
  }

  private PExpr getExprType(PExpr expr) {
    /* Plus, minus, times and divide */
    if (utility.isAPlusExpr(expr) || utility.isAMinusExpr(expr) || utility.isATimesExpr(expr) || utility.isADivideExpr(expr)) {
      PExpr left;
      PExpr right;

      if (utility.isAPlusExpr(expr)) {
        /* Plus expression */
        APlusExpr plus = (APlusExpr) expr;
        left = getExprType(plus.getLeft());
        right = getExprType(plus.getRight());
      } else if (utility.isAMinusExpr(expr)) {
        /* Minus expression */
        AMinusExpr minus = (AMinusExpr) expr;
        left = getExprType(minus.getLeft());
        right = getExprType(minus.getRight());
      } else if (utility.isATimesExpr(expr)) {
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
      if (isExprTypeString(left) || isExprTypeString(right)) {
        return isExprTypeString(left) ? left : right;
      }
      /* Handle float */
      if (isExprTypeFloat(left) || isExprTypeFloat(right)) {
        return isExprTypeFloat(left) ? left : right;
      }

      /* Handle int */
      return left;
    }

    /* Unary minus expression */
    if (utility.isAUnaryExpr(expr)) {
      AUnaryExpr unary = (AUnaryExpr) expr;
      return getExprType(unary.getExpr());
    }

    /* All base cases */
    return expr;
  }

}

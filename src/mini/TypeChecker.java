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
      System.out.println("Error: [" + id.getLine() + "," + id.getPos() + "] Indentifier " + key + " already declared.");
      System.exit(0);
    } else {
      symbolTable.put(key, type);
    }
  }

  public void outAPlusExpr(APlusExpr node) {
    PExpr leftExpr = getExprType(node.getLeft());
    PExpr rightExpr = getExprType(node.getRight());

  }

  public void outAMinusExpr(AMinusExpr node) {
    PExpr left = getExprType(node.getLeft());
    PExpr right = getExprType(node.getRight());


  }

  public void outATimesExpr(ATimesExpr node) {
    PExpr leftExpr = getExprType(node.getLeft());
    PExpr rightExpr = getExprType(node.getRight());

    if (utility.isAStringExpr(leftExpr) || utility.isAStringExpr(rightExpr)) {
      TStringconst str;
      if (utility.isAStringExpr(leftExpr)) {
        str = ((AStringExpr) leftExpr).getStringconst();
      } else {
        str = ((AStringExpr) rightExpr).getStringconst();
      }

      printStringExprTypeError(str.getLine(), str.getPos(), "*");
      System.exit(0);
    }

    if (utility.isAIdExpr(leftExpr) || utility.isAIdExpr(rightExpr)) {
      TId id;
      if (utility.isAIdExpr(leftExpr)) {
        id = ((AIdExpr) leftExpr).getId();
      } else {
        id = ((AIdExpr) rightExpr).getId();
      }

      String key = id.getText();
      if (utility.isAStringType(symbolTable.get(key))) {
        printStringExprTypeError(id.getLine(), id.getPos(), "*");
      }
    }
  }

  public void outADivideExpr(ADivideExpr node) {
    PExpr leftExpr = getExprType(node.getLeft());
    PExpr rightExpr = getExprType(node.getRight());

    if (utility.isAStringExpr(leftExpr) || utility.isAStringExpr(rightExpr)) {
      TStringconst str;
      if (utility.isAStringExpr(leftExpr)) {
        str = ((AStringExpr) leftExpr).getStringconst();
      } else {
        str = ((AStringExpr) rightExpr).getStringconst();
      }

      printStringExprTypeError(str.getLine(), str.getPos(), "/");
      System.exit(0);
    }

    if (utility.isAIdExpr(leftExpr) || utility.isAIdExpr(rightExpr)) {
      TId id;
      if (utility.isAIdExpr(leftExpr)) {
        id = ((AIdExpr) leftExpr).getId();
      } else {
        id = ((AIdExpr) rightExpr).getId();
      }

      String key = id.getText();
      if (utility.isAStringType(symbolTable.get(key))) {
        printStringExprTypeError(id.getLine(), id.getPos(), "/");
      }
    }
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
      if (utility.isAStringExpr(left) || utility.isAStringExpr(right)) {
        return selectStringExpr(left, right);
      }
      if (isIdTypeString(left) || isIdTypeString(right)) {
        return selectIdTypeString(left, right);
      }

      /* Handle float */
      if (utility.isAFloatExpr(left) || utility.isAFloatExpr(right)) {
        return selectFloatExpr(left, right);
      }
      if (isIdTypeFloat(left) || isIdTypeFloat(right)) {
        return selectIdTypeFloat(left, right);
      }

      /* Handle int */
      return selectNonIdExpr(left, right);
    }

    /* Unary minus expression */
    if (utility.isAUnaryExpr(expr)) {
      AUnaryExpr unary = (AUnaryExpr) expr;
      return getExprType(unary.getExpr());
    }

    /* All base cases */
    return expr;
  }

  private PExpr selectStringExpr(PExpr left, PExpr right) {
    return utility.isAStringExpr(left) ? left : right;
  }

  private boolean isIdTypeString(PExpr expr) {
    if (!utility.isAIdExpr(expr)) {
      return false;
    }

    String key = ((AIdExpr) expr).getId().getText();
    return utility.isAStringType(symbolTable.get(key));
  }

  private PExpr selectIdTypeString(PExpr left, PExpr right) {
    return isIdTypeString(left) ? left : right;
  }

  private PExpr selectFloatExpr(PExpr left, PExpr right) {
    return utility.isAFloatExpr(left) ? left : right;
  }

  private boolean isIdTypeFloat(PExpr expr) {
    if (!utility.isAIdExpr(expr)) {
      return false;
    }

    String key = ((AIdExpr) expr).getId().getText();
    return utility.isAStringType(symbolTable.get(key));
  }

  private PExpr selectIdTypeFloat(PExpr left, PExpr right) {
    return isIdTypeFloat(left) ? left : right;
  }

  private PExpr selectNonIdExpr(PExpr left, PExpr right) {
    return !utility.isAIdExpr(left) ? left : right;
  }

  private void printStringExprTypeError(int line, int pos, String message) {
    System.out.println("Error: [" + line + "," + pos + "] " + message + " cannot be applied to string.");
  }

  public void outAIdExpr(AIdExpr node) {
    TId id = node.getId();
    String key = id.getText();

    if (!symbolTable.containsKey(key)) {
      System.out.println("Error: [" + id.getLine() + "," + id.getPos() + "] Undifined identifier " + key + ".");
      System.exit(0);
    }
  }

}

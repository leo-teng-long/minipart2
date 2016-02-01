package mini;

import mini.node.*;
import mini.SymbolTable;

public class AdapterUtility {

  /*
    For declaration type checking
  */

  public static boolean isAIntType(PType type) {
    return type instanceof AIntType;
  }

  public static boolean isAFloatType(PType type) {
    return type instanceof AFloatType;
  }

  public static boolean isAStringType(PType type) {
    return type instanceof AStringType;
  }

  /*
    For expression type checking
  */

  public static boolean isAPlusExpr(PExpr expr) {
    return expr instanceof APlusExpr;
  }

  public static boolean isAMinusExpr(PExpr expr) {
    return expr instanceof AMinusExpr;
  }

  public static boolean isATimesExpr(PExpr expr) {
    return expr instanceof ATimesExpr;
  }

  public static boolean isADivideExpr(PExpr expr) {
    return expr instanceof ADivideExpr;
  }

  public static boolean isAUnaryExpr(PExpr expr) {
    return expr instanceof AUnaryExpr;
  }

  public static boolean isAIdExpr(PExpr expr) {
    return expr instanceof AIdExpr;
  }

  public static boolean isAIntExpr(PExpr expr) {
    return expr instanceof AIntExpr;
  }

  public static boolean isAFloatExpr(PExpr expr) {
    return expr instanceof AFloatExpr;
  }

  public static boolean isAStringExpr(PExpr expr) {
    return expr instanceof AStringExpr;
  }

  /*
    For id and expression type checking
  */

  public static boolean isExprTypeInt(PExpr expr) {
    return isAIntExpr(expr) || isIdTypeInt(expr);
  }

  public static boolean isExprTypeFloat(PExpr expr) {
    return isAFloatExpr(expr) || isIdTypeFloat(expr);
  }

  public static boolean isExprTypeString(PExpr expr) {
    return isAStringExpr(expr) || isIdTypeString(expr);
  }

  public static boolean isIdTypeInt(PExpr expr) {
    if (!isAIdExpr(expr)) {
      return false;
    }

    String key = ((AIdExpr) expr).getId().getText();
    return isAIntType(SymbolTable.getVariableType(key));
  }

  public static boolean isIdTypeFloat(PExpr expr) {
    if (!isAIdExpr(expr)) {
      return false;
    }

    String key = ((AIdExpr) expr).getId().getText();
    return isAFloatType(SymbolTable.getVariableType(key));
  }

  public static boolean isIdTypeString(PExpr expr) {
    if (!isAIdExpr(expr)) {
      return false;
    }

    String key = ((AIdExpr) expr).getId().getText();
    return isAStringType(SymbolTable.getVariableType(key));
  }

}

package mini;

import mini.analysis.*;
import mini.node.*;
import java.util.*;

public class AdapterUtility {

  public AdapterUtility() {
    /* Constructor */
  }

  /*
    For declaration type checking
  */

  public boolean isAIntType(PType type) {
    return type instanceof AIntType;
  }

  public boolean isAFloatType(PType type) {
    return type instanceof AFloatType;
  }

  public boolean isAStringType(PType type) {
    return type instanceof AStringType;
  }

  /*
    For expression type checking
  */

  public boolean isAPlusExpr(PExpr expr) {
    return expr instanceof APlusExpr;
  }

  public boolean isAMinusExpr(PExpr expr) {
    return expr instanceof AMinusExpr;
  }

  public boolean isATimesExpr(PExpr expr) {
    return expr instanceof ATimesExpr;
  }

  public boolean isADivideExpr(PExpr expr) {
    return expr instanceof ADivideExpr;
  }

  public boolean isAUnaryExpr(PExpr expr) {
    return expr instanceof AUnaryExpr;
  }

  public boolean isAIdExpr(PExpr expr) {
    return expr instanceof AIdExpr;
  }

  public boolean isAIntExpr(PExpr expr) {
    return expr instanceof AIntExpr;
  }

  public boolean isAFloatExpr(PExpr expr) {
    return expr instanceof AFloatExpr;
  }

  public boolean isAStringExpr(PExpr expr) {
    return expr instanceof AStringExpr;
  }

}

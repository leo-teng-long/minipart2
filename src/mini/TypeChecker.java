package mini;

import mini.analysis.*;
import mini.node.*;
import java.util.*;

public class TypeChecker extends DepthFirstAdapter {

  private Hashtable symbolTable = new Hashtable();

  public TypeChecker() {
    /* Constructor */
  }

  public void outADeclareDecl(ADeclareDecl node) {
    TId id = node.getId();
    PType type = node.getType();

    String key = id.getText();
    String value = getVarType(type);

    if (symbolTable.containsKey(key)) {
      System.out.println("Error: [" + id.getLine() + ", " + id.getPos() + "] Indentifier " + id.getText() + " already defined.");
      System.exit(0);
    } else {
      symbolTable.put(key, value);
    }
  }

  private String getVarType(PType type) {
    if (checkInt(type)) {
      return ((AIntType) type).getInt().getText();
    }
    if (checkFloat(type)) {
      return ((AFloatType) type).getFloat().getText();
    }
    if (checkString(type)) {
      return ((AStringType) type).getString().getText();
    }

    return null;
  }

  private boolean checkInt(PType type) {
    return type instanceof AIntType;
  }

  private boolean checkFloat(PType type) {
    return type instanceof AFloatType;
  }

  private boolean checkString(PType type) {
    return type instanceof AStringType;
  }

  public String toString() {
    return symbolTable.toString();
  }
}

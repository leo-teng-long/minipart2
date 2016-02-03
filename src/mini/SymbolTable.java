package mini;

import mini.node.*;
import java.util.*;

public class SymbolTable {

  private static HashMap<String, PType> symbolTable = new HashMap<String, PType>();

  public static boolean declareVariable(String key, PType type) {
    if (symbolTable.containsKey(key)) {
      return false;
    }
    
    symbolTable.put(key, type);
    return true;
  }

  public static boolean containVariable(String key) {
    return symbolTable.containsKey(key);
  }

  public static PType getVariableType(String key) {
    return symbolTable.get(key);
  }

  public static Set<String> getAllVariables() {
    return symbolTable.keySet();
  }

  public static String getStringRepresentation() {
    String s = "";
    for (String key : symbolTable.keySet()) {
      s += key + " : " + symbolTable.get(key).toString() + ";\n";
    }

    return s;
  }

}

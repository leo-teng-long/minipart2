package mini;

import mini.parser.*;
import mini.lexer.*;
import mini.node.*;
import mini.TypeChecker;
import mini.PrettyPrinter;
import mini.Emitter;
import java.io.*;

public class Main {
  public static void main(String[] args) {
    try {
      Lexer lexer = new Lexer(new PushbackReader(new InputStreamReader(System.in), 1024));
      Parser parser = new Parser(lexer);
      Start tree = parser.parse();

      TypeChecker checker = new TypeChecker();
      tree.apply(checker);

    } catch (Exception e) {
      System.out.println("Invalid: " + e.getMessage());
    }
  }
}

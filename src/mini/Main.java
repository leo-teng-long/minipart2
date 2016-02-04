package mini;

import mini.parser.*;
import mini.lexer.*;
import mini.node.*;
import mini.TypeChecker;
import mini.PrettyPrinter;
import mini.CodeGenerator;
import java.io.*;

public class Main {
  public static void main(String[] args) {
    try {
      Lexer lexer = new Lexer(new PushbackReader(new FileReader(args[0]), 1024));
      Parser parser = new Parser(lexer);
      Start tree = parser.parse();

      String fileName = args[0].substring(0, args[0].indexOf("."));
      TypeChecker checker = new TypeChecker(fileName);
      PrettyPrinter printer = new PrettyPrinter(fileName);
      CodeGenerator generator = new CodeGenerator(fileName);

      tree.apply(checker);
      tree.apply(printer);
      tree.apply(generator);
    } catch (Exception e) {
      System.out.println("Invalid: " + e.getMessage());
    }
  }
}

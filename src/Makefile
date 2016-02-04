
# Please change the next line to the actual path of your SableCC ver.3
# SABLECCDIR = "......"

all: clean compile_grammar compile_code

compile_grammar:
	java -jar $(SABLECCDIR)/lib/sablecc.jar mini.sablecc

compile_code:
	javac mini/lexer/*.java mini/parser/*.java mini/node/*.java mini/analysis/*.java mini/*.java

clean:
	rm -fr mini/lexer/ mini/parser/ mini/node/ mini/analysis/ mini/*.class

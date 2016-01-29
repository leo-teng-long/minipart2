/* This file was generated by SableCC (http://www.sablecc.org/). */

package mini.analysis;

import mini.node.*;

public interface Analysis extends Switch
{
    Object getIn(Node node);
    void setIn(Node node, Object o);
    Object getOut(Node node);
    void setOut(Node node, Object o);

    void caseStart(Start node);
    void caseAProgramProg(AProgramProg node);
    void caseADeclareDecl(ADeclareDecl node);
    void caseAIntType(AIntType node);
    void caseAFloatType(AFloatType node);
    void caseAStringType(AStringType node);
    void caseAAssignStmt(AAssignStmt node);
    void caseAIfStmt(AIfStmt node);
    void caseAIfelseStmt(AIfelseStmt node);
    void caseAWhileStmt(AWhileStmt node);
    void caseAReadStmt(AReadStmt node);
    void caseAPrintStmt(APrintStmt node);
    void caseAPlusExpr(APlusExpr node);
    void caseAMinusExpr(AMinusExpr node);
    void caseAMultiplyExpr(AMultiplyExpr node);
    void caseADivideExpr(ADivideExpr node);
    void caseAUnaryExpr(AUnaryExpr node);
    void caseAIdExpr(AIdExpr node);
    void caseAIntExpr(AIntExpr node);
    void caseAFloatExpr(AFloatExpr node);
    void caseAStringExpr(AStringExpr node);

    void caseTVar(TVar node);
    void caseTInt(TInt node);
    void caseTFloat(TFloat node);
    void caseTString(TString node);
    void caseTWhile(TWhile node);
    void caseTDo(TDo node);
    void caseTDone(TDone node);
    void caseTIf(TIf node);
    void caseTThen(TThen node);
    void caseTElse(TElse node);
    void caseTEndif(TEndif node);
    void caseTRead(TRead node);
    void caseTPrint(TPrint node);
    void caseTEqual(TEqual node);
    void caseTPlus(TPlus node);
    void caseTMinus(TMinus node);
    void caseTMul(TMul node);
    void caseTDiv(TDiv node);
    void caseTLPar(TLPar node);
    void caseTRPar(TRPar node);
    void caseTCol(TCol node);
    void caseTSemi(TSemi node);
    void caseTId(TId node);
    void caseTIntconst(TIntconst node);
    void caseTFloatconst(TFloatconst node);
    void caseTStringconst(TStringconst node);
    void caseTComment(TComment node);
    void caseTBlank(TBlank node);
    void caseTEol(TEol node);
    void caseEOF(EOF node);
    void caseInvalidToken(InvalidToken node);
}
/* This file was generated by SableCC (http://www.sablecc.org/). */

package mini.node;

import java.util.*;
import mini.analysis.*;

@SuppressWarnings("nls")
public final class AIfelseStmt extends PStmt
{
    private PExpr _expr_;
    private final LinkedList<PStmt> _thenStmts_ = new LinkedList<PStmt>();
    private PList _else_;

    public AIfelseStmt()
    {
        // Constructor
    }

    public AIfelseStmt(
        @SuppressWarnings("hiding") PExpr _expr_,
        @SuppressWarnings("hiding") List<?> _thenStmts_,
        @SuppressWarnings("hiding") PList _else_)
    {
        // Constructor
        setExpr(_expr_);

        setThenStmts(_thenStmts_);

        setElse(_else_);

    }

    @Override
    public Object clone()
    {
        return new AIfelseStmt(
            cloneNode(this._expr_),
            cloneList(this._thenStmts_),
            cloneNode(this._else_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAIfelseStmt(this);
    }

    public PExpr getExpr()
    {
        return this._expr_;
    }

    public void setExpr(PExpr node)
    {
        if(this._expr_ != null)
        {
            this._expr_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._expr_ = node;
    }

    public LinkedList<PStmt> getThenStmts()
    {
        return this._thenStmts_;
    }

    public void setThenStmts(List<?> list)
    {
        for(PStmt e : this._thenStmts_)
        {
            e.parent(null);
        }
        this._thenStmts_.clear();

        for(Object obj_e : list)
        {
            PStmt e = (PStmt) obj_e;
            if(e.parent() != null)
            {
                e.parent().removeChild(e);
            }

            e.parent(this);
            this._thenStmts_.add(e);
        }
    }

    public PList getElse()
    {
        return this._else_;
    }

    public void setElse(PList node)
    {
        if(this._else_ != null)
        {
            this._else_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._else_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._expr_)
            + toString(this._thenStmts_)
            + toString(this._else_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._expr_ == child)
        {
            this._expr_ = null;
            return;
        }

        if(this._thenStmts_.remove(child))
        {
            return;
        }

        if(this._else_ == child)
        {
            this._else_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._expr_ == oldChild)
        {
            setExpr((PExpr) newChild);
            return;
        }

        for(ListIterator<PStmt> i = this._thenStmts_.listIterator(); i.hasNext();)
        {
            if(i.next() == oldChild)
            {
                if(newChild != null)
                {
                    i.set((PStmt) newChild);
                    newChild.parent(this);
                    oldChild.parent(null);
                    return;
                }

                i.remove();
                oldChild.parent(null);
                return;
            }
        }

        if(this._else_ == oldChild)
        {
            setElse((PList) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}

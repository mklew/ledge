/* Generated By:JJTree: Do not edit this line. ASTcontainmentCondition.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package org.objectledge.jsonql.impl;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.objectledge.jsonql.EvaluationContext;

public class ASTcontainmentPredicate
    extends SimpleNode
{
    private final Set<String> values = new HashSet<>();

    public ASTcontainmentPredicate(int id)
    {
        super(id);
    }

    public ASTcontainmentPredicate(JSONQL p, int id)
    {
        super(p, id);
    }

    public void addValue(Token token)
    {
        values.add(token.image.substring(1, token.image.length() - 1).replaceAll("''", "'"));
    }

    public Set<String> getValues()
    {
        return Collections.unmodifiableSet(values);
    }

    @Override
    public String toString()
    {
        return super.toString() + String.format(" in %s", values.toString());
    }

    /** Accept the visitor. **/
    public Object jjtAccept(JSONQLVisitor visitor, EvaluationContext data)
    {
        return visitor.visit(this, data);
    }
}
/* JavaCC - OriginalChecksum=5d0ff7b8896986923d9d5389e1005999 (do not edit this line) */
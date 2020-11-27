package org.kostenko.example.jpa.dialect;

import org.hibernate.QueryException;
import org.hibernate.dialect.function.SQLFunction;
import org.hibernate.engine.spi.Mapping;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.type.Type;

import java.util.List;

public class SafeInFunction implements SQLFunction {

    private final static int IN_CAUSE_LIMIT = 1000;

    @Override
    public boolean hasArguments() {
        return true;
    }

    @Override
    public boolean hasParenthesesIfNoArguments() {
        return true;
    }

    @Override
    public Type getReturnType(Type firstArgumentType, Mapping mapping) throws QueryException {
        return firstArgumentType;
    }

    @Override
    public String render(Type firstArgumentType, List arguments, SessionFactoryImplementor factory) throws QueryException {
        final StringBuilder buf = new StringBuilder();

        String fieldName = (String) arguments.get(0);
        for (int i = 1; i < arguments.size(); i++) {
            if (i % IN_CAUSE_LIMIT == 0) {
                buf.deleteCharAt(buf.length() - 1).append(") or ").append(fieldName).append(" in (");
            }
            buf.append("?,");
        }
        return buf.deleteCharAt(buf.length() - 1).toString();
    }
}

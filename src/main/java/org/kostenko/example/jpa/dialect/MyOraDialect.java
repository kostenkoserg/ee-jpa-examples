package org.kostenko.example.jpa.dialect;

import org.hibernate.dialect.Oracle10gDialect;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.dialect.function.VarArgsSQLFunction;
import org.hibernate.type.StandardBasicTypes;

/**
 * @author kostenko
 */
public class MyOraDialect extends Oracle10gDialect {

    public MyOraDialect () {
        super();
        registerFunction("countover", new SQLFunctionTemplate(StandardBasicTypes.INTEGER, "count(*) over()"));
        registerFunction( "safeTupleIn", new VarArgsSQLFunction( StandardBasicTypes.INTEGER, "(", ",0),(", ",0)"));
        registerFunction( "safeIn", new SafeInFunction());
    }
}

package org.kostenko.example.jpa.dialect;

import org.hibernate.dialect.Oracle10gDialect;
import org.hibernate.dialect.function.StandardSQLFunction;

/**
 *
 * @author kostenko
 */
public class MyOraDialect extends Oracle10gDialect {

    @Override
    protected void registerFunctions() {
        registerFunction("over", new StandardSQLFunction("over"));
        registerKeyword("over");
        super.registerFunctions();
    }
}

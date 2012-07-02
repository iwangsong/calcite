/*
// Licensed to Julian Hyde under one or more contributor license
// agreements. See the NOTICE file distributed with this work for
// additional information regarding copyright ownership.
//
// Julian Hyde licenses this file to you under the Apache License,
// Version 2.0 (the "License"); you may not use this file except in
// compliance with the License. You may obtain a copy of the License at:
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
*/
package net.hydromatic.linq4j.expressions;

import java.lang.reflect.Modifier;

/**
 * Expression that declares and optionally initializes a variable.
 *
 * @author jhyde
 */
public class DeclarationExpression extends Statement {
    public final int modifiers;
    public final ParameterExpression parameter;
    public final Expression initializer;

    public DeclarationExpression(
        int modifiers, ParameterExpression parameter, Expression initializer)
    {
        super(ExpressionType.Declaration, Void.TYPE);
        this.modifiers = modifiers;
        this.parameter = parameter;
        this.initializer = initializer;
    }

    @Override
    public Statement accept(Visitor visitor) {
        ParameterExpression parameter = this.parameter.accept(visitor);
        Expression initializer = this.initializer.accept(visitor);
        return visitor.visit(this, parameter, initializer);
    }

    @Override
    void accept0(ExpressionWriter writer) {
        final String modifiers = Modifier.toString(this.modifiers);
        if (!modifiers.isEmpty()) {
            writer.append(modifiers).append(' ');
        }
        writer.append(parameter.type)
            .append(' ')
            .append(parameter.name);
        if (initializer != null) {
            writer.append(" = ")
                .append(initializer);
        }
        writer.append(';');
        writer.newlineAndIndent();
    }
}

// End DeclarationExpression.java

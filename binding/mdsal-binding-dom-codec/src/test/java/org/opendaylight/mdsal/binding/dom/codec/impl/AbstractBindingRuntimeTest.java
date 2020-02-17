/*
 * Copyright (c) 2014 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.mdsal.binding.dom.codec.impl;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.opendaylight.mdsal.binding.generator.api.BindingRuntimeContext;
import org.opendaylight.mdsal.binding.generator.impl.DefaultBindingRuntimeGenerator;
import org.opendaylight.mdsal.binding.generator.impl.ModuleInfoBackedContext;
import org.opendaylight.mdsal.binding.spec.reflect.BindingReflections;
import org.opendaylight.yangtools.yang.model.api.EffectiveModelContext;

public abstract class AbstractBindingRuntimeTest {
    private static EffectiveModelContext schemaContext;
    private static BindingRuntimeContext runtimeContext;

    @BeforeClass
    public static void beforeClass() {
        ModuleInfoBackedContext ctx = ModuleInfoBackedContext.create();
        ctx.addModuleInfos(BindingReflections.loadModuleInfos());
        schemaContext = ctx.tryToCreateModelContext().get();

        runtimeContext = BindingRuntimeContext.create(
            new DefaultBindingRuntimeGenerator().generateTypeMapping(schemaContext), ctx);
    }

    @AfterClass
    public static void afterClass() {
        schemaContext = null;
        runtimeContext = null;
    }

    public static final EffectiveModelContext getSchemaContext() {
        return schemaContext;
    }

    public static final BindingRuntimeContext getRuntimeContext() {
        return runtimeContext;
    }
}

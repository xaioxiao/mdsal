/*
 * Copyright (c) 2013 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.mdsal.binding.generator.api;

import java.util.Collection;
import java.util.List;
import org.eclipse.jdt.annotation.NonNull;
import org.opendaylight.mdsal.binding.model.api.Type;
import org.opendaylight.yangtools.yang.model.api.Module;
import org.opendaylight.yangtools.yang.model.api.SchemaContext;

/**
 * Transform Schema Context to Generated types.
 */
public interface BindingGenerator {
    /**
     * Generate Types from whole Schema Context. The method will return List of All Generated Types that could be
     * Generated from Schema Context.
     *
     * @param context Schema Context
     * @return List of Generated Types
     *
     * @see SchemaContext
     */
    default @NonNull List<Type> generateTypes(final SchemaContext context) {
        return generateTypes(context, context.getModules());
    }

    /**
     * Generate Types from Schema Context restricted by sub set of specified Modules. The Schema Context MUST contain
     * all of the sub modules otherwise the there is no guarantee that result List of Generated Types will contain
     * correct Generated Types.
     *
     * @param context Schema Context
     * @param modules Sub Set of Modules
     * @return List of Generated Types restricted by sub set of Modules
     *
     * @see Module
     * @see SchemaContext
     */
    @NonNull List<Type> generateTypes(SchemaContext context, Collection<? extends Module> modules);
}

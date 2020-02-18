/*
 * Copyright (c) 2019 PANTHEON.tech, s.r.o. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.mdsal.binding.generator.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;
import org.junit.Test;
import org.opendaylight.mdsal.binding.model.api.Type;
import org.opendaylight.yangtools.yang.model.api.SchemaContext;
import org.opendaylight.yangtools.yang.test.util.YangParserTestUtils;

public class Mdsal438Test extends AbstractOpaqueTest {
    @Test
    public void generateAnydataTest() {
        final SchemaContext context = YangParserTestUtils.parseYangResource("/mdsal438.yang");

        final List<Type> types = DefaultBindingGenerator.generateFor(context);
        assertNotNull(types);
        assertEquals(7, types.size());

        assertOpaqueNode(types, "mdsal438", "", "Any");
        assertOpaqueNode(types, "mdsal438", ".cont", "Cont");
        assertOpaqueNode(types, "mdsal438", ".grp", "Grp");
    }
}

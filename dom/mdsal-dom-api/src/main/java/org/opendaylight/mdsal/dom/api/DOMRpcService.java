/*
 * Copyright (c) 2015 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.mdsal.dom.api;

import com.google.common.util.concurrent.ListenableFuture;
import org.eclipse.jdt.annotation.NonNull;
import org.opendaylight.yangtools.concepts.ListenerRegistration;
import org.opendaylight.yangtools.yang.data.api.schema.ContainerNode;
import org.opendaylight.yangtools.yang.model.api.SchemaPath;

/**
 * A {@link DOMService} which allows clients to invoke RPCs. The conceptual model of this service is that of a dynamic
 * router, where the set of available RPC services can change dynamically. The service allows users to add a listener
 * to track the process of RPCs becoming available.
 */
// FIXME: once we have a DOMOperationService implementation, deprecate this interface
public interface DOMRpcService extends DOMService {
    /**
     * Initiate invocation of an RPC. This method is guaranteed to not block on any external
     * resources.
     *
     * @param type SchemaPath of the RPC to be invoked
     * @param input Input arguments, null if the RPC does not take any.
     * @return A {@link ListenableFuture} which will return either a result structure, or report a subclass
     *         of {@link DOMRpcException} reporting a transport error.
     */
    @NonNull ListenableFuture<? extends DOMRpcResult> invokeRpc(@NonNull SchemaPath type,
            @NonNull ContainerNode input);

    /**
     * Register a {@link DOMRpcAvailabilityListener} with this service to receive notifications
     * about RPC implementations becoming (un)available. The listener will be invoked with the
     * current implementations reported and will be kept uptodate as implementations come and go.
     * Users should note that using a listener does not necessarily mean that
     * {@link #invokeRpc(SchemaPath, ContainerNode)} will not report a failure due to
     * {@link DOMRpcImplementationNotAvailableException} and need to be ready to handle it.
     * Implementations are encouraged to take reasonable precautions to prevent this scenario from
     * occurring.
     *
     * @param listener {@link DOMRpcAvailabilityListener} instance to register
     * @return A {@link ListenerRegistration} representing this registration. Performing a
     *         {@link ListenerRegistration#close()} will cancel it. Returned object is guaranteed to be non-null.
     */
    @NonNull <T extends DOMRpcAvailabilityListener> ListenerRegistration<T> registerRpcListener(@NonNull T listener);
}

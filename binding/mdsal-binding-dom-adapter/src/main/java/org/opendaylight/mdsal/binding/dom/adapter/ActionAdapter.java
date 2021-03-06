/*
 * Copyright (c) 2018 Pantheon Technologies, s.r.o. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.mdsal.binding.dom.adapter;

import static java.util.Objects.requireNonNull;
import static org.opendaylight.mdsal.binding.dom.adapter.StaticConfiguration.ENABLE_CODEC_SHORTCUT;
import static org.opendaylight.yangtools.yang.common.YangConstants.operationInputQName;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.opendaylight.mdsal.common.api.LogicalDatastoreType;
import org.opendaylight.mdsal.dom.api.DOMActionResult;
import org.opendaylight.mdsal.dom.api.DOMActionService;
import org.opendaylight.mdsal.dom.api.DOMDataTreeIdentifier;
import org.opendaylight.yangtools.yang.binding.Action;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import org.opendaylight.yangtools.yang.binding.RpcInput;
import org.opendaylight.yangtools.yang.data.api.YangInstanceIdentifier.NodeIdentifier;
import org.opendaylight.yangtools.yang.model.api.SchemaPath;

@NonNullByDefault
final class ActionAdapter extends AbstractBindingAdapter<DOMActionService> implements InvocationHandler {
    private final Class<? extends Action<?, ?, ?>> type;
    private final NodeIdentifier inputName;
    private final SchemaPath schemaPath;

    ActionAdapter(final AdapterContext codec, final DOMActionService delegate,
            final Class<? extends Action<?, ?, ?>> type) {
        super(codec, delegate);
        this.type = requireNonNull(type);
        this.schemaPath = currentSerializer().getActionPath(type);
        this.inputName = NodeIdentifier.create(operationInputQName(schemaPath.getLastComponent().getModule()));
    }

    @Override
    public @Nullable Object invoke(final @Nullable Object proxy, final @Nullable Method method,
            final Object @Nullable [] args) throws Throwable {
        switch (method.getName()) {
            case "equals":
                if (args.length == 1) {
                    return proxy == args[0];
                }
                break;
            case "hashCode":
                if (args.length == 0) {
                    return System.identityHashCode(proxy);
                }
                break;
            case "toString":
                if (args.length == 0) {
                    return type.getName() + "$Adapter{delegate=" + getDelegate() + "}";
                }
                break;
            case "invoke":
                if (args.length == 2) {
                    final InstanceIdentifier<?> path = (InstanceIdentifier<?>) requireNonNull(args[0]);
                    final RpcInput input = (RpcInput) requireNonNull(args[1]);
                    final CurrentAdapterSerializer serializer = currentSerializer();
                    final ListenableFuture<? extends DOMActionResult> future = getDelegate().invokeAction(schemaPath,
                        new DOMDataTreeIdentifier(LogicalDatastoreType.OPERATIONAL,
                            serializer.toYangInstanceIdentifier(path)),
                        serializer.toLazyNormalizedNodeActionInput(type, inputName, input));

                    // Invocation returned a future we know about -- return that future instead
                    if (ENABLE_CODEC_SHORTCUT && future instanceof BindingRpcFutureAware) {
                        return ((BindingRpcFutureAware) future).getBindingFuture();
                    }

                    return Futures.transform(future,
                        dom -> RpcResultUtil.rpcResultFromDOM(dom.getErrors(), dom.getOutput()
                            .map(output -> serializer.fromNormalizedNodeActionOutput(type, output))
                            .orElse(null)),
                        MoreExecutors.directExecutor());
                }
                break;
            default:
                break;
        }

        throw new NoSuchMethodError("Method " + method.toString() + "is unsupported.");
    }
}

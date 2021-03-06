/*
 * Copyright (c) 2013 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.mdsal.binding.model.util.generated.type.builder;

import com.google.common.base.Preconditions;
import java.util.Collections;
import java.util.List;
import org.opendaylight.mdsal.binding.model.api.GeneratedProperty;
import org.opendaylight.mdsal.binding.model.api.GeneratedTransferObject;
import org.opendaylight.mdsal.binding.model.api.JavaTypeName;
import org.opendaylight.mdsal.binding.model.api.ParameterizedType;
import org.opendaylight.mdsal.binding.model.api.Type;
import org.opendaylight.mdsal.binding.model.api.type.builder.GeneratedPropertyBuilder;
import org.opendaylight.mdsal.binding.model.api.type.builder.GeneratedTOBuilder;
import org.opendaylight.mdsal.binding.model.api.type.builder.MethodSignatureBuilder;
import org.opendaylight.yangtools.util.LazyCollections;
import org.opendaylight.yangtools.yang.model.api.TypeDefinition;

abstract class AbstractGeneratedTOBuilder extends AbstractGeneratedTypeBuilder<GeneratedTOBuilder>
        implements GeneratedTOBuilder {

    // FIXME are these three referenced anywhere at runtime?
    private List<GeneratedPropertyBuilder> equalsProperties = Collections.emptyList();
    private List<GeneratedPropertyBuilder> hashProperties = Collections.emptyList();
    private List<GeneratedPropertyBuilder> toStringProperties = Collections.emptyList();
    private GeneratedTransferObject extendsType;
    private boolean isTypedef = false;
    private boolean isUnionType = false;
    private boolean isUnionTypeBuilder = false;
    private TypeDefinition<?> baseType = null;

    AbstractGeneratedTOBuilder(final JavaTypeName identifier) {
        super(identifier);
        setAbstract(false);
    }

    @Override
    public final GeneratedTOBuilder setExtendsType(final GeneratedTransferObject genTransObj) {
        Preconditions.checkArgument(genTransObj != null, "Generated Transfer Object cannot be null!");
        this.extendsType = genTransObj;
        return this;
    }

    /**
     * Add new Method Signature definition for Generated Type Builder and
     * returns Method Signature Builder for specifying all Method parameters. <br>
     * Name of Method cannot be <code>null</code>, if it is <code>null</code>
     * the method SHOULD throw {@link IllegalArgumentException} <br>
     * By <i>Default</i> the MethodSignatureBuilder SHOULD be pre-set as
     * {@link MethodSignatureBuilder#setAbstract(boolean)},
     * {TypeMemberBuilder#setFinal(boolean)} and
     * {TypeMemberBuilder#setAccessModifier(boolean)}
     *
     * @param name
     *            Name of Method
     * @return <code>new</code> instance of Method Signature Builder.
     */
    @Override
    public final MethodSignatureBuilder addMethod(final String name) {
        final MethodSignatureBuilder builder = super.addMethod(name);
        builder.setAbstract(false);
        return builder;
    }

    @Override
    public final GeneratedTOBuilder addEqualsIdentity(final GeneratedPropertyBuilder property) {
        this.equalsProperties = LazyCollections.lazyAdd(this.equalsProperties, property);
        return this;
    }

    @Override
    public final GeneratedTOBuilder addHashIdentity(final GeneratedPropertyBuilder property) {
        this.hashProperties = LazyCollections.lazyAdd(this.hashProperties, property);
        return this;
    }

    @Override
    public final GeneratedTOBuilder addToStringProperty(final GeneratedPropertyBuilder property) {
        this.toStringProperties = LazyCollections.lazyAdd(this.toStringProperties, property);
        return this;
    }

    @Override
    protected final GeneratedTOBuilder thisInstance() {
        return this;
    }

    @Override
    public final String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("GeneratedTransferObject [packageName=");
        builder.append(getPackageName());
        builder.append(", name=");
        builder.append(getName());
        builder.append(", comment=");
        builder.append(getComment());
        builder.append(", constants=");
        builder.append(getConstants());
        builder.append(", enumerations=");
        builder.append(getEnumerations());
        builder.append(", equalsProperties=");
        builder.append(this.equalsProperties);
        builder.append(", hashCodeProperties=");
        builder.append(this.hashProperties);
        builder.append(", stringProperties=");
        builder.append(this.toStringProperties);
        builder.append(", annotations=");
        builder.append(getAnnotations());
        builder.append(", methods=");
        builder.append(getMethodDefinitions());
        builder.append("]");
        return builder.toString();
    }

    @Override
    public final void setTypedef(final boolean newIsTypedef) {
        this.isTypedef = newIsTypedef;
    }

    @Override
    public final void setBaseType(final TypeDefinition<?> typeDef) {
        this.baseType = typeDef;
    }

    @Override
    public final void setIsUnion(final boolean newIsUnion) {
        this.isUnionType = newIsUnion;
    }

    @Override
    public final boolean isUnion() {
        return isUnionType;
    }

    @Override
    public final void setIsUnionBuilder(final boolean newIsUnionTypeBuilder) {
        this.isUnionTypeBuilder = newIsUnionTypeBuilder;
    }

    abstract static class AbstractGeneratedTransferObject extends AbstractGeneratedType implements
            GeneratedTransferObject {

        private final List<GeneratedProperty> equalsProperties;
        private final List<GeneratedProperty> hashCodeProperties;
        private final List<GeneratedProperty> stringProperties;
        private final GeneratedTransferObject extendsType;
        private final boolean isTypedef;
        private final TypeDefinition<?> baseType;
        private final boolean isUnionType;
        private final boolean isUnionTypeBuilder;

        AbstractGeneratedTransferObject(final AbstractGeneratedTOBuilder builder) {
            super(builder);
            this.extendsType = builder.extendsType;

            // FIXME: if these fields were guaranteed to be constant, we could perhaps
            //        cache and reuse them between instances...
            this.equalsProperties = toUnmodifiableProperties(builder.equalsProperties);
            this.hashCodeProperties = toUnmodifiableProperties(builder.hashProperties);
            this.stringProperties = toUnmodifiableProperties(builder.toStringProperties);

            this.isTypedef = builder.isTypedef;
            this.baseType = builder.baseType;
            this.isUnionType = builder.isUnionType;
            this.isUnionTypeBuilder = builder.isUnionTypeBuilder;
        }

        @Override
        public final boolean isTypedef() {
            return this.isTypedef;
        }

        @Override
        public final TypeDefinition<?> getBaseType() {
            return this.baseType;
        }

        @Override
        public final boolean isUnionType() {
            return this.isUnionType;
        }

        @Override
        public final boolean isUnionTypeBuilder() {
            return this.isUnionTypeBuilder;
        }

        @Override
        public final GeneratedTransferObject getSuperType() {
            return this.extendsType;
        }

        @Override
        public final List<GeneratedProperty> getEqualsIdentifiers() {
            return this.equalsProperties;
        }

        @Override
        public final List<GeneratedProperty> getHashCodeIdentifiers() {
            return this.hashCodeProperties;
        }

        @Override
        public final List<GeneratedProperty> getToStringIdentifiers() {
            return this.stringProperties;
        }

        @Override
        public final String toString() {
            if (this.isTypedef) {
                return serializeTypedef(this);
            }
            final StringBuilder builder = new StringBuilder();
            builder.append("GeneratedTransferObject [packageName=");
            builder.append(getPackageName());
            builder.append(", name=");
            builder.append(getName());
            builder.append(", comment=");
            builder.append(", annotations=");
            builder.append(getAnnotations());
            builder.append(getComment());
            builder.append(", extends=");
            builder.append(getSuperType());
            builder.append(", implements=");
            builder.append(getImplements());
            builder.append(", enclosedTypes=");
            builder.append(getEnclosedTypes());
            builder.append(", constants=");
            builder.append(getConstantDefinitions());
            builder.append(", enumerations=");
            builder.append(getEnumerations());
            builder.append(", properties=");
            builder.append(getProperties());
            builder.append(", equalsProperties=");
            builder.append(this.equalsProperties);
            builder.append(", hashCodeProperties=");
            builder.append(this.hashCodeProperties);
            builder.append(", stringProperties=");
            builder.append(this.stringProperties);
            builder.append(", methods=");
            builder.append(getMethodDefinitions());
            builder.append("]");
            return builder.toString();
        }

        public final String serializeTypedef(final Type type) {
            if (!(type instanceof ParameterizedType)) {
                return type.getFullyQualifiedName();
            }

            final ParameterizedType parameterizedType = (ParameterizedType) type;
            final StringBuilder sb = new StringBuilder();
            sb.append(parameterizedType.getRawType().getFullyQualifiedName()).append('<');
            boolean first = true;
            for (final Type parameter : parameterizedType.getActualTypeArguments()) {
                if (first) {
                    first = false;
                } else {
                    sb.append(',');
                }
                sb.append(serializeTypedef(parameter));
            }
            return sb.append('>').toString();
        }
    }
}

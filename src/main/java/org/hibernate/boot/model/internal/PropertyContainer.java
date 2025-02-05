//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.hibernate.boot.model.internal;

import jakarta.persistence.Access;
import jakarta.persistence.Basic;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Transient;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.hibernate.AnnotationException;
import org.hibernate.annotations.Any;
import org.hibernate.annotations.JavaType;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.ManyToAny;
import org.hibernate.annotations.Target;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.common.reflection.XClass;
import org.hibernate.annotations.common.reflection.XProperty;
import org.hibernate.boot.MappingException;
import org.hibernate.boot.jaxb.Origin;
import org.hibernate.boot.jaxb.SourceType;
import org.hibernate.boot.spi.AccessType;
import org.hibernate.internal.CoreMessageLogger;
import org.hibernate.internal.util.StringHelper;
import org.hibernate.internal.util.collections.CollectionHelper;
import org.jboss.logging.Logger;

public class PropertyContainer {
    private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, PropertyContainer.class.getName());
    private final XClass xClass;
    private final XClass entityAtStake;
    private final AccessType classLevelAccessType;
    private final List<XProperty> persistentAttributes;

    public PropertyContainer(XClass clazz, XClass entityAtStake, AccessType defaultClassLevelAccessType) {
        this.xClass = clazz;
        this.entityAtStake = entityAtStake;
        if (defaultClassLevelAccessType == AccessType.DEFAULT) {
            defaultClassLevelAccessType = AccessType.PROPERTY;
        }

        AccessType localClassLevelAccessType = this.determineLocalClassDefinedAccessStrategy();

        assert localClassLevelAccessType != null;

        this.classLevelAccessType = localClassLevelAccessType != AccessType.DEFAULT ? localClassLevelAccessType : defaultClassLevelAccessType;

        assert this.classLevelAccessType == AccessType.FIELD || this.classLevelAccessType == AccessType.PROPERTY || this.classLevelAccessType == AccessType.RECORD;

        List<XProperty> fields = this.xClass.getDeclaredProperties(AccessType.FIELD.getType());
        List<XProperty> getters = this.xClass.getDeclaredProperties(AccessType.PROPERTY.getType());
        List<XProperty> recordComponents = this.xClass.getDeclaredProperties(AccessType.RECORD.getType());
        this.preFilter(fields, getters, recordComponents);
        Map<String, XProperty> persistentAttributesFromGetters = new HashMap();
        Map<String, XProperty> persistentAttributesFromComponents = new HashMap();
        Map<String, XProperty> localAttributeMap;
        if (!recordComponents.isEmpty() && recordComponents.size() == fields.size()) {
            localAttributeMap = new LinkedHashMap();
        } else {
            localAttributeMap = new LinkedHashMap();
        }

        collectPersistentAttributesUsingLocalAccessType(this.xClass, localAttributeMap, persistentAttributesFromGetters, persistentAttributesFromComponents, fields, getters, recordComponents);
        collectPersistentAttributesUsingClassLevelAccessType(this.xClass, this.classLevelAccessType, localAttributeMap, persistentAttributesFromGetters, persistentAttributesFromComponents, fields, getters, recordComponents);
        this.persistentAttributes = verifyAndInitializePersistentAttributes(this.xClass, localAttributeMap);
    }

    private void preFilter(List<XProperty> fields, List<XProperty> getters, List<XProperty> recordComponents) {
        Iterator<XProperty> propertyIterator = fields.iterator();

        while(propertyIterator.hasNext()) {
            XProperty property = (XProperty)propertyIterator.next();
            if (mustBeSkipped(property)) {
                propertyIterator.remove();
            }
        }

        propertyIterator = getters.iterator();

        while(propertyIterator.hasNext()) {
            XProperty property = (XProperty)propertyIterator.next();
            if (mustBeSkipped(property)) {
                propertyIterator.remove();
            }
        }

        propertyIterator = recordComponents.iterator();

        while(propertyIterator.hasNext()) {
            XProperty property = (XProperty)propertyIterator.next();
            if (mustBeSkipped(property)) {
                propertyIterator.remove();
            }
        }

    }

    private static void collectPersistentAttributesUsingLocalAccessType(XClass xClass, Map<String, XProperty> persistentAttributeMap, Map<String, XProperty> persistentAttributesFromGetters, Map<String, XProperty> persistentAttributesFromComponents, List<XProperty> fields, List<XProperty> getters, List<XProperty> recordComponents) {
        Iterator<XProperty> propertyIterator = fields.iterator();

        while(propertyIterator.hasNext()) {
            XProperty xProperty = (XProperty)propertyIterator.next();
            Access localAccessAnnotation = (Access)xProperty.getAnnotation(Access.class);
            if (localAccessAnnotation != null && localAccessAnnotation.value() == jakarta.persistence.AccessType.FIELD) {
                propertyIterator.remove();
                persistentAttributeMap.put(xProperty.getName(), xProperty);
            }
        }

        propertyIterator = getters.iterator();

        while(propertyIterator.hasNext()) {
            XProperty xProperty = (XProperty)propertyIterator.next();
            Access localAccessAnnotation = (Access)xProperty.getAnnotation(Access.class);
            if (localAccessAnnotation != null && localAccessAnnotation.value() == jakarta.persistence.AccessType.PROPERTY) {
                propertyIterator.remove();
                String name = xProperty.getName();
                XProperty previous = (XProperty)persistentAttributesFromGetters.get(name);
                if (previous != null) {
                    throw new MappingException(LOG.ambiguousPropertyMethods(xClass.getName(), HCANNHelper.annotatedElementSignature(previous), HCANNHelper.annotatedElementSignature(xProperty)), new Origin(SourceType.ANNOTATION, xClass.getName()));
                }

                persistentAttributeMap.put(name, xProperty);
                persistentAttributesFromGetters.put(name, xProperty);
            }
        }

        propertyIterator = recordComponents.iterator();

        while(propertyIterator.hasNext()) {
            XProperty xProperty = (XProperty)propertyIterator.next();
            Access localAccessAnnotation = (Access)xProperty.getAnnotation(Access.class);
            if (localAccessAnnotation != null) {
                propertyIterator.remove();
                String name = xProperty.getName();
                persistentAttributeMap.put(name, xProperty);
                persistentAttributesFromComponents.put(name, xProperty);
            }
        }

    }

    private static void collectPersistentAttributesUsingClassLevelAccessType(XClass xClass, AccessType classLevelAccessType, Map<String, XProperty> persistentAttributeMap, Map<String, XProperty> persistentAttributesFromGetters, Map<String, XProperty> persistentAttributesFromComponents, List<XProperty> fields, List<XProperty> getters, List<XProperty> recordComponents) {
        if (classLevelAccessType == AccessType.FIELD) {
            for(XProperty field : fields) {
                String name = field.getName();
                if (!persistentAttributeMap.containsKey(name)) {
                    persistentAttributeMap.put(name, field);
                }
            }
        } else {
            for(XProperty getter : getters) {
                String name = getter.getName();
                XProperty previous = (XProperty)persistentAttributesFromGetters.get(name);
                if (previous != null) {
                    throw new MappingException(LOG.ambiguousPropertyMethods(xClass.getName(), HCANNHelper.annotatedElementSignature(previous), HCANNHelper.annotatedElementSignature(getter)), new Origin(SourceType.ANNOTATION, xClass.getName()));
                }

                if (!persistentAttributeMap.containsKey(name)) {
                    persistentAttributeMap.put(getter.getName(), getter);
                    persistentAttributesFromGetters.put(name, getter);
                }
            }

            for(XProperty recordComponent : recordComponents) {
                String name = recordComponent.getName();
                if (!persistentAttributeMap.containsKey(name)) {
                    persistentAttributeMap.put(name, recordComponent);
                    persistentAttributesFromComponents.put(name, recordComponent);
                }
            }
        }

    }

    public XClass getEntityAtStake() {
        return this.entityAtStake;
    }

    public XClass getDeclaringClass() {
        return this.xClass;
    }

    public AccessType getClassLevelAccessType() {
        return this.classLevelAccessType;
    }

    public Iterable<XProperty> propertyIterator() {
        return this.persistentAttributes;
    }

    private static List<XProperty> verifyAndInitializePersistentAttributes(XClass xClass, Map<String, XProperty> localAttributeMap) {
        ArrayList<XProperty> output = new ArrayList(localAttributeMap.size());

        for(XProperty xProperty : localAttributeMap.values()) {
            if (!xProperty.isTypeResolved() && !discoverTypeWithoutReflection(xClass, xProperty)) {
                String msg = "Property '" + StringHelper.qualify(xClass.getName(), xProperty.getName()) + "' has an unbound type and no explicit target entity (resolve this generics usage issue or set an explicit target attribute with '@OneToMany(target=)' or use an explicit '@Type')";
                throw new AnnotationException(msg);
            }

            output.add(xProperty);
        }

        return CollectionHelper.toSmallList(output);
    }

    private AccessType determineLocalClassDefinedAccessStrategy() {
        AccessType classDefinedAccessType = AccessType.DEFAULT;
        Access access = (Access)this.xClass.getAnnotation(Access.class);
        if (access != null) {
            classDefinedAccessType = AccessType.getAccessStrategy(access.value());
        }

        return classDefinedAccessType;
    }

    private static boolean discoverTypeWithoutReflection(XClass clazz, XProperty property) {
        if (property.isAnnotationPresent(OneToOne.class) && !((OneToOne)property.getAnnotation(OneToOne.class)).targetEntity().equals(Void.TYPE)) {
            return true;
        } else if (property.isAnnotationPresent(OneToMany.class) && !((OneToMany)property.getAnnotation(OneToMany.class)).targetEntity().equals(Void.TYPE)) {
            return true;
        } else if (property.isAnnotationPresent(ManyToOne.class) && !((ManyToOne)property.getAnnotation(ManyToOne.class)).targetEntity().equals(Void.TYPE)) {
            return true;
        } else if (property.isAnnotationPresent(ManyToMany.class) && !((ManyToMany)property.getAnnotation(ManyToMany.class)).targetEntity().equals(Void.TYPE)) {
            return true;
        } else if (property.isAnnotationPresent(Any.class)) {
            return true;
        } else if (property.isAnnotationPresent(ManyToAny.class)) {
            if (!property.isCollection() && !property.isArray()) {
                throw new AnnotationException("Property '" + StringHelper.qualify(clazz.getName(), property.getName()) + "' annotated '@ManyToAny' is neither a collection nor an array");
            } else {
                return true;
            }
        } else if (property.isAnnotationPresent(Basic.class)) {
            return true;
        } else if (property.isAnnotationPresent(Type.class)) {
            return true;
        } else if (property.isAnnotationPresent(JavaType.class)) {
            return true;
        } else if (property.isAnnotationPresent(JdbcTypeCode.class)) {
            return true;
        } else {
            return property.isAnnotationPresent(Target.class);
        }
    }

    private static boolean mustBeSkipped(XProperty property) {
        return property.isAnnotationPresent(Transient.class) || "net.sf.cglib.transform.impl.InterceptFieldCallback".equals(property.getType().getName());
    }
}

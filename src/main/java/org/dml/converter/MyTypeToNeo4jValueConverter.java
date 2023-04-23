package org.dml.converter;

import org.neo4j.driver.internal.value.StringValue;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class MyTypeToNeo4jValueConverter implements GenericConverter {
    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        Set<ConvertiblePair> convertiblePairs = new HashSet<>();
        convertiblePairs.add(new ConvertiblePair(String.class, StringValue.class));
        convertiblePairs.add(new ConvertiblePair(StringValue.class, String.class));
        return convertiblePairs;
    }

    @Override
    public Object convert(Object obj, TypeDescriptor sourceType, TypeDescriptor targetType) {
        // todo: 实现如何从sourceType -> targetType
        // todo: 实现如何从targetType -> sourceType
        return (String.class.isAssignableFrom(sourceType.getType())) ? s2tConvert(obj) : t2sConvert(obj);
    }

    private Object t2sConvert(Object obj) {
        if (obj instanceof StringValue) {
            StringValue sv = (StringValue) obj;
            return sv.asString();
        }
        return null;
    }

    private Object s2tConvert(Object obj) {
        if (obj instanceof String) {
            return new StringValue((String) obj);
        }
        return null;
    }
}

package org.dml.converter;

import org.neo4j.driver.Value;
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
        convertiblePairs.add(new ConvertiblePair(MyTypeJustForTestConverter.class, Value.class));
        convertiblePairs.add(new ConvertiblePair(Value.class, MyTypeJustForTestConverter.class));
        return convertiblePairs;
    }

    @Override
    public Object convert(Object obj, TypeDescriptor sourceType, TypeDescriptor targetType) {
        if (MyTypeJustForTestConverter.class.isAssignableFrom(sourceType.getType())) {
            // todo: 实现如何从sourceType -> targetType
            return s2tConvert(obj);
        }
        // todo: 实现如何从targetType -> sourceType
        return t2sConvert(obj);
    }

    private Object t2sConvert(Object obj) {
        return null;
    }

    private Object s2tConvert(Object obj) {
        return null;
    }
}

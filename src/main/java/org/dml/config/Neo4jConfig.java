package org.dml.config;

import org.dml.converter.MyTypeToNeo4jValueConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.data.neo4j.core.convert.Neo4jConversions;

import java.util.Collections;
import java.util.Set;

@Configuration
public class Neo4jConfig {

    @Bean
    public Neo4jConversions neo4jConversions(MyTypeToNeo4jValueConverter myTypeToNeo4jValueConverter) {
        Set<GenericConverter> converters = Collections.singleton(myTypeToNeo4jValueConverter);
        return new Neo4jConversions(converters);
    }
}

package org.dml.config;

import org.dml.converter.MyTypeToNeo4jValueConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.data.neo4j.core.convert.Neo4jConversions;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class Neo4jConfig{



    /**
     * 针对自定义的类型进行转换, 例如Person类
     *
     * @return
     */
    @Bean
    public Neo4jConversions neo4jConversions() {
        Set<GenericConverter> converters = new HashSet<>();
        converters.add(new MyTypeToNeo4jValueConverter());
        return new Neo4jConversions(converters);
    }


}

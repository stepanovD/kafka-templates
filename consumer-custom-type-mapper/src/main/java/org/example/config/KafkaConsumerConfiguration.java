package org.example.config;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.example.dto.in.InMessageAbstract;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.mapping.DefaultJackson2JavaTypeMapper;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.util.ClassUtils;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfiguration {

    private final String SERVER;
    private final String SERVER_PORT;

    public KafkaConsumerConfiguration(
            @Value("${spring.cloud.stream.kafka.binder.brokers}") String server,
            @Value("${spring.cloud.stream.kafka.binder.defaultBrokerPort}") String port
    ) {
        this.SERVER = server;
        this.SERVER_PORT = port;
    }

    private <T> ConsumerFactory<String, T> typeConsumerFactory(Class<T> clazz) {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, String.format("%s:%s", SERVER, SERVER_PORT));

        JsonDeserializer<T> jsonDeserializer = new JsonDeserializer<>(clazz);
        jsonDeserializer.setTypeMapper(new CustomTypeMapper<>(clazz));

        return new DefaultKafkaConsumerFactory<>(
                props,
                new StringDeserializer(),
                jsonDeserializer);
    }

    private <T> ConcurrentKafkaListenerContainerFactory<String, T> initFactory(Class<T> clazz) {
        ConcurrentKafkaListenerContainerFactory<String, T> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(typeConsumerFactory(clazz));
        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ?> kafkaListenerContainerFactory() {
        return initFactory(InMessageAbstract.class);
    }

    private static class CustomTypeMapper<T> extends DefaultJackson2JavaTypeMapper {
        private final Class<T> clazz;

        public CustomTypeMapper(Class<T> clazz) {
            this.clazz = clazz;
        }

        @Override
        public JavaType toJavaType(Headers headers) {
            try {
                return TypeFactory.defaultInstance()
                        .constructType(ClassUtils.forName(clazz.getName(), getClassLoader()));
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

package com.targa.labs.dev.demokafka;

import badgeevent.BadgeEvent;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConfiguration {


    private String topic= "newesttopic";

    @Value("${spring.kafka.bootstrap-servers}")
    private String brokerAddress;


    /*
      @Bean public KafkaMessageDrivenChannelAdapter<String, String> adapter(
      KafkaMessageListenerContainer<String, String> container) {
      KafkaMessageDrivenChannelAdapter<String, String>
      kafkaMessageDrivenChannelAdapter = new
      KafkaMessageDrivenChannelAdapter<>( container, ListenerMode.record);
      kafkaMessageDrivenChannelAdapter.setOutputChannel(received()); return
      kafkaMessageDrivenChannelAdapter; }

      @Bean public QueueChannel received() { return new QueueChannel(); }
     */

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, BadgeEvent>> kafkaListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, BadgeEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setConcurrency(3);
        factory.getContainerProperties().setPollTimeout(30000);
        return factory;

    }

    /*
     * @Bean public KafkaMessageListenerContainer<String, String> container()
     * throws Exception { ContainerProperties properties = new
     * ContainerProperties(this.topic); // set more properties return new
     * KafkaMessageListenerContainer<>(consumerFactory(), properties); }
     */

    @Bean
    public ConsumerFactory<String, BadgeEvent> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, this.brokerAddress);
        // props.put(ConsumerConfig.GROUP_ID_CONFIG, "mygroup");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest"); // earliest
        // smallest
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "hello");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "100");
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "15000");
        props.put("schema.registry.url", "http://13.90.61.176:8081");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class);
        props.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, true);
        //props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "smallest");
        return new DefaultKafkaConsumerFactory<>(props);
    }

}

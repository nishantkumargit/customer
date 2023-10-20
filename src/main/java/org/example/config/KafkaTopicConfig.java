package org.example.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;

public class KafkaTopicConfig {

    private final String topicName = "customerNotificationTopic";
    private final int numPartitions = 1;
    private final short replicationFactor = 1;

    @Bean
    public NewTopic customerNotificationTopic() {
        return new NewTopic(topicName, numPartitions, replicationFactor);
    }
}

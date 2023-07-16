package run.yuyang.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class ProducerDemo {

    public static void main(String[] args) {
        sendOneSync();
    }


    private static void sendOneSync() {
        Properties kafkaProps = new Properties();
        // 连接的集群："bootstrap.servers"
        kafkaProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.3.14:9092");

        // 指定key value的序列化器："key.serializer"、"value.serializer"
        // "org.apache.kafka.common.serialization.StringSerializer"
        kafkaProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        kafkaProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        KafkaProducer<String, String> producer = new KafkaProducer<>(kafkaProps);

        ProducerRecord<String, String> record = new ProducerRecord<>("first", "Precision Products", "France");

        try {
            producer.send(record);
        } catch (Exception e) {
            e.printStackTrace();
        }
        producer.close();
    }

}

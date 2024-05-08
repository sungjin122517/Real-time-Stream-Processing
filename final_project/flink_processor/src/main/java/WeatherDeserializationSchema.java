import java.util.Objects;

public class WeatherDeserializationSchema extends AbstractDeserializationSchema<Weather>{
    /*
     * Deserializer takes in raw msg from Kafka and map it into a Weather object
     */

    private static final long serialVersionUUID = 1L;

    private transient ObjectMapper objectMapper;

    @Override
    public void open(InitalizationContext context) {
        objectMapper = JsonMapper.builder()
            .build()
            .registerModule(new JavaTimeModule());
    }

    @Override
    public Weather deserialize(byte[] message) throws IOException {
        return objectMapper.readValue(message, Weather.class);
    }
}

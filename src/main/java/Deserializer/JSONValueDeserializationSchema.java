package Deserializer;

import dto.Update;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;

import java.io.IOException;

public class JSONValueDeserializationSchema implements DeserializationSchema<Update> {

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public void open(InitializationContext context) throws Exception {
        DeserializationSchema.super.open(context);
    }

    @Override
    public Update deserialize(byte[] bytes) throws IOException {
        return objectMapper.readValue(bytes, Update.class);
    }

    @Override
    public boolean isEndOfStream(Update update) {
        return false;
    }

    @Override
    public TypeInformation<Update> getProducedType() {
        return TypeInformation.of(Update.class);
    }
}

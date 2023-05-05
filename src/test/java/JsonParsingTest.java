import MyObject.MyObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

public class JsonParsingTest {

    @Test
    void jsonParseTest() throws IOException {
        // создаем объект ObjectMapper для разбора JSON

        ObjectMapper objectMapper = new ObjectMapper();

        // считываем JSON из файла и преобразуем его в объект
        MyObject myObject = objectMapper.readValue(new File("src/123.json"), MyObject.class);

        // проверяем соответствие значений объекта и JSON
        Assertions.assertEquals("MyObject", myObject.getName());
        Assertions.assertArrayEquals(new String[]{"value1", "value2", "value3"}, myObject.getValues());
    }

}

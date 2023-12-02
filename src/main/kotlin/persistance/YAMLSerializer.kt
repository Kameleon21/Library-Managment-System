package persistance

import models.Book
import org.yaml.snakeyaml.Yaml
import java.io.File
import models.Person

class YAMLSerializer(private val file: File):Serializer {

    override fun read(): Any {
        val yaml = Yaml()
        return file.reader().use { reader ->
            yaml.load<Any>(reader)
        }
    }

    override fun write(obj: Any?) {
        val yaml = Yaml()
        file.writer().use { writer ->
            yaml.dump(obj, writer)
        }
    }
}
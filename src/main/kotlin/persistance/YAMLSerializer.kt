package persistance

import models.Book
import org.yaml.snakeyaml.Yaml
import java.io.File
import models.Person

/**
 * YAMLSerializer class for serializing and deserializing objects to and from YAML.
 * Implements the Serializer interface using the SnakeYAML library for YAML operations.
 *
 * @property file The file used for reading from and writing to.
 */
class YAMLSerializer(private val file: File):Serializer {

    /**
     * Reads from a YAML file and deserializes its content into an object.
     * The method uses SnakeYAML to convert YAML content back into object form.
     *
     * @return The deserialized object from the YAML file.
     * @throws Exception If there is an error during the file reading or deserialization process.
     */
    override fun read(): Any {
        val yaml = Yaml()
        return file.reader().use { reader ->
            yaml.load<Any>(reader)
        }
    }

    /**
     * Serializes an object and writes it to a YAML file.
     * The method uses SnakeYAML to convert the object into YAML form.
     * It is capable of handling serialization of any object.
     *
     * @param obj The object to be serialized and written to the file.
     * @throws Exception If there is an error during the file writing or serialization process.
     */
    override fun write(obj: Any?) {
        val yaml = Yaml()
        file.writer().use { writer ->
            yaml.dump(obj, writer)
        }
    }
}
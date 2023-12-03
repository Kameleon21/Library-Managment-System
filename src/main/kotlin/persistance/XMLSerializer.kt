package persistance

import com.thoughtworks.xstream.XStream
import models.Book
import java.io.FileReader
import java.io.FileWriter
import models.Person
import java.io.File

/**
 * XMLSerializer class for serializing and deserializing objects to and from XML.
 * Implements the Serializer interface using XStream library for XML operations.
 *
 * @property file The file used for reading from and writing to.
 */

class XMLSerializer(private val file: File):Serializer {

    /**
     * Reads from an XML file and deserializes its content into an object.
     * The method uses XStream to convert XML back into object form.
     * Currently, it is set up to handle Book and Person objects.
     *
     * @return The deserialized object from the XML file.
     * @throws Exception If there is an error during the file reading or deserialization process.
     */
    @Throws(Exception::class)
    override fun read(): Any {
        val xstream = XStream()
        xstream.allowTypes(arrayOf(Book::class.java, Person::class.java))
        val inputStream = xstream.createObjectInputStream(FileReader(file))
        val obj = inputStream.readObject() as Any
        inputStream.close()
        return obj
    }

    /**
     * Serializes an object and writes it to an XML file.
     * The method uses XStream to convert the object into XML form.
     * It is capable of handling serialization of any object, specifically Book and Person objects in this context.
     *
     * @param obj The object to be serialized and written to the file.
     * @throws Exception If there is an error during the file writing or serialization process.
     */
    @Throws(Exception::class)
    override fun write(obj: Any?) {
        val xstream = XStream()
        xstream.allowTypes(arrayOf(Book::class.java, Person::class.java))
        val outputStream = xstream.createObjectOutputStream(FileWriter(file))
        outputStream.writeObject(obj)
        outputStream.close()
    }
}
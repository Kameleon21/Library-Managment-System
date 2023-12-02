package persistance

import com.thoughtworks.xstream.XStream
import models.Book
import java.io.FileReader
import java.io.FileWriter
import models.Person
import java.io.File

class XMLSerializer(private val file: File):Serializer {

    @Throws(Exception::class)
    override fun read(): Any {
        val xstream = XStream()
        xstream.allowTypes(arrayOf(Book::class.java, Person::class.java))
        val inputStream = xstream.createObjectInputStream(FileReader(file))
        val obj = inputStream.readObject() as Any
        inputStream.close()
        return obj
    }

    @Throws(Exception::class)
    override fun write(obj: Any?) {
        val xstream = XStream()
        xstream.allowTypes(arrayOf(Book::class.java, Person::class.java))
        val outputStream = xstream.createObjectOutputStream(FileWriter(file))
        outputStream.writeObject(obj)
        outputStream.close()
    }
}
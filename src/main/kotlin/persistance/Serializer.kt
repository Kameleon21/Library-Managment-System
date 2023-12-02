package persistance

import models.Book
import models.Person
import java.lang.reflect.Member

interface Serializer {

    @Throws(Exception::class)
    fun write(obj: Any?)

    @Throws(Exception::class)
    fun read(): Any?
}
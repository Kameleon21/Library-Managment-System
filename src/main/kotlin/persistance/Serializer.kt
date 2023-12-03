package persistance

import models.Book
import models.Person
import java.lang.reflect.Member

/**
 * Interface defining the contract for serialization and deserialization of objects in the library system.
 * This interface allows for persisting and retrieving data, such as books and members, to and from a storage medium.
 */
interface Serializer {

    /**
     * Serializes and writes an object to a storage medium.
     * The specific implementation should handle the serialization logic for different types of objects.
     *
     * @param obj The object to be serialized and written. This could be any type of object.
     * @throws Exception If an error occurs during the write process.
     */
    @Throws(Exception::class)
    fun write(obj: Any?)

    /**
     * Reads and deserializes an object from a storage medium.
     * The specific implementation should handle the deserialization logic for different types of objects.
     * The method should return the deserialized object, or null if the operation fails.
     *
     * @return The deserialized object, or null if an error occurs.
     * @throws Exception If an error occurs during the read process.
     */

    @Throws(Exception::class)
    fun read(): Any?
}
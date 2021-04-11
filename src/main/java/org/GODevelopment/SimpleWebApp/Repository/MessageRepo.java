package org.GODevelopment.SimpleWebApp.Repository;

import org.GODevelopment.SimpleWebApp.EntityModel.Message;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * public interface CrudRepository<T, ID> extends Repository<T, ID> {
 *
 *   <S extends T> S save(S entity);
 *
 *   Optional<T> findById(ID primaryKey);
 *
 *   Iterable<T> findAll();
 *
 *   long count();
 *
 *   void delete(T entity);
 *
 *   boolean existsById(ID primaryKey);
 *
 *   // … more functionality omitted.
 *
 * }
 * Saves the given entity.
 * Returns the entity identified by the given ID.
 * Returns all entities.
 * Returns the number of entities.
 * Deletes the given entity.
 * Indicates whether an entity with the given ID exists.
 *
 *  You need to create the repository that holds Message records
 *  Spring automatically implements this repository interface in a bean that has the same name (with a change in the case
 *  This will be AUTO IMPLEMENTED by Spring into a Bean called MessageRepo
 *  CRUD refers Create, Read, Update, Delete
 */

public interface MessageRepo extends CrudRepository<Message, Long> {

    // Declare query methods on the interface.
    // Имя метода составляется по правилам Спринга, он его превращает в запрос в БД
    List<Message> findByTag(String tag);
}

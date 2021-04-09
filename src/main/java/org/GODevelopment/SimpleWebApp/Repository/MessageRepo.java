package org.GODevelopment.SimpleWebApp.Repository;

import org.GODevelopment.SimpleWebApp.EntityModel.Message;
import org.springframework.data.repository.CrudRepository;

// You need to create the repository that holds Message records
// Spring automatically implements this repository interface in a bean that has the same name (with a change in the case
// This will be AUTO IMPLEMENTED by Spring into a Bean called MessageRepo
// CRUD refers Create, Read, Update, Delete

public interface MessageRepo extends CrudRepository<Message, Long> {
}

package org.moussaud.micropets.pets;

import org.springframework.data.repository.CrudRepository;

public interface DogRepository extends CrudRepository<DogBean, Long> {

}

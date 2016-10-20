
package ru.javafx.repository;

import javax.annotation.security.RolesAllowed;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;
import ru.javafx.entity.Word;

@Transactional
@RepositoryRestResource(collectionResourceRel = "words", path = "words")
//@RolesAllowed({"USER"})
public interface WordRepository extends PagingAndSortingRepository<Word, Integer> {
    
}

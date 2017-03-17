package alch.repository;

import alch.model.Grid;
import alch.model.user.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@Qualifier(value="gridRepository")
public interface GridRepository extends CrudRepository<Grid, Long> {
    Iterable<Grid> findAllByOwner(User user);
}

package api.interfaces.crud;

import api.exceptions.NotFoundException;
import api.interfaces.IEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDelete<E extends IEntity<T>, T, R extends JpaRepository<E, T>> {
    R getRepository();

    String getEntityNotFoundMessage();

    default void delete(T id) {
        if (Boolean.FALSE.equals(this.getRepository().existsById(id))) {
            throw new NotFoundException(this.getEntityNotFoundMessage());
        }

        this.getRepository().deleteById(id);
    }
}

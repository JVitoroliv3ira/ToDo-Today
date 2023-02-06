package api.interfaces.crud;

import api.exceptions.NotFoundException;
import api.interfaces.utils.IEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUpdate<E extends IEntity<T>, T, R extends JpaRepository<E, T>> {
    R getRepository();

    String getEntityNotFoundMessage();

    default E update(E entity) {
        if (Boolean.FALSE.equals(this.getRepository().existsById(entity.getId()))) {
            throw new NotFoundException(this.getEntityNotFoundMessage());
        }

        return this.getRepository().save(entity);
    }
}

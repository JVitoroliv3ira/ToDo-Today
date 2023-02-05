package api.interfaces.crud;

import api.exceptions.NotFoundException;
import api.interfaces.IEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRead<E extends IEntity<T>, T, R extends JpaRepository<E, T>> {
    R getRepository();

    String getEntityNotFoundMessage();

    default E read(T id) {
        return this.getRepository()
                .findById(id)
                .orElseThrow(() -> new NotFoundException(this.getEntityNotFoundMessage()));
    }
}

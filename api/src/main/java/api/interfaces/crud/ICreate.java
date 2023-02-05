package api.interfaces.crud;

import api.interfaces.utils.IEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICreate<E extends IEntity<T>, T, R extends JpaRepository<E, T>> {
    R getRepository();

    default E create(E entity) {
        entity.setId(null);
        return this.getRepository().save(entity);
    }
}

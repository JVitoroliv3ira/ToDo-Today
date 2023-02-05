package api.interfaces.crud;

import api.interfaces.IEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICrudService<E extends IEntity<T>, T, R extends JpaRepository<E, T>>
        extends ICreate<E, T, R>, IRead<E, T, R>, IUpdate<E, T, R>, IDelete<E, T, R> {
}

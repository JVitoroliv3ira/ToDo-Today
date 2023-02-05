package api.interfaces;

public interface IEntity<T> {
    T getId();

    void setId(T id);
}

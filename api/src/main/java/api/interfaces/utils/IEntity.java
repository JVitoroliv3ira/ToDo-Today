package api.interfaces.utils;

public interface IEntity<T> {
    T getId();

    void setId(T id);
}

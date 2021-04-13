package Repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class BaseRepository<T> {
    protected List<T> list = new ArrayList<T>();

    public void add(T entity) {
        list.add(entity);
    }

    public void remove(T entity) {
        list.remove(entity);
    }

    public void update(int id, T entity) {
        list.set(id, entity);
    }

    public T get(int id) {
        return list.get(id);
    }

    public List<T> getAll() {
        return list;
    }

    public List<T> getAllByPredicate(Predicate<T> predicate) {
        return list.stream().filter(predicate).collect(Collectors.toList());
    }
}

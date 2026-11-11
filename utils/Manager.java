package KexTools.utils;

import java.util.List;
import java.util.NoSuchElementException;

public abstract class Manager<T>{
	
	private final List<T> list;

    protected Manager(List<T> list) {
        this.list = list;
        onCreated();
    }
    public final void add(T object) {
        list.add(object);
    }
    public final T get(int index) {
        return list.get(index);
    }
    @SuppressWarnings("all")
    public <E extends T> E get(Class<? extends T> clazz) {
        return (E) list.stream().filter(element -> element.getClass().equals(clazz)).findFirst().orElseThrow((() -> new NoSuchElementException("RETARD ALERT: Element belonging to class '" + clazz.getName() + "' not found")));
    }
    public List<T> getObjects() {
        return list;
    }
    public void onCreated() {

    }
	
}

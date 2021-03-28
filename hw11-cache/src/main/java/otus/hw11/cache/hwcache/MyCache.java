package otus.hw11.cache.hwcache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import otus.hw11.cache.exception.KeyNotFound;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.stream.Collectors;

/**
 * @author sergey
 * created on 14.12.18.
 */
public class MyCache<K, V> implements HwCache<K, V> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final Map<K, V> weakMap;
    private final List<WeakReference<HwListener<K, V>>> listeners = new ArrayList<>();

    public MyCache(){
        this.weakMap = new WeakHashMap<>();
    }

    @Override
    public void put(K key, V value) {
        weakMap.put(key, value);
        notifyListeners(key, value, "put");
    }

    @Override
    public void remove(K key) {
        V value = weakMap.remove(key);
        if (value == null){
            throw new KeyNotFound();
        }
        notifyListeners(key, value, "remove");
    }

    @Override
    public V get(K key) {
        V value = weakMap.get(key);
        notifyListeners(key, value, "get");
        return value;
    }

    @Override
    public void addListener(HwListener<K, V> listener) {
        if (isExistListener(listener)){
            logger.info("listener is already in list");
        } else {
            listeners.add(new WeakReference<>(listener));
        }
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        var weakListenersToRemove = listeners.stream()
                .filter(l -> listener.equals(l.get()))
                .collect(Collectors.toList());
        listeners.removeAll(weakListenersToRemove);
    }

    private boolean isExistListener(HwListener<K, V> listener) {
        return listeners.stream().anyMatch(l -> l.equals(listener));
    }

    private void notifyListeners(K key, V value, String action) {
        var iterator = listeners.iterator();

        for (var listenerItem: listeners) {
            var listener = listenerItem.get();
            if (listener != null) {
                try {
                    listener.notify(key, value, action);
                } catch (Exception e) {
                    logger.error("Error notification: {}", e.getMessage());
                }
            } else {
                iterator.remove();
            }
        }
    }
}
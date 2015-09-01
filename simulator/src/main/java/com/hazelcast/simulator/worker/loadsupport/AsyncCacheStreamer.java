package com.hazelcast.simulator.worker.loadsupport;

import com.hazelcast.cache.ICache;
import com.hazelcast.core.ICompletableFuture;

public class AsyncCacheStreamer<K, V> extends AbstractAsyncStreamer<K, V> {
    private final ICache<K, V> cache;

    AsyncCacheStreamer(ICache<K, V> cache) {
        this.cache = cache;
    }

    @Override
    ICompletableFuture storeAsync(K key, V value) {
        return (ICompletableFuture) cache.putAsync(key, value);
    }
}

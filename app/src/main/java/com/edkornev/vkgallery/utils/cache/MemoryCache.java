package com.edkornev.vkgallery.utils.cache;

import android.graphics.Bitmap;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Created by Eduard on 10.11.2016.
 */
public class MemoryCache {

    private final Map<String, Reference<Bitmap>> mSoftMap = Collections.synchronizedMap(new HashMap<String, Reference<Bitmap>>());

    public Bitmap get(String key) {
        Bitmap result = null;
        Reference<Bitmap> reference = mSoftMap.get(key);
        if (reference != null) {
            result = reference.get();
        }
        return result;
    }

    public boolean put(String key, Bitmap value) {
        mSoftMap.put(key, createReference(value));
        return true;
    }

    public Bitmap remove(String key) {
        Reference<Bitmap> bmpRef = mSoftMap.remove(key);
        return bmpRef == null ? null : bmpRef.get();
    }

    public Collection<String> keys() {
        synchronized (mSoftMap) {
            return new HashSet<String>(mSoftMap.keySet());
        }
    }

    public void clear() {
        mSoftMap.clear();
    }

    private Reference<Bitmap> createReference(Bitmap value) {
        return new WeakReference<Bitmap>(value);
    }
}

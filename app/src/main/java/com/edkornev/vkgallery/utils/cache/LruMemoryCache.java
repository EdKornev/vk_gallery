package com.edkornev.vkgallery.utils.cache;

import android.graphics.Bitmap;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by kornev on 11/11/16.
 */
public class LruMemoryCache {

    private final LinkedHashMap<String, Bitmap> mHardCache = new LinkedHashMap<String, Bitmap>(0, 0.75f, true);

    private final int mMaxSize = 48 * 1024 * 1024;
    private int mSize;

    public final Bitmap get(String key) {
        if (key == null) {
            throw new NullPointerException("key == null");
        }

        synchronized (this) {
            return mHardCache.get(key);
        }
    }

    public final boolean put(String key, Bitmap value) {
        if (key == null || value == null) {
            return false;
        }

        synchronized (this) {
            mSize += sizeOf(key, value);
            Bitmap previous = mHardCache.put(key, value);
//            if (previous != null) {
//                mSize -= sizeOf(key, previous);
//            }
        }

        trimToSize(mMaxSize);
        return true;
    }

    private void trimToSize(int maxSize) {
        while (true) {
            String key;
            Bitmap value;
            synchronized (this) {
                if (mSize < 0 || (mHardCache.isEmpty() && mSize != 0)) {
                    throw new IllegalStateException(getClass().getName() + ".sizeOf() is reporting inconsistent results!");
                }

                if (mSize <= maxSize || mHardCache.isEmpty()) {
                    break;
                }

                Map.Entry<String, Bitmap> toEvict = mHardCache.entrySet().iterator().next();

                if (toEvict == null) {
                    break;
                }

                key = toEvict.getKey();
                value = toEvict.getValue();
                mHardCache.remove(key);
                mSize -= sizeOf(key, value);
            }
        }
    }


    public final Bitmap remove(String key) {
        if (key == null) {
            throw new NullPointerException();
        }

        synchronized (this) {
            Bitmap previous = mHardCache.remove(key);
            if (previous != null) {
                mSize -= sizeOf(key, previous);
            }
            return previous;
        }
    }

    public Collection<String> keys() {
        synchronized (this) {
            return new HashSet<String>(mHardCache.keySet());
        }
    }

    public void clear() {
        trimToSize(-1); // -1 will evict 0-sized elements
    }

    private int sizeOf(String key, Bitmap value) {
        return value.getRowBytes() * value.getHeight();
    }
}

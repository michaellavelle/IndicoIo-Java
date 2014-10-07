package io.indico.api.listener;

import java.util.Map;

public interface OnMapResponseListener<T, V> extends OnErrorListener {
    public void onSuccess(Map<T, V> result);
}

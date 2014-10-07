package io.indico.api.listener;

public interface OnObjectResponseListener<T> extends OnErrorListener {
    public void onSuccess(T t);
}

package io.indico.api.listener;

import io.indico.api.exception.IndicoException;

public interface OnErrorListener {
    public void onError(IndicoException e);
}

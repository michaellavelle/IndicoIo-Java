package io.indico.api.model.error;

import com.google.gson.annotations.SerializedName;

/**
 * Model is used for parsing json response when server might report about wrong use of the api.
 * If api's user can put wrong parameter we should extend from this model new one where described case takes place.
 */
public class Error {

    @SerializedName("error")
    protected String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }
}

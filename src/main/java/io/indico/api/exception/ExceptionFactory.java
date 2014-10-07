package io.indico.api.exception;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class ExceptionFactory {

    public static IndicoException get(RetrofitError retrofitError) {

        return retrofitError.isNetworkError() ?
                new ConnectionException(retrofitError.getCause()) :
                getServerException(retrofitError);
    }

    public static ParameterException getParameterException(String message) {
        return new ParameterException(message);
    }

    private static IndicoException getServerException(RetrofitError retrofitError) {
        Response response = retrofitError.getResponse();

        return response != null ?
                new ServerException(response.getStatus(), retrofitError.getCause()) :
                new ServerException(retrofitError.getCause());
    }
}
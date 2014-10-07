package io.indico.api.text;

import io.indico.api.Config;
import io.indico.api.exception.ExceptionFactory;
import io.indico.api.exception.IndicoException;
import io.indico.api.listener.OnMapResponseListener;
import io.indico.api.listener.OnObjectResponseListener;
import io.indico.api.model.text.request.Text;
import io.indico.api.model.text.response.Sentiment;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import java.util.Map;

/**
 * Api for work with text.
 */
public class IndicoTextApi {

    private static IndicoTextApi instance;

    private final IndicoTextService indicoTextService;

    private IndicoTextApi() {
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(Config.API_URL)
                .build();

        indicoTextService = adapter.create(IndicoTextService.class);
    }

    /**
     * Returns instance of the api for work with text.
     *
     * @return Text api instance.
     */
    public static IndicoTextApi getInstance() {
        if (instance == null) {
            instance = new IndicoTextApi();
        }

        return instance;
    }

    /**
     * Given input text, returns a probability distribution over 33 possible languages of what language
     * the text was written in.
     *
     * @param text               The text to be analyzed.
     * @param onResponseListener Callback which will be called when operation ends.
     */
    public void language(String text, final OnMapResponseListener<String, Double> onResponseListener) {
        this.indicoTextService.language(new Text(text), new Callback<Map<String, Double>>() {
            @Override
            public void success(Map<String, Double> stringDoubleMap, Response response) {
                onResponseListener.onSuccess(stringDoubleMap);
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                onResponseListener.onError(ExceptionFactory.get(retrofitError));
            }
        });
    }

    /**
     * Given input text, returns a probability distribution over 33 possible languages of what language
     * the text was written in.
     *
     * @param text The text to be analyzed.
     * @return Language probability pairs.
     * @throws IndicoException
     */
    public Map<String, Double> language(String text) throws IndicoException {
        try {
            return this.indicoTextService.language(new Text(text));
        } catch (RetrofitError error) {
            throw ExceptionFactory.get(error);
        }
    }

    /**
     * Given input text, returns a probability distribution over the political alignment of the speaker.
     *
     * @param text               The text to be analyzed.
     * @param onResponseListener Callback which will be called when operation ends.
     */
    public void politicalSentiment(String text, final OnMapResponseListener<String, Double> onResponseListener) {
        this.indicoTextService.politicalSentiment(new Text(text), new Callback<Map<String, Double>>() {
            @Override
            public void success(Map<String, Double> stringDoubleMap, Response response) {
                onResponseListener.onSuccess(stringDoubleMap);
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                onResponseListener.onError(ExceptionFactory.get(retrofitError));
            }
        });
    }

    /**
     * Given input text, returns a probability distribution over the political alignment of the speaker.
     *
     * @param text The text to be analyzed.
     * @return Political alignment probability pairs.
     * @throws IndicoException
     */
    public Map<String, Double> politicalSentiment(String text) throws IndicoException {
        try {
            return this.indicoTextService.politicalSentiment(new Text(text));
        } catch (RetrofitError error) {
            throw ExceptionFactory.get(error);
        }
    }

    /**
     * Given input text, returns a scalar estimate of the sentiment of that text.
     * Values are roughly in the range 0 to 1 with 0.5 indicating neutral sentiment.
     * For reference, 0 suggests very negative sentiment and 1 suggests very positive sentiment.
     *
     * @param text             The text to be analyzed.
     * @param responseListener Callback which will be called when operation ends.
     */
    public void sentiment(String text, final OnObjectResponseListener<Double> responseListener) {
        this.indicoTextService.sentiment(new Text(text), new Callback<Sentiment>() {
            @Override
            public void success(Sentiment sentiment, Response response) {
                responseListener.onSuccess(sentiment.getValue());
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                responseListener.onError(ExceptionFactory.get(retrofitError));
            }
        });
    }

    /**
     * Given input text, returns a scalar estimate of the sentiment of that text.
     * Values are roughly in the range 0 to 1 with 0.5 indicating neutral sentiment.
     * For reference, 0 suggests very negative sentiment and 1 suggests very positive sentiment.
     *
     * @param text The text to be analyzed.
     * @return Value that indicates sentiment.
     * @throws IndicoException
     */
    public double sentiment(String text) throws IndicoException {
        try {
            Sentiment sentiment = this.indicoTextService.sentiment(new Text(text));

            return sentiment.getValue();
        } catch (RetrofitError error) {
            throw ExceptionFactory.get(error);
        }
    }
}

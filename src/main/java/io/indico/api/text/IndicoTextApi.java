package io.indico.api.text;

import java.util.Map;
import java.util.HashMap;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import io.indico.api.Config;
import io.indico.api.exception.ExceptionFactory;
import io.indico.api.exception.IndicoException;
import io.indico.api.listener.OnMapResponseListener;
import io.indico.api.listener.OnObjectResponseListener;
import io.indico.api.model.text.request.Text;
import io.indico.api.model.text.request.TextList;

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

    private IndicoTextService IndicoTextApi(String cloudUrl) {
        String fullUrl = "https://" + cloudUrl + ".indico.domains";
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(fullUrl)
                .build();

        return adapter.create(IndicoTextService.class);
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
     * Returns either an indicoTextService with the endpoint
     * or, if it exists, the endpoint set in the parameter 'cloud' in auth
     */
    private IndicoTextService whichTextService(Map<String, String> auth) {
        if (auth != null && auth.get("cloud") != null) {
            return IndicoTextApi(auth.get("cloud"));
        } else if (Config.CLOUD != null) {
            return IndicoTextApi(Config.CLOUD);
        } else {
            return this.indicoTextService;
        }
    }

    /**
     * Given input text, returns a probability distribution over 33 possible languages of what language
     * the text was written in.
     *
     * @param text: The text to be analyzed.
     * @param auth: a map optionally containning a username, password, and cloud url.
     * @param onResponseListener: Callback which will be called when operation ends.
     */
    public void language(String text, Map<String, String> auth, final OnMapResponseListener<String, Double> onResponseListener) throws IndicoException {
        Callback<Map<String, Map<String, Double>>> callback = new Callback<Map<String, Map<String, Double>>>() {
                @Override
                public void success(Map<String, Map<String, Double>> stringDoubleMap, Response response) {
                    onResponseListener.onSuccess(stringDoubleMap.get("results"));
                }

                @Override
                public void failure(RetrofitError retrofitError) {
                    onResponseListener.onError(ExceptionFactory.get(retrofitError));
                }
            };

        IndicoTextService thisIndicoTextService = whichTextService(auth);
        String credentials= Config.findCorrectApiKey(auth.get("api_key"));
        thisIndicoTextService.language(new Text(text), credentials, callback);

    }

    /**
     * Given a list of input texts, returns a list of a probability distribution over 33 possible
     * languages of what language each text was written in.
     *
     * @param text: The text to be analyzed.
     * @param auth: a map optionally containning a username, password, and cloud url.
     * @param onResponseListener: Callback which will be called when operation ends.
     */
    public void batchLanguage(List<String> text, Map<String, String> auth,
            final OnObjectResponseListener<List<Map<String, Double>>> onResponseListener) throws IndicoException {
        Callback<Map<String, List<Map<String, Double>>>> callback = new Callback<Map<String, List<Map<String, Double>>>>() {
                @Override
                public void success(Map<String, List<Map<String, Double>>> stringDoubleMap, Response response) {
                    onResponseListener.onSuccess(stringDoubleMap.get("results"));
                }

                @Override
                public void failure(RetrofitError retrofitError) {
                    onResponseListener.onError(ExceptionFactory.get(retrofitError));
                }
            };

        IndicoTextService thisIndicoTextService = whichTextService(auth);
        String credentials= Config.findCorrectApiKey(auth.get("api_key"));
        thisIndicoTextService.batchLanguage(new TextList(text), credentials, callback);
    }

    public void batchLanguage(List<String> text, final OnObjectResponseListener<List<Map<String, Double>>> onResponseListener)
            throws IndicoException{
        HashMap<String, String> emptyHash = new HashMap<String, String>();
        batchLanguage(text, emptyHash, onResponseListener);
    }

    /**
     * Given input text, returns a probability distribution over 33 possible languages of what language
     * the text was written in.
     *
     * @param text: The text to be analyzed.
     * @param auth: a map optionally containning a username, password, and cloud url.
     * @return Language probability pairs.
     * @throws IndicoException
     */
    public Map<String, Double> language(String text, Map<String, String> auth) throws IndicoException {
        try {
            IndicoTextService thisIndicoTextService = whichTextService(auth);
            return thisIndicoTextService.language(new Text(text), Config.findCorrectApiKey(auth.get("api_key"))).get("results");
        } catch (RetrofitError error) {
            throw ExceptionFactory.get(error);
        }
    }

    public void language(String text, final OnMapResponseListener<String, Double> onResponseListener)
            throws IndicoException{
        HashMap<String, String> emptyHash = new HashMap<String, String>();
        language(text, emptyHash, onResponseListener);
    }

    public Map<String, Double> language(String text) throws IndicoException {
        HashMap<String, String> emptyHash = new HashMap<String, String>();
        return language(text, emptyHash);
    }

    /**
     * Given a list of input texts, returns a list of a probability distribution over 33 possible
     * languages of what language each text was written in.
     *
     * @param text: A list of the text to be analyzed.
     * @param auth: a map optionally containning a username, password, and cloud url.
     * @param onResponseListener: Callback which will be called when operation ends.
     */
    public List<Map<String, Double>> batchLanguage(List<String> text, Map<String, String> auth) throws IndicoException {
        try {
            IndicoTextService thisIndicoTextService = whichTextService(auth);
            return thisIndicoTextService.batchLanguage(new TextList(text), Config.findCorrectApiKey(auth.get("api_key"))).get("results");
        } catch (RetrofitError error) {
            throw ExceptionFactory.get(error);
        }
    }

    public List<Map<String, Double>> batchLanguage(List<String> text) throws IndicoException {
        HashMap<String, String> emptyHash = new HashMap<String, String>();
        return batchLanguage(text, emptyHash);
    }

    /**
     * Given input text, returns a probability distribution over the political alignment of the speaker.
     *
     * @param text: The text to be analyzed.
     * @param auth: a map optionally containning a username, password, and cloud url.
     * @param onResponseListener: Callback which will be called when operation ends.
     */
    public void politicalSentiment(String text, Map<String, String> auth,
            final OnMapResponseListener<String, Double> onResponseListener) throws IndicoException {
        Callback<Map<String, Map<String, Double>>> callback = new Callback<Map<String, Map<String, Double>>>() {
                @Override
                public void success(Map<String, Map<String, Double>> stringDoubleMap, Response response) {
                    onResponseListener.onSuccess(stringDoubleMap.get("results"));
                }

                @Override
                public void failure(RetrofitError retrofitError) {
                    onResponseListener.onError(ExceptionFactory.get(retrofitError));
                }
            };

        IndicoTextService thisIndicoTextService = whichTextService(auth);
        String credentials= Config.findCorrectApiKey(auth.get("api_key"));
        thisIndicoTextService.politicalSentiment(new Text(text), credentials, callback);
    }

    /**
     * Given a list of input texts, returns a list of probability distributions over the political alignment of the speakers
     *
     * @param text: The text to be analyzed.
     * @param auth: a map optionally containning a username, password, and cloud url.
     * @param onResponseListener: Callback which will be called when operation ends.
     */
    public void batchPoliticalSentiment(List<String> text, Map<String, String> auth,
            final OnObjectResponseListener<List<Map<String, Double>>> onResponseListener) throws IndicoException {
        Callback<Map<String, List<Map<String, Double>>>> callback = new Callback<Map<String, List<Map<String, Double>>>>() {
                @Override
                public void success(Map<String, List<Map<String, Double>>> stringDoubleMap, Response response) {
                    onResponseListener.onSuccess(stringDoubleMap.get("results"));
                }

                @Override
                public void failure(RetrofitError retrofitError) {
                    onResponseListener.onError(ExceptionFactory.get(retrofitError));
                }
            };

        IndicoTextService thisIndicoTextService = whichTextService(auth);
        String credentials = Config.findCorrectApiKey(auth.get("api_key"));
        thisIndicoTextService.batchPoliticalSentiment(new TextList(text), credentials, callback);
    }

    public void batchPoliticalSentiment(List<String> text, final OnObjectResponseListener<List<Map<String, Double>>> onResponseListener)
            throws IndicoException{
        HashMap<String, String> emptyHash = new HashMap<String, String>();
        batchPoliticalSentiment(text, emptyHash, onResponseListener);
    }
     /**
     * Given input text, returns a probability distribution over the political alignment of the speaker.
     *
     * @param text: The text to be analyzed.
     * @param auth: a map optionally containning a username, password, and cloud url.
     * @return Political alignment probability pairs.
     * @throws IndicoException
     */
    public Map<String, Double> politicalSentiment(String text, Map<String, String> auth) throws IndicoException {
        try {
            IndicoTextService thisIndicoTextService = whichTextService(auth);
            return thisIndicoTextService.politicalSentiment(new Text(text), Config.findCorrectApiKey(auth.get("api_key"))).get("results");
        } catch (RetrofitError error) {
            throw ExceptionFactory.get(error);
        }
    }

    public void politicalSentiment(String text,
            final OnMapResponseListener<String, Double> onResponseListener) throws IndicoException {
        politicalSentiment(text, null, onResponseListener);
    }

    public Map<String, Double> politicalSentiment(String text) throws IndicoException {
        HashMap<String, String> emptyHash = new HashMap<String, String>();
        return politicalSentiment(text, emptyHash);
    }


    /**
     * Given a list of input texts, returns a list of probability distributions over the political alignment of the speakers
     *
     * @param text: The text to be analyzed.
     * @param auth: a map optionally containning a username, password, and cloud url.
     * @param onResponseListener: Callback which will be called when operation ends.
     */
    public List<Map<String, Double>> batchPoliticalSentiment(List<String> text, Map<String, String> auth) throws IndicoException {
        try {
            IndicoTextService thisIndicoTextService = whichTextService(auth);
            return thisIndicoTextService.batchPoliticalSentiment(new TextList(text), Config.findCorrectApiKey(auth.get("api_key"))).get("results");
        } catch (RetrofitError error) {
            throw ExceptionFactory.get(error);
        }
    }



    /**
     * Given input text, returns a probability distribution over the topics it could be about.
     *
     * @param text: The text to be analyzed.
     * @param auth: a map optionally containning a username, password, and cloud url.
     * @param onResponseListener: Callback which will be called when operation ends.
     */
    public void textTags(String text, Map<String, String> auth,
            final OnObjectResponseListener<Map<String, Double>> onResponseListener) throws IndicoException {
        Callback<Map<String, Map<String, Double>>> callback = new Callback<Map<String, Map<String, Double>>>() {
                @Override
                public void success(Map<String, Map<String, Double>> stringDoubleMap, Response response) {
                    onResponseListener.onSuccess(stringDoubleMap.get("results"));
                }

                @Override
                public void failure(RetrofitError retrofitError) {
                    onResponseListener.onError(ExceptionFactory.get(retrofitError));
                }
            };

        IndicoTextService thisIndicoTextService = whichTextService(auth);
        String credentials= Config.findCorrectApiKey(auth.get("api_key"));
        thisIndicoTextService.textTags(new Text(text), credentials, callback);
    }

    /**
     * Given a list of input texts, returns a list of probability distributions over the political alignment of the speakers
     *
     * @param text: The text to be analyzed.
     * @param auth: a map optionally containning a username, password, and cloud url.
     */
    public void batchTextTags(List<String> text, Map<String, String> auth,
            final OnObjectResponseListener<List<Map<String, Double>>> onResponseListener) throws IndicoException {
        Callback<Map<String, List<Map<String, Double>>>> callback = new Callback<Map<String, List<Map<String, Double>>>>() {
                @Override
                public void success(Map<String, List<Map<String, Double>>> stringDoubleMap, Response response) {
                    onResponseListener.onSuccess(stringDoubleMap.get("results"));
                }

                @Override
                public void failure(RetrofitError retrofitError) {
                    onResponseListener.onError(ExceptionFactory.get(retrofitError));
                }
            };

        IndicoTextService thisIndicoTextService = whichTextService(auth);
        String credentials= Config.findCorrectApiKey(auth.get("api_key"));
        thisIndicoTextService.batchTextTags(new TextList(text), credentials, callback);
    }

    public void batchTextTags(List<String> text, final OnObjectResponseListener<List<Map<String, Double>>> onResponseListener)
            throws IndicoException{
        HashMap<String, String> emptyHash = new HashMap<String, String>();
        batchTextTags(text, emptyHash, onResponseListener);
    }

    /**
     * Given input text, returns a probability distribution over the topics it could be about.
     *
     * @param text: The text to be analyzed.
     * @param auth: a map optionally containning a username, password, and cloud url.
     * @return Political alignment probability pairs.
     * @throws IndicoException
     */
    public Map<String, Double> textTags(String text, Map<String, String> auth) throws IndicoException {
        try {
            IndicoTextService thisIndicoTextService = whichTextService(auth);
            return thisIndicoTextService.textTags(new Text(text), Config.findCorrectApiKey(auth.get("api_key"))).get("results");

        } catch (RetrofitError error) {
            throw ExceptionFactory.get(error);
        }
    }

    public void textTags(String text,
            final OnObjectResponseListener<Map<String, Double>> onResponseListener) throws IndicoException {
        textTags(text, null, onResponseListener);
    }

    public Map<String, Double> textTags(String text) throws IndicoException {
        HashMap<String, String> emptyHash = new HashMap<String, String>();
        return textTags(text, emptyHash);
    }


    /**
     * Given a list of input texts, returns a list of probability distributions over the political alignment of the speakers
     *
     * @param text: The text to be analyzed.
     * @param auth: a map optionally containning a username, password, and cloud url.
     * @return A list of TextTags probability pairs.
     * @throws IndicoException
     */
    public List<Map<String, Double>> batchTextTags(List<String> text, Map<String, String> auth) throws IndicoException {
        try {
            IndicoTextService thisIndicoTextService = whichTextService(auth);
            return thisIndicoTextService.batchTextTags(new TextList(text), Config.findCorrectApiKey(auth.get("api_key"))).get("results");
        } catch (RetrofitError error) {
            throw ExceptionFactory.get(error);
        }
    }



    /**
     * Given input text, returns a scalar estimate of the sentiment of that text.
     * Values are roughly in the range 0 to 1 with 0.5 indicating neutral sentiment.
     * For reference, 0 suggests very negative sentiment and 1 suggests very positive sentiment.
     *
     * @param text: The text to be analyzed.
     * @param auth: a map optionally containning a username, password, and cloud url.
     * @return Value that indicates sentiment.
     * @throws IndicoException
     */
    public void sentiment(String text, Map<String, String> auth,
            final OnMapResponseListener<String, Double> onResponseListener) throws IndicoException {
        Callback<Map<String, Double>> callback = new Callback<Map<String, Double>>() {
                @Override
                public void success(Map<String, Double> stringDoubleMap, Response response) {
                    onResponseListener.onSuccess(stringDoubleMap);
                }

                @Override
                public void failure(RetrofitError retrofitError) {
                    onResponseListener.onError(ExceptionFactory.get(retrofitError));
                }
            };

        IndicoTextService thisIndicoTextService = whichTextService(auth);
        String credentials= Config.findCorrectApiKey(auth.get("api_key"));
        thisIndicoTextService.sentiment(new Text(text), credentials, callback);
    }

    /**
     * Given a list of input texts, returns a list of scalar estimates of the sentiments of those texts.
     * Values are roughly in the range 0 to 1 with 0.5 indicating neutral sentiment.
     * For reference, 0 suggests very negative sentiment and 1 suggests very positive sentiment.
     *
     * @param text: The text to be analyzed.
     * @param auth: a map optionally containning a username, password, and cloud url.
     * @return  A list of values that indicates sentiment of each text
     * @throws IndicoException
     *
     */
    public void batchSentiment(List<String> text, Map<String, String> auth,
            final OnMapResponseListener<String, List<Double>> onResponseListener) throws IndicoException {
        Callback<Map<String, List<Double>>> callback = new Callback<Map<String, List<Double>>>() {
                @Override
                public void success(Map<String, List<Double>> stringDoubleMap, Response response) {
                    onResponseListener.onSuccess(stringDoubleMap);
                }

                @Override
                public void failure(RetrofitError retrofitError) {
                    onResponseListener.onError(ExceptionFactory.get(retrofitError));
                }
            };

        IndicoTextService thisIndicoTextService = whichTextService(auth);
        String credentials= Config.findCorrectApiKey(auth.get("api_key"));
        thisIndicoTextService.batchSentiment(new TextList(text), credentials, callback);
    }

    /**
     * Given a list of input texts, returns a list of scalar estimates of the sentiments of those texts.
     * Values are roughly in the range 0 to 1 with 0.5 indicating neutral sentiment.
     * For reference, 0 suggests very negative sentiment and 1 suggests very positive sentiment.
     *
     * @param text: The text to be analyzed.
     * @param auth: a map optionally containning a username, password, and cloud url.
     * @return Values that indicates sentiment.
     * @throws IndicoException
     *
     */
    public Double sentiment(String text, Map<String, String> auth) throws IndicoException {
        try {
            IndicoTextService thisIndicoTextService = whichTextService(auth);
            return thisIndicoTextService.sentiment(new Text(text), Config.findCorrectApiKey(auth.get("api_key"))).get("results");
        } catch (RetrofitError error) {
            throw ExceptionFactory.get(error);
        }
    }

    public void sentiment(String text,
            final OnMapResponseListener<String, Double> onResponseListener) throws IndicoException {
        sentiment(text, null, onResponseListener);
    }

    public Double sentiment(String text) throws IndicoException {
        HashMap<String, String> emptyHash = new HashMap<String, String>();
        return sentiment(text, emptyHash);
    }

    /**
     * Given a list of input texts, returns a list of scalar estimates of the sentiments of those texts.
     * Values are roughly in the range 0 to 1 with 0.5 indicating neutral sentiment.
     * For reference, 0 suggests very negative sentiment and 1 suggests very positive sentiment.
     *
     * @param text: The text to be analyzed.
     * @param auth: a map optionally containning a username, password, and cloud url.
     * @return  A list of values that indicates sentiment of each text
     * @throws IndicoException
     *
     */
    public List<Double> batchSentiment(List<String> text, Map<String, String> auth) throws IndicoException {
        try {
            IndicoTextService thisIndicoTextService = whichTextService(auth);
            return thisIndicoTextService.batchSentiment(new TextList(text), Config.findCorrectApiKey(auth.get("api_key"))).get("results");
        } catch (RetrofitError error) {
            throw ExceptionFactory.get(error);
        }
    }
}

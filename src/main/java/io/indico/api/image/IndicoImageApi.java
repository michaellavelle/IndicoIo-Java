package io.indico.api.image;

import java.util.List;
import java.util.Map;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import biz.source_code.base64Coder.Base64Coder;

import io.indico.api.Config;
import io.indico.api.exception.ExceptionFactory;
import io.indico.api.exception.IndicoException;
import io.indico.api.listener.OnMapResponseListener;
import io.indico.api.listener.OnObjectResponseListener;
import io.indico.api.model.image.request.FacePixels;
import io.indico.api.model.image.request.FacePixelsList;
import io.indico.api.model.image.request.IndicoImage;
import io.indico.api.model.image.request.IndicoImageList;

/**
 * Api for work with images.
 */
public class IndicoImageApi {

    private static IndicoImageApi instance;

    private final IndicoImageService indicoImageService;

    private IndicoImageApi() {
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(Config.API_URL)
                .build();
        indicoImageService = adapter.create(IndicoImageService.class);
    }

    private IndicoImageService IndicoImageApi(String cloudUrl) {
        String fullUrl = "http://" + cloudUrl + ".indico.domains";
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(fullUrl)
                .build();

        return adapter.create(IndicoImageService.class);
    }

    /**
     * Returns instance of the api for work with text.
     */
    public static IndicoImageApi getInstance() {
        if (instance == null) {
            instance = new IndicoImageApi();
        }

        return instance;
    }

    /**
     * Returns either an indicoImageService with the endpoint
     * or, if it exists, the endpoint set in the parameter 'cloud' in auth
     */
    private IndicoImageService whichImageService(Map<String, String> auth) {
        if (auth != null && auth.get("cloud") != null) {
            return IndicoImageApi(auth.get("cloud"));
        } else {
            return this.indicoImageService;
        }
    }

    /**
     * Given an input image of a face, returns a 48 dimensional feature vector explaining that face.
     * Useful as a form of feature engineering for face oriented tasks.
     * Input should be in a list of list format, resizing will be attempted internally but for best
     * performance, images should be already sized at 48x48 pixels.
     *
     * @param image: the BufferedImage to be analyzed
     * @param auth: a map optionally containning a username, password, and cloud url.
     * @param responseListener Callback which will be called when operation ends.
     */
    public void facialFeatures(BufferedImage image, Map<String, String> auth, final OnObjectResponseListener<List<Double>>
            responseListener) {

        Callback<List<Double>> callback = new Callback<List<Double>>() {
            @Override
            public void success(List<Double> facialFeatures, Response response) {
                responseListener.onSuccess(facialFeatures);
            }

            @Override
            public void failure(RetrofitError error) {
                responseListener.onError(ExceptionFactory.get(error));
            }
        };

        IndicoImageService thisIndicoImageService = whichImageService(auth);
        if (auth != null && auth.get("cloud") != null) {
            String credentials= encodeCredentials(auth.get("username"), auth.get("password"));
            thisIndicoImageService.facialFeatures(new IndicoImage(image), credentials, callback);
        } else {
            thisIndicoImageService.facialFeatures(new IndicoImage(image), callback);
        }
    }

    public void facialFeatures(BufferedImage image, final OnObjectResponseListener<List<Double>> responseListener) {
        facialFeatures(image, null, responseListener);
    }

    /**
     * Given an input image of a face, returns a 48 dimensional feature vector explaining that face.
     * Useful as a form of feature engineering for face oriented tasks.
     * Input should be in a list of list format, resizing will be attempted internally but for best
     * performance, images should be already sized at 48x48 pixels.
     *
     * @param images: the BufferedImages to be analyzed
     * @param auth: a map optionally containning a username, password, and cloud url.
     * @param responseListener Callback which will be called when operation ends.
     */
    public void batchFacialFeatures(List<BufferedImage> images, Map<String, String> auth, final OnObjectResponseListener<List<List<Double>>>
            responseListener) {

        Callback<List<List<Double>>> callback = new Callback<List<List<Double>>>() {
            @Override
            public void success(List<List<Double>> facialFeatures, Response response) {
                responseListener.onSuccess(facialFeatures);
            }

            @Override
            public void failure(RetrofitError error) {
                responseListener.onError(ExceptionFactory.get(error));
            }
        };

        IndicoImageService thisIndicoImageService = whichImageService(auth);

        String credentials= encodeCredentials(auth.get("username"), auth.get("password"));
        thisIndicoImageService.batchFacialFeatures(new IndicoImageList(images), credentials, callback);
    }

    /**
     * Given an input image of a face, returns a 48 dimensional feature vector explaining that face.
     * Useful as a form of feature engineering for face oriented tasks.
     * Input should be in a list of list format, resizing will be attempted internally but for best
     * performance, images should be already sized at 48x48 pixels.
     *
     * @param image: the BufferedImage to be analyzed
     * @param auth: a map optionally containning a username, password, and cloud url.
     * @return 48 dimensional feature vector explaining that face.
     * @throws IndicoException
     */
    public List<Double> facialFeatures(BufferedImage image, Map<String, String> auth) throws IndicoException {
        try {
            List<Double> facialFeatures = null;

            IndicoImageService thisIndicoImageService = whichImageService(auth);
            if (auth != null && auth.get("cloud") != null) {
                String credentials= encodeCredentials(auth.get("username"), auth.get("password"));
                facialFeatures = thisIndicoImageService.facialFeatures(new IndicoImage(image), credentials);
            } else {
                facialFeatures = thisIndicoImageService.facialFeatures(new IndicoImage(image));
            }

            return facialFeatures;

        } catch (RetrofitError error) {
            throw ExceptionFactory.get(error);
        }
    }

    public List<Double> facialFeatures(BufferedImage image) throws IndicoException {
        HashMap<String, String> emptyHash = new HashMap<String, String>();
        return facialFeatures(image, emptyHash);
    }

    /**
     * Given an input image of a face, returns a 48 dimensional feature vector explaining that face.
     * Useful as a form of feature engineering for face oriented tasks.
     * Input should be in a list of list format, resizing will be attempted internally but for best
     * performance, images should be already sized at 48x48 pixels.
     *
     * @param images: the BufferedImages to be analyzed
     * @param auth: a map optionally containning a username, password, and cloud url.
     * @return 48 dimensional feature vector explaining that face.
     * @throws IndicoException
     */
    public List<List<Double>> batchFacialFeatures(List<BufferedImage> images, Map<String, String> auth) throws IndicoException {
        try {IndicoImageService thisIndicoImageService = whichImageService(auth);
            String credentials= encodeCredentials(auth.get("username"), auth.get("password"));
            List<List<Double>> facialFeatures = thisIndicoImageService.batchFacialFeatures(new IndicoImageList(images), credentials);

            return facialFeatures;
        } catch (RetrofitError error) {
            throw ExceptionFactory.get(error);
        }
    }





/**
     * Given an input image, returns a 2048 dimensional sparse feature vector explaining that image.
     * Useful as a form of feature engineering for image oriented tasks.
     * <p/>
     * <ul>
     * <li>Input can be either or rgb color and should either be a numpy array or nested list format.</li>
     * <li>Input data should be either uint8 0-255 range values or floating point between 0 and 1.</li>
     * <li>Large images (i.e. 1024x768+) are much bigger than needed, resizing will be done internally to 64x64 if
     * needed.</li>
     * <li>For ideal performance, images should be square aspect ratio but non-square aspect ratios are supported as
     * well.</li>
     * </ul>
     * <p/>
     * Since the image features returned are a semantic description of the contents of an image they can be used
     * to implement many other common image related tasks such as object recognition or image similarity and retrieval.
     * <p/>
     * For image similarity, simple distance metrics applied to collections of image feature vectors can work very well.
     *
     * @param image: the BufferedImage to be analyzed
     * @param auth: a map optionally containning a username, password, and cloud url.
     * @param responseListener Callback which will be called when operation ends.
     */
    public void imageFeatures(BufferedImage image, Map<String, String> auth, final OnObjectResponseListener<List<Double>>
            responseListener) {

        Callback<List<Double>> callback = new Callback<List<Double>>() {
            @Override
            public void success(List<Double> imageFeatures, Response response) {
                responseListener.onSuccess(imageFeatures);
            }

            @Override
            public void failure(RetrofitError error) {
                responseListener.onError(ExceptionFactory.get(error));
            }
        };

        IndicoImageService thisIndicoImageService = whichImageService(auth);
        if (auth != null && auth.get("cloud") != null) {
            String credentials= encodeCredentials(auth.get("username"), auth.get("password"));
            thisIndicoImageService.imageFeatures(new IndicoImage(image), credentials, callback);
        } else {
            thisIndicoImageService.imageFeatures(new IndicoImage(image), callback);
        }
    }

    public void imageFeatures(BufferedImage image, final OnObjectResponseListener<List<Double>> responseListener) {
        imageFeatures(image, null, responseListener);
    }

    /**
     * Given an input image, returns a 2048 dimensional sparse feature vector explaining that image.
     * Useful as a form of feature engineering for image oriented tasks.
     * <p/>
     * <ul>
     * <li>Input can be either or rgb color and should either be a numpy array or nested list format.</li>
     * <li>Input data should be either uint8 0-255 range values or floating point between 0 and 1.</li>
     * <li>Large images (i.e. 1024x768+) are much bigger than needed, resizing will be done internally to 64x64 if
     * needed.</li>
     * <li>For ideal performance, images should be square aspect ratio but non-square aspect ratios are supported as
     * well.</li>
     * </ul>
     * <p/>
     * Since the image features returned are a semantic description of the contents of an image they can be used
     * to implement many other common image related tasks such as object recognition or image similarity and retrieval.
     * <p/>
     * For image similarity, simple distance metrics applied to collections of image feature vectors can work very well.
     *
     * @param images: the BufferedImages to be analyzed
     * @param auth: a map optionally containning a username, password, and cloud url.
     * @param responseListener Callback which will be called when operation ends.
     */
    public void batchImageFeatures(List<BufferedImage> images, Map<String, String> auth, final OnObjectResponseListener<List<List<Double>>>
            responseListener) {

        Callback<List<List<Double>>> callback = new Callback<List<List<Double>>>() {
            @Override
            public void success(List<List<Double>> ImageFeatures, Response response) {
                responseListener.onSuccess(ImageFeatures);
            }

            @Override
            public void failure(RetrofitError error) {
                responseListener.onError(ExceptionFactory.get(error));
            }
        };

        IndicoImageService thisIndicoImageService = whichImageService(auth);

        String credentials= encodeCredentials(auth.get("username"), auth.get("password"));
        thisIndicoImageService.batchImageFeatures(new IndicoImageList(images), credentials, callback);
    }

    /**
     * Given an input image, returns a 2048 dimensional sparse feature vector explaining that image.
     * Useful as a form of feature engineering for image oriented tasks.
     * <p/>
     * <ul>
     * <li>Input can be either or rgb color and should either be a numpy array or nested list format.</li>
     * <li>Input data should be either uint8 0-255 range values or floating point between 0 and 1.</li>
     * <li>Large images (i.e. 1024x768+) are much bigger than needed, resizing will be done internally to 64x64 if
     * needed.</li>
     * <li>For ideal performance, images should be square aspect ratio but non-square aspect ratios are supported as
     * well.</li>
     * </ul>
     * <p/>
     * Since the image features returned are a semantic description of the contents of an image they can be used
     * to implement many other common image related tasks such as object recognition or image similarity and retrieval.
     * <p/>
     * For image similarity, simple distance metrics applied to collections of image feature vectors can work very well.
     *
     * @param image: the BufferedImage to be analyzed
     * @param auth: a map optionally containning a username, password, and cloud url.
     * @return 48 dimensional feature vector explaining that face.
     * @throws IndicoException
     */
    public List<Double> imageFeatures(BufferedImage image, Map<String, String> auth) throws IndicoException {
        try {
            List<Double> imageFeatures = null;

            IndicoImageService thisIndicoImageService = whichImageService(auth);
            if (auth != null && auth.get("cloud") != null) {
                String credentials= encodeCredentials(auth.get("username"), auth.get("password"));
                imageFeatures = thisIndicoImageService.imageFeatures(new IndicoImage(image), credentials);
            } else {
                imageFeatures = thisIndicoImageService.imageFeatures(new IndicoImage(image));
            }

            return imageFeatures;

        } catch (RetrofitError error) {
            throw ExceptionFactory.get(error);
        }
    }

    public List<Double> imageFeatures(BufferedImage image) throws IndicoException {
        HashMap<String, String> emptyHash = new HashMap<String, String>();
        return imageFeatures(image, emptyHash);
    }

    /**
     * Given an input image, returns a 2048 dimensional sparse feature vector explaining that image.
     * Useful as a form of feature engineering for image oriented tasks.
     * <p/>
     * <ul>
     * <li>Input can be either or rgb color and should either be a numpy array or nested list format.</li>
     * <li>Input data should be either uint8 0-255 range values or floating point between 0 and 1.</li>
     * <li>Large images (i.e. 1024x768+) are much bigger than needed, resizing will be done internally to 64x64 if
     * needed.</li>
     * <li>For ideal performance, images should be square aspect ratio but non-square aspect ratios are supported as
     * well.</li>
     * </ul>
     * <p/>
     * Since the image features returned are a semantic description of the contents of an image they can be used
     * to implement many other common image related tasks such as object recognition or image similarity and retrieval.
     * <p/>
     * For image similarity, simple distance metrics applied to collections of image feature vectors can work very well.
     *
     * @param images: the BufferedImages to be analyzed
     * @param auth: a map optionally containning a username, password, and cloud url.
     * @return 48 dimensional feature vector explaining that face.
     * @throws IndicoException
     */
    public List<List<Double>> batchImageFeatures(List<BufferedImage> images, Map<String, String> auth) throws IndicoException {
        try {IndicoImageService thisIndicoImageService = whichImageService(auth);
            String credentials= encodeCredentials(auth.get("username"), auth.get("password"));
            List<List<Double>> imageFeatures = thisIndicoImageService.batchImageFeatures(new IndicoImageList(images), credentials);

            return imageFeatures;
        } catch (RetrofitError error) {
            throw ExceptionFactory.get(error);
        }
    }




/**
     * Given a input image of a face, returns a probability distribution over emotional state.
     * Input should be in a list of list format, resizing will be attempted internally but for best
     * performance, images should be already sized at 48x48 pixels.
     *
     * @param image: the BufferedImage to be analyzed
     * @param auth: a map optionally containning a username, password, and cloud url.
     * @param responseListener Callback which will be called when operation ends.
     */
    public void emotionalState(BufferedImage image, Map<String, String> auth, final OnMapResponseListener<String, Double>
            responseListener) {

        Callback<Map<String, Map<String, Double>>> callback = new Callback<Map<String, Map<String, Double>>>() {
            @Override
            public void success(Map<String, Map<String, Double>> stringDoubleMap, Response response) {
                responseListener.onSuccess(stringDoubleMap.get("results"));
            }

            @Override
            public void failure(RetrofitError error) {
                responseListener.onError(ExceptionFactory.get(error));
            }
        };

        IndicoImageService thisIndicoImageService = whichImageService(auth);
        if (auth != null && auth.get("cloud") != null) {
            String credentials= encodeCredentials(auth.get("username"), auth.get("password"));
            thisIndicoImageService.emotionalState(new IndicoImage(image), credentials, callback);
        } else {
            thisIndicoImageService.emotionalState(new IndicoImage(image), callback);
        }
    }

    public void emotionalState(BufferedImage image, final OnMapResponseListener<String, Double> responseListener) {
        emotionalState(image, null, responseListener);
    }

    /**
     * Given a input image of a face, returns a probability distribution over emotional state.
     * Input should be in a list of list format, resizing will be attempted internally but for best
     * performance, images should be already sized at 48x48 pixels.
     *
     * @param images: the BufferedImages to be analyzed
     * @param auth: a map optionally containning a username, password, and cloud url.
     * @param responseListener Callback which will be called when operation ends.
     */
    public void batchEmotionalState(List<BufferedImage> images, Map<String, String> auth, final OnObjectResponseListener<List<Map<String, Double>>>
            responseListener) {

        Callback<Map<String, List<Map<String, Double>>>> callback = new Callback<Map<String, List<Map<String, Double>>>>() {
            @Override
            public void success(Map<String, List<Map<String, Double>>> stringDoubleMap, Response response) {
                responseListener.onSuccess(stringDoubleMap.get("results"));
            }

            @Override
            public void failure(RetrofitError error) {
                responseListener.onError(ExceptionFactory.get(error));
            }
        };

        IndicoImageService thisIndicoImageService = whichImageService(auth);

        String credentials= encodeCredentials(auth.get("username"), auth.get("password"));
        thisIndicoImageService.batchEmotionalState(new IndicoImageList(images), credentials, callback);
    }

    /**
     * Given a input image of a face, returns a probability distribution over emotional state.
     * Input should be in a list of list format, resizing will be attempted internally but for best
     * performance, images should be already sized at 48x48 pixels.
     *
     * @param image: the BufferedImage to be analyzed
     * @param auth: a map optionally containning a username, password, and cloud url.
     * @return Probability distribution over emotional state.
     * @throws IndicoException
     */
    public Map<String, Double> emotionalState(BufferedImage image, Map<String, String> auth) throws IndicoException {
        try {
            Map<String, Map<String, Double>> emotionalState = null;

            IndicoImageService thisIndicoImageService = whichImageService(auth);
            if (auth != null && auth.get("cloud") != null) {
                String credentials= encodeCredentials(auth.get("username"), auth.get("password"));
                emotionalState = thisIndicoImageService.emotionalState(new IndicoImage(image), credentials);
            } else {
                emotionalState = thisIndicoImageService.emotionalState(new IndicoImage(image));
            }

            return emotionalState.get("results");

        } catch (RetrofitError error) {
            throw ExceptionFactory.get(error);
        }
    }

    public Map<String, Double> emotionalState(BufferedImage image) throws IndicoException {
        HashMap<String, String> emptyHash = new HashMap<String, String>();
        return emotionalState(image, emptyHash);
    }

    /**
     * Given a input image of a face, returns a probability distribution over emotional state.
     * Input should be in a list of list format, resizing will be attempted internally but for best
     * performance, images should be already sized at 48x48 pixels.
     *
     * @param images: the BufferedImages to be analyzed
     * @param auth: a map optionally containning a username, password, and cloud url.
     * @return Probability distribution over emotional state.
     * @throws IndicoException
     */
    public List<Map<String, Double>> batchEmotionalState(List<BufferedImage> images, Map<String, String> auth) throws IndicoException {
        try {IndicoImageService thisIndicoImageService = whichImageService(auth);
            String credentials= encodeCredentials(auth.get("username"), auth.get("password"));
            Map<String, List<Map<String, Double>>> emotionalState = thisIndicoImageService.batchEmotionalState(new IndicoImageList(images), credentials);

            return emotionalState.get("results");
        } catch (RetrofitError error) {
            throw ExceptionFactory.get(error);
        }
    }

    private static String encodeCredentials(String username, String password) {
        final String credentials = username + ":" + password;
        return "Basic " + Base64Coder.encodeString(credentials);
    }
}

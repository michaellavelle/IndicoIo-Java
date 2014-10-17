package io.indico.api.image;

import io.indico.api.Config;
import io.indico.api.exception.ExceptionFactory;
import io.indico.api.exception.IndicoException;
import io.indico.api.listener.OnMapResponseListener;
import io.indico.api.listener.OnObjectResponseListener;
import io.indico.api.model.image.request.FacePixels;
import io.indico.api.model.image.request.ImagePixels;
import io.indico.api.model.image.response.FacialFeatures;
import io.indico.api.model.image.response.ImageFeatures;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import java.util.List;
import java.util.Map;

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
     * Given an grayscale input image of a face, returns a 48 dimensional feature vector explaining that face.
     * Useful as a form of feature engineering for face oriented tasks.
     * Input should be in a list of list format, resizing will be attempted internally but for best
     * performance, images should be already sized at 48x48 pixels.
     *
     * @param pixels           Pixels of the image to be analyzed.
     * @param responseListener Callback which will be called when operation ends.
     */
    public void facialFeatures(List<List<Double>> pixels, final OnObjectResponseListener<List<Double>>
            responseListener) {

        this.indicoImageService.facialFeatures(new FacePixels(pixels), new Callback<FacialFeatures>() {
            @Override
            public void success(FacialFeatures facialFeatures, Response response) {
                List<Double> features = facialFeatures.getValue();

                responseListener.onSuccess(features);
            }

            @Override
            public void failure(RetrofitError error) {
                responseListener.onError(ExceptionFactory.get(error));
            }
        });
    }

    /**
     * Given an grayscale input image of a face, returns a 48 dimensional feature vector explaining that face.
     * Useful as a form of feature engineering for face oriented tasks.
     * Input should be in a list of list format, resizing will be attempted internally but for best
     * performance, images should be already sized at 48x48 pixels.
     *
     * @param pixels Pixels of the image to be analyzed.
     * @return 48 dimensional feature vector explaining that face.
     * @throws IndicoException
     */
    public List<Double> facialFeatures(List<List<Double>> pixels) throws IndicoException {
        try {
            FacialFeatures facialFeatures = this.indicoImageService.facialFeatures(new FacePixels(pixels));
            List<Double> features = facialFeatures.getValue();

            if (features == null) {
                throw ExceptionFactory.getParameterException(facialFeatures.getErrorMessage());
            }

            return features;

        } catch (RetrofitError error) {
            throw ExceptionFactory.get(error);
        }
    }

    /**
     * Given an input image, returns a 2048 dimensional sparse feature vector explaining that image.
     * Useful as a form of feature engineering for image oriented tasks.
     * <p/>
     * <ul>
     * <li>Input can be either grayscale or rgb color and should either be a numpy array or nested list format.</li>
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
     * @param pixels           Pixels of the image to be analyzed.
     * @param responseListener Callback which will be called when operation ends.
     */
    public void imageFeatures(List<List<Double>> pixels, final OnObjectResponseListener<ImageFeatures>
            responseListener) {

        this.indicoImageService.imageFeatures(new ImagePixels(pixels), new Callback<ImageFeatures>() {
            @Override
            public void success(ImageFeatures imageFeatures, Response response) {
                responseListener.onSuccess(imageFeatures);
            }

            @Override
            public void failure(RetrofitError error) {
                responseListener.onError(ExceptionFactory.get(error));
            }
        });
    }

    /**
     * Given an input image, returns a 2048 dimensional sparse feature vector explaining that image.
     * Useful as a form of feature engineering for image oriented tasks.
     * <p/>
     * <ul>
     * <li>Input can be either grayscale or rgb color and should either be a numpy array or nested list format.</li>
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
     * @param pixels Pixels of the image to be analyzed.
     * @return 2048 dimensional sparse feature vector explaining that image.
     * @throws IndicoException
     */
    public List<Double> imageFeatures(List<List<Double>> pixels) throws IndicoException {
        try {
            ImageFeatures imageFeatures = this.indicoImageService.imageFeatures(new ImagePixels(pixels));

            return imageFeatures.getFeatures();
        } catch (RetrofitError error) {
            throw ExceptionFactory.get(error);
        }
    }

    /**
     * Given a grayscale input image of a face, returns a probability distribution over emotional state.
     * Input should be in a list of list format, resizing will be attempted internally but for best
     * performance, images should be already sized at 48x48 pixels.
     *
     * @param pixels           Pixels of the image to be analyzed.
     * @param responseListener Callback which will be called when operation ends.
     */
    public void emotionalState(List<List<Double>> pixels, final OnMapResponseListener<String, Double>
            responseListener) {

        this.indicoImageService.emotionalState(new FacePixels(pixels), new Callback<Map<String, Map<String, Double>>>() {
            @Override
            public void success(Map<String, Map<String, Double>> stringDoubleMap, Response response) {
                responseListener.onSuccess(stringDoubleMap.get("results"));
            }

            @Override
            public void failure(RetrofitError error) {
                responseListener.onError(ExceptionFactory.get(error));
            }
        });
    }

    /**
     * Given a grayscale input image of a face, returns a probability distribution over emotional state.
     * Input should be in a list of list format, resizing will be attempted internally but for best
     * performance, images should be already sized at 48x48 pixels.
     *
     * @param pixels Pixels of the image to be analyzed.
     * @return Probability distribution over emotional state.
     * @throws IndicoException
     */
    public Map<String, Double> emotionalState(List<List<Double>> pixels) throws IndicoException {
        try {
            return this.indicoImageService.emotionalState(new FacePixels(pixels)).get("results");
        } catch (RetrofitError error) {
            throw ExceptionFactory.get(error);
        }
    }
}

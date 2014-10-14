package io.indico.api.model.text.request;

/**
 * Model is used for creating json object which represents text for analysis.
 */
public class Text {
    public Text(String text) {
        this.data = text;
    }

    private String data;
}

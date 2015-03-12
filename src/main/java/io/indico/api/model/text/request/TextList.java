package io.indico.api.model.text.request;

import java.util.List;

/**
 * Model is used for creating json object which represents text for analysis.
 */
public class TextList {
    public TextList(List<String> text) {
        this.data = text;
    }

    private List<String> data;
}

IndicoIo-Java
===============

A wrapper for the [indico API](http://indico.io).

The indico API is free to use, and no training data is required.  

Requirements
------------
IndicoIO-Java requires Java 7 or later to run.

Download
--------
[Download the latest JAR][1] or add Maven dependency:
```xml
<dependency>
    <groupId>io.indico</groupId>
    <artifactId>indico</artifactId>
    <version>1.2-SNAPSHOT</version>
</dependency>
```
or Gradle:
```groovy
compile 'io.indico:indico:1.2-SNAPSHOT'
```

API Keys + Setup
----------------
For API key registration and setup, checkout our [quickstart guide](http://docs.indico.io/v2.0/docs/api-keys).

Full Documentation
------------
Detailed documentation and further code examples are available at [indico.reame.io](http://indico.readme.io/v2.0/docs/java).

Supported APIs:
------------

- Positive/Negative Sentiment Analysis
- Political Sentiment Analysis
- Image Feature Extraction
- Facial Emotion Recognition
- Facial Feature Extraction
- Language Detection
- Text Topic Tagging

Examples
--------
```java
Map<String, Double> response = Indico.text().politicalSentiment("Religion is the opium for people");
System.out.println(response);
// {Libertarian=0.4474012107361873, Liberal=0.08358713291873496, Green=0.14591047596276718,
// Conservative=0.32310118038231056}

double value = Indico.text().sentiment("Thank you");
System.out.println(value);
// 0.9407597464840836

double value = Indico.text().sentiment("Bad news");
System.out.println(value);
// 0.19165873789277

Map<String, Double> result = Indico.image().emotionalState(ImageIO.read("testImage.png"));
System.out.println(result);
// {Angry=0.09078825415146091, Sad=0.3934412480651238, Neutral=0.19024607709767583, Surprise=0.03485237630053983,
// Fear=0.17539499747972068, Happy=0.11527704690547856}

// async
Indico.image().facialFeatures(getPixels(image), new OnObjectResponseListener<List<Double>>() {
    @Override
    public void onSuccess(List<Double> result) {
        // handle result
    }


    @Override
    public void onError(IndicoException e) {
        // handle error
    }
});
```

Batch API Access
----------------

Each `indicoio` function has a corresponding batch function for analyzing many examples with a single request. Simply pass in a list of inputs and receive a list of results in return.

```java
List<String> textData = Arrays.asList("Bad news","Good news");
List<Double> value = Indico.text().batchSentiment(textData);

assertTrue(value.get(0) >= 0);
assertTrue(value.get(0) <= 0.5);

assertTrue(value.get(1) >= 0.5);
assertTrue(value.get(1) <= 1);
```

[1]: https://oss.sonatype.org/content/repositories/snapshots/io/indico/indico/1.2-SNAPSHOT/indico-1.2-20150411.202824-4-jar-with-dependencies.jar

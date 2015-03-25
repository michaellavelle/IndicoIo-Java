IndicoIo-Java
===============

A wrapper for a series of APIs made by indico.

Check out the main site on:

http://indico.io

Our APIs are totally free to use, and ready to be used in your application. No data or training required.

Documentation
------------
Found [here](http://indico.readme.io/v1.0/docs)

Current APIs
------------

Right now this wrapper supports the following apps:

- Positive / Negative Sentiment Analysis;
- Political Sentiment Analysis;
- Language Detection.
- Text Tagging
- Image Feature Extraction;
- Facial Emotion Recognition;
- Facial Feature Extraction;

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

Map<String, Double> result = Indico.image().emotionalState(getPixels(image));
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

Download
--------
[Download the latest JAR][1] or add Maven dependency:
```xml
<dependency>
    <groupId>io.indico</groupId>
    <artifactId>indico</artifactId>
    <version>1.0</version>
</dependency>
```
or Gradle:
```groovy
compile 'io.indico:indico:1.0'
```

[1]: http://repository.sonatype.org/service/local/artifact/maven/redirect?r=central-proxy&g=io.indico&a=indico&v=LATEST

Batch API Access
----------------

If you'd like to use our batch api interface, please check out the [pricing page](https://indico.io/pricing) on our website to find the right plan for you.

```java
List<String> textData = Arrays.asList("Bad news","Good news");
List<Double> value = Indico.text().batchSentiment(textData);

assertTrue(value.get(0) >= 0);
assertTrue(value.get(0) <= 0.5);

assertTrue(value.get(1) >= 0.5);
assertTrue(value.get(1) <= 1);
```

Private cloud API Access
------------------------

If you're looking to use indico's API for high throughput applications, please check out the [pricing page](https://indico.io/pricing) on our website to find the right plan for you.

```java
public static final Map<String, String> config;
static
{
    config = new HashMap<String, String>();
    auth.put("cloud" , "test-cloud");
}

double value = Indico.text().sentiment("Bad news", config);
System.out.println(value);
// 0.19165873789277
```

The `cloud` parameter in config redirects API calls to your private cloud hosted at `[cloud].indico.domains`

Private cloud subdomains can also be set as the environment variable `$INDICO_CLOUD` or as `cloud` in the indicorc file. Additionally, the cloud parameter can be set with a single call to `Indico.setPrivateCloud(String cloud)`, causing all subsequent api calls to be made to `cloud`.

Configuration
------------------------

Indicoio-java will search ./.indicorc and $HOME/.indicorc for the optional configuration file. Values in the local configuration file (./.indicorc) take precedence over those found in a global configuration file ($HOME/.indicorc). The indicorc file can be used to set an authentication username and password or a private cloud subdomain, so these arguments don't need to be specified for every api call. All sections are optional.

Here is an example of a valid indicorc file:


```
[auth]
api_key=example-api-key

[private_cloud]
cloud = example
```

Environment variables take precedence over any configuration found in the indicorc file.
The following environment variables are valid:

```
$INDICO_API_KEY
$INDICO_CLOUD
```

Variables set with a `Indico.setPrivateCloud(String cloud)` or `Indico.setApiKey(String apiKey)` override any environment variables or configuration found in the indicorc file for any subsequent api calls, like so:

```java
Indico.setApiKey('example-api-key');
Indico.setPrivateCloud('test-cloud');

double value = Indico.text().sentiment("Bad news", config);
System.out.println(value);
// 0.19165873789277
```


 Finally, any values explicitly passed in to an api call will override configuration options set in the indicorc file, in an environment variable, or with a `Indico.setPrivateCloud(String cloud)` or `Indico.setApiKey(String apiKey)` call. These values are sent in a config map, as shown:

```java
public static final Map<String, String> config;
static
{
    config = new HashMap<String, String>();
    config.put("api_key" , "example-api-key");
    auth.put("cloud" , "test-cloud");
}

double value = Indico.text().sentiment("Bad news", config);
System.out.println(value);
// 0.19165873789277
```

IndicoIo-Java
===============

A wrapper for a series of APIs made by indico.

Check out the main site on:

http://indico.io

Our APIs are totally free to use, and ready to be used in your application. No data or training required.

Update
------------
We now have [organized documentation](http://indico.readme.io/v1.0/docs)

Current APIs
------------

Right now this wrapper supports the following apps:

- Positive / Negative Sentiment Analysis;
- Political Sentiment Analysis;
- Language Detection.
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

Local
--------
When using a local version of the api you must remember to change API_URL constant in Config.java class. 

``` java
public final static String API_URL = "http://api.indico.io"; 
```

Announcement: Indico has partnered with Experfy, a data science consulting marketplace based in the Harvard 
Innovation Lab.  Through Experfy, we are helping our data science community members find lucrative projects and advance 
their skills. Please signup for Experfy at https://www.experfy.com/ to get started.

[1]: http://repository.sonatype.org/service/local/artifact/maven/redirect?r=central-proxy&g=io.indico&a=indico&v=LATEST

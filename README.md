# crawler4j

This is a crawler for Java.

### Target:
- [x] InputStreamConverter
- [x] ByteArrayConverter
- [x] StringConverter
- [x] FileConverter
- [x] JsonConverter
- [ ] HtmlConverter

### Simple Start:

```java
class Example {
    public static void main(String[] args) {
        Crawler crawler = new Crawler();
        // Return a byte array
        byte[] result = crawler.get("https://example.com");
    }
}
```

To change result type, you can implement Receiver:

```java
public class StringReceiver implements Receiver<String> {
    @Override
    public String toOriginal(byte[] content) {
        return new String(content);
    }
}
```

Then we can change our code to:

```java
class Example {
    public static void main(String[] args) {
        Crawler crawler = new Crawler();
        // Return a string
        String result = crawler.get("https://example.com", new StringConverter());
    }
}
```

For post request, you can implement Sender:

```java
public class StringSender implements Sender<String> {
    @Override
    public byte[] toBytes(String original) {
        return original.getBytes(StandardCharsets.UTF_8);
    }
}
```

A post request:

```java
class Example {
    public static void main(String[] args) {
        Crawler crawler = new Crawler();
        // Return a string
        String result = crawler.post(
                // url
                "https://example.com",
                // body
                "TEST",
                // Send converter
                new StringSender(),
                // Receive converter
                new StringReceiver()
        );
    }
}
```

You can create a json object by using 
```java
class Example {
    public static void main(String[] args) {
        JSONObject obj = new JSONObject().setEntry("key", "value")
                .setEntry("num", 120)
                .setEntry("array", 21, 31219281, 13.219)
                .setEntry("obj", new JSONObject()
                        .setEntry("newObj", "new Value")
                        .setEntry("bool", true)
                )
                .setNullEntry("last");
        System.out.println(obj); // {"last": null, "array": [21, 31219281, 13.219], "obj": {"bool": true, "newObj": "new Value"}, "num": 120, "key": "value"}
    }
}
```

Also can send a request with JSONSender.

For more examples, also see [Example](https://github.com/lderic/crawler4j/blob/master/src/main/java/com/lderic/crawler4j/Example.java)
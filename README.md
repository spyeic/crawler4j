# crawler4j

This is a crawler for Java.

### Target:
- [x] StringConverter
- [x] FileConverter
- [ ] JsonConverter
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

To change result type, you can implement Receivable:

```java
public class StringConverter implements Receivable<String> {
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

For post request, you can implement Sendable:

```java
public class StringConverter implements Sendable<String> {
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
                new StringConverter(),
                // Receive converter
                new StringConverter()
        );
    }
}

```

For more examples, also see [Example](https://github.com/lderic/crawler4j/blob/master/src/main/java/com/lderic/crawler4j/Example.java)
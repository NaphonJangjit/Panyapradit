Artificial Intelligence
Creating by Naphon Jangjit
Testing by Iyaaut Kumpiranont
Usage:
```xml
<repositories>
   <repository>
       <id>jitpack.io</id>
       <url>https://jitpack.io</url>
   </repository>
</repositories>

<dependencies>
  <dependency>
     <groupId>com.github.NaphonJangjit</groupId>
     <artifactId>Panyapradit</artifactId>
     <version>42460222cf</version>
  </dependency>
</dependencies>


```java
LinearRegression lr = new LinearRegression();
lr.train(List<Double> key, List<Double> values);
System.out.println(lr.predict(double d));

-LinearRegression
-MultipleLinearRegression
-PolynomialRegression

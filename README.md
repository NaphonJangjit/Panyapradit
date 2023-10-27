An Artificial Intelligence
Creating by Naphon Jangjit
Testing by Iyaaut Kumpiranont
Usage:

```gradle:
dependencyResolutionManagement {
		repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
		repositories {
			mavenCentral()
			maven { url 'https://jitpack.io' }
		}
	}
 dependencies {
	        implementation 'com.github.NaphonJangjit:Panyapradit:b7206f8e5b'
	}
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
     <version>b7206f8e5b</version>
  </dependency>
</dependencies>


```java
LinearRegression lr = new LinearRegression();
lr.train(List<Double> key, List<Double> values);
System.out.println(lr.predict(double d));

-LinearRegression
-MultipleLinearRegression
-PolynomialRegression

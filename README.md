# BitsCraftingAPI
**How to use**

Your .yml file has to be setup like this in order for the deserializer to work. Make sure that `Items:` is in the first key (the farthest left). It is highly recommended you create a custom config file for this.
```yaml
Items:
    example_item:
      name: "&eExample Item"
      item: DIAMOND_SWORD
      enchants:
        - "sharpness:1"
      lore:
        - "You can change this"
        - "Or add lines"
      amount: 1

```




**Maven Repository**
```
	<repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>
```

```
	<dependency>
	    <groupId>com.github.EvilTerabite</groupId>
	    <artifactId>BitsCraftingAPI</artifactId>
	    <version>v0.1-alpha</version>
	</dependency>
```

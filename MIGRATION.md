###Upgrade Notes
#### v0.3.0
* this release split apart success and failure callback's. replace your old single callback **for example** with the following:
```java
new Storyblok.SuccessCallback<Story>() {
    @Override
    public void onResponse(final Result<Story> result) {
    }
}, new Storyblok.ErrorCallback() {
    @Override
    public void onFailure(@Nullable IOException exception, @Nullable String response) {
    }
}
```
* in addition the `ErrorCallback` is optional
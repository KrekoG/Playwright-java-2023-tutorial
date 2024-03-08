# About

Following along a playwrigth tutorial by Koushik
Find the link [here](https://www.youtube.com/playlist?list=PL699Xf-_ilW7qlOrCGqwsWkgNkHQTqaBb).

## Playwright
## The code generator tool

Run:
```
mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="codegen <the url of the site you wish to visit>"
```

### The test trace reader tool

Run:
```
mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="show-trace <path or url to the zip file>"
```

Alternatively, feed it to the [progressive web app](https://trace.playwright.dev/).
It also accepts the zip file as a parameter: `https://trace.playwright.dev/?trace=<yourUrlToYourTraceFile.zip>`

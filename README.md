# About

Following along a playwrigth tutorial by Koushik
Find the link [here](https://www.youtube.com/playlist?list=PL699Xf-_ilW7qlOrCGqwsWkgNkHQTqaBb).

## Playwright
### The code generator tool

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

### Debugging

Set the `PWDEBUG=1` flag and set the `PLAYWRIGHT_JAVA_SRC="src/main/java/"` variable
in your environment (pointing to your source code) before running the tests.
In the code, use the `page.pause();` call to set a debug breakpoint where the debugger should pause.
```
PWDEBUG=1 PLAYWRIGHT_JAVA_SRC="src/main/java/" mvn test -Pdebug
```
You may also run playwright related commands in the console of your test browser
(unless running in headless mode).

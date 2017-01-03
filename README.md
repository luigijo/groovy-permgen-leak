## GroovyScriptEngine PermGen memory leak example

- Groovy 2.4.7
- JDK 1.7

Bug report: [GROOVY-7683](https://issues.apache.org/jira/browse/GROOVY-7683)

Workaround: inform a system property at startup of application: `-Dgroovy.use.classvalue=true` ([Groovy-7731](https://issues.apache.org/jira/browse/GROOVY-7731))


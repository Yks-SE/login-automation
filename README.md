# Login Automation E2E (Selenium + TestNG)

This is a minimal, production-grade starter to run end-to-end login tests against the Sauce Demo app.

## How to run

1. Install JDK 17+, Maven, and a browser (Chrome/Firefox/Edge).
2. From the project root, run:

```bash
mvn -B test                # defaults to Chrome
mvn -B test -Dbrowser=firefox
mvn -B test -Dbrowser=edge
```

*No driver downloads needed — Selenium Manager will resolve webdriver binaries automatically.*

### Reports
- HTML report at `target/extent-report.html`.
- Screenshots for failures at `target/screenshots/`.

## CI (GitHub Actions)
The workflow in `.github/workflows/selenium-ci.yml` runs tests headlessly on GitHub-hosted runners for Chrome and Firefox.

# Contributing to Dify API Java SDK

We love your input! We want to make contributing to the Dify API Java SDK as easy and transparent as possible, whether it's:

- Reporting a bug
- Discussing the current state of the code
- Submitting a fix
- Proposing new features
- Becoming a maintainer

## Development Process

We use GitHub to host code, to track issues and feature requests, as well as accept pull requests.

## Pull Requests

Pull requests are the best way to propose changes to the codebase. We actively welcome your pull requests:

1. Fork the repo and create your branch from `main`.
2. If you've added code that should be tested, add tests.
3. If you've changed APIs, update the documentation.
4. Ensure the test suite passes.
5. Make sure your code follows the existing code style.
6. Issue that pull request!

## Development Setup

### Prerequisites

- Java 8 or higher
- Maven 3.6+
- Git

### Setup Steps

1. **Clone the repository**
   ```bash
   git clone https://github.com/chat-pass/dify-api-java-sdk.git
   cd dify-api-java-sdk
   ```

2. **Build the project**
   ```bash
   mvn clean compile
   ```

3. **Run tests**
   ```bash
   mvn test
   ```

4. **Run code quality checks**
   ```bash
   mvn spotbugs:check checkstyle:check pmd:check
   ```

## Code Style

We use [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html) with some modifications:

- Use 4 spaces for indentation (not tabs)
- Line length limit is 120 characters
- Use meaningful variable and method names
- Add JavaDoc comments for public methods and classes

### Code Quality Tools

We use several tools to maintain code quality:

- **Checkstyle**: Enforces coding standards
- **SpotBugs**: Finds potential bugs
- **PMD**: Detects code smells
- **JaCoCo**: Measures test coverage

Run all checks with:
```bash
mvn clean verify
```

## Testing Guidelines

### Writing Tests

- Write unit tests for all public methods
- Use meaningful test method names
- Test both success and error scenarios
- Aim for high test coverage (>80%)

### Test Structure

```java
@Test
public void testMethodName_WhenCondition_ThenExpectedResult() {
    // Given
    // setup test data
    
    // When
    // call the method under test
    
    // Then
    // verify the results
}
```

### Running Tests

```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=DifyChatApiTest

# Run with coverage report
mvn test jacoco:report
```

## Commit Message Guidelines

We follow [Conventional Commits](https://www.conventionalcommits.org/):

```
<type>[optional scope]: <description>

[optional body]

[optional footer(s)]
```

### Types
- `feat`: A new feature
- `fix`: A bug fix
- `docs`: Documentation only changes
- `style`: Changes that do not affect the meaning of the code
- `refactor`: A code change that neither fixes a bug nor adds a feature
- `test`: Adding missing tests or correcting existing tests
- `chore`: Changes to the build process or auxiliary tools

### Examples
```
feat(chat): add streaming response support
fix(datasets): handle null response in document creation
docs: update README with new examples
test(workflow): add tests for workflow execution
```

## Issue Reporting

### Bug Reports

Please use the bug report template and include:

- A quick summary and/or background
- Steps to reproduce
- What you expected would happen
- What actually happens
- Sample code (if applicable)
- Environment details (OS, Java version, SDK version)

### Feature Requests

Please use the feature request template and include:

- A clear description of the problem you're trying to solve
- A description of the desired solution
- Any alternative solutions you've considered
- Additional context

## Documentation

- Update README.md if you change APIs
- Add/update JavaDoc comments for public APIs
- Update EXAMPLES.md if you add new features
- Add entries to CHANGELOG.md

## Release Process

1. Update version in `pom.xml`
2. Update `CHANGELOG.md`
3. Create a pull request
4. After merge, create a Git tag
5. GitHub Actions will automatically build and deploy

## Code of Conduct

This project and everyone participating in it is governed by our Code of Conduct. By participating, you are expected to uphold this code.

## Questions?

Feel free to contact us:
- Create an issue on GitHub
- Email: chatpass@ti-net.com.cn

## License

By contributing, you agree that your contributions will be licensed under the Apache License 2.0. 
# Security Policy

## Supported Versions

We provide security updates for the following versions of Dify API Java SDK:

| Version       | Supported          |
|---------------| ------------------ |
| 1.0.x.FINAL   | :white_check_mark: |
| < 1.0.1.FINAL | :x:                |

## Reporting a Vulnerability

We take the security of our SDK seriously. If you believe you have found a security vulnerability, please report it to us as described below.

### How to Report

**Please do not report security vulnerabilities through public GitHub issues.**

Instead, please send an email to: **chatpass@ti-net.com.cn**

Include as much of the following information as possible:

- A description of the vulnerability
- Steps to reproduce the issue
- Potential impact of the vulnerability
- Any possible mitigations you have identified

### What to Expect

- **Acknowledgment**: We will acknowledge receipt of your vulnerability report within 48 hours.
- **Investigation**: We will investigate the report and determine its validity and severity.
- **Updates**: We will keep you informed of our progress every 5-7 days.
- **Resolution**: We aim to resolve critical vulnerabilities within 90 days.

### Responsible Disclosure

We follow the principle of responsible disclosure:

1. **Grace Period**: We ask that you give us a reasonable amount of time to address the issue before disclosing it publicly.
2. **Coordination**: We may ask to coordinate the disclosure to ensure users have adequate time to update.
3. **Credit**: We will acknowledge your contribution in our security advisory (unless you prefer to remain anonymous).

## Security Measures

### Development Security

- **Dependency Scanning**: We regularly scan our dependencies for known vulnerabilities
- **Code Analysis**: We use static analysis tools to identify potential security issues
- **Automated Testing**: Our CI/CD pipeline includes security tests
- **Dependency Updates**: We keep dependencies up-to-date through automated monitoring

### API Security

- **Authentication**: All API calls require proper authentication
- **Input Validation**: We validate all inputs to prevent injection attacks
- **Error Handling**: We avoid exposing sensitive information in error messages
- **Rate Limiting**: We respect API rate limits to prevent abuse

### Data Security

- **No Sensitive Data Storage**: We do not store sensitive user data
- **Secure Transmission**: All communications use HTTPS/TLS
- **Minimal Permissions**: We request only necessary permissions
- **Secure Defaults**: Default configurations are secure

## Security Best Practices for Users

### API Key Management

```java
// ❌ Don't hardcode API keys
String apiKey = "dify-12345...";

// ✅ Use environment variables
String apiKey = System.getenv("DIFY_API_KEY");

// ✅ Use configuration files (not in version control)
Properties config = loadConfig("config.properties");
String apiKey = config.getProperty("dify.api.key");
```

### Network Security

```java
// ✅ Use HTTPS endpoints only
DifyApiFactory.newInstance("https://api.dify.ai/v1", apiKey);

// ✅ Configure proxy if needed
DIfyApiServiceGenerator.setHttpProxy("proxy.company.com", 8080, "user", "pass");

// ✅ Set reasonable timeouts
OkHttpClient client = new OkHttpClient.Builder()
    .connectTimeout(10, TimeUnit.SECONDS)
    .readTimeout(30, TimeUnit.SECONDS)
    .build();
```

### Input Validation

```java
// ✅ Validate user inputs
if (userInput == null || userInput.trim().isEmpty()) {
    throw new IllegalArgumentException("Input cannot be null or empty");
}

// ✅ Sanitize inputs if needed
String sanitizedInput = sanitizeInput(userInput);
```

### Error Handling

```java
// ✅ Handle errors gracefully without exposing sensitive information
try {
    response = difyChatApi.sendChatMessage(request);
} catch (DifyApiException e) {
    // Log the full error internally
    logger.error("API call failed", e);
    
    // Return generic error to user
    return "Service temporarily unavailable. Please try again later.";
}
```

## Security Updates

### Notification

We will notify users of security updates through:

- GitHub Security Advisories
- Release notes
- Email notifications (for registered users)

### Update Process

1. **Immediate**: Critical vulnerabilities are addressed immediately
2. **High Priority**: High-severity issues are addressed within 30 days
3. **Medium Priority**: Medium-severity issues are addressed in the next regular release
4. **Low Priority**: Low-severity issues are addressed when convenient

## Compliance and Standards

- **OWASP**: We follow OWASP security guidelines
- **CVE**: We participate in the CVE program for vulnerability disclosure
- **Industry Standards**: We adhere to industry-standard security practices

## Security Contact

For security-related questions or concerns:

- **Email**: chatpass@ti-net.com.cn
- **Response Time**: Within 48 hours during business days
- **Languages**: English, Chinese

## Hall of Fame

We would like to thank the following individuals for responsibly disclosing security vulnerabilities:

<!-- Future contributors will be listed here -->

---

*This security policy is reviewed and updated regularly to ensure it remains current and effective.* 

# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [1.0.2.FINAL] - 2025-06-12

### Added
- Implemented retry mechanism for API calls with configurable retry count and delay
- Added comprehensive monitoring system for API performance and error tracking
- Enhanced API parameter validation with detailed error messages
- Synchronized with the latest Dify API version (v1.0.0)

### Changed
- Updated API endpoints to match Dify's latest specifications
- Improved error handling with more descriptive messages
- Enhanced documentation with new API parameters and usage examples

### Fixed
- Resolved connection timeout issues in high-latency environments
- Fixed parameter validation edge cases
- Corrected API response parsing for new Dify API format

## [Unreleased]

### Added
- Added code quality tools (SpotBugs, Checkstyle, PMD)  
- Added test coverage reporting with JaCoCo
- Added automated dependency updates with Dependabot
- Added complete examples documentation (EXAMPLES.md)
- Added multi-language README support (EN, JP)

### Changed
- Improved project structure and organization
- Enhanced .gitignore with comprehensive file patterns
- Optimized Maven build configuration

### Fixed
- Removed duplicate slf4j-api dependency in pom.xml

## [1.0.1.FINAL] - 2025-06-02

### Added
- Initial release of Dify API Java SDK
- Support for Chat API (blocking and streaming modes)
- Support for Workflow API (execution and monitoring)
- Support for Datasets API (knowledge base management)
- Audio processing capabilities (text-to-speech, speech-to-text)
- File upload and management
- Comprehensive test suite 

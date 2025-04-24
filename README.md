# muncher-parent 
[![License: Apache-2.0](https://img.shields.io/badge/license-Apache%202.0-blue.svg)](./LICENSE)
[![CI](https://github.com/contentmunch/muncher-parent/actions/workflows/maven.yml/badge.svg)](https://github.com/contentmunch/muncher-parent/actions)


**Muncher Parent POM** for standardizing the build, dependency management, and plugin configuration across all muncher projects.

## Overview

This project defines a parent POM for the muncher ecosystem, providing:

- Centralized dependency management via BOMs.
- Shared plugin configuration (compiler, source, javadoc, GPG signing).
- Modules support.
- Java 21 compatibility.

## Modules

This parent project currently includes:

- muncher-bom
- muncher-foundation-spring-boot-starter
- muncher-auth-spring-boot-starter

## How to Use

In your Maven project, set `muncher-parent` as the parent:

```xml
<parent>
  <groupId>com.contentmunch</groupId>
  <artifactId>muncher-parent</artifactId>
  <version>1.0.0.RELEASE</version>
</parent>
```
This gives your project:
* Java 21 setup
* Centralized dependency versions
* Automatic source/javadoc jars
* GPG signing support
* Plugin configurations
* Compatibility with Maven Central publishing (via Sonatype)

## Dependency Management

The parent imports:
* Spring Boot BOM: 3.4.4
* OpenTelemetry BOM: 2.15.0
* muncher-bom for internal library version alignment

These ensure all projects use compatible dependency versions out of the box.

## Coding Guidelines

We follow [GitHub Flow](https://guides.github.com/introduction/flow/) for development. To maintain a high-quality, maintainable codebase, please follow these guidelines:

---

### Workflow Rules

- Branch from `develop` when starting work.
- Open pull requests **into `develop`**.
- Ensure your code is covered by tests (unit/integration as appropriate).
- Keep your branch up-to-date with `develop`.
- Follow existing code style and structure.

---

### Pull Request Format

Your PR description should follow this format:

Short summary of the change
	
Key changes (bullet points)
	
* Any notes on why/how things are implemented (optional)

Closes #

```
Add support for user authentication
• Introduced login API with JWT support
• Added controller, service, and security config
• Updated README

Closes #42

```

---

### Git Commit Format

We follow the [Conventional Commits](https://www.conventionalcommits.org/en/v1.0.0/) style:

Types

* feat: A new feature
* fix: A bug fix
* refactor: Internal code change without affecting behavior
* test: Adding or improving tests
* chore: Tooling, dependency updates, or minor infra changes

Notes:

* Use imperative, present tense: “add” not “added” or “adds”
* No trailing period in subject
* Max 100 characters for subject line

---
CI/CD and Quality

These are enforced via GitHub Actions and other automation tools.

	✅ Tests must pass before merging
	✅ Code formatting should match Google Java Format
	✅ PR should be reviewed and approved
	✅ No merge conflicts
---


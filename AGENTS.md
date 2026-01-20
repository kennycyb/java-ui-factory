# Java UI Factory - Developer Guide for AI Agents

## Project Overview

**Java UI Factory** is a Java framework that simplifies the creation of Java Swing applications using annotations. The library allows developers to build UI components declaratively through annotations rather than imperative code.

- **Repository**: https://github.com/kennycyb/java-ui-factory
- **Language**: Java 8+
- **Build Tool**: Maven
- **License**: Apache 2.0
- **Main Package**: `com.github.kennycyb.uifactory.core`

## Project Structure

```
java-ui-factory/
├── src/
│   ├── main/java/com/github/kennycyb/uifactory/core/
│   │   ├── components/      # UI component interfaces and base classes
│   │   ├── data/           # Data source interfaces
│   │   ├── events/         # Event handling system
│   │   ├── factory/        # Core factory and annotation processing
│   │   │   ├── annotations/    # UI annotations (@UiText, @UiSize, etc.)
│   │   │   ├── enums/          # Enumeration types
│   │   │   └── impl/           # Factory implementations
│   │   ├── layout/         # Layout managers and handlers
│   │   ├── listeners/      # Event listeners
│   │   └── utils/          # Utility classes
│   └── test/               # Unit tests
├── docs/                   # Documentation
│   ├── components/         # Component-specific documentation
│   ├── events/            # Event handling documentation
│   └── layouts/           # Layout documentation
├── pom.xml                # Maven build configuration
└── README.md              # Project README

```

## Key Concepts

### 1. Annotation-Driven UI Creation

The library uses annotations to define UI components and their properties. Example:

```java
@UiText("Sample Window")
@UiWindowPosition(WindowPosition.CENTER)
@UiLayout(SpringLayout.class)
@UiFrameCloseOperation(FrameCloseOperation.EXIT)
public class MyWindow extends JFrame {
    
    @UiText("Username:")
    JLabel usernameLabel;
    
    @UiText("")
    @UiColumns(20)
    JTextField usernameField;
    
    public static void main(String[] args) {
        UiFactory.instance().createComponent(MyWindow.class).setVisible(true);
    }
}
```

### 2. Core Annotations

- **@UiText**: Set text/title for components
- **@UiSize**: Define component dimensions
- **@UiColumns**: Set text field column width
- **@UiLayout**: Specify layout manager (SpringLayout, BorderLayout, FlowLayout, etc.)
- **@UiWindowPosition**: Position window (CENTER, TOP_LEFT, etc.)
- **@UiFrameCloseOperation**: Define close operation behavior
- **@UiScrollable**: Make components scrollable
- **@UiSpringGridConstraint**: Spring layout grid constraints
- **@UiPreInit**: Mark methods to run before initialization
- **@UiSimpleItems**: Populate combo boxes or lists with items

### 3. Event Handling

The framework supports auto-wiring of UI events through naming conventions:

```java
private void onNotepad_windowClosing(WindowEvent e) {
    LOGGER.debug("Window closing");
}

private void onNotepad_windowActivated(WindowEvent e) {
    LOGGER.debug("Window activated");
}
```

Event method naming pattern: `on{ComponentName}_{eventType}`

### 4. Supported Layouts

- **Standard Java Layouts**: SpringLayout, BorderLayout, FlowLayout
- **Custom Layouts**: VerticalFlowLayout, NullLayout
- **Spring Grid Layouts**: Compact grid support with @UiSpringGridConstraint

### 5. Factory Pattern

The `UiFactory.instance().createComponent()` method is the entry point for creating UI components. It processes annotations and constructs the UI hierarchy.

## Build and Test

### Building the Project

```bash
mvn clean compile
```

### Running Tests

```bash
mvn test
```

### Creating Package

```bash
mvn package
```

### Installing Locally

```bash
mvn clean install
```

## Code Conventions

### Java Style
- Use Java 8+ features
- Follow standard Java naming conventions (camelCase for methods/fields, PascalCase for classes)
- Use SLF4J with Logback for logging

### Annotations
- Annotation classes are in `com.github.kennycyb.uifactory.core.factory.annotations`
- Annotation handlers implement `IAnnotationHandler` interface
- Factory implementations are in `com.github.kennycyb.uifactory.core.factory.impl`

### Testing
- Unit tests use JUnit 4
- Mock objects use JMock framework
- Test classes mirror the structure of main classes in `src/test/java`

## Common Tasks

### Adding a New Annotation

1. Create annotation class in `factory/annotations/`
2. Create handler implementing `IAnnotationHandler`
3. Register handler in the factory system
4. Add tests for the new annotation
5. Update documentation in `docs/`

### Adding a New Component Factory

1. Create factory class in `factory/impl/components/`
2. Implement `IComponentFactory` interface
3. Handle component-specific annotations
4. Add unit tests
5. Update component documentation

### Adding a New Layout Manager

1. Create layout manager in `layout/managers/`
2. Create layout handler in `layout/handlers/`
3. Add layout-specific annotations if needed
4. Add example usage in documentation

## Dependencies

- **cglib-nodep**: For dynamic proxy generation
- **commons-lang3**: Common utilities
- **logback-classic**: Logging implementation
- **junit**: Testing framework
- **jmock-junit4**: Mocking framework for tests

## Related Projects

- **java-ui-factory-ext**: Extension project with additional features
  - Repository: https://github.com/kennycyb/java-ui-factory-ext

## Documentation

Detailed documentation is available in the `docs/` directory:
- Component guides in `docs/components/`
- Event handling in `docs/events/`
- Layout documentation in `docs/layouts/`
- Main documentation: `docs/README.md`

## Migration Note

This project was originally hosted on Google Code and migrated to GitHub. Historical references may point to https://code.google.com/p/java-ui-factory.

## Support

- **Issues**: https://github.com/kennycyb/java-ui-factory/issues
- **Main Developer**: Kenny Chong (kennycyb@gmail.com)

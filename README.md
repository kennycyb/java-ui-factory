# java-ui-factory

The Java UI Factory Library is a framework that helps create Java Swing Application easily and faster.

The library used annotations to build UI components.

Read more in [docs](docs/)

## java-ui-factory-ext

This project extends java-ui-factory, see [java-ui-factory-ext](https://github.com/kennycyb/java-ui-factory-ext)

# Features

## Layouts

```java
@UiText("Sample - SpringLayout - CompactGrid")
@UiWindowPosition(WindowPosition.CENTER)
@UiLayout(SpringLayout.class)
@UiFrameCloseOperation(FrameCloseOperation.EXIT)
@UiSpringGridConstraint(gridType = SpringGridType.COMPACT)
public class SpringLayoutCompactGridSample extends JFrame {
                                            
    private static Logger LOGGER = LoggerFactory.getLogger(SpringLayoutCompactGridSample.class);

    @UiText("Host:")
    JLabel label1;

    @UiText("127.0.0.1")
    @UiColumns(20)
    JTextField mHost;

    @UiText("Port:")
    JLabel label2;

    @UiText("21")
    @UiColumns(5)
    JTextField mPort;

    public static void main(String[] args) {
        UiFactory.instance().createComponent(SpringLayoutCompactGridSample.class).setVisible(true);
    }
}
```

## Components

### Create a scrollbar automatically for a JTextArea
```java
@UiScrollable(horizontal = ScrollBarPolicy.ALWAYS, vertical = ScrollBarPolicy.ALWAYS)
private JTextArea content;
```

## Auto Wired UI Event

```java
private void onNotepad_windowClosing(WindowEvent e) {
    LOGGER.debug("onNotepad_windowClosing: new state={}", e.getNewState());
}

private void onNotepad_windowActivated(WindowEvent e) {
    LOGGER.debug("onNotepad_windowActivated");
}
```

# Migrated from Google Code

This project was migrated from https://code.google.com/p/java-ui-factory



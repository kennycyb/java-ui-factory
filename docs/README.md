# Java UI Factory

The Java UI Factory uses annotations to ease of creating Java Swing application easily.

# Features

## Layouts

### Standard Java Layout

* [SpringLayout](layouts/SpringLayout.md)
* [BorderLayout](layouts/BorderLayout.md)
* [FlowLayout](layouts/FlowLayout.md)

### Custom Java Layout

* [NullLayout](layouts/NullLayout.md)
* [VerticalFlowLayout](layouts/VerticalFlowLayout.md)

### Layout components with inner classes

* [InnerClass](layouts/InnerClasses.md)

## Components

```java
@UiScrollable(horizontal = ScrollBarPolicy.ALWAYS, vertical = ScrollBarPolicy.ALWAYS)
private JTextArea content;
```

* JTabbedPane(components/JTabbedPane.md), JMenuBar, JToolBar, JTextField, JProgressBar, JRadioButton, JTextArea, JList

## Auto Wired UI Event

* [WindowListener](events/WindowListener.md)

### Sample

```java
private void onNotepad_windowClosing(WindowEvent e) {
    LOGGER.debug("onNotepad_windowClosing: new state={}", e.getNewState());
}

private void onNotepad_windowActivated(WindowEvent e) {
    LOGGER.debug("onNotepad_windowActivated");
}
```

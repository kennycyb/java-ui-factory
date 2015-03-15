# java-ui-factory
Automatically exported from code.google.com/p/java-ui-factory

# Java UI Factory

The Java UI Factory Library is a framework that help create Java Swing Application easily and faster.

The library used JAVA annotations to automatically build UI component.

See [Overview].

There are three projects:
 * [Overview java-ui-factory-core], [ReleaseNote Release Note for java-ui-factory-core]
 * [OverviewExt java-ui-factory-ext], [ReleaseNoteExt Release Note for java-ui-factory-ext]
 * java-ui-factory-sample - Sample codes


# Features

* See [NewReleasePreview] for upcoming features.

## Simplified Layouts

Standard Java Layout
 * Using [SpringLayout]
 * Using [BorderLayout]
 * Using [FlowLayout]

Custom Java Layout
 * Using [NullLayout]
 * Using [VerticalFlowLayout]

Layout Components with Inner Class
 * Using [InnerClasses]

## Components

|| [JTabbedPane] || [JMenuBar] || [JToolBar] || [JTextField] ||
|| [JToolBar]   || [JProgressBar] || [JRadioButton] || [JTextArea] || 
|| [JList] ||
	
```
@UiScrollable(horizontal = ScrollBarPolicy.ALWAYS, vertical = ScrollBarPolicy.ALWAYS)
private JTextArea content;
```


## Auto Wired of UI event

 * Annotate with [AutoWired]
 * Method signature: 
```
    void on[ComponentName]_[Actions] () {}
```

_See [SampleNotePadApplication], [WindowListener], [WindowFocusListener]_

### Standard Java AWT/Swing events

 * [WindowListener], [WindowFocusListener]
 * [ActionListener], [ItemListener]
 * [MouseMotionListener], [MouseListener], [MouseWheelListener]
 * [KeyListener]

### Using Custom Events
 * [EventHandler]

=== Sample ===

```
private void onNotepad_windowClosing(WindowEvent e) {
	LOGGER.debug("onNotepad_windowClosing: new state={}", e.getNewState());
}

private void onNotepad_windowActivated(WindowEvent e) {
	LOGGER.debug("onNotepad_windowActivated");
}
```



## JMenuBar from XML

* Declare the JMenuBar,
```
@UiResource("Notepad-Menu.xml")
private JMenuBar menuBar;
```

* Create the XML file,

```
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<menuBar id="mainMenu">
    <menu text="File">
        <menuItem id="file.new" text="New"/>
        <menuItem text="Open..."/>
        <menuItem text="Save"/>
        <menuItem text="Save As..."/>
        <menuItem type="SEPARATOR"/>
        <menuItem text="Page Setup..."/>
	<menuItem text="Print..."/>
	<menuItem type="SEPARATOR"/>
	<menuItem id="file.exit" text="Exit"/>
    </menu>
</menuBar>
```

More details at [SampleNotePadApplication]

# Development Environment

* This library support JDK6/JDK7 only.

# Others

* Softpedia: http://mac.softpedia.com/get/Development/Java/Java-UI-Factory.shtml



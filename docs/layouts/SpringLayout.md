# SpringLayout

# Sample Code

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



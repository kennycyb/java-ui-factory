# VerticalFlowLayout

```java
@UiText("Vertical Flow Layout Sample")
@UiWindowPosition(WindowPosition.CENTER)
@UiFrameCloseOperation(FrameCloseOperation.EXIT)
@UiLayout(VerticalFlowLayout.class)
public class VerticalFlowLayoutSample extends JFrame {

    @SuppressWarnings("unused")
    private static Logger LOGGER = LoggerFactory.getLogger(VerticalFlowLayoutSample.class);

    @UiText("line 1")
    JLabel line1;

    @UiText("line 2")
    JLabel line2;

    @UiText("text 3")
    JTextField text3;

    @UiText("check 4")
    JCheckBox check4;

    @UiScrollable(autoScroll = true, horizontal = ScrollBarPolicy.ALWAYS, vertical = ScrollBarPolicy.ALWAYS)
   @UiText("=============== text area 5 ===============")
    @UiRows(5)
    JTextArea textarea5;

    public static void main(String[] args) {
        SwingFactory.create(VerticalFlowLayoutSample.class).setVisible(true);
    }
}
```

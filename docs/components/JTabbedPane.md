```
@UiWindowPosition(WindowPosition.CENTER)
@UiSize(height = 600, width = 800)
@UiLayout(BorderLayout.class)
public class TabbedPaneSample extends JFrame {
        private static Logger LOGGER = LoggerFactory
                        .getLogger(TabbedPaneSample.class);

        public static class Tab1 extends JPanel {

                @JTextFieldProperties(text = "Tab1")
                JTextField field1;

        }

        public static class Tab2 extends JPanel {

                @UiText("HELLO")
                JLabel field2;
        }

        @UiBorderLayoutConstraint(BorderLayoutConstraint.CENTER)
        JTabbedPane tabbedPane;

        @UiName("Tab 1")
        @UiComponentOf("tabbedPane")
        Tab1 tab1;

        @UiName("Tab 2")
        @UiComponentOf("tabbedPane")
        Tab2 tab2;

        public static void main(String[] args) {
                UiFactory.create(TabbedPaneSample.class).setVisible(true);
        }
}
```
package frame;


public class InfoModal extends BaseModal {

    static int WIDTH = 1024, HEIGHT = 820;

    InfoModal() {
        super("", WIDTH, HEIGHT, new InfoFrame());
    }

    public static void main(String[] args) {
        new InfoModal().setVisible(true);
    }

    static class InfoFrame extends BaseFrame {
        InfoFrame() {
            super("", WIDTH, HEIGHT);
        }
    }
}

package frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainFrame extends BaseFrame {
    MainFrame() {
        super("MAIN", 800, 400);
        JLabel hyperlink = new JLabel("<html><p style = 'color:blue'><U>Login<U></p></html>");
        JPanel northpanel = new JPanel(new BorderLayout());
        JPanel north_south = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        north_south.setForeground(Color.white);
        northpanel.setForeground(Color.white);

        hyperlink.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mouseClicked(e);
                if (checkLogin == false) {
                    openModal(new LoginModal());
                } else {
                    hyperlink.setText("<html><p style = 'color:blue'><U>Login<U></p></html>");
                    checkLogin = false;
                    userNo = -1;
                    userName = null;
                    userId = null;
                }
            }
        });

        JPanel centerpanel = new JPanel(new GridLayout(0, 1));
        centerpanel.setForeground(Color.white);

        ActionListener[] actionListeners = new ActionListener[]{e -> openModal(new ReservationModal()), e -> openModal(new MyReservationModal()), e -> openModal(new ScheduleModal()), e -> openModal(new InfoModal())};

        int i = 1;
        for (ActionListener actionListener : actionListeners) {
            JButton button;
            centerpanel.add(button = createButton("", actionListener));
            button.setIcon(getImage("./datafiles/img/Button" + i, 200, 330));
            i++;
        }

        north_south.add(hyperlink);
        northpanel.add(createLabel(new JLabel("<html><p style = 'color:orange'>SMART air<p></html>", 0), new Font("Gothic", 1, 26)), BorderLayout.NORTH);
        northpanel.add(north_south, BorderLayout.SOUTH);
        add(northpanel, BorderLayout.NORTH);
        add(centerpanel);
    }

    public void action(ActionEvent event) {

    }

    public static void main(String[] args) {
        new MainFrame().setVisible(true);
    }
}

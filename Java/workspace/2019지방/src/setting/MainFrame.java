package setting;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MainFrame extends BaseFrame {

    String currentMenuGroup = "음료";
    int currentMenuNo = 1;

    JPanel menuPanel = createComponent(new JPanel(new FlowLayout(FlowLayout.LEFT)), 580, 400);
    JLabel lbDetailImg = createComponent(new JLabel(), 10, 50, 100, 100);
    JTextField tfMenuName = createComponent(new JTextField(), 175, 0, 130, 25);
    JTextField tfPrice = createComponent(new JTextField(), 175, 35, 130, 25);
    JComboBox<Integer> cbCount = createComponent(new JComboBox<>(), 175, 70, 130, 25);
    JComboBox<String> cbSize = createComponent(new JComboBox<>(), 175, 105, 130, 25);
    JTextField tfAmount = createComponent(new JTextField(), 175, 140, 130, 25);
    JLabel lbUserInfo = createLabel(new JLabel("", 2), new Font("맑은고딕", 1, 13));

    JPanel detailPanel = createComponent(new JPanel(new BorderLayout()),  310, 210);

    MainFrame() {
        super(700, 600, "STARBOX");
        JPanel northPanel = new JPanel(new BorderLayout());
        JPanel north_south = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel westPanel = createComponent(new JPanel(new FlowLayout()), 55, 0);
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        northPanel.add(lbUserInfo, BorderLayout.NORTH);

        north_south.add(createButton("구매내역", e -> openFrame(new OrderListFrame())));
        north_south.add(createButton("장바구니", e -> openFrame(new ShoppingFrame())));
        north_south.add(createButton("인기 상품 Top5", e -> openFrame(new Top5Frame())));
        north_south.add(createButton("Logout", e -> openFrame(new LoginFrame())));

        northPanel.add(north_south, BorderLayout.SOUTH);

        for (String text : new String[]{"음료", "푸드", "상품"}) {
            westPanel.add(createComponent(createButtonWithOutMargin(text, e -> clickGroup(text)), 55, 35));
        }

        JScrollPane jScrollPane = createComponent(new JScrollPane(menuPanel), 590, 480);


        JPanel detailCenter = createComponent(new JPanel(null), 310, 170);
        JPanel detailBottom = new JPanel();

        String[] labelTextList = {"주문메뉴 : ", "가격 : ", "수량 : ", "사이즈 : ", "총금액 : "};
        for (int i = 0; i < labelTextList.length; i++) {
            detailCenter.add(createComponent(new JLabel(labelTextList[i], JLabel.RIGHT), 105, i * 35, 60, 25));
        }
        tfMenuName.setEditable(false);
        tfPrice.setEditable(false);
        tfAmount.setEditable(false);

        detailPanel.setVisible(false);
        detailCenter.add(lbDetailImg);
        detailCenter.add(tfMenuName);
        detailCenter.add(tfPrice);
        detailCenter.add(cbCount);
        detailCenter.add(cbSize);
        detailCenter.add(tfAmount);

        for (int i = 1; i <= 10; i++) {
            cbCount.addItem(i);
        }

        detailBottom.add(createButton("장바구니에 담기", e -> clickCart()));
        detailBottom.add(createButton("구매하기", e -> clickBuy()));

        detailPanel.add(detailCenter, BorderLayout.CENTER);
        detailPanel.add(detailBottom, BorderLayout.SOUTH);
        detailPanel.setVisible(false);

        cbCount.addActionListener(e -> tfAmount.setText("" + getAmount()));
        cbSize.addActionListener(e -> tfAmount.setText("" + getAmount()));

        centerPanel.add(jScrollPane);
        centerPanel.add(detailPanel);

        add(centerPanel);
        add(northPanel, BorderLayout.NORTH);
        add(westPanel, BorderLayout.WEST);

        clickGroup("음료");
        updateLabel();
    }

    public static void main(String[] args) {
        new MainFrame().setVisible(true);
    }

    public void updateLabel() {
        lbUserInfo.setText("회원명 : " + userName + " / 회원등급 : " + userGrade + " / 총 누적 포인트 : " + userPoint);
    }

    public void clickGroup(String text) {
        try (PreparedStatement pst = connection.prepareStatement("select * from menu where m_group = ?")) {
            pst.setObject(1, text);
            ResultSet rs = pst.executeQuery();

            int count = 0;
            menuPanel.removeAll();

            while (rs.next()) {
                menuPanel.add(new ImagePanel(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4)));
                count++;
            }

            int rows = count / 3;

            if (count % 3 != 0) {
                rows++;
            }
            menuPanel.setPreferredSize(new Dimension(570, 205 * rows));
            collapse();
        } catch (Exception e) {
            e.printStackTrace();
        }
        revalidate();
    }

    private void collapse() {
        setSize(700, getHeight());
        detailPanel.setVisible(false);
        setLocationRelativeTo(null);
    }

    public void clickBuy() {
        int amount = getAmount();
        int answer = JOptionPane.NO_OPTION;
        String curGrade = userGrade;
        String newGrade = userGrade;
        if (userPoint > getAmount()) {
            answer = JOptionPane.showConfirmDialog(null,
                    "회원님의 총 포인트 : " + userPoint + "\n" + "포인트로 결제하시겠습니까?\n(아니오를 클릭 시 현금결제가 됩니다)", "결제수단",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        }
        if (answer == JOptionPane.NO_OPTION) {
            try (var pst = connection.prepareStatement("insert into orderlist values(0,CURDATE(),?,?,?,?,?,?,?)")) {
                pst.setObject(1, userNo);
                pst.setObject(2, currentMenuNo);
                pst.setObject(3, currentMenuGroup);
                pst.setObject(4, cbSize.getSelectedItem());
                pst.setObject(5, tfPrice.getText());
                pst.setObject(6, cbCount.getSelectedItem());
                pst.setObject(7, amount);
                pst.execute();
            } catch (Exception e2) {
                e2.printStackTrace();
            }

            try (var pst = connection.prepareStatement("select sum(o_amount) from orderlist where u_no = ? ")) {
                pst.setObject(1, userNo);

                var rs = pst.executeQuery();
                rs.next();
                int result = rs.getInt(1);

                if (result >= 800000) {
                    newGrade = "Gold";
                } else if (result >= 500000) {
                    newGrade = "Silver";
                } else if (result >= 300000) {
                    newGrade = "Bronze";
                } else {
                    newGrade = "일반";
                }

            } catch (Exception e1) {
                e1.printStackTrace();
            }

            userPoint += amount * 0.05f;

            informationMessage("구매되었습니다.");

            if (curGrade.equals(newGrade) == false) {
                informationMessage("축하합니다!\n회원님 등급이 " + newGrade + "로 승급하셨습니다.");
                userGrade = newGrade;
            }

        } else {
            userPoint -= amount;
            informationMessage("포인트로 결제 완료되었습니다.\n남은 포인트 : " + userPoint);
        }

        try (var pst = connection.prepareStatement("update user set u_point = ? u_grade = ? where u_no = ?")) {
            pst.setObject(1, userPoint);
            pst.setObject(2, userGrade);
            pst.setObject(3, userNo);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        updateLabel();
    }

    public void clickCart() {
        try (var pst = connection.prepareStatement("insert into shopping values (0,?,?,?,?,?)")) {
            pst.setObject(1, currentMenuNo);
            pst.setObject(2, tfPrice.getText());
            pst.setObject(3, cbCount.getSelectedItem());
            pst.setObject(4, cbSize.getSelectedItem());
            pst.setObject(5, getAmount());
            pst.execute();
            informationMessage("장바구니에 담았습니다.");
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private class ImagePanel extends JPanel {

        ImagePanel(int menuNo, String group, String menuName, int price) {
            String path = "./DataFiles/이미지/" + menuName + ".jpg";
            setPreferredSize(new Dimension(180, 200));
            setLayout(new BorderLayout());
            var lbImg = new JLabel(getImage(path, 180, 180));
            var lbText = new JLabel(menuName, 0);

            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    super.mousePressed(e);
                    currentMenuNo = menuNo;
                    currentMenuGroup = group;

                    detailPanel.setVisible(true);
                    MainFrame.this.setSize(700 + detailPanel.getWidth() + 20, 600);
                    setLocationRelativeTo(null);

                    lbDetailImg.setIcon(getImage(path, lbDetailImg.getWidth(), lbDetailImg.getHeight()));
                    tfMenuName.setText(menuName);
                    tfPrice.setText("" + price);

                    cbSize.removeAllItems();

                    if (group.equals("상품") == false) {
                        cbSize.addItem("M");
                        cbSize.addItem("L");
                    }
                    tfAmount.setText("" + getAmount());
                }
            });
            lbImg.setBorder(new LineBorder(Color.black));
            add(lbImg);
            add(lbText, BorderLayout.SOUTH);
        }
    }

    private int getAmount() {
        int price = Integer.parseInt(tfPrice.getText());
        int count = (Integer) cbCount.getSelectedItem();

        if (cbSize.getSelectedIndex() == 1) {
            price += 1000;
        }

        float discount = 0;

        if (userGrade.equals("Bronze")) {
            discount = 0.03f;
        } else if (userGrade.equals("silver")) {
            discount = 0.05f;
        } else if (userGrade.equals("Gold")) {
            discount = 0.1f;
        }

        return (int) ((price * count) * (1f - discount));
    }
}

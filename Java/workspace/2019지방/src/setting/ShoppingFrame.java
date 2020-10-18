package setting;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

public class ShoppingFrame extends BaseFrame {
    DefaultTableModel model = new DefaultTableModel("메뉴명,가격,수량,사이즈,금액".split(","), 0);
    JTable table = new JTable(model);

    public ShoppingFrame() {
        super(700, 350, "장바구니");

        add(createComponent(createLabel(new JLabel(userName + "장바구니", JLabel.CENTER), new Font("굴림", Font.BOLD, 24)), 0,
                45), BorderLayout.NORTH);

        table.removeColumn(table.getColumn("n"));
        table.getColumn("메뉴명").setPreferredWidth(200);
        add(new JScrollPane(table), BorderLayout.CENTER);

        var sp = new JPanel();

        sp.add(createComponent(createButton("구매", this::clickBuy), 100, 30));
        sp.add(createComponent(createButton("삭제", this::clickDelete), 100, 30));
        sp.add(createComponent(createButton("닫기", e -> openFrame(new MainFrame())), 100, 30));
        add(sp, BorderLayout.SOUTH);
        refresh();
    }

    public void refresh() {
        model.setRowCount(0);
        try (var pst = connection.prepareStatement(
                "select shopping.*, menu.m_name from shopping inner join menu on shopping.m_no = menu.m_no")) {
            var rs = pst.executeQuery();

            while (rs.next()) {
                model.addRow(
                        new Object[]{rs.getInt(1), rs.getString(7), rs.getInt(3), rs.getInt(4), rs.getString(5), rs.getInt(6)});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clickDelete(ActionEvent e) {
        int row = table.getSelectedRow();

        if (row == -1) {
            errorMessage("삭제할메뉴를 선택해주세요.");
            return;
        }
        try {
            statement.execute("delete from shopping where s_no = " + model.getValueAt(row, 0));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        model.removeRow(row);
    }

    public void clickBuy(ActionEvent event) {
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
            try {
                statement.execute("INSERT INTO orderlist ("
                        + "SELECT 0, CURDATE(), " + userNo + ", shopping.m_no, m_group, s_size, s_price, s_count, s_amount FROM shopping "
                        + "INNER JOIN menu on shopping.m_no = menu.m_no)");

            } catch (SQLException e2) {
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
    }

    public int getAmount() {
        int amount = 0;
        for (int i = 0; i < table.getRowCount(); i++) {
            amount += (int) table.getValueAt(i, 5);
        }
        return amount;
    }

    public static void main(String[] args) {
        new ShoppingFrame().setVisible(true);
    }
}

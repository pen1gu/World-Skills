import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;

public class NYPC_7 {

    static String[] equation = {"+", "-", "*", "/", "."};

    public static void main(String[] args) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(bufferedReader.readLine());
        for (int i = 0; i < n; i++) {
            String[] input = bufferedReader.readLine().split(" ");
            loop:
            for (int j = 0; j < 5; j++) {
                for (int k = 0; k < 5; k++) {
                    if (j == k) continue;

                    boolean bResult = getResult(input[0], equation[j], input[1], equation[k], input[2], input[3]);
                    if (bResult) {
                        System.out.println(input[0] + equation[j] + input[1] + equation[k] + input[2] + "=" + input[3]);
                        break loop;
                    }
                }
            }
        }
        bufferedReader.close();
    }

    public static boolean getResult(String n1, String equ1, String n2, String equ2, String n3, String n4) {
        BigDecimal no1 = new BigDecimal(0);
        BigDecimal no2 = new BigDecimal(0);
        BigDecimal no3 = new BigDecimal(0);
        BigDecimal no4 = new BigDecimal(n4);
        BigDecimal result = new BigDecimal(0);
        boolean bResult = false;
        if (".".equals(equ1)) {
            no1 = new BigDecimal(n1 + "." + n2);
            no3 = new BigDecimal(n3);
            result = calculate(no1, equ2, no3);
        } else if (".".equals(equ2)) {
            no1 = new BigDecimal(n1);
            no3 = new BigDecimal(n2 + "." + n3);
            result = calculate(no1, equ1, no3);
        } else {
            no1 = new BigDecimal(n1);
            no2 = new BigDecimal(n2);
            no3 = new BigDecimal(n3);
            if ("/".equals(equ1) || "*".equals(equ1)){
                result = calculate(no1, equ1, no2);
                result = calculate(result, equ2, no3);
            }else if ("*".equals(equ2) || "/".equals(equ2)) {
                result = calculate(no2, equ2, no3);
                result = calculate(no1, equ1, result);
            } else {
                result = calculate(no1, equ1, no2);
                result = calculate(result, equ2, no3);
            }
        }
        if (result.compareTo(no4) == 0) {
            bResult = true;
        }
        return bResult;
    }

    public static BigDecimal calculate(BigDecimal n1, String equ1, BigDecimal n2) {
        BigDecimal result = new BigDecimal(0);
        if ("+".equals(equ1)) {
            result = n1.add(n2);
        } else if ("-".equals(equ1)) {
            result = n1.subtract(n2);
        } else if ("*".equals(equ1)) {
            result = n1.multiply(n2);
        } else if ("/".equals(equ1)) {
            result = n1.divide(n2, 0, BigDecimal.ROUND_DOWN);
        }
        return result;
    }
}



import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class NYPC_4 {

    final static int INDEX = 0;
    final static int ORIGINAL_VALUE = 1;
    static int[][] arr_new;
    static boolean[] dp;
    static StringTokenizer stringTokenizer;
    public static void main(String[] args) throws Exception{

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(bufferedReader.readLine());
        String sk = bufferedReader.readLine();
        stringTokenizer = new StringTokenizer(sk," ");
        int s = Integer.parseInt(stringTokenizer.nextToken()), k = Integer.parseInt(stringTokenizer.nextToken());

        arr_new = new int[n][2];
        dp = new boolean[n];

        String input = bufferedReader.readLine();
        stringTokenizer = new StringTokenizer(input," ");
        for (int i = 0; i < n; i++) {
            arr_new[i][INDEX] = i + 1;
            arr_new[i][ORIGINAL_VALUE] = Math.abs(Integer.parseInt(stringTokenizer.nextToken()));
        }

        int near_index = 1;
        for (int i = 0; i < k; i++) {
            long min = Integer.MAX_VALUE;
            for (int j = 0; j < n; j++) {
                if (dp[j] != true) {
                    long value = Math.abs(arr_new[j][ORIGINAL_VALUE] - s);
                    if (min >= value) {
                        if (min == value) {
                            if (arr_new[near_index - 1][ORIGINAL_VALUE] < arr_new[j][ORIGINAL_VALUE]) {
                                continue;
                            }
                        }
                        min = value;
                        near_index = (int) arr_new[j][INDEX];
                    }
                }
            }
            System.out.print(near_index + " ");
            dp[near_index - 1] = true;
        }
        bufferedReader.close();
    }
}

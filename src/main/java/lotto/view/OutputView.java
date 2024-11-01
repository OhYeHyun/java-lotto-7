package lotto.view;

public class OutputView {

    public static void print(String string) {
        System.out.println(string);
    }

    public static void promptPurchaseAmount() {
        print("구입금액을 입력해 주세요.");
    }

    public static void promptWinningNumbers() {
        print("당첨 번호를 입력해 주세요.");
    }
}
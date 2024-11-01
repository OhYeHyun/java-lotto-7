package lotto.validator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class WinningNumbersValidatorTest {
    private NumbersValidator winningNumbersValidator;

    @Test
    @DisplayName("로또 번호가 숫자인지 확인")
    void 로또_번호_숫자_테스트() {
        assertThatThrownBy(() -> {
            winningNumbersValidator = new NumbersValidator("!,3,@");
        }).isInstanceOf(IllegalArgumentException.class).hasMessage("[ERROR] 로또 번호는 숫자 형식이어야 합니다.");
    }

    @Test
    @DisplayName("당첨 번호가 6개인지 확인")
    void 당첨_번호_개수_테스트() {
        winningNumbersValidator = new NumbersValidator("1,2,3");
        assertThatThrownBy(() -> {
            winningNumbersValidator.validateWinningNumbers();
        }).isInstanceOf(IllegalArgumentException.class).hasMessage("[ERROR] 당첨 번호는 6개이어야 합니다.");
    }

    @Test
    @DisplayName("보너스 번호가 1개인지 확인")
    void 보너스_번호_개수_테스트() {
        winningNumbersValidator = new NumbersValidator("7,8");
        assertThatThrownBy(() -> {
            winningNumbersValidator.validateBonusNumber();
        }).isInstanceOf(IllegalArgumentException.class).hasMessage("[ERROR] 보너스 번호는 1개이어야 합니다.");
    }
}
package lotto.domain;

import lotto.Lotto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LottoGeneratorTest {
    private LottoGenerator lotto;

    @Test
    @DisplayName("6개의 로또 번호를 랜덤으로 생성하는 기능 확인")
    void 로또_번호_생성_테스트() {
        Lotto lotto = new Lotto(LottoGenerator.generateNumbers());
        assertThat(lotto.getNumbers().size()).isEqualTo(6);
    }
}
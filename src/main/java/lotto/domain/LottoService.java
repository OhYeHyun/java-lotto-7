package lotto.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lotto.Lotto;

import static lotto.domain.LottoConstants.*;

public class LottoService {
    private final Map<Integer, Integer> winningRankCount = initializeWinningRankCount();
    private final List<Lotto> lottoTickets;
    private final Lotto winningNumbers;
    private final Lotto bonusNumber;

    private BigDecimal winningAmount;
    private BigDecimal earningsRate;

    public LottoService(List<Lotto> lottoTickets, Lotto winningNumbers, Lotto bonusNumber) {
        this.lottoTickets = lottoTickets;
        this.winningNumbers = winningNumbers;
        this.bonusNumber = bonusNumber;
    }

    private static Map<Integer, Integer> initializeWinningRankCount() {
        Map<Integer, Integer> initialCount = new HashMap<>();
        for (int rank = FIRST_RANK; rank <= NUMBER_OF_RANKS; rank++) {
            initialCount.put(rank, INITIAL_WINNING_COUNT);
        }

        return initialCount;
    }

    public void calculateWinningAmount() {
        calculateRankResults();
        this.winningAmount = winningRankCount.entrySet().stream()
                .map(entry -> calculatePrizeAmount(entry.getKey(), entry.getValue()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculatePrizeAmount(int rank, int count) {
        BigDecimal prizeAmount = BigDecimal.valueOf(PRIZE_AMOUNT_BY_RANK.get(rank));
        return prizeAmount.multiply(BigDecimal.valueOf(count));
    }

    private void calculateRankResults() {
        for (Lotto ticket : lottoTickets) {
            int matchCount = ticket.getMatchCount(winningNumbers);
            int rank = determineRank(matchCount, ticket);

            updateWinningRankCount(rank);
        }
    }

    private void updateWinningRankCount(int rank) {
        if (rank > 0) {
            winningRankCount.merge(rank, 1, Integer::sum);
        }
    }

    private boolean isSecondRankWinner(int matchCount, Lotto ticket) {
        return matchCount == SECOND_RANK_MATCH_COUNT && ticket.isBonusNumberMatched(bonusNumber);
    }

    private int determineRank(int matchCount, Lotto ticket) {
        if (isSecondRankWinner(matchCount, ticket)) {
            return 2;
        }
        return MATCH_COUNT_TO_RANK.getOrDefault(matchCount, 0);
    }

    public String getWinningAmount() {
        return this.winningAmount.toPlainString();
    }

    public void calculateEarningsRate() {
        BigDecimal purchaseAmount = BigDecimal.valueOf(lottoTickets.size() * LOTTO_PRICE);
        BigDecimal winningAmountMultiplyPercent = winningAmount.multiply(BigDecimal.valueOf(PERCENT));
        this.earningsRate = winningAmountMultiplyPercent.divide(purchaseAmount, PLACE_TO_ROUND_TO, RoundingMode.HALF_UP);
    }

    public String getEarningsRate() {
        return this.earningsRate.toPlainString();
    }
}

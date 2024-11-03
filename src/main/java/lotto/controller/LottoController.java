package lotto.controller;

import com.sun.source.tree.Tree;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;
import lotto.Lotto;
import lotto.view.InputView;
import lotto.domain.LottoGenerator;
import lotto.domain.LottoService;

import static lotto.constants.LottoConstants.*;
import static lotto.view.OutputView.*;

public class LottoController {
    private final InputView inputView;
    private LottoService lottoService;

    public LottoController(InputView inputView) {
        this.inputView = inputView;
    }

    public void start() {
        int purchaseAmount = inputView.getInputPurchaseAmount();

        int lottoCount = calculateLottoCount(purchaseAmount);
        promptLottoCount(lottoCount);

        List<Lotto> lottoTickets = generateLottoTickets(lottoCount);
        printLottoTickets(lottoTickets);

        Lotto winningNumbers = new Lotto(inputView.getInputWinningNumbers());
        Lotto bonusNumber = new Lotto(inputView.getInputBonusNumber());
        lottoService = new LottoService(lottoTickets, winningNumbers, bonusNumber);

        lottoService.calculateWinningAmount();
        TreeMap<Integer, Integer> winningResult = new TreeMap<>(Comparator.reverseOrder());
        winningResult.putAll(lottoService.getWinningResult());
        promptWinningResult();
        printWinningResult(winningResult);

        lottoService.calculateEarningsRate();
        String earningRate = lottoService.getEarningsRate();
        printEarningRate(earningRate);
    }

    private int calculateLottoCount(int purchaseAmount) {
        return purchaseAmount / PURCHASE_AMOUNT_UNIT;
    }

    private List<Lotto> generateLottoTickets(int count) {
        List<Lotto> tickets = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            tickets.add(new Lotto(LottoGenerator.generateNumbers()));
        }

        return tickets;
    }
}

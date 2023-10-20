package com.example.demo.services;

import com.example.demo.model.Card;
import com.example.demo.model.Player;
import com.example.demo.model.constant.StageOfGame;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class MainService {
    public static final String COUNT_OF_CARDS_FROM_SESSION_CARDS = "Осталось кард в колоде: {}";
    public static final String CLOSE_SESSION = "Закрытие сессии";
    @Getter
    private StageOfGame stageOfGame;
    private Deque<String> sessionCards;
    private final Map<String, Card> cardMap = Card.colodeCardForCompare();
    private List<Player> tablePlayers = new ArrayList<>();
    private List<String> tableCards = new ArrayList<>();
    private Player me = new Player("Влад", 200);
    private Player player1 = new Player("Полина", 200);
    private Player player2 = new Player("Вадим", 200);
    private Player player3 = new Player("Каравуля", 200);
    private Player player4 = new Player("HelloKitty", 200);
    private Player player5 = new Player("Изольда", 200);

    private final CombinationService combinationService;

    public void startPreflop() {
        stageOfGame = StageOfGame.PREFLOP;

        sessionCards = Card.colodeCardForGame();

        tablePlayers = List.of(player1,
                player2,
                player3,
                player4,
                player5,
                me);

        tablePlayers.forEach(this::setUpHand);

        me.setCombination(combinationService.checkCombination(tableCards, me.getHandCards()));

        log.info(me.toString());
        log.info(COUNT_OF_CARDS_FROM_SESSION_CARDS, sessionCards.size());
    }

    public void startFlop() {
        stageOfGame = StageOfGame.FLOP;

        setUpTableCardsForFlop();

        me.setCombination(combinationService.checkCombination(tableCards, me.getHandCards()));

        log.info(me.toString());
        log.info(tableCards.toString());
        log.info(COUNT_OF_CARDS_FROM_SESSION_CARDS, sessionCards.size());
    }

    public void startTern() {
        stageOfGame = StageOfGame.TERN;

        setUpTableCardsForTern();

        me.setCombination(combinationService.checkCombination(tableCards, me.getHandCards()));

        log.info(me.toString());
        log.info(tableCards.toString());
        log.info(COUNT_OF_CARDS_FROM_SESSION_CARDS, sessionCards.size());
    }


    public void startRiver() {
        stageOfGame = StageOfGame.RIVER;

        setUpTableCardsForRiver();

        me.setCombination(combinationService.checkCombination(tableCards, me.getHandCards()));

        log.info(me.toString());
        log.info(tableCards.toString());
        log.info(COUNT_OF_CARDS_FROM_SESSION_CARDS, sessionCards.size());
    }

    public void startResult() {
        stageOfGame = StageOfGame.RESULT;

        for (int expectedSizeTableCard = tableCards.size();
             expectedSizeTableCard != 5;
             expectedSizeTableCard++){
            tableCards.add(sessionCards.removeFirst());
        }

        for (Player player:tablePlayers) {
            player.setCombination(combinationService.checkCombination(tableCards, player.getHandCards()));
        }

        tablePlayers.forEach(player -> player.setCombination(combinationService.checkCombination(tableCards, player.getHandCards())));
        log.info(tablePlayers.toString());
        log.info(tableCards.toString());

        log.info(COUNT_OF_CARDS_FROM_SESSION_CARDS, sessionCards.size());
        log.info(CLOSE_SESSION);
        closeSession();
    }

    private void closeSession() {
        sessionCards = Card.colodeCardForGame();

        tableCards.clear();
        tablePlayers.forEach(player -> player.getHandCards().clear());
    }

    private void setUpTableCardsForTern() {
        tableCards.add(sessionCards.removeFirst());
    }

    private void setUpTableCardsForRiver() {
        tableCards.add(sessionCards.removeFirst());
    }

    private void setUpTableCardsForFlop() {
        tableCards = new ArrayList<>();
        for (int times = 3; times > 0; times--) {
            tableCards.add(sessionCards.removeFirst());
        }
    }

    private void setUpHand(Player player) {
        List<String> hand = new ArrayList<>();
        hand.add(sessionCards.removeFirst());
        hand.add(sessionCards.removeFirst());
        player.setHandCards(hand);
    }
}

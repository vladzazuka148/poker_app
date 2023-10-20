package com.example.demo.services;

import com.example.demo.model.Card;
import com.example.demo.model.constant.CardMaste;
import com.example.demo.model.constant.CardValue;
import com.example.demo.model.constant.Combination;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CombinationService {
    public Combination checkCombination(List<String> tableCards, List<String> playerCards) {
        List<String> cards = new ArrayList<>();
        cards.addAll(tableCards);
        cards.addAll(playerCards);

        List<Card> cardsForChecking = new ArrayList<>(parseCardsForChecking(cards));

        Combination combination = Combination.KICKER;

        if (pare(cardsForChecking)) {
            combination = Combination.PARE;
        }
        if (twoPare(cardsForChecking)) {
            combination = Combination.TWO_PARE;
        }
        if (pokerSet(cardsForChecking)) {
            combination = Combination.SET;
        }
        if (straith(cardsForChecking)) {
            combination = Combination.STRAIT;
        }
        if (flash(cardsForChecking)) {
            combination = Combination.FLASH;
        }
        if (fullHouse(cardsForChecking)) {
            combination = Combination.FULL_HOUSE;
        }
        if (quart(cardsForChecking)) {
            combination = Combination.QUART;
        }
        if (straithFlash(cardsForChecking)) {
            combination = Combination.STRAIT_FLASH;
        }
        if (royalFlash(cardsForChecking)) {
            combination = Combination.ROYAL_FLASH;
        }
        return combination;
    }

    private List<Card> parseCardsForChecking(List<String> cards) {
        Map<String, Card> fullPack = Card.colodeCardForCompare();
        return cards.stream()
                .map(fullPack::get)
                .toList();
    }

    private boolean royalFlash(List<Card> cards) {
        if (cards.size() < 5) {
            return false;
        }
        Collections.sort(cards);
        Collections.reverse(cards);

        return straith(cards)
                & flash(cards)
                & cards.get(0).getValue() == CardValue.ACE;
    }

    private boolean straithFlash(List<Card> cards) {
        if (cards.size() < 5) {
            return false;
        }
        return flash(cards)
                & straith(cards);
    }

    private boolean quart(List<Card> cards) {
        if (cards.size() < 5) {
            return false;
        }
        Map<CardValue, Long> frequencies = cards.parallelStream()
                .map(Card::getValue)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        return frequencies.values().stream().anyMatch(f -> f == 4);
    }

    private boolean fullHouse(List<Card> cards) {
        if (cards.size() < 5) {
            return false;
        }
        Map<CardValue, Integer> valueCount = new HashMap<>();
        for (Card card : cards) {
            CardValue value = card.getValue();
            valueCount.put(value, valueCount.getOrDefault(value, 0) + 1);
        }
        boolean hasThree = false;
        boolean hasTwo = false;
        for (int count : valueCount.values()) {
            if (count == 3) {
                hasThree = true;
            }
            if (count == 2) {
                hasTwo = true;
            }
        }
        return hasThree && hasTwo;
    }

    private boolean flash(List<Card> cards) {
        if (cards.size() < 5) {
            return false;
        }
        Map<CardMaste, Integer> masteCount = new HashMap<>();
        for (Card card : cards) {
            CardMaste maste = card.getType();
            masteCount.put(maste, masteCount.getOrDefault(maste, 0) + 1);
        }
        for (int count : masteCount.values()) {
            if (count >= 5) {
                return true;
            }
        }
        return false;
    }

    private boolean straith(List<Card> cards) {
        if (cards.size() < 5) {
            return false;
        }
        Collections.sort(cards);
        List<CardValue> cardValues = cards.stream().map(Card::getValue).distinct().toList();
        int count = 1;
        for (int i = 1; i < cardValues.size(); i++) {
            if (cardValues.get(i).getRank() == cardValues.get(i - 1).getRank() + 1) {
                count++;
            } else {
                count = 1;
            }
            if (count == 5) {
                return true;
            }
        }
        if (cardValues.get(0) == CardValue.ACE && cardValues.get(4) == CardValue.FIVE) {
            return cardValues.get(1) == CardValue.TWO
                    && cardValues.get(2) == CardValue.THREE
                    && cardValues.get(3) == CardValue.FOUR;
        }
        return false;
    }

    private boolean pokerSet(List<Card> cards) {
        Map<CardValue, Long> frequencies = cards.parallelStream()
                .map(Card::getValue)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        return frequencies.values().stream().anyMatch(f -> f == 3);
    }

    private boolean twoPare(List<Card> cards) {
        {
            HashMap<CardValue, Integer> frequencies = new HashMap<> ();
            for (Card card : cards) {
                CardValue value = card.getValue ();
                frequencies.put (value, frequencies.getOrDefault (value, 0) + 1);
            }
            int pairs = 0;
            for (int count : frequencies.values ()) {
                if (count == 2) pairs++;
            }
            return pairs >= 2;
        }
    }

    private boolean pare(List<Card> cards) {
        Map<CardValue, Long> frequencies = cards.parallelStream()
                .map(Card::getValue)
                .collect(Collectors.groupingBy(Function.identity(),
                        Collectors.counting()));
        return frequencies.values().stream().anyMatch(f -> f == 2);
    }
}


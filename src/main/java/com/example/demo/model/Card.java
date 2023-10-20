package com.example.demo.model;

import com.example.demo.model.constant.CardMaste;
import com.example.demo.model.constant.CardValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class Card implements Comparable<Card>{
    private final CardMaste type;
    private final CardValue value;

    private static final List<String> cardValues =
            List.of("2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A");


    public static Map<String, Card> colodeCardForCompare() {
        Set<Card> colodeCard = new HashSet<>();
        Arrays.stream(CardValue.values()).toList().forEach(cardValue -> Arrays.stream(CardMaste.values())
                .toList()
                        .forEach(cardMaste -> colodeCard.add(new Card(cardMaste, cardValue))));
        return colodeCard.stream().collect(Collectors.toMap(Card::getShortNameCard, Function.identity()));
    }

    public static Deque<String> colodeCardForGame(){
        Set<Card> colodeCard = new HashSet<>();
        Arrays.stream(CardValue.values()).toList().forEach(cardValue -> Arrays.stream(CardMaste.values())
                .toList()
                .forEach(cardMaste -> colodeCard.add(new Card(cardMaste, cardValue))));
        List<String> cardList = new ArrayList<>(colodeCard.stream().map(Card::getShortNameCard).toList());
        Collections.shuffle(cardList);
        return new ArrayDeque<>(cardList);
    }

    private static String getShortNameCard(Card card) {
        return card.getValue()+ " " + card.getType().name();
    }

    @Override
    public int compareTo(Card o) {
        return this.getValue().getRank().compareTo(o.getValue().getRank());
    }
}

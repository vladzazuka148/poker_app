package com.example.demo.services;

import com.example.demo.model.Card;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CardService {
    public void findAndPrintCard(String card) {
        Map<String, Card> colodeForCompare = Card.colodeCardForCompare();

        System.out.println(colodeForCompare.get(card).toString());
    }
}

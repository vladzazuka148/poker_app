package com.example.demo.model;

import com.example.demo.model.constant.Combination;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
public class Player {
    private List<String> handCards;
    private String name;
    private Integer moneyStack;
    private Combination combination;

    public Player(String name, Integer moneyStack) {
        this.name = name;
        this.moneyStack = moneyStack;
    }

    @Override
    public String toString() {
        return "\nИгрок:" + "\nКарты: " + handCards +
                "\nИмя: " + name +
                "\nДеньги: " + moneyStack +
                "\nТекущая комбинация: " + combination +"\n";
    }
}

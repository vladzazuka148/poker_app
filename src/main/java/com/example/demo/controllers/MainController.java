package com.example.demo.controllers;

import com.example.demo.model.constant.StageOfGame;
import com.example.demo.services.CardService;
import com.example.demo.services.MainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class MainController {
    public static final String LOG_ERROR_PREFLOP = "Начать новую игру можно после последней стадии - Result " +
            "\n Текущая стадия игры {}";
    public static final String LOG_ERROR_FLOP = "Перейти в стадию Flop можно после стадии - Preflop " +
            "\n Текущая стадия игры {}";
    public static final String LOG_ERROR_TERN = "Перейти в стадию Tern можно после стадии - Flop " +
            "\n Текущая стадия игры {}";
    public static final String LOG_ERROR_RIVER = "Перейти в стадию River можно после стадии - Tern " +
            "\n Текущая стадия игры {}";
    public static final String LOG_ERROR_NULL_ON_STAGE = "Нельзя начать финал сессии если текущая стадия игры = {}";

    private final MainService mainService;
    private final CardService cardService;

    @GetMapping("/preflop")
    public void startPreflop() {
        if (mainService.getStageOfGame() == StageOfGame.RESULT
                || mainService.getStageOfGame() == null) {
            mainService.startPreflop();
        } else
            log.info(LOG_ERROR_PREFLOP, mainService.getStageOfGame());
    }

    @GetMapping("/flop")
    public void startFlop() {
        if (mainService.getStageOfGame() == StageOfGame.PREFLOP) {
            mainService.startFlop();
        } else
            log.info(LOG_ERROR_FLOP, mainService.getStageOfGame());
    }

    @GetMapping("/tern")
    public void startTern() {
        if (mainService.getStageOfGame() == StageOfGame.FLOP) {
            mainService.startTern();
        } else
            log.info(LOG_ERROR_TERN, mainService.getStageOfGame());
    }

    @GetMapping("/river")
    public void startRiver() {
        if (mainService.getStageOfGame() == StageOfGame.TERN) {
            mainService.startRiver();
        } else
            log.info(LOG_ERROR_RIVER, mainService.getStageOfGame());
    }

    @GetMapping("/result")
    public void startResult() {
        if (mainService.getStageOfGame() == null) {
            log.info(LOG_ERROR_NULL_ON_STAGE, mainService.getStageOfGame());
        } else
            mainService.startResult();
    }

    @GetMapping("/session")
    public void getFullSession() {
        mainService.startPreflop();
        mainService.startResult();
    }
}

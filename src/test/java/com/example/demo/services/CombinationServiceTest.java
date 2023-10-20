package com.example.demo.services;

import com.example.demo.model.constant.Combination;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CombinationServiceTest {
    private CombinationService combinationService = new CombinationService();

    @Test
    void checkCombination() {
        // GIVEN
        List<String> pare = new ArrayList<>();
        pare.add("TEN CHERVA");
        pare.add("ACE CHERVA");
        pare.add("KING CHERVA");
        pare.add("QUEEN CHERVA");
        pare.add("JACK CHERVA");


        // WHEN
        Combination combination = combinationService.checkCombination(pare, Collections.emptyList());

        // THEN
        assertEquals(Combination.PARE, combination);
    }
}
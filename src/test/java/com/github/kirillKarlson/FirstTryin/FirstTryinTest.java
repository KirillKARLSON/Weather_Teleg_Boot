package com.github.kirillKarlson.FirstTryin;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;



@DisplayName("TESTS FOR TRYING")


class FirstTryinTest {

    @Test
    public void mnozhReturn(){
        Assertions.assertEquals(12, new FirstTryin().mnozh(3,4));
    }



    @Test
    public void mnozhReturn1(){
        Assertions.assertEquals(15, new FirstTryin().mnozh(3,5));
    }



}
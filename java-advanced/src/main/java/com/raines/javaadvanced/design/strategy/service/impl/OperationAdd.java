package com.raines.javaadvanced.design.strategy.service.impl;

import com.raines.javaadvanced.design.strategy.service.Strategy;

/**
 * 实现策略接口的实体类：加法
 */
public class OperationAdd implements Strategy {
    @Override
    public int doOperation(int num1, int num2) {
        return num1 + num2;
    }
}
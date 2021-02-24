/**
 * @author <a href="mailto: mmgrebenschikov@edu.hse.ru"> Maxim Grebenschikov</a>
 */

package com.maxgromash.cells;

public class EmptyCell extends Cell {

    /**
     * Основной конструктор без параметров
     */
    public EmptyCell() {
        sign = "E";
    }

    /**
     * Переопределенный метод взаимоджействия с игроком
     * при по падании на клетку
     */
    @Override
    public void onPlayerStepCell() {
        System.out.println("Just relax there");
    }

    /**
     * Переопределенный метод взаимоджействия c ботов
     * при по падании его на клетку
     */
    @Override
    public void onBotStepCell() {
        System.out.println("- Bot is relaxing on empty sell");
    }
}

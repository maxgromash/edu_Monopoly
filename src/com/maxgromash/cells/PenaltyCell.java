/**
 * @author <a href="mailto: mmgrebenschikov@edu.hse.ru"> Maxim Grebenschikov</a>
 */

package com.maxgromash.cells;

import com.maxgromash.Main;

public class PenaltyCell extends Cell {

    static public double penaltyCoeff;

    /**
     * Основной конструктор без параметров
     */
    public PenaltyCell() {
        sign = "%";
    }

    /**
     * Переопределенный метод взаимоджействия с игроком
     * при по падании на клетку
     */
    @Override
    public void onPlayerStepCell() {
        int amount = (int) (penaltyCoeff * Main.player.getMoney());
        System.out.print("- You are on the penalty cell, and you need to pay " + amount + "$\nPress enter to pay");
        Main.in.nextLine();
        Main.player.reduceMoney(amount);
    }

    /**
     * Переопределенный метод взаимоджействия c ботов
     * при по падании его на клетку
     */
    @Override
    public void onBotStepCell() {
        int amount = (int) (penaltyCoeff * Main.bot.getMoney());
        System.out.println("- Bot payed " + amount + "$ on penalty cell");
        Main.bot.reduceMoney(amount);
    }
}

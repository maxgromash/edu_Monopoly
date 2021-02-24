/**
 * @author <a href="mailto: mmgrebenschikov@edu.hse.ru"> Maxim Grebenschikov</a>
 */

package com.maxgromash.cells;

import com.maxgromash.Main;

public class Taxi extends Cell {

    /**
     * Основной конструктор без параметров
     */
    public Taxi() {
        sign = "T";
    }

    /**
     * Переопределенный метод взаимоджействия с игроком
     * при по падании на клетку
     */
    @Override
    public void onPlayerStepCell() {
        int distance = Main.rand.nextInt(3) + 3;
        System.out.println("You are shifted forward by " + distance + " cells\n");
        System.out.print("Press Enter to continue");
        Main.in.nextLine();
        System.out.print("\n");
        Main.player.moveCell(distance);
        Main.field.drawField();

        Main.field.cells[Main.player.getCurrentCell()].onPlayerStepCell();
    }

    /**
     * Переопределенный метод взаимоджействия c ботов
     * при по падании его на клетку
     */
    @Override
    public void onBotStepCell() {
        int distance = Main.rand.nextInt(3) + 3;
        System.out.println("- Bot is shifted forward by " + distance + " cells");
        Main.bot.moveCell(distance);
        Main.field.cells[Main.bot.getCurrentCell()].onBotStepCell();
    }
}
/**
 * @author <a href="mailto: mmgrebenschikov@edu.hse.ru"> Maxim Grebenschikov</a>
 */

package com.maxgromash.cells;

public abstract class Cell {

    /**
     * Метод для взаимодействия с полоьзователем
     */
    public abstract void onPlayerStepCell();

    /**
     * Метод для взаимодействия с ботом
     */
    public abstract void onBotStepCell();

    //Знак точки на карте
    protected String sign;

    /**
     * Переопределенный toString для вывода точки на карту
     * @return обозначение
     */
    @Override
    public String toString() {
        return sign;
    }
}

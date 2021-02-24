/**
 * @author <a href="mailto: mmgrebenschikov@edu.hse.ru"> Maxim Grebenschikov</a>
 */

package com.maxgromash;

import java.util.ArrayList;

public class Player {
    private int money;
    private int currentCell;
    private ArrayList<Integer> myShops;
    private int credit;
    private int sumOfSpentMoney;

    /**
     * Метод добавления денег
     *
     * @param amount - количество денег
     */
    public void addSpentMoney(int amount) {
        sumOfSpentMoney += amount;
    }

    /**
     * Гетер для потраченных на магазины денег
     *
     * @return количество денег
     */
    public int getSumOfSpentMoney() {
        return sumOfSpentMoney;
    }

    /**
     * Добавление нового магазина
     *
     * @param pos Позиция магазина
     */
    public void addShop(int pos) {
        myShops.add(pos);
    }

    /**
     * Гетер для кредита
     *
     * @return размер кредита
     */
    public int getCredit() {
        return credit;
    }

    /**
     * Сеттер для кредита
     *
     * @param credit кредит
     */
    public void setCredit(int credit) {
        this.credit = credit;
    }

    /**
     * Геттер для магазинов
     *
     * @return лист магазинов
     */
    public ArrayList<Integer> getMyShops() {
        return myShops;
    }

    /**
     * Основной контруктор
     *
     * @param money стартовое количество денег
     */
    public Player(int money) {
        this.money = money;
        currentCell = 0;
        myShops = new ArrayList<>();
        credit = 0;
        sumOfSpentMoney = 0;
    }

    /**
     * Гетер для денег
     *
     * @return количесмтво денег
     */
    public int getMoney() {
        return money;
    }

    /**
     * Уменьшение количествао денег
     *
     * @param amount количество денег
     */
    public void reduceMoney(int amount) {
        money -= amount;
        if (money < 0)
            throw new ArithmeticException();
    }

    /**
     * Добавление денег
     *
     * @param money деньги
     */
    public void addMoney(int money) {
        this.money += money;

    }

    /**
     * Геттер для текущей позитции пользователя
     *
     * @return
     */
    public int getCurrentCell() {
        return currentCell;
    }

    /**
     * Метод перемещения пользователя по карте
     *
     * @param addCell на сколько клеток сдвинуть
     */
    public void moveCell(int addCell) {
        if (currentCell + addCell >= Main.field.getCountOfCells())
            currentCell += addCell - Main.field.getCountOfCells();
        else
            currentCell += addCell;
    }
}

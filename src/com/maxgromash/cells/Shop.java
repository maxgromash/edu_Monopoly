/**
 * @author <a href="mailto: mmgrebenschikov@edu.hse.ru"> Maxim Grebenschikov</a>
 */

package com.maxgromash.cells;

import com.maxgromash.Main;

import java.util.ArrayList;

public class Shop extends Cell {
    private int position;
    private int cost;
    private int compensation;
    private double improvementCoeff;
    private double compensationCoeff;
    private boolean hasOwner;

    /**
     * Конструктор магазина с параметром
     *
     * @param pos - позиция магазина на карте
     */
    public Shop(int pos) {
        position = pos;
        sign = "S";
        hasOwner = false;
        improvementCoeff = Main.rand.nextDouble() * 1.9 + 0.1;
        compensationCoeff = Main.rand.nextDouble() * 0.9 + 0.1;
        cost = Main.rand.nextInt(451) + 50;
        compensation = (int) ((Main.rand.nextDouble() * 0.4 + 0.5) * cost);
    }

    /**
     * Переопределенный метод взаимоджействия с игроком
     * при по падании на клетку
     */
    @Override
    public void onPlayerStepCell() {
        if (hasOwner) {
            ArrayList<Integer> shops = Main.player.getMyShops();
            if (shops.stream().anyMatch(u -> u.equals(position))) {
                System.out.println("- Do you want to upgrade your shop for " + cost + "$?");
                if (Main.funYesNo()) {
                    if (cost > Main.player.getMoney()) {
                        System.out.println("- Sorry, but you don't have enough money!");
                    } else {
                        Main.player.addSpentMoney(cost);
                        Main.player.reduceMoney(cost);
                        cost += Math.round(improvementCoeff * cost);
                        compensation += Math.round(compensationCoeff * compensation);
                    }
                } else {
                    System.out.println("- Ok, no problem!");
                }
            } else {
                System.out.print("- Oh man, you in your opponent's shop. Unfortunately you need to pay a compensation!" +
                        "\nPress Enter to pay " + compensation + "$ ");
                Main.in.nextLine();
                Main.player.reduceMoney(compensation);
                Main.bot.addMoney(compensation);
            }
        } else onStepNoOwner();
    }

    /**
     * Метод взаимодействия с игроком при условии, что у магазина нет владельца
     */
    private void onStepNoOwner() {
        System.out.println("- You are in the shop and you can buy it for " + cost + "$ \n- Do you want to buy it?");
        if (Main.funYesNo()) {
            if (cost > Main.player.getMoney())
                System.out.println("- Sorry, but you don't have enough money!");
            else {
                hasOwner = true;
                sign = "M";
                Main.player.addShop(position);
                Main.player.reduceMoney(cost);
                Main.player.addSpentMoney(cost);
                System.out.println("- Congratulations! You bought a shop!");
            }
        } else {
            System.out.println("-Ok, no problem.");
        }
    }

    /**
     * Переопределенный метод взаимоджействия c ботов
     * при по падании его на клетку
     */
    @Override
    public void onBotStepCell() {
        if (hasOwner) {
            ArrayList<Integer> shops = Main.bot.getMyShops();
            if (shops.stream().anyMatch(u -> u.equals(position))) {
                if (Main.rand.nextBoolean()) {
                    System.out.println("- Bot upgrade his shop for " + cost + "$");
                    Main.bot.reduceMoney(cost);
                    cost += Math.round(improvementCoeff * cost);
                    compensation += Math.round(compensationCoeff * compensation);
                } else {
                    System.out.println("- Bot just chilling on his shop cell");
                }
            } else {
                Main.bot.reduceMoney(compensation);
                Main.player.addMoney(compensation);
                System.out.println("- Bot stepped on your shop and payed " + compensation + "$ for you");
                System.out.println("Your current balance is " + Main.player.getMoney() + "$");
            }
        } else {
            if (Main.rand.nextBoolean() && cost <= Main.bot.getMoney()) {
                hasOwner = true;
                sign = "O";
                Main.bot.addShop(position);
                Main.bot.reduceMoney(cost);
                System.out.println("- Bot bought a shop for " + cost + "$");
            } else {
                System.out.println("- Bot didn't buy a shop");
            }
        }
    }
}
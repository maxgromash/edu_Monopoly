/**
 * @author <a href="mailto: mmgrebenschikov@edu.hse.ru"> Maxim Grebenschikov</a>
 */

package com.maxgromash;

import com.maxgromash.cells.*;

import java.util.ArrayList;

public class GameField {
    private final int Height, Width;
    private int CountOfCells;
    private final ArrayList<Integer> emptyCellsA = new ArrayList<>(), emptyCellsB = new ArrayList<>(),
            emptyCellsC = new ArrayList<>(), emptyCellsD = new ArrayList<>();

    public int getCountOfCells() {
        return CountOfCells;
    }

    public Cell[] cells;

    /**
     * Основной коструктор
     *
     * @param height высота
     * @param width  ширина
     */
    public GameField(int height, int width) {
        Height = height;
        Width = width;
        CountOfCells = 2 * height + 2 * width - 4;
        cells = new Cell[CountOfCells];
        generateCells();
    }

    /**
     * Генерация клеток
     */
    public void generateCells() {
        //Создание листов со свободными клетками
        for (int i = 1; i < CountOfCells; i++) {
            if (i < Width - 1) emptyCellsA.add(i);
            if (i > Width - 1 && i < Width - 2 + Height) emptyCellsB.add(i);
            if (i > Width - 2 + Height && i < 2 * Width - 3 + Height) emptyCellsC.add(i);
            if (i > 2 * Width - 3 + Height) emptyCellsD.add(i);
        }

        addBanksAndEmptyCells();
        addPenalty();
        addTaxi();

        //Заполнение оставшихся клеток магазинами
        for (int i = 0; i < cells.length; i++) {
            if (cells[i] == null)
                cells[i] = new Shop(i);
        }

    }

    /**
     * Метод генерации клеток штрафов
     */
    private void addPenalty() {
        int countOfPenalty = Main.rand.nextInt(3);
        for (int i = 0; i < countOfPenalty; i++) {
            int rx = Main.rand.nextInt(emptyCellsA.size());
            cells[emptyCellsA.get(rx)] = new PenaltyCell();
            emptyCellsA.remove(rx);

            rx = Main.rand.nextInt(emptyCellsB.size());
            cells[emptyCellsB.get(rx)] = new PenaltyCell();
            emptyCellsB.remove(rx);
            rx = Main.rand.nextInt(emptyCellsC.size());
            cells[emptyCellsC.get(rx)] = new PenaltyCell();
            emptyCellsC.remove(rx);
            rx = Main.rand.nextInt(emptyCellsD.size());
            cells[emptyCellsD.get(rx)] = new PenaltyCell();
            emptyCellsD.remove(rx);
        }
    }

    /**
     * Метод генерации такси
     */
    private void addTaxi() {
        int countOfTaxi = Main.rand.nextInt(3);
        for (int i = 0; i < countOfTaxi; i++) {
            if (emptyCellsA.size() == 0 || emptyCellsB.size() == 0) {
                return;
            }
            int rx = Main.rand.nextInt(emptyCellsA.size());
            cells[emptyCellsA.get(rx)] = new Taxi();
            emptyCellsA.remove(rx);
            rx = Main.rand.nextInt(emptyCellsB.size());
            cells[emptyCellsB.get(rx)] = new Taxi();
            emptyCellsB.remove(rx);
            rx = Main.rand.nextInt(emptyCellsC.size());
            cells[emptyCellsC.get(rx)] = new Taxi();
            emptyCellsC.remove(rx);
            rx = Main.rand.nextInt(emptyCellsD.size());
            cells[emptyCellsD.get(rx)] = new Taxi();
            emptyCellsD.remove(rx);
        }
    }

    /**
     * Метод генерации банковских офисов
     */
    private void addBanksAndEmptyCells() {
        int rx = Main.rand.nextInt(emptyCellsA.size());
        cells[emptyCellsA.get(rx)] = new Bank();
        emptyCellsA.remove(rx);

        rx = Main.rand.nextInt(emptyCellsB.size());
        cells[emptyCellsB.get(rx)] = new Bank();
        emptyCellsB.remove(rx);
        rx = Main.rand.nextInt(emptyCellsC.size());
        cells[emptyCellsC.get(rx)] = new Bank();
        emptyCellsC.remove(rx);
        rx = Main.rand.nextInt(emptyCellsD.size());
        cells[emptyCellsD.get(rx)] = new Bank();
        emptyCellsD.remove(rx);

        cells[0] = new EmptyCell();
        cells[Width - 1] = new EmptyCell();
        cells[Width + Height - 2] = new EmptyCell();
        cells[2 * Width + Height - 3] = new EmptyCell();
    }

    /**
     * Метод отрисовки игрового поля
     */
    public void drawField() {
        int curCell = 0;
        for (int i = 0; i < Height; i++) {
            for (int j = 0; j < Width; j++) {
                if (i == 0 && j == 0 || j > i)
                    curCell = i + j;
                else if (i == Height - 1)
                    curCell = Height + 2 * Width - j - 3;
                else if (j == 0)
                    curCell = 2 * Height + 2 * Width - i - 4;

                if (i == 0 || j == 0 || j == Width - 1 || i == Height - 1) {
                    if (curCell == Main.player.getCurrentCell() && curCell == Main.bot.getCurrentCell())
                        System.out.print("<{" + cells[curCell].toString() + "}>");
                    else if (curCell == Main.player.getCurrentCell())
                        System.out.print("<<" + cells[curCell].toString() + ">>");
                    else if (curCell == Main.bot.getCurrentCell())
                        System.out.print("{{" + cells[curCell].toString() + "}}");
                    else System.out.print("[ " + cells[curCell].toString() + " ]");
                } else
                    System.out.print("     ");
            }
            System.out.print("\n");
        }
        System.out.print("\n");
    }
}

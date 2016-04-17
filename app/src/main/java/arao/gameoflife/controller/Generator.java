package arao.gameoflife.controller;

public interface Generator {

    boolean[][] nextGeneration(boolean[][] currentGeneration);

}

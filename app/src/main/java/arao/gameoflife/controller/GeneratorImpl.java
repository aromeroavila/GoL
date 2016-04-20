package arao.gameoflife.controller;

class GeneratorImpl implements Generator {

    @Override
    public boolean[][] nextGeneration(boolean[][] currentGeneration) {
        int boardWidth = currentGeneration.length;
        int boardHeight = currentGeneration[0].length;

        boolean[][] nextGeneration = new boolean[boardWidth][boardHeight];

        byte neighbourCount;

        for (int i = 0; i < boardWidth; i++) {
            for (int j = 0; j < boardHeight; j++) {
                neighbourCount = 0;
                neighbourCount += checkLeftNeighbours(currentGeneration, boardHeight, i, j);
                neighbourCount += checkCentralNeighbours(currentGeneration, boardHeight, i, j);
                neighbourCount += checkRightNeighbours(currentGeneration, boardWidth, boardHeight, i, j);

                checkNextGenerationValue(currentGeneration[i][j], nextGeneration, neighbourCount, i, j);
            }
        }

        return nextGeneration;
    }

    private byte checkLeftNeighbours(boolean[][] currentGeneration, int boardHeight, int i, int j) {
        byte neighbourCount = 0;
        if (i - 1 > 0) {
            if (j - 1 > 0) {
                neighbourCount += currentGeneration[i - 1][j - 1] ? 1 : 0;
            }
            neighbourCount += currentGeneration[i - 1][j] ? 1 : 0;
            if (j + 1 < boardHeight) {
                neighbourCount += currentGeneration[i - 1][j + 1] ? 1 : 0;
            }
        }
        return neighbourCount;
    }

    private byte checkCentralNeighbours(boolean[][] currentGeneration, int boardHeight, int i, int j) {
        byte neighbourCount = 0;
        if (j - 1 > 0) {
            neighbourCount += currentGeneration[i][j - 1] ? 1 : 0;
        }
        if (j + 1 < boardHeight) {
            neighbourCount += currentGeneration[i][j + 1] ? 1 : 0;
        }
        return neighbourCount;
    }

    private byte checkRightNeighbours(boolean[][] currentGeneration, int boardWidth, int boardHeight, int i, int j) {
        byte neighbourCount = 0;
        if (i + 1 < boardWidth) {
            if (j - 1 > 0) {
                neighbourCount += currentGeneration[i + 1][j - 1] ? 1 : 0;
            }
            neighbourCount += currentGeneration[i + 1][j] ? 1 : 0;
            if (j + 1 < boardHeight) {
                neighbourCount += currentGeneration[i + 1][j + 1] ? 1 : 0;
            }
        }
        return neighbourCount;
    }

    private void checkNextGenerationValue(boolean currentValue, boolean[][] nextGeneration, byte neighbourCount, int i, int j) {
        if (currentValue) {
            if (neighbourCount == 2 || neighbourCount == 3) {
                nextGeneration[i][j] = true;
            }
        } else {
            if (neighbourCount == 3) {
                nextGeneration[i][j] = true;
            }
        }
    }

}

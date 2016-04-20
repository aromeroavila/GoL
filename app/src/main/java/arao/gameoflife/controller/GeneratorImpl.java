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

                if (i - 1 > 0) {
                    if (j - 1 > 0) {
                        neighbourCount = checkNeighbour(currentGeneration[i - 1][j - 1], neighbourCount);
                    }

                    neighbourCount = checkNeighbour(currentGeneration[i - 1][j], neighbourCount);

                    if (j + 1 < boardHeight) {
                        neighbourCount = checkNeighbour(currentGeneration[i - 1][j + 1], neighbourCount);
                    }
                }

                if (i + 1 < boardWidth) {
                    if (j - 1 > 0) {
                        neighbourCount = checkNeighbour(currentGeneration[i + 1][j - 1], neighbourCount);
                    }

                    neighbourCount = checkNeighbour(currentGeneration[i + 1][j], neighbourCount);

                    if (j + 1 < boardHeight) {
                        neighbourCount = checkNeighbour(currentGeneration[i + 1][j + 1], neighbourCount);
                    }

                }

                if (j - 1 > 0) {
                    neighbourCount = checkNeighbour(currentGeneration[i][j - 1], neighbourCount);
                }

                if (j + 1 < boardHeight) {
                    neighbourCount = checkNeighbour(currentGeneration[i][j + 1], neighbourCount);
                }

                if (currentGeneration[i][j]) {
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

        return nextGeneration;
    }

    private byte checkNeighbour(boolean b, byte neighbourCount) {
        if (b) {
            neighbourCount++;
        }
        return neighbourCount;
    }

}

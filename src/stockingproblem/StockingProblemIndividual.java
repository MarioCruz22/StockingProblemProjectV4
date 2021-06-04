package stockingproblem;

import algorithms.IntVectorIndividual;
import ga.GeneticAlgorithm;

import java.util.Random;

public class StockingProblemIndividual extends IntVectorIndividual<StockingProblem, StockingProblemIndividual> {
    //TODO this class might require the definition of additional methods and/or attributes
    protected int numColunasTotalItems = 0;
    protected char[][] matrixFenotipo;
    protected int numCortes;
    protected int numBrancos;
    protected int maxColFen;

    public StockingProblemIndividual(StockingProblem problem, int size) {
        super(problem, size);
        maxColFen = 0;
        //TODO

        for (int i = 0; i < genome.length; ) {
            Random random = GeneticAlgorithm.random;
            int id = random.nextInt(size) + 1;
            for (int j = 0; j <= i; j++) {
                if (genome[j] == id) {
                    break;
                }
                if (i == j) {
                    genome[i] = id;
                    i++;
                    break;
                }
            }
        }

        for (Item item : problem.getItems()) {
            numColunasTotalItems += item.getColumns();
        }

        //computeFitness();
        preencherFenotipo();
    }



    public StockingProblemIndividual(StockingProblemIndividual original) {
        super(original);
        //TODO
        this.numBrancos = original.numBrancos;
        this.numCortes = original.numCortes;
        this.numColunasTotalItems = original.numColunasTotalItems;
        this.matrixFenotipo = original.getMatrixFenotipoClone();
        this.maxColFen = original.maxColFen;
    }

    private char[][] getMatrixFenotipoClone() {
        char[][] cloneMatrix = new char[matrixFenotipo.length][matrixFenotipo[0].length];
        for (int i = 0; i < matrixFenotipo.length; i++) {
            for (int j = 0; j < matrixFenotipo[0].length; j++) {
                cloneMatrix[i][j] = matrixFenotipo[i][j];
            }
        }
        return cloneMatrix;
    }

    private void preencherFenotipo() {
        matrixFenotipo = new char[problem.getMaterialHeight()][numColunasTotalItems];
        int numeroItem = 1;
        for (int numColuna = 0; numColuna < matrixFenotipo[0].length && numeroItem <= size; numColuna++) {
            for (int numLinha = 0; numLinha < matrixFenotipo.length && numeroItem <= size; numLinha++) {
                if (preencherItemNoFenotipo(matrixFenotipo, numLinha, numColuna, numeroItem)) {
                    numeroItem++;
                }
            }
        }
    }

    private Boolean preencherItemNoFenotipo(char[][] matrixFenotipo, int numLinha, int numColuna, int numItem) {
        Item item = problem.getItems().get(genome[numItem - 1] - 1);
        for (int j = numColuna; j < matrixFenotipo[0].length; j++) {
            int i = 0;
            if (j == numColuna) {
                i = numLinha;
            }
            while (i < matrixFenotipo.length) {
                if (item.getLines() <= matrixFenotipo.length - numLinha && item.getColumns() <= matrixFenotipo[i].length - numColuna) {
                    //verify if the matrix fenotipo is clean where item matrix has his elements
                    int[][] matrixItem = item.getMatrix();
                    for (int x = 0; x < matrixItem.length; x++) {
                        for (int y = 0; y < matrixItem[x].length; y++) {
                            if (matrixItem[x][y] > 0 && matrixFenotipo[x + numLinha][y + numColuna] > 0) {
                                return false;
                            }
                        }
                    }
                    //if success set item matrix in correct fenotipo matrix and returns true, otherwise returns false before reaching this code point
                    for (int x = 0; x < matrixItem.length; x++) {
                        for (int y = 0; y < matrixItem[x].length; y++) {
                            if (matrixItem[x][y] > 0) {
                                matrixFenotipo[x + numLinha][y + numColuna] = item.getRepresentation();
                            }
                        }
                    }
                    return true;
                }
                i++;
            }
        }
        return false;
    }


    @Override
    public double computeFitness() {
        //TODO
        preencherFenotipo();

        double overLoadBrancos = 1.6;
        numBrancos = numCortes = 0;

        int numLinhas = matrixFenotipo.length;
        int numColunas = matrixFenotipo[0].length;


        for (int maxColumn = 0; maxColumn < numColunas; maxColumn++) {
            boolean emptyCol = true;
            for (int linha = 0; linha < numLinhas; linha++) {
                if ((int) matrixFenotipo[linha][maxColumn] != 0) {
                    emptyCol = false;
                    break;
                }
            }
            if (emptyCol) {
                maxColFen = maxColumn;
                break;
            }
        }

        for (int coluna = 0; coluna < numColunas && coluna < maxColFen; coluna++) {
            for (int linha = 0; linha < numLinhas; linha++) {
                if ((int) matrixFenotipo[linha][coluna] == 0) {
                    numBrancos++;
                }
                if (linha + 1 <= numLinhas - 1 && (matrixFenotipo[linha][coluna] != matrixFenotipo[linha + 1][coluna])) {
                    numCortes++;
                }

                if (coluna + 1 <= numColunas - 1 && (matrixFenotipo[linha][coluna] != matrixFenotipo[linha][coluna + 1])) {
                    numCortes++;
                }
            }
        }

        //numCortes += numLinhas;

        fitness = numCortes + numBrancos * overLoadBrancos;

        //mostarFenotipo();
        return fitness;
    }

    private boolean checkValidPlacement(Item item, int[][] material, int lineIndex, int columnIndex) {
        int[][] itemMatrix = item.getMatrix();
        for (int i = 0; i < itemMatrix.length; i++) {
            for (int j = 0; j < itemMatrix[i].length; j++) {
                if (itemMatrix[i][j] != 0) {
                    if ((lineIndex + i) >= material.length
                            || (columnIndex + j) >= material[0].length
                            || material[lineIndex + i][columnIndex + j] != 0) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void mostarFenotipo() {
        for (int x = 0; x < matrixFenotipo.length; x++) {
            for (int y = 0; y < maxColFen; y++) {
                if ((int) matrixFenotipo[x][y] == 0) {
                    System.out.print("-|");
                } else {
                    System.out.print(matrixFenotipo[x][y] + "|");
                }
            }
            System.out.println("");
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("fitness: ");
        sb.append(fitness);
        //TODO
        return sb.toString();
    }

    /**
     * @param i
     * @return 1 if this object is BETTER than i, -1 if it is WORST than I and
     * 0, otherwise.
     */
    @Override
    public int compareTo(StockingProblemIndividual i) {
        return (this.fitness == i.getFitness()) ? 0 : (this.fitness < i.getFitness()) ? 1 : -1;
    }

    @Override
    public StockingProblemIndividual clone() {
        return new StockingProblemIndividual(this);

    }
}





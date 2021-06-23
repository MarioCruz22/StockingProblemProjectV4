package random;

import algorithms.Algorithm;
import algorithms.AlgorithmEvent;
import algorithms.Individual;
import algorithms.Problem;

import java.util.Random;

public class RandomAlgorithm<I extends Individual, P extends Problem<I>> extends Algorithm<I, P> {
    //TODO this class might require the definition of additional methods and/or attributes
    private  I individuo;





    public RandomAlgorithm(int maxIterations, Random rand) {
        super(maxIterations, rand);
    }

    @Override
    public I run(P problem) {
        //TODO
        t = 0;
        individuo = problem.getNewIndividual();
        globalBest = individuo;
        individuo.computeFitness();
        this.fireIterationEnded(new AlgorithmEvent(this));

        while(this.t < maxIterations && !stopped) {
            individuo = problem.getNewIndividual();
            individuo.computeFitness();
            this.computeBestInRun(individuo);
            ++t;
            this.fireIterationEnded(new AlgorithmEvent(this));
        }

        this.fireRunEnded(new AlgorithmEvent(this));
        return globalBest;
    }

    private void computeBestInRun(I individual) {
        if (individual.compareTo(globalBest) > 0) {
            globalBest = (I) individual.clone();
        }
    }
}

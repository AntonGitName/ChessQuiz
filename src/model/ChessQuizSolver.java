package model;

import java.util.Random;

public class ChessQuizSolver {
    
    private static final Random rnd = new Random();
    
    private static void swap(int[] a, int i, int j) {
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }
    
    private static int energy(int[] a) {
        int len = a.length;
        int E = 0;
        for (int i = 0; i < len; ++i) {
            for (int j = i + 1; j < len; ++j) {
                if (Math.abs(i - j) == Math.abs(a[i] - a[j])) {
                    ++E;
                }
            }
        }
        return E;
    }
    
    private static final int MAX_ITERATIONS = 1000000;
    
    public static int solve(int n, double startTemperature, double alpha, int iter, int[] queens) {
                
        for (int i = 0; i < n; ++i) {
            queens[i] = i;
        }
        for (int i = 2; i < n; ++i) {
            swap(queens, i, rnd.nextInt(i - 1));
        }

        int cycleIter = 0;
        int currentEnergy = energy(queens);
        int newEnergy;
        int totalIterations = 0;
        for (; totalIterations < MAX_ITERATIONS && energy(queens) > 0; ++totalIterations) {
            
            double swapProbability;
            
            int newQueens[] = queens.clone();
            int x = rnd.nextInt(n);
            int y = rnd.nextInt(n);
            swap(newQueens, x, y);
            newEnergy = energy(newQueens);
            
            if (newEnergy < currentEnergy) {
                swapProbability = 1.0;
            } else {
                swapProbability = Math.exp((currentEnergy - newEnergy) / startTemperature);
            }
            
            if (swapProbability > 0.5) {
                currentEnergy = newEnergy;
                swap(queens, x, y);
            }
            
            if (cycleIter < iter) {
                ++cycleIter;
            } else {
                cycleIter = 0;
                startTemperature *= alpha;
            }
        }
        
        return totalIterations;
    }

}

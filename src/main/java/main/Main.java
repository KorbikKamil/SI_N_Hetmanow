package main;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solution;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.search.strategy.Search;
import org.chocosolver.solver.variables.IntVar;

public class Main {
    public static void main(String[] args) {

        int n = 8;

        Model model = new Model("Problem " + n + " hetmanów");
        IntVar[] queens = model.intVarArray("H", n, 1, n, false);
        IntVar[] diagonal1 = new IntVar[n];
        IntVar[] diagonal2 = new IntVar[n];

        for(int i = 0 ; i < n; i++){
            diagonal1[i] = queens[i].sub(i).intVar();
            diagonal2[i] = queens[i].add(i).intVar();
        }

        model.post(
                model.allDifferent(queens),
                model.allDifferent(diagonal1),
                model.allDifferent(diagonal2)
        );

        Solver solver = model.getSolver();
        solver.setSearch(Search.domOverWDegSearch(queens));
        Solution solution = solver.findSolution();

        if (solution != null) {
            System.out.println(solution.toString());
        }
    }
}

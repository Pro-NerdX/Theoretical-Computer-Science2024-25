package org.example.reduction

import org.example.cnf.CNF
import org.example.graph.Graph
import org.example.graph._3ColoringSolver

/**
 * @author here goes your implementation
 */
class _3SatSolver {
    /**
     * @author this is supposed to be the actual reduction function
     */
    fun reduceTo3Coloring(cnf: CNF): Graph {
        TODO()
    }

    /**
     * @author first use the reduction function to get a [Graph] and then use [_3ColoringSolver.solve] to solve 3Sat.
     */
    fun solve(cnf: CNF): Boolean {
        val _3coloringSolver = _3ColoringSolver()

        TODO()
    }
}

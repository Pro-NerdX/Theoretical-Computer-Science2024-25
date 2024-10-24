package org.example.graph

/**
 * @brief 3Col-Solver.
 * @see solve
 */
class Slow3ColoringSolver {
    /**
     * @author this is a real fvcking slow algorithm
     */
    fun solve(graph: Graph): Boolean {
        val vertices: List<Vertex> = graph.vertices.toList()
        return this.solverSubroutine(graph, vertices, listOf(Color.SEND_HELP))
    }

    /**
     * @see solve
     */
    private fun solverSubroutine(graph: Graph, vertices: List<Vertex>, assignment: List<Color>): Boolean {
        if (vertices.size != assignment.size) {
            return solverSubroutine(graph, vertices, assignment + Color.TRUE)
                    || solverSubroutine(graph, vertices, assignment + Color.FALSE)
                    || solverSubroutine(graph, vertices, assignment + Color.SEND_HELP)
        }

        for (index: Int in vertices.indices) {
            vertices[index].assignment = assignment[index]
        }
        return graph.hasValidColoring()
    }
}

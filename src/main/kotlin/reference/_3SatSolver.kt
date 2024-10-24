package org.example.reference

import org.example.cnf.CNF
import org.example.cnf.Clause
import org.example.cnf.Literal
import org.example.cnf.Variable
import org.example.graph.Edge
import org.example.graph.Graph
import org.example.graph.Vertex
import org.example.graph.Slow3ColoringSolver

/**
 * @brief reference implementation
 */
class _3SatSolver {
    /**
     * @brief reference reduction function
     */
    fun reduceTo3Coloring(cnf: CNF): Graph {
        // Add 1 copy of G_1
        val bottomNode = Vertex("bottom")
        val topNode = Vertex("top")
        val zNode = Vertex("z")
        val vertices: MutableSet<Vertex> = mutableSetOf(bottomNode, topNode, zNode)
        val edges: MutableSet<Edge> = mutableSetOf(
            Edge(bottomNode, topNode),
            Edge(topNode, zNode),
            Edge(zNode, bottomNode)
        )

        // Add 1 copy of G_2 for each variable x_i
        for (variable: Variable in cnf.getAllVariables()) {
            val positive = Vertex(variable.name)
            val negative = Vertex("~" + variable.name)
            vertices.add(positive)
            vertices.add(negative)
            edges.add(Edge(positive, negative))
            edges.add(Edge(zNode, positive))
            edges.add(Edge(zNode, negative))
        }

        // Add 1 copy of G_3 for each clause
        var dummyIdCounter = 0
        for (disjunction: Clause in cnf.clauses) {
            // get vertices for each literal in the clause
            val literalVertices: MutableList<Vertex> = mutableListOf(bottomNode, bottomNode, bottomNode)
            for (index: Int in disjunction.literals.indices) {
                val literal: Literal = disjunction.literals[index]
                literalVertices[index] = vertices.first { it.name == literal.getName() }
            }

            // introduce dummy vertices
            val dummies: MutableList<Vertex> = mutableListOf()
            for (index: Int in 0..5) {
                dummies.add(Vertex("D" + dummyIdCounter))
                dummyIdCounter++
            }
            // introduce the corresponding edges
            edges.add(Edge(literalVertices[0], dummies[0]))
            edges.add(Edge(literalVertices[1], dummies[1]))
            edges.add(Edge(literalVertices[2], dummies[4]))
            edges.add(Edge(dummies[0], dummies[1]))
            edges.add(Edge(dummies[0], dummies[2]))
            edges.add(Edge(dummies[1], dummies[2]))
            edges.add(Edge(dummies[2], dummies[3]))
            edges.add(Edge(dummies[3], dummies[4]))
            edges.add(Edge(dummies[3], dummies[5]))
            edges.add(Edge(dummies[4], dummies[5]))
            edges.add(Edge(dummies[5], bottomNode))
        }

        val finalEdges: MutableSet<Edge> = mutableSetOf()
        edges.forEach { finalEdges.add(it) }
        return Graph(finalEdges.toList())
    }

    /**
     * @brief reference solver
     */
    fun solve(cnf: CNF): Boolean {
        val _3coloringSolver = Slow3ColoringSolver()
        return _3coloringSolver.solve(this.reduceTo3Coloring(cnf))
    }
}

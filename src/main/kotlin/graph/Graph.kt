package org.example.graph

const val END_OF_LINE = "0\n"

data class Graph(
    val edges: List<Edge>
) {
    val vertices: Set<Vertex>

    init {
        val vertices: MutableSet<Vertex> = mutableSetOf()
        edges.forEach {
            if (it.v1 == it.v2) { error("Graph: Edge with self-loop found ($it)") }
            vertices.add(it.v1)
            vertices.add(it.v2)
        }
        this.vertices = vertices
    }

    /**
     * @brief checks, whether the given coloring of the graph is valid.
     */
    fun hasValidColoring(): Boolean {
        this.edges.forEach {
            if (it.v1.assignment == it.v2.assignment || it.v1.assignment == null || it.v2.assignment == null) {
                return false
            }
        }
        return true
    }

    /**
     * @brief converts the graph into a cnf structure for 3Coloring
     *
     * @return [String], because the string will be written to a file at the end.
     *
     * @author for each integer i, we have:
     * - i+0 refers to X_red
     * - i+1 refers to X_green
     * - i+2 refers to X_blue
     */
    fun convertToCNF(): String {
        var cnfString = ""

        // Setup
        val nameMap: Map<String, Int> = this.generateNameMap()
        var numberOfVariables = 0
        var numberOfClauses = 0

        // Enforce the variables to have exactly 1 possible color
        nameMap.entries.forEach {
            val red: Int = it.value
            val green: Int = red + 1
            val blue: Int = green + 1
            numberOfVariables += 3

            cnfString += "$red $green $blue$END_OF_LINE"
            cnfString += "${-red} ${-green}$END_OF_LINE"
            cnfString += "${-green} ${-blue}$END_OF_LINE"
            cnfString += "${-blue} ${-red}$END_OF_LINE"
            numberOfClauses += 4
        }

        // Enforce no 2 adjacent vertices to have the same color
        this.edges.forEach {
            val x: Int = nameMap[it.v1.name] ?: error("won't happen, trust me")
            val y: Int = nameMap[it.v2.name] ?: error("read other error message")

            for (i: Int in 0..2) {
                val xCol: Int = x + i
                val yCol: Int = y + i

                cnfString += "$xCol $yCol$END_OF_LINE"
                cnfString += "${-xCol} ${-yCol}$END_OF_LINE"
                numberOfClauses += 2
            }
        }

        return "p cnf $numberOfVariables $numberOfClauses\n$cnfString"
    }

    /**
     * @brief maps the name of each vertex to a unique integer.
     */
    private fun generateNameMap(): Map<String, Int> {
        val result: MutableMap<String, Int> = mutableMapOf()
        var indexOfVertex = 1
        for (vertex: Vertex in this.vertices) {
            result[vertex.name] = indexOfVertex
            indexOfVertex += 3
        }
        return result
    }
}

data class Edge(
    val v1: Vertex,
    val v2: Vertex
) {
    var assignment: Color? = null

    fun contains(vertex: Vertex): Boolean {
        return vertex == this.v1 || vertex == this.v2
    }

    override fun equals(other: Any?): Boolean {
        if (other !is Edge) {
            return false
        }
        return (other.v1 == this.v1 && other.v2 == this.v2) || (other.v1 == this.v2 && other.v2 == this.v1)
    }

    override fun hashCode(): Int {
        return this.v1.hashCode() * this.v2.hashCode()
    }
}

data class Vertex(
    val name: String
) {
    var assignment: Color? = null

    override fun equals(other: Any?): Boolean {
        if (other !is Vertex) {
            return false
        }
        return other.name == this.name
    }

    override fun hashCode(): Int {
        return this.name.hashCode()
    }
}

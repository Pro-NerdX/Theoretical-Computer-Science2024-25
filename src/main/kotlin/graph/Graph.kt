package org.example.graph

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

    fun getEdges(vertex: Vertex): List<Edge> {
        return this.edges.filter { it.contains(vertex) }
    }

    fun isFullyColored(): Boolean {
        this.vertices.forEach {
            if (it.assignment == null) return false
        }
        return true
    }

    fun hasValidColoring(): Boolean {
        this.edges.forEach {
            if (it.v1.assignment == it.v2.assignment) return false
        }
        return true
    }

    fun colorWouldWorkForVertexTemporarily(color: Color, vertex: Vertex): Boolean {
        val initialAssignment: Color? = vertex.assignment
        vertex.assignment = color
        val result = this.hasValidColoring()
        vertex.assignment = initialAssignment
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

package org.example.cnf

/**
 * @brief data class for boolean formulas in conjunctive normal form
 */
data class CNF(val clauses: List<Clause>) {
    init {
        this.clauses.forEach {
            assert(it.literals.size in 1..3)
        }
    }

    /**
     * @brief smart-getter
     */
    fun getAllVariables(): Set<Variable> {
        val result: MutableSet<Variable> = mutableSetOf()
        this.clauses.forEach { clause ->
            clause.literals.forEach { literal ->
                result.add(literal.variable)
            }
        }
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (other !is CNF) {
            return false
        }
        return other.clauses == this.clauses
    }

    override fun hashCode(): Int {
        return this.clauses.hashCode()
    }

    override fun toString(): String {
        var string = ""
        for (index: Int in 0..< this.clauses.size) {
            string += "(${this.clauses[index]}" + if (index == this.clauses.size - 1) {
                ")"
            } else {
                ") ^ "
            }
        }
        return string
    }
}

/**
 * @brief data class for clauses in a [CNF]
 */
data class Clause(val literals: List<Literal>) {
    override fun equals(other: Any?): Boolean {
        if (other !is Clause) {
            return false
        }
        return other.literals == this.literals
    }

    override fun hashCode(): Int {
        return this.literals.hashCode()
    }

    override fun toString(): String {
        var string = ""
        for (index: Int in 0..< this.literals.size) {
            string += this.literals[index].toString() + if (index == this.literals.size - 1) {
                ""
            } else {
                " v "
            }
        }
        return string
    }
}

/**
 * @brief data class for literals in a [Clause]
 */
data class Literal(
    val negated: Boolean,
    val variable: Variable
) {
    /**
     * @brief smart-getter
     */
    fun getName(): String {
        return if (this.negated) {
            "~"
        } else {
            ""
        } + this.variable.name
    }

    override fun equals(other: Any?): Boolean {
        if (other !is Literal) {
            return false
        }
        return other.negated == this.negated && other.variable == this.variable
    }

    override fun hashCode(): Int {
        return this.negated.hashCode() * variable.hashCode()
    }

    override fun toString(): String {
        return if (this.negated) {
            "~"
        } else {
            ""
        } + this.variable.toString()
    }
}

/**
 * @brief data class for variables.
 * @see Literal
 */
data class Variable(
    val name: String
) {
    var assignment: Boolean? = null

    override fun equals(other: Any?): Boolean {
        if (other !is Variable) {
            return false
        }
        return other.name == this.name
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }

    override fun toString(): String {
        return this.name
    }
}

package org.example.cnf

/**
 * @brief helper class to make testing easier.
 */
class CNFFactory {

    private fun parseLiteral(literalAsString: String, variables: MutableSet<Variable>): Literal {
        val unaryNegation: Boolean = literalAsString.startsWith("~")
        val variableName: String = if (unaryNegation) {
            literalAsString.substring(1)
        } else {
            literalAsString
        }
        val variable: Variable = if (variables.any { it.name == variableName }) {
            variables.first { it.name == variableName }
        } else {
            Variable(variableName)
        }
        variables.add(variable)
        return Literal(unaryNegation, variable)
    }

    private fun parseClause(clauseAsString: String, variables: MutableSet<Variable>): Clause {
        val regex: Regex = """\((.*?)\)""".toRegex()
        val matchResult: MatchResult = regex.find(clauseAsString) ?: error(
            "CNFFactory.parseClause: Given clause (= $clauseAsString) did not match the expected pattern. (1)"
        )
        val literalsAsStrings: List<String> = matchResult.groups[1]?.value?.split("v") ?: error(
            "CNFFactory.parseClause: Given clause (= $clauseAsString) did not match the expected pattern. (2)"
        )
        val literals: MutableList<Literal> = mutableListOf()
        literalsAsStrings.forEach {
            literals.add(this.parseLiteral(it, variables))
        }
        return Clause(literals)
    }

    private fun parseCNF(cnfAsString: String): CNF {
        val cnfString: String = cnfAsString.replace("\\s+".toRegex(), "")
        val clausesAsStrings: List<String> = cnfString.split('^')
        val variables: MutableSet<Variable> = mutableSetOf()
        val clauses: MutableList<Clause> = mutableListOf()
        clausesAsStrings.forEach {
            val clause: Clause = this.parseClause(it, variables)
            clauses.add(clause)
        }
        return CNF(clauses)
    }

    /**
     * @brief generates a [CNF] given a [String]. For specification, read README.md
     */
    fun createCNFByString(string: String): CNF {
        return this.parseCNF(string)
    }
}

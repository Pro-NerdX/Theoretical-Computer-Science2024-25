package org.example.graph

import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * @brief 3Col-Solver using the glucose solver.
 */
class Fast3ColoringSolver {
    /**
     * @author TODO
     */
    fun solve(graph: Graph): Boolean {
        TODO()
    }

    private fun runCommand(command: List<String>): String {
        val processBuilder = ProcessBuilder(command)

        // Redirect error stream to standard output
        processBuilder.redirectErrorStream(true)

        // Start the process
        val process = processBuilder.start()

        // Capture the output (stdout)
        val output = StringBuilder()

        BufferedReader(InputStreamReader(process.inputStream)).use { reader ->
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                output.append(line).append("\n")
            }
        }

        // Wait for the process to finish
        process.waitFor()

        return output.toString()
    }
}

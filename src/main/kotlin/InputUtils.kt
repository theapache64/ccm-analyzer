/*
 * Copyright 2021 Boil (https://github.com/theapache64/boil)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import java.util.*

/**
 * Operations related to reading text input from console
 */
object InputUtils {

    private val scanner by lazy { Scanner(System.`in`) }

    /**
     * Get a String with given prompt as prompt
     */
    fun getString(prompt: String, isRequired: Boolean): String {
        print("$prompt: ")
        val value = scanner.nextLine()
        while (value.trim().isEmpty() && isRequired) {
            println("Invalid ${prompt.toLowerCase()} `$value`")
            return getString(prompt, isRequired)
        }
        return value
    }

    fun getInt(prompt: String, lowerBound: Int, upperBound: Int, but: Array<Int> = arrayOf()): Int {
        print("$prompt :")

        val sVal = scanner.nextLine()
        try {
            val value = sVal.toInt()
            if (!but.contains(value) && (value < lowerBound || value > upperBound)) {
                // error
                println("Input must between $lowerBound and $upperBound")
                return getInt(prompt, lowerBound, upperBound)
            }
            return value
        } catch (e: NumberFormatException) {
            println("Invalid input `$sVal`")
            return getInt(prompt, lowerBound, upperBound)
        }
    }

}

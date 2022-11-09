import org.json.JSONException
import org.json.JSONObject
import java.io.File
import kotlin.io.path.div
import kotlin.io.path.exists
import kotlin.io.path.readText
import kotlin.io.path.writeText


fun main(args: Array<String>) {
    val userDir = File(System.getProperty("user.dir"))
    val joSum = JSONObject()
    println("Looking for CCM json...")
    userDir.walk()
        .filter {
            val isModuleJson = it.name.endsWith("release-module.json")
            val relPath = it.absolutePath.replace(userDir.absolutePath, "")
            if(isModuleJson){
                println("➡️ $relPath")
            }
            isModuleJson
        }.forEach {
            val joRoot = JSONObject(it.readText())
            val keys = joRoot.keys()
            for (key in keys) {
                val currentValue = try {
                    joSum.getInt(key)
                } catch (e: JSONException) {
                    0
                }
                joSum.put(key, currentValue + joRoot.getInt(key))
            }
        }

    print("\r☑️ Done")

    if (joSum.isEmpty) {
        println("Couldn't find any CCM files")
        return
    }


    println("✨NEW")
    println("-------")
    println(joSum.toString(2))
    println("---------------------------")

    val outputJson = userDir.toPath() / "build" / "ccm-analyzer.json"
    if (outputJson.exists()) {

        // Calculating difference
        val joCalculated = JSONObject()
        val joOld = JSONObject(outputJson.readText())
        val keys = joOld.keys()
        for (key in keys) {
            val oldValue = joOld.getInt(key)
            val newValue = joSum.getInt(key)
            val diff = newValue - oldValue
            joCalculated.put(key, diff)
        }

        println("👴🏻OLD")
        println("------")
        println(joOld.toString(2))
        println("---------------------------")


        println(" 🔀 DIFF")
        println("---------")
        println(joCalculated.toString(2))
        println("---------------------------")
    }


    if (outputJson.exists()) {
        val shouldReplace =
            InputUtils.getString("Do you want to replace the output? (y/n)", true).equals("y", ignoreCase = true)
        if (shouldReplace) {
            println("Replaced")
            // Replacing JSON
            outputJson.writeText(joSum.toString(2))
        }
    } else {
        println("Saved")
        // Saving new JSON
        outputJson.writeText(joSum.toString(2))
    }

}
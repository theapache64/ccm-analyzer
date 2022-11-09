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
    userDir.walk()
        .filter { it.name.endsWith("release-module.json") }.forEach {
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

    val outputJson = userDir.toPath() / "build" / "ccm-analyzer.json"
    if (outputJson.exists()) {
        // Calculating difference
        val joCalculated = JSONObject()
        val joOld = JSONObject(outputJson.readText())
        val keys = joOld.keys()
        for (key in keys) {
            val oldValue = joOld.getInt(key)
            val newValue = joSum.getInt(key)
            val diff = oldValue - newValue
            joCalculated.put(key, diff)
        }

        println(joCalculated.toString(2))
    }



    // Saving new JSON
    outputJson.writeText(joSum.toString(2))
}
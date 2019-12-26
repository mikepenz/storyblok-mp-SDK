import kotlin.coroutines.*
import kotlinx.coroutines.*
import com.mikepenz.storyblok.sdk.Storyblok

external fun require(module: String): dynamic

fun main(args: Array<String>) {
    println("Hello JavaScript!")

    val express = require("express")
    val app = express()
    val application = Application()
    val client = Storyblok("test")

    app.get("/", { req, res ->
        res.type("text/plain")

        application.launch {
            client.fetchStories().forEach {
                res.send(it.name)
            }
        }
    })

    app.listen(3000, {
        println("Listening on port 3000")
    })
}

class Application : CoroutineScope {
    internal var job = Job()
    override val coroutineContext: CoroutineContext
        get() = job

    private fun onClear() {
        job.cancel()
        job = Job()
    }
}

import kotlin.coroutines.*
import kotlinx.coroutines.*

external fun require(module: String): dynamic

fun main(args: Array<String>) {
    println("Hello JavaScript!")

    val express = require("express")
    val app = express()
    val application = Application()

    app.get("/", { req, res ->
        res.type("text/plain")

        application.launch {
            delay(3000)
            res.send("Kotlin/JS is kool 2")

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

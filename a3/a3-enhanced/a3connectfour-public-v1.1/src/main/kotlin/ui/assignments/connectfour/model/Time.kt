package ui.assignments.connectfour.model

class Time {
    private var hour: Int = 0
    private var minute: Int = 0
    private var second: Int = 0

    val useTime: String
        get() = "$hour:$minute:$second"

    fun increase() {
        second++
        if (second == 60) {
            minute++
            second = 0
            if (minute == 60) {
                hour++
                minute = 0
            }
        }
    }
}
package dev.csshortcourse.student

import android.os.Bundle
import dev.csshortcourse.shared.BaseActivity
import dev.csshortcourse.shared.currentContext
import dev.csshortcourse.shared.debugger

class HomeActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        debugger(currentContext)
    }
}

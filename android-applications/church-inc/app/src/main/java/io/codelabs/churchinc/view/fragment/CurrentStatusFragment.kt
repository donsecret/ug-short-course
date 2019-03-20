package io.codelabs.churchinc.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.codelabs.churchinc.R
import io.codelabs.churchinc.core.RootFragment
import kotlinx.android.synthetic.main.fragment_background_info.*

class CurrentStatusFragment : RootFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_background_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        test_text.text = this::class.java.simpleName
    }
}
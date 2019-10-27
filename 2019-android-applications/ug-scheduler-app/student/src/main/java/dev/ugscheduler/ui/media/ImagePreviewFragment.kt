package dev.ugscheduler.ui.media

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import coil.api.load
import coil.request.CachePolicy
import dev.ugscheduler.databinding.ImagePreviewFragmentBinding
import dev.ugscheduler.util.MainNavigationFragment

class ImagePreviewFragment : MainNavigationFragment() {
    private lateinit var binding: ImagePreviewFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ImagePreviewFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Get image uri
        val imageUri = arguments?.get("extra_image")?.toString()

        if (!imageUri.isNullOrEmpty()) {
            binding.preview.load(imageUri) {
                diskCachePolicy(CachePolicy.ENABLED)
                crossfade(true)
            }
        }
    }
}
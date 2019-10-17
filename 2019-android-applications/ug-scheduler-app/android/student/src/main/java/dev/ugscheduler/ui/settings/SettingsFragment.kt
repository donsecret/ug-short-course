package dev.ugscheduler.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import coil.api.load
import coil.request.CachePolicy
import coil.transform.CircleCropTransformation
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dev.ugscheduler.R
import dev.ugscheduler.databinding.FragmentSettingsBinding
import dev.ugscheduler.shared.data.Student
import dev.ugscheduler.shared.prefs.AppPreferences
import dev.ugscheduler.shared.util.activityViewModelProvider
import dev.ugscheduler.shared.util.debugger
import dev.ugscheduler.shared.util.toast
import dev.ugscheduler.shared.viewmodel.AppViewModel
import dev.ugscheduler.shared.viewmodel.AppViewModelFactory
import dev.ugscheduler.util.MainNavigationFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject

class SettingsFragment : MainNavigationFragment() {

    private val viewModel by lazy { activityViewModelProvider<AppViewModel>(AppViewModelFactory(get())) }
    private lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(inflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Observe current student
        viewModel.getCurrentStudent(false).observe(viewLifecycleOwner, Observer { student ->
            if (student != null) bindStudent(student) else debugger("Student could not be found")
        })

        // Swipe refresh setup
        binding.swipeRefresh.setOnRefreshListener {
            val currentStudent = viewModel.getCurrentStudent(true)
            currentStudent.removeObservers(viewLifecycleOwner)
            currentStudent.observe(viewLifecycleOwner, Observer { student ->
                binding.swipeRefresh.isRefreshing = false
                if (student != null) {
                    bindStudent(student)
                } else toast("Student could not be found")
            })
        }

        // Get theme preferences instance from DI
        val prefs by inject<AppPreferences>()
        binding.prefsTheme.setOnClickListener {
            val options =
                arrayOf<CharSequence>("Light", "Dark", "Set by Battery Saver", "System Default")
            MaterialAlertDialogBuilder(this@SettingsFragment.requireContext()).apply {
                setTitle("Select theme")
                setSingleChoiceItems(
                    options,
                    if (prefs.currentMode == -1) 3 else prefs.currentMode.minus(1)
                ) { dialog, which ->
                    dialog.dismiss()
                    uiScope.launch {
                        // Add short delay
                        delay(550)
                        //TransitionManager.beginDelayedTransition(binding.container)
                        when (which) {
                            0 -> prefs.setDarkMode(AppCompatDelegate.MODE_NIGHT_NO)
                            1 -> prefs.setDarkMode(AppCompatDelegate.MODE_NIGHT_YES)
                            2 -> prefs.setDarkMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY)
                            else -> prefs.setDarkMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                        }
                    }
                }
                setNegativeButton("Dismiss") { dialog, _ ->
                    dialog.cancel()
                }
                show()
            }
        }
    }

    private fun bindStudent(student: Student) {
        binding.addressPrefs.apply {
            summary = student.address ?: "Not set"
            setOnClickListener { editContent(EditType.ADDRESS, student) }
        }
        binding.dobPrefs.apply {
            summary = student.dob ?: "Not set"
            setOnClickListener { editContent(EditType.DOB, student) }
        }
        binding.eduPrefs.apply {
            summary = student.eduBackground ?: "Not set"
            setOnClickListener { editContent(EditType.EDU, student) }
        }
        binding.orgPrefs.apply {
            summary = student.organisation ?: "Not set"
            setOnClickListener { editContent(EditType.ORG, student) }
        }
        binding.phonePrefs.apply {
            summary = student.phone ?: "Not set"
            setOnClickListener { editContent(EditType.PHONE, student) }
        }
        binding.posPrefs.apply {
            summary = student.position ?: "Not set"
            setOnClickListener { editContent(EditType.POS, student) }
        }
        binding.residencePrefs.apply {
            summary = student.residence ?: "Not set"
            setOnClickListener { editContent(EditType.RESIDENCE, student) }
        }

        binding.userName.apply {
            text = student.fullName ?: "Not set"
            setOnClickListener { editContent(EditType.USERNAME, student) }
        }
        binding.userEmail.text = student.email
        binding.userAvatar.apply {
            load(student.avatar) {
                transformations(CircleCropTransformation())
                placeholder(R.drawable.ic_default_avatar_1)
                error(R.drawable.ic_default_avatar)
                crossfade(true)
                lifecycle(viewLifecycleOwner)
                diskCachePolicy(CachePolicy.ENABLED)
            }

            setOnClickListener { editContent(EditType.AVATAR, student) }
        }
    }

    private fun editContent(type: EditType, student: Student) {
        // todo: allow content editing
        /*student.apply {
            residence = "Dansoman"
            address = "2nd Danso close"
            eduBackground = "Tertiary (University of Ghana, Legon)"
            organisation = "Quabynah Codelabs LLC"
            position = "C.E.O."
            dob = "August 23, 1993"
        }*/
        when (type) {
            EditType.ADDRESS -> {
                MaterialAlertDialogBuilder(requireContext()).apply {
                    setTitle("Oops...")
                    setMessage("This feature is currently under active development and will be available in the next release of this application. Thank you")
                    setPositiveButton("Dismiss") { dialog, _ -> dialog.dismiss() }
                    show()
                }
            }

            EditType.DOB -> {
                MaterialAlertDialogBuilder(requireContext()).apply {
                    setTitle("Oops...")
                    setMessage("This feature is currently under active development and will be available in the next release of this application. Thank you")
                    setPositiveButton("Dismiss") { dialog, _ -> dialog.dismiss() }
                    show()
                }
            }

            EditType.EDU -> {
                MaterialAlertDialogBuilder(requireContext()).apply {
                    setTitle("Oops...")
                    setMessage("This feature is currently under active development and will be available in the next release of this application. Thank you")
                    setPositiveButton("Dismiss") { dialog, _ -> dialog.dismiss() }
                    show()
                }
            }

            EditType.ORG -> {
                MaterialAlertDialogBuilder(requireContext()).apply {
                    setTitle("Oops...")
                    setMessage("This feature is currently under active development and will be available in the next release of this application. Thank you")
                    setPositiveButton("Dismiss") { dialog, _ -> dialog.dismiss() }
                    show()
                }
            }

            EditType.PHONE -> {
                MaterialAlertDialogBuilder(requireContext()).apply {
                    setTitle("Oops...")
                    setMessage("This feature is currently under active development and will be available in the next release of this application. Thank you")
                    setPositiveButton("Dismiss") { dialog, _ -> dialog.dismiss() }
                    show()
                }
            }

            EditType.POS -> {
                MaterialAlertDialogBuilder(requireContext()).apply {
                    setTitle("Oops...")
                    setMessage("This feature is currently under active development and will be available in the next release of this application. Thank you")
                    setPositiveButton("Dismiss") { dialog, _ -> dialog.dismiss() }
                    show()
                }
            }

            EditType.RESIDENCE -> {
                MaterialAlertDialogBuilder(requireContext()).apply {
                    setTitle("Oops...")
                    setMessage("This feature is currently under active development and will be available in the next release of this application. Thank you")
                    setPositiveButton("Dismiss") { dialog, _ -> dialog.dismiss() }
                    show()
                }
            }

            EditType.USERNAME -> {
                MaterialAlertDialogBuilder(requireContext()).apply {
                    setTitle("Oops...")
                    setMessage("This feature is currently under active development and will be available in the next release of this application. Thank you")
                    setPositiveButton("Dismiss") { dialog, _ -> dialog.dismiss() }
                    show()
                }
            }

            EditType.AVATAR -> {
                MaterialAlertDialogBuilder(requireContext()).apply {
                    setTitle("Oops...")
                    setMessage("This feature is currently under active development and will be available in the next release of this application. Thank you")
                    setPositiveButton("Dismiss") { dialog, _ -> dialog.dismiss() }
                    show()
                }
            }
        }

        // Store student information
        viewModel.addStudent(student)
    }

    // Enumeration for the various editable contents
    enum class EditType { ADDRESS, DOB, EDU, ORG, POS, PHONE, RESIDENCE, USERNAME, AVATAR }
}

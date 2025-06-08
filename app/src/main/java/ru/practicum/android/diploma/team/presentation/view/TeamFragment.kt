package ru.practicum.android.diploma.team.presentation.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentTeamBinding
import ru.practicum.android.diploma.team.presentation.viewmodel.TeamViewModel
import kotlin.getValue

class TeamFragment : Fragment(R.layout.fragment_team) {

    // ViewBinding
    private var _binding: FragmentTeamBinding? = null
    private val binding: FragmentTeamBinding
        get() = requireNotNull(_binding) { "Binding wasn't initiliazed!" }

    // ViewModel
    private val viewModel: TeamViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentTeamBinding.bind(view)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

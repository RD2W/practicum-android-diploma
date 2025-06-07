package ru.practicum.android.diploma.favorites.presentation.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFavoritesBinding
import ru.practicum.android.diploma.favorites.presentation.viewmodel.FavoritesViewModel
import kotlin.getValue

class FavoritesFragment : Fragment(R.layout.fragment_favorites) {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding: FragmentFavoritesBinding
        get() = requireNotNull(_binding) { "Binding wasn't initialized!" }

    private val viewModel: FavoritesViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFavoritesBinding.bind(view)
        setupButtonListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupButtonListeners() {
        // Обработка первой кнопки
        binding.centerButton.setOnClickListener {
            findNavController().navigate(
                FavoritesFragmentDirections.actionFavoritesFragmentToVacancyFragment("your_vacancy_id_here")
            )
        }
    }
}

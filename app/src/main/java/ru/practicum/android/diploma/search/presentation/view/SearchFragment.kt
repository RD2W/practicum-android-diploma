package ru.practicum.android.diploma.search.presentation.view

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentSearchBinding
import ru.practicum.android.diploma.search.presentation.adapter.ListItem
import ru.practicum.android.diploma.search.presentation.adapter.VacancyLoadMoreAdapter
import ru.practicum.android.diploma.search.presentation.state.SearchVacanciesScreenState
import ru.practicum.android.diploma.search.presentation.viewmodel.SearchViewModel
import kotlin.properties.Delegates

class SearchFragment : Fragment(R.layout.fragment_search) {

    private var _binding: FragmentSearchBinding? = null
    private val binding: FragmentSearchBinding
        get() = requireNotNull(_binding) { "Binding is null!" }

    // Viewmodel
    private val viewModel: SearchViewModel by viewModel()

    // Адаптер для RecyclerView
    private val adapter = VacancyLoadMoreAdapter { vacancy ->
        // Логика, исполняемая по нажатию на элемент списка вакансий
        val action = SearchFragmentDirections.actionSearchFragmentToVacancyFragment(vacancy.id)
        findNavController().navigate(action)
    }

    // Загрузилась ли первая страница
    private var isFirstPageLoaded = false
    private var isLoading = false

    // Для красивой загрузки  ресайклера
    private var offset by Delegates.notNull<Int>()
    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(rv: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(rv, dx, dy)
            updateRecyclerViewPadding(rv, offset)

            if (dy > 0) {
                with(binding.rvSearchVacancies) {
                    val lastVisibleItemPosition = (layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                    val totalItems = adapter!!.itemCount
                    if (!isLoading && lastVisibleItemPosition >= totalItems - 1) {
                        isLoading = true
                        this@SearchFragment.adapter.addLoadingFooter()
                        viewModel.loadNextPage()
                    }
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSearchBinding.bind(view)

        setupSearchField()
        setupButtonListeners()
        setupRecyclerView()
        observeViewModel()

        // Инициализация состояния фильтров
        updateFilterIcons(false) // По умолчанию фильтры не выбраны
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupSearchField() {
        // Получаем доступ к элементам из layout
        var lastText = ""
        with(binding) {
            with(searchItem) { // Дополнительный with для searchItem
                // Обработка изменений текста
                inputEditText.doOnTextChanged { text, _, _, _ ->

                    val newText = text?.toString()?.trim() ?: ""

                    if (newText != lastText) {
                        lastText = newText
                        searchIcon.isVisible = newText.isEmpty()
                        clearIcon.isVisible = newText.isNotEmpty()
                        viewModel.onSearchQueryChanged(newText)
                    }
                    // Обработка клика по иконке очистки
                    clearIcon.setOnClickListener {
                        inputEditText.text?.clear()
                        // Возвращаем фокус на поле ввода и показываем клавиатуру после очистки
                        toggleKeyboardAndCursor(show = true, view = inputEditText)
                        // Обнуляем память о RecyclerView
                        binding.rvSearchVacancies.removeOnScrollListener(scrollListener)
                        binding.rvSearchVacancies.scrollToPosition(0)
                        binding.rvSearchVacancies.addOnScrollListener(scrollListener)
                    }
                }
            }
        }
    }

    /**
     * Обработка клика по кнопкам
     */
    private fun setupButtonListeners() {
        with(binding) {
            icFilterOff.setOnClickListener { navigateToFilters() }
            icFilterOn.setOnClickListener { navigateToFilters() }
        }
    }

    /**
     * Переход на экран фильтры
     */
    private fun navigateToFilters() {
        findNavController().navigate(
            SearchFragmentDirections.actionSearchFragmentToFilterFragment()
        )
    }

    /**
     * Обработка состояние иконки фильтров (выбраны или нет)
     */
    private fun updateFilterIcons(hasFilters: Boolean) {
        binding.icFilterOff.isVisible = !hasFilters
        binding.icFilterOn.isVisible = hasFilters
    }

    /**
     * Настраивает RecyclerView с адаптером
     */
    private fun setupRecyclerView() {
        binding.rvSearchVacancies.adapter = adapter

        offset = resources.getDimensionPixelSize(R.dimen.value_32)

        binding.rvSearchVacancies.setPadding(
            binding.rvSearchVacancies.paddingLeft,
            offset,
            binding.rvSearchVacancies.paddingRight,
            binding.rvSearchVacancies.paddingBottom
        )

        binding.rvSearchVacancies.addOnScrollListener(scrollListener)
    }

    private fun updateRecyclerViewPadding(rv: RecyclerView, offset: Int) {
        val layoutManager = rv.layoutManager as LinearLayoutManager
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
        val newPaddingTop = if (firstVisibleItemPosition == 0) offset else 0

        if (rv.paddingTop != newPaddingTop) {
            rv.setPadding(
                rv.paddingLeft,
                newPaddingTop,
                rv.paddingRight,
                rv.paddingBottom
            )
        }
    }

    /**
     * Подписка на вьюмодель
     */
    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.screenState.collect { state ->
                    when (state) {
                        is SearchVacanciesScreenState.Initial -> showInitialState()
                        is SearchVacanciesScreenState.Loading -> showLoading()
                        is SearchVacanciesScreenState.Pagination -> showLoadingPagination()
                        is SearchVacanciesScreenState.Content -> showContent(state)
                        is SearchVacanciesScreenState.NoResults -> showNoResults()
                        is SearchVacanciesScreenState.NetworkError -> showNetworkError()
                        is SearchVacanciesScreenState.ServerError -> showServerError()
                    }
                }
            }
        }
    }

    private fun switchUiMode(
        showLoading: Boolean = false,
        showContent: Boolean = false,
        showNoResults: Boolean = false,
        showNetworkError: Boolean = false,
        showServerError: Boolean = false,
        showInitialState: Boolean = false,
        keepVisible: Boolean = false,
    ) {
        with(binding) {
            // Управление видимостью основных групп элементов
            progressIndicator.isVisible = showLoading
            if (!keepVisible) {
                rvSearchVacancies.isVisible = showContent
                foundedVacancy.isVisible = showContent || showNoResults
            }
            svPlaceholder.isVisible = showInitialState
            groupNoInternetPlaceholder.isVisible = showNetworkError
            groupNotFoundPlaceholder.isVisible = showNoResults
            groupServerErrorPlaceholder.isVisible = showServerError
        }
    }

    /**
     * Состояние инициализации
     */
    private fun showInitialState() {
        switchUiMode(showInitialState = true)
    }

    /**
     * Состояние загрузки
     */
    private fun showLoading() {
        switchUiMode(showLoading = true)
        binding.progressIndicator.isIndeterminate = true
        toggleKeyboardAndCursor(show = false)
    }

    /**
     * Состояние загрузки при пагинации
     */
    private fun showLoadingPagination() {
        switchUiMode(showLoading = false, keepVisible = true)
    }

    /**
     * Состояние показа найденных вакансий
     */
    private fun showContent(state: SearchVacanciesScreenState.Content) {
        isFirstPageLoaded = true
        switchUiMode(showContent = true)

        adapter.removeLoadingFooter()
        isLoading = false

        val listItems = state.searchResult.vacancies.map { ListItem.VacancyItem(it) }
        adapter.submitList(listItems)

        with(binding) {
            foundedVacancy.text = resources.getQuantityString(
                R.plurals.vacancies_found,
                state.searchResult.resultsFound,
                state.searchResult.resultsFound
            )
        }
    }

    /**
     * Состояние нет найденных вакансий
     */
    private fun showNoResults() {
        isFirstPageLoaded = true
        switchUiMode(showNoResults = true)
        binding.foundedVacancy.text = getString(R.string.no_vacancies_found)
    }

    /**
     * Состояние нет интернета
     */
    private fun showNetworkError() {
        isFirstPageLoaded = true
        switchUiMode(showNetworkError = true)
        showToast(getString(R.string.check_internet_connection))
    }

    /**
     * Состояние ошибки сервера
     */
    private fun showServerError() {
        isFirstPageLoaded = true
        switchUiMode(showServerError = true)
        showToast(getString(R.string.something_went_wrong))
    }

    /**
     * Переключатель видимости клавиатуры и курсора
     */
    private fun toggleKeyboardAndCursor(show: Boolean, view: View? = null) {
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        if (show) {
            view?.let {
                it.requestFocus()
                imm.showSoftInput(it, InputMethodManager.SHOW_IMPLICIT)
            }
        } else {
            val focusedView = view ?: requireView().findFocus()
            if (focusedView is EditText) focusedView.clearFocus()
            focusedView?.let {
                imm.hideSoftInputFromWindow(it.windowToken, 0)
            }
        }
    }

    /**
     * Показ тоаста
     */
    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}

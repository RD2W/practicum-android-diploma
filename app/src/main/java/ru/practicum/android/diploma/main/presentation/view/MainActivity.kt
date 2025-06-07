package ru.practicum.android.diploma.main.presentation.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.ActivityMainBinding
import timber.log.Timber

/**
 * Главная Activity приложения, содержащая навигацию и основной UI.
 * Реализует:
 * - Навигацию между фрагментами
 * - Управление Toolbar и BottomNavigation
 */
class MainActivity : AppCompatActivity() {
    /**
     * ViewBinding для activity_main.xml. Инициализируется лениво при первом обращении.
     * Ленивая инициализация позволяет отложить создание до фактической необходимости.
     */
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    /**
     * Контроллер навигации. Инициализируется лениво при первом обращении.
     * Получает NavController из NavHostFragment.
     */
    private val navController by lazy {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.navHostFragment) as NavHostFragment
        navHostFragment.navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.topAppBar)
        setupNavigation()
    }

    /**
     * Основной метод настройки навигации в приложении:
     * - Связывает BottomNavigation с NavController
     * - Настраивает обработчик кнопки "Назад"
     * - Устанавливает слушатель изменений destination
     */
    private fun setupNavigation() {
        // Настройка BottomNavigation
        binding.bottomNavigation.setupWithNavController(navController)

        // Обновление заголовка при старте
        binding.root.post {
            updateToolbarTitle(navController.currentDestination)
        }

        // Обработчик изменений destination
        navController.addOnDestinationChangedListener { _, destination, _ ->
            updateToolbarTitle(destination)
            updateUIForDestination(destination.id)
        }
    }

    /**
     * Обновляет заголовок Toolbar в зависимости от текущего destination.
     * @param destination Текущий пункт назначения навигации
     */
    private fun updateToolbarTitle(destination: NavDestination?) {
        Timber.d("Navigated to ${destination?.label}")
        binding.topAppBar.title = destination?.label ?: getString(R.string.app_name)
    }

    /**
     * Обновляет видимость UI элементов в зависимости от destination.
     * @param destinationId ID текущего пункта назначения
     */
    private fun updateUIForDestination(destinationId: Int) {
        when (destinationId) {
            R.id.searchFragment -> {
                showMainToolBar(false)
                showNavBarHideBackButton(true)
            }
            R.id.vacancyFragment -> {
                showMainToolBar(false)
                showNavBarHideBackButton(false)
            }
            R.id.favoritesFragment, R.id.teamFragment -> {
                showMainToolBar(true)
                showNavBarHideBackButton(true)
            }
            else -> {
                showMainToolBar(true)
                showNavBarHideBackButton(false)
                binding.topAppBar.setNavigationOnClickListener {
                    navController.popBackStack()
                }
            }
        }
    }

    /**
     * Управляет видимостью основного Toolbar.
     * @param isVisible Показывать или скрывать Toolbar
     */
    private fun showMainToolBar(isVisible: Boolean) {
        binding.topAppBar.isVisible = isVisible
    }

    /**
     * Управляет видимостью BottomNavigation и кнопки "Назад".
     * @param isVisible Показывать или скрывать BottomNavigation
     */
    private fun showNavBarHideBackButton(isVisible: Boolean) {
        binding.groupNavigationView.isVisible = isVisible
        supportActionBar?.setDisplayHomeAsUpEnabled(!isVisible)
    }
}

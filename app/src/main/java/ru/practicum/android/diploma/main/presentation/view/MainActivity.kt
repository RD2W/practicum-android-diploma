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
     * ViewBinding для activity_main.xml. Инициализируется лениво (по требованию)
     * для оптимизации производительности. Гарантирует безопасный доступ к элементам UI.
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
        // Установка корневого view из binding
        setContentView(binding.root)
        // Настройка Toolbar как ActionBar
        setSupportActionBar(binding.topAppBar)
        // Инициализация системы навигации
        setupNavigation()
    }

    /**
     * Настраивает систему навигации:
     * 1. Связывает BottomNavigationView с NavController
     * 2. Устанавливает начальный заголовок Toolbar
     * 3. Регистрирует слушатель изменений destination
     */
    private fun setupNavigation() {
        // Связывание BottomNavigation с NavController
        binding.bottomNavigation.setupWithNavController(navController)

        // Отложенное обновление заголовка (после отрисовки UI)
        binding.root.post {
            updateToolbarTitle(navController.currentDestination)
        }

        // Слушатель изменений destination
        navController.addOnDestinationChangedListener { _, destination, _ ->
            updateToolbarTitle(destination)
            updateUIForDestination(destination.id)
        }
    }

    /**
     * Обновляет заголовок Toolbar в соответствии с текущим destination
     * @param destination Текущий пункт назначения навигации
     */
    private fun updateToolbarTitle(destination: NavDestination?) {
        Timber.d("Navigation update: ${destination?.label}")
        // Установка заголовка из метки destination или стандартного значения
        binding.topAppBar.title = destination?.label ?: getString(R.string.app_name)
    }

    /**
     * Обновляет видимость UI элементов в зависимости от текущего экрана
     * @param destinationId ID текущего destination
     */
    private fun updateUIForDestination(destinationId: Int) {
        when (destinationId) {
            R.id.searchFragment -> {
                // Экран поиска - скрываем Toolbar, показываем нижнюю панель
                showMainToolBar(false)
                showNavBar(true)
                setupBackButton(showBackButton = false)
            }
            R.id.vacancyFragment -> {
                // Экран вакансии - скрываем оба бара, показываем кнопку "Назад"
                showMainToolBar(false)
                showNavBar(false)
                setupBackButton(showBackButton = true)
            }
            R.id.favoritesFragment, R.id.teamFragment -> {
                // Избранное и команда - стандартное отображение
                showMainToolBar(true)
                showNavBar(true)
                setupBackButton(showBackButton = false)
            }
            else -> {
                // Все остальные экраны - Toolbar виден, нижняя панель скрыта
                showMainToolBar(true)
                showNavBar(false)
                setupBackButton(showBackButton = true)
            }
        }
    }

    /**
     * Управляет видимостью основного Toolbar
     * @param isVisible Флаг видимости
     */
    private fun showMainToolBar(isVisible: Boolean) {
        binding.topAppBar.isVisible = isVisible
    }

    /**
     * Управляет видимостью нижней панели навигации
     * @param isVisible Флаг видимости
     */
    private fun showNavBar(isVisible: Boolean) {
        binding.groupNavigationView.isVisible = isVisible
    }

    /**
     * Настраивает кнопку "Назад" в Toolbar
     * @param showBackButton Флаг видимости и активности кнопки
     */
    private fun setupBackButton(showBackButton: Boolean) {
        // Установка видимости кнопки "Назад" в ActionBar
        supportActionBar?.setDisplayHomeAsUpEnabled(showBackButton)

        if (showBackButton) {
            // Установка обработчика нажатия - возврат на предыдущий экран
            binding.topAppBar.setNavigationOnClickListener {
                navController.popBackStack()
            }
        } else {
            // Удаление обработчика, если кнопка не нужна
            binding.topAppBar.setNavigationOnClickListener(null)
        }
    }
}

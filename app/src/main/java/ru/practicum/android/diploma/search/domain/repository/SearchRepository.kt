package ru.practicum.android.diploma.search.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.common.domain.model.Result
import ru.practicum.android.diploma.filter.domain.model.Filter
import ru.practicum.android.diploma.search.domain.model.SearchParameters
import ru.practicum.android.diploma.search.domain.model.SearchResult

/**
 * Интерфейс репозитория для работы с поиском вакансий и фильтрами.
 * Объединяет операции поиска и управления фильтрами.
 */
interface SearchRepository {
    /**
     * Выполняет поиск вакансий по заданным параметрам.
     * @param searchParameters Параметры поиска, включая:
     * - Поисковый запрос
     * - Номер страницы
     * - Примененные фильтры
     * @return Flow с результатом поиска:
     * - Result.Success с данными при успешном поиске
     * - Result.Error с информацией об ошибке при неудаче
     */
    suspend fun searchVacancies(searchParameters: SearchParameters): Flow<Result<SearchResult>>

    /**
     * Получает текущий активный фильтр.
     * @return Flow, который эмитит:
     * - null, если фильтр не установлен
     * - актуальный объект Filter при его изменении
     *
     * Примечание: реализация должна отслеживать изменения фильтра в реальном времени
     */
    fun getFilter(): Flow<Filter?>
}

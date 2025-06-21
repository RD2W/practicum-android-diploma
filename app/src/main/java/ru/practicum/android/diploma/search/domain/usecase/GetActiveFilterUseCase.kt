package ru.practicum.android.diploma.search.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.filter.domain.model.Filter

/**
 * UseCase для получения текущего активного фильтра.
 * Предоставляет потоковое (Flow) API для отслеживания изменений фильтров в реальном времени.
 *
 * Основные сценарии использования:
 * - Получение текущего состояния фильтров
 * - Реакция на изменения фильтров в UI
 * - Обновление состояния поиска при изменении фильтров
 */
interface GetActiveFilterUseCase {
    /**
     * Получает поток изменений активного фильтра.
     * @return Flow, который эмитит:
     * - null, если фильтр не установлен
     * - объект Filter, когда фильтр установлен или изменен
     */
    operator fun invoke(): Flow<Filter?>
}

package ru.practicum.android.diploma.vacancy.domain.model

/**
 * Обобщенный sealed-класс для представления результата операций с вакансиями.
 * Используется для инкапсуляции успешных результатов и различных типов ошибок.
 *
 * @param T Тип данных при успешном результате
 */
sealed class Result<out T> {
    /**
     * Успешный результат операции, содержащий данные.
     *
     * @property data Результат операции типа [T]
     * @param T Тип возвращаемых данных
     */
    data class Success<out T>(val data: T) : Result<T>()

    /**
     * Ошибка сервера при выполнении запроса.
     *
     * @property error Исключение, содержащее детали ошибки. Может быть null,
     * если информация об ошибке недоступна.
     */
    data class ServerError(val error: Throwable? = null) : Result<Nothing>()

    /**
     * Объект, представляющий ситуацию, когда запрошенные данные не найдены (HTTP 404).
     */
    object NotFound : Result<Nothing>()

    /**
     * Объект, представляющий отсутствие интернет-соединения.
     */
    object NoInternet : Result<Nothing>()
}

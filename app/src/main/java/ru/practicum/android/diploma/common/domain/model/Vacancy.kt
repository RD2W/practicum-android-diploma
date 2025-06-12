package ru.practicum.android.diploma.common.domain.model

/**
 * Модель данных краткой информации о вакансии для экрана "Поиск вакансий".
 *
 * @property id Уникальный идентификатор вакансии в системе HeadHunter (обязательное поле)
 * @property titleOfVacancy Название вакансии (обязательное поле). Пример: "Android-разработчик"
 * @property regionName Название региона размещения вакансии. Может быть null для вакансий без привязки к региону.
 *                     Пример: "Москва", "Санкт-Петербург"
 * @property salary Строковое представление зарплаты. Формат зависит от API HeadHunter.
 *                  Может содержать диапазон ("от 100 000 до 150 000 руб.") или конкретное значение.
 *                  Примеры: "от 150 000 руб.", "200 000 - 250 000 руб. до вычета налогов"
 * @property employerName Наименование компании-работодателя (обязательное поле).
 *                        Пример: "Яндекс", "СберТех"
 * @property employerLogoUrl Ссылка на логотип работодателя. Может быть null, если логотип не предоставлен.
 *                           Формат: HTTPS URL. Пример: "https://hh.ru/employer-logo/123.png"
 */

data class Vacancy(
    val id: String,
    val titleOfVacancy: String,
    val regionName: String? = null,
    val salary: String? = null,
    val employerName: String,
    val employerLogoUrl: String? = null,
)

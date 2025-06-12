package ru.practicum.android.diploma.vacancy.domain.model

/**
 * Модель данных вакансии.
 * Содержит основную информацию о вакансии, включая данные о работодателе, зарплате и требованиях.
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
 * @property experience Требуемый опыт работы. Формат зависит от API HeadHunter.
 *                      Примеры: "Нет опыта", "От 1 года до 3 лет", "От 3 лет"
 * @property employmentType Тип занятости. Примеры: "Полная занятость", "Частичная занятость", "Удаленная работа"
 * @property scheduleType График работы. Примеры: "Полный день", "Гибкий график", "Сменный график"
 * @property keySkills Строка с перечислением ключевых навыков, разделенных запятыми.
 *                     Пример: "Kotlin, Android SDK, Git, MVVM"
 * @property description HTML-форматированное описание вакансии. Содержит:
 *                       - Обязанности
 *                       - Требования
 *                       - Условия работы
 *                       Пример: "<p><strong>Обязанности:</strong></p><ul><li>Разработка...</li></ul>"
 * @property alternateUrl Прямая ссылка на вакансию на сайте HeadHunter для sharing/открытия в браузере.
 *                        Пример: "https://hh.ru/vacancy/12345678"
 */

data class VacancyDetails(
    val id: String,
    val titleOfVacancy: String,
    val regionName: String?,
    val salary: String?,
    val employerName: String,
    val employerLogoUrl: String?,
    val experience: String? = null,
    val employmentType: String? = null,
    val scheduleType: String? = null,
    val keySkills: String? = null,
    val description: String? = null,
    val alternateUrl: String? = null,
)

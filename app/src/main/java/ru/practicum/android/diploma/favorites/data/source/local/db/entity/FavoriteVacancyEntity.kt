package ru.practicum.android.diploma.favorites.data.source.local.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Сущность для хранения избранной вакансии в базе данных.
 *
 * @property id Уникальный идентификатор вакансии (PrimaryKey)
 * @property pictureOfCompanyLogoUri URI логотипа компании (может быть null)
 * @property titleOfVacancy Название вакансии
 * @property companyName Название компании
 * @property salary Зарплата (может быть null)
 * @property regionName Имя региона размещенной вакансии (может быть null)
 * @property address Адрес (может быть null)
 * @property experience Требуемый опыт (может быть null)
 * @property employmentType Тип занятости (может быть null)
 * @property scheduleType График работы (может быть null)
 * @property keySkills Ключевые навыки (может быть null)
 * @property vacancyDescription Описание вакансии (может быть null)
 * @property vacancyUrl URL вакансии на hh.ru (может быть null)
 * @property addedTimestamp Временная метка добавления в избранное (по умолчанию текущее время)
 */
@Entity(tableName = "favorite_vacancies")
data class FavoriteVacancyEntity(
    @PrimaryKey
    @ColumnInfo(name = "vacancy_id")
    val id: String,
    @ColumnInfo(name = "picture_of_company_logo_uri")
    val pictureOfCompanyLogoUri: String?,
    @ColumnInfo(name = "title_of_vacancy")
    val titleOfVacancy: String,
    @ColumnInfo(name = "company_name")
    val companyName: String,
    @ColumnInfo(name = "salary")
    val salary: String?,
    @ColumnInfo(name = "region")
    val regionName: String?,
    @ColumnInfo(name = "address")
    val address: String?,
    @ColumnInfo(name = "experience")
    val experience: String?,
    @ColumnInfo(name = "employment_type")
    val employmentType: String?,
    @ColumnInfo(name = "schedule_type")
    val scheduleType: String?,
    @ColumnInfo(name = "key_skills")
    val keySkills: String?,
    @ColumnInfo(name = "vacancy_description")
    val vacancyDescription: String?,
    @ColumnInfo(name = "vacancy_url")
    val vacancyUrl: String?,
    @ColumnInfo(name = "added_timestamp")
    val addedTimestamp: Long = System.currentTimeMillis(),
)

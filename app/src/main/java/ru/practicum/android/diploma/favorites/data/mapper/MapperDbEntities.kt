package ru.practicum.android.diploma.favorites.data.mapper

import ru.practicum.android.diploma.common.domain.model.Vacancy
import ru.practicum.android.diploma.favorites.data.source.local.db.entity.FavoriteVacancyEntity
import ru.practicum.android.diploma.vacancy.domain.model.VacancyDetails
import kotlin.collections.map

/**
 * Преобразует доменную модель VacancyDetails в сущность FavoriteVacancyEntity для сохранения в БД.
 *
 * @return FavoriteVacancyEntity Сущность для хранения в базе данных
 */
fun VacancyDetails.toFavoriteVacancyEntity() = FavoriteVacancyEntity(
    id = this.id,
    pictureOfCompanyLogoUri = this.employerLogoUrl,
    titleOfVacancy = this.titleOfVacancy,
    companyName = this.employerName,
    salary = this.salary,
    regionName = this.regionName,
    address = this.address,
    experience = this.experience,
    employmentType = this.employmentType,
    scheduleType = this.scheduleType,
    keySkills = this.keySkills,
    vacancyDescription = this.description,
    vacancyUrl = this.alternateUrl,
    addedTimestamp = System.currentTimeMillis(),
)

/**
 * Преобразует сущность FavoriteVacancyEntity в доменную модель VacancyDetails.
 *
 * @return VacancyDetails Доменная модель вакансии
 */
fun FavoriteVacancyEntity.toVacancyDetails() = VacancyDetails(
    id = this.id,
    titleOfVacancy = this.titleOfVacancy,
    regionName = this.regionName,
    address = this.address,
    salary = this.salary,
    employerName = this.companyName,
    employerLogoUrl = this.pictureOfCompanyLogoUri,
    experience = this.experience,
    employmentType = this.employmentType,
    scheduleType = this.scheduleType,
    keySkills = this.keySkills,
    description = this.vacancyDescription,
    alternateUrl = this.vacancyUrl,
)

/**
 * Преобразует сущность FavoriteVacancyEntity в доменную модель Vacancy.
 *
 * @return Vacancy Доменная модель вакансии
 */
fun FavoriteVacancyEntity.toVacancy() = Vacancy(
    id = this.id,
    titleOfVacancy = this.titleOfVacancy,
    regionName = this.regionName,
    salary = this.salary,
    employerName = this.companyName,
    employerLogoUrl = this.pictureOfCompanyLogoUri,
)

/**
 * Преобразует список сущностей FavoriteVacancyEntity в список доменных моделей Vacancy.
 *
 * @return List<Vacancy> Список доменных моделей вакансий
 */
fun List<FavoriteVacancyEntity>.toVacancyList(): List<Vacancy> {
    return this.map { vacancyEntity -> vacancyEntity.toVacancy() }
}

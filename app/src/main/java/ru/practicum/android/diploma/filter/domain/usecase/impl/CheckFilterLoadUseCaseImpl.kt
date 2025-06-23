package ru.practicum.android.diploma.filter.domain.usecase.impl

import ru.practicum.android.diploma.filter.domain.repository.CheckFilterLoadRepository
import ru.practicum.android.diploma.filter.domain.usecase.CheckFilterLoadUseCase

class CheckFilterLoadUseCaseImpl(
    private val checkFilterLoadRepository: CheckFilterLoadRepository
) : CheckFilterLoadUseCase {
    override fun execute(): Boolean {
        return checkFilterLoadRepository.checkFilterLoad()
    }
}

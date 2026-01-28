package com.example.roomsathi

import com.example.roomsathi.repository.PropertyRepoImpl
import com.example.roomsathi.viewmodel.DashboardViewModel
import com.example.roomsathi.model.PropertyModel
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.*

class DashboardUnitTest {
    private lateinit var viewModel: DashboardViewModel
    private lateinit var repository: PropertyRepoImpl

    @Before
    fun setup() {
        // Initialize the mock
        repository = mock()
        viewModel = DashboardViewModel(repository)
    }

    @Test
    fun `fetchAllProperties should call repository getAllProperties`() {
        // Act: Call the function
        viewModel.fetchAllProperties()

        // Assert: Use atLeastOnce() or times(2) to handle the multiple calls
        verify(repository, atLeastOnce()).getAllProperties(any())
    }
}
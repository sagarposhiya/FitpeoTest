package com.example.fitpeoassignment.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.fitpeoassignment.utils.NetworkStatus
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito

@RunWith(JUnit4::class)
class DataViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private var viewModel = UserDataActivityViewModel()

    @Test
    fun testNull() {
        MatcherAssert.assertThat(viewModel.data2, CoreMatchers.nullValue())
        MatcherAssert.assertThat(viewModel.networkLiveData, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(viewModel.statusLiveData, CoreMatchers.notNullValue())
        Mockito.verify(viewModel.data2, Mockito.never())
        Mockito.verify(viewModel.networkLiveData, Mockito.never())
        Mockito.verify(viewModel.statusLiveData, Mockito.never())
    }

    @Test
    fun doNotFetchWithoutObserverOnConnectionChange() {
        viewModel.statusLiveData.value = NetworkStatus.SUCCESS
        Mockito.verify(viewModel.statusLiveData, Mockito.never())
    }
}
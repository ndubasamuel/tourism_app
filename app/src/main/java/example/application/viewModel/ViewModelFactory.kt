package example.application.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import example.application.model.repository.Repository

class ViewModelFactory (private val repository: Repository): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(ViewModelClass::class.java) -> ViewModelClass(repository as Repository) as T

            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
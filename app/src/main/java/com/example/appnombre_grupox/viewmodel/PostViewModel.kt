package viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.model.Post
import repository.PostRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PostViewModel : ViewModel() {

    private val repository = PostRepository()

    private val _postList =
        MutableStateFlow<List<Post>>(emptyList())

    val postList: StateFlow<List<Post>> = _postList

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        fetchPosts()
    }

    fun fetchPosts() {
        viewModelScope.launch {
            try {
                _error.value = null
                _postList.value = repository.getPosts()
            } catch (e: Exception) {
                _error.value = "Error al cargar: ${e.message}"
            }
        }
    }

    fun createPost(title: String, body: String) {
        viewModelScope.launch {
            try {
                _error.value = null

                val post = Post(
                    userId = 1,
                    id = 0,
                    title = title,
                    body = body
                )

                val created = repository.createPost(post)

                _postList.value =
                    listOf(created) + _postList.value

            } catch (e: Exception) {
                _error.value = "Error al crear: ${e.message}"
            }
        }
    }

    fun updatePost(post: Post) {
        viewModelScope.launch {
            try {
                _error.value = null
                val updated = repository.updatePost(post)

                _postList.value = _postList.value.map {
                    if (it.id == updated.id) updated else it
                }
            } catch (e: Exception) {
                _error.value = "Error al actualizar: ${e.message}"
            }
        }
    }

    fun deletePost(id: Int) {
        viewModelScope.launch {
            try {
                _error.value = null
                repository.deletePost(id)

                _postList.value =
                    _postList.value.filter { it.id != id }

            } catch (e: Exception) {
                _error.value = "Error al eliminar: ${e.message}"
            }
        }
    }
}

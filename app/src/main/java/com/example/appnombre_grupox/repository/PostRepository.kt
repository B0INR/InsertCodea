
package repository

import data.model.Post
import data.remote.RetrofitInstance

class PostRepository {

    suspend fun getPosts(): List<Post> {
        return RetrofitInstance.api.getPosts()
    }

    suspend fun createPost(post: Post): Post {
        return RetrofitInstance.api.createPost(post)
    }

    suspend fun updatePost(post: Post): Post {
        return RetrofitInstance.api.updatePost(
            post.id,
            post
        )
    }

    suspend fun deletePost(id: Int) {
        RetrofitInstance.api.deletePost(id)
    }
}

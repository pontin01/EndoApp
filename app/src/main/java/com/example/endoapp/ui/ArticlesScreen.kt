package com.example.endoapp

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

data class Article(
    val id: Int,
    val title: String,
    val body: String,
    val imageRes: Int,          // swap for an image URL once you load from the web
    val postedDate: LocalDate,
    val likes: Int
)

@RequiresApi(Build.VERSION_CODES.O)
private val dateFormatter = DateTimeFormatter.ofPattern("d MMM yyyy", Locale.getDefault())

// ---------- List adapter ----------
class ArticleAdapter(
    private val items: List<Article>,
    private val onClick: (Article) -> Unit
) : RecyclerView.Adapter<ArticleAdapter.VH>() {

    class VH(v: View) : RecyclerView.ViewHolder(v) {
        val card: View = v.findViewById(R.id.articleCard)
        val image: ImageView = v.findViewById(R.id.articleThumbnail)
        val title: TextView = v.findViewById(R.id.articleTitle)
        val date: TextView = v.findViewById(R.id.articleDate)
        val likes: TextView = v.findViewById(R.id.articleLikes)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(LayoutInflater.from(parent.context).inflate(R.layout.item_article, parent, false))

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(h: VH, position: Int) {
        val a = items[position]
        h.image.setImageResource(a.imageRes)
        h.title.text = a.title
        h.date.text = "Posted ${a.postedDate.format(dateFormatter)}"
        h.likes.text = "♥ ${a.likes}"
        h.card.setOnClickListener { onClick(a) }
    }

    override fun getItemCount() = items.size
}

// ---------- Articles list screen ----------
@Composable
fun ArticlesScreen(
    articles: List<Article>,
    onArticleClick: (Article) -> Unit,
    onChatClick: () -> Unit,
    onForumClick: () -> Unit,
    onHomeClick: () -> Unit,
    onArticlesClick: () -> Unit,
    onProfileClick: () -> Unit,
) {
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            val view = LayoutInflater.from(context).inflate(R.layout.articles, null)

            view.findViewById<ImageButton>(R.id.chatButton).setOnClickListener { onChatClick() }
            view.findViewById<ImageButton>(R.id.forumButton).setOnClickListener { onForumClick() }
            view.findViewById<ImageButton>(R.id.homeButton).setOnClickListener { onHomeClick() }
            view.findViewById<ImageButton>(R.id.articlesButton).setOnClickListener { onArticlesClick() }
            view.findViewById<ImageButton>(R.id.profileButton).setOnClickListener { onProfileClick() }

            view.findViewById<RecyclerView>(R.id.articlesRecyclerView).apply {
                layoutManager = LinearLayoutManager(context)
                adapter = ArticleAdapter(articles, onArticleClick)
            }

            view
        }
    )
}

// ---------- Article detail screen ----------
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ArticleDetailScreen(
    article: Article,
    onBackClick: () -> Unit,
) {
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            val view = LayoutInflater.from(context).inflate(R.layout.article_detail, null)

            view.findViewById<View>(R.id.backButton).setOnClickListener { onBackClick() }
            view.findViewById<ImageView>(R.id.articleImage).setImageResource(article.imageRes)
            view.findViewById<TextView>(R.id.articleTitle).text = article.title
            view.findViewById<TextView>(R.id.articleDate).text =
                "Posted ${article.postedDate.format(dateFormatter)}"
            view.findViewById<TextView>(R.id.articleLikes).text = "♥ ${article.likes}"
            view.findViewById<TextView>(R.id.articleBody).text = article.body

            view
        }
    )
}

@RequiresApi(Build.VERSION_CODES.O)
val sampleArticles = listOf(
    Article(1, "Understanding endometriosis: symptoms, diagnosis and treatment",
        "Long article text here...", R.drawable.map, LocalDate.now().minusDays(3), 42),
    Article(2, "Managing chronic pain day to day",
        "Long article text here...", R.drawable.map, LocalDate.now().minusDays(10), 27),
)

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ArticlesFlow(navController: NavHostController) {
    var selected by remember { mutableStateOf<Article?>(null) }

    val article = selected
    if (article == null) {
        ArticlesScreen(
            articles = sampleArticles,
            onArticleClick = { selected = it },
            onChatClick = { navController.navigate("chat") },
            onForumClick = { navController.navigate("login") },
            onHomeClick = { navController.navigate("home") },
            onArticlesClick = { navController.navigate("articles") },
            onProfileClick = { navController.navigate("profile") },
        )
    } else {
        BackHandler { selected = null }
        ArticleDetailScreen(article = article, onBackClick = { selected = null })
    }
}
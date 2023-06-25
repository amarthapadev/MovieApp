package com.example.movielist.ui.details

import android.content.res.Configuration
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.movielist.R
import com.example.movielist.data.source.database.BookmarkMovieDao
import com.example.movielist.data.source.database.MovieDao
import com.example.movielist.data.source.database.entity.BookmarkMovieEntity
import com.example.movielist.data.source.database.entity.MovieEntity
import com.example.movielist.databinding.ActivityMovieDetailBinding
import com.example.movielist.ui.main.MainActivity
import com.example.movielist.ui.model.Credit
import com.example.movielist.ui.model.SimilarMovie
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class MovieDetailActivity : AppCompatActivity() {

    @Inject
    lateinit var bookmarkMovieDao: BookmarkMovieDao

    @Inject
    lateinit var movieDao: MovieDao

    private lateinit var binding: ActivityMovieDetailBinding

    private val detailViewModel: DetailViewModel by viewModels()

    private var creditList = ArrayList<Credit>()
    private var similarMovieList = ArrayList<SimilarMovie>()

    private lateinit var creditsAdapter: CreditsAdapter
    private lateinit var similarMoviesAdapter: SimilarMoviesAdapter

    private var movieId = -1
    private var movieName = ""
    private var releaseDate = ""
    private var posterUrl = ""
    private var isBookmarked = false

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    override fun onCreate(savedInstanceState: Bundle?) {

        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val collapsingToolbar = findViewById<CollapsingToolbarLayout>(R.id.collapsing_toolbar)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val isDarkThemeOn = this.resources.configuration.uiMode and
                Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES

        if (isDarkThemeOn) {

            binding.collapsingToolbar.setContentScrimColor(
                ContextCompat.getColor(
                    this,
                    R.color.dark_surface
                )
            )
        } else {

            binding.collapsingToolbar.setContentScrimColor(
                ContextCompat.getColor(
                    this,
                    R.color.light_surface
                )
            )
        }

        binding.shimmerLayout.startShimmer()

        val position = intent.getIntExtra("position", -1)
        movieId = intent.getIntExtra("movieId", -1)

        if (position != -1 || movieId != -1) {

            movieId = if (movieId != -1) movieId else
                MainActivity.MovieListHolder.movieList[position].movieId

            val movieImage = binding.collapsingToolbarImageView

            detailViewModel.getDetails(movieId = movieId).observe(this) { movieDetails ->

                movieDetails?.let {

                    binding.shimmerLayout.apply {
                        stopShimmer()
                        visibility = View.GONE
                    }

                    binding.cvSynopsis.visibility = View.VISIBLE

                    Glide.with(this)
                        .load(movieDetails.getBackDropUrl())
                        .listener(object : RequestListener<Drawable> {
                            override fun onLoadFailed(
                                e: GlideException?,
                                model: Any?,
                                target: Target<Drawable>?,
                                isFirstResource: Boolean
                            ): Boolean {
                                binding.shimmerViewContainer.stopShimmer() // Stop the shimmer animation if the image loading fails
                                binding.shimmerViewContainer.setShimmer(null) // Optionally, clear the shimmer effect completely
                                return false
                            }

                            override fun onResourceReady(
                                resource: Drawable?,
                                model: Any?,
                                target: Target<Drawable>?,
                                dataSource: DataSource?,
                                isFirstResource: Boolean
                            ): Boolean {
                                binding.shimmerViewContainer.stopShimmer() // Stop the shimmer animation when the image is loaded
                                binding.shimmerViewContainer.setShimmer(null) // Optionally, clear the shimmer effect completely
                                return false
                            }
                        })
                        .into(movieImage)

                    collapsingToolbar.title = movieDetails.title
                    binding.tvSynopsis.text = movieDetails.overview

                    movieName = movieDetails.title
                    releaseDate = movieDetails.releaseDate
                    posterUrl = movieDetails.posterPath
                }
            }

            detailViewModel.getReview(movieId = movieId).observe(this) { movieReview ->

                movieReview?.let {

                    binding.shimmerLayout.apply {
                        stopShimmer()
                        visibility = View.GONE
                    }

                    binding.cvReview.visibility = View.VISIBLE

                    binding.tvName.text = it.name
                    binding.tvReview.text = it.review
                }
            }

            binding.rvCredits.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            creditsAdapter = CreditsAdapter(creditList)
            binding.rvCredits.adapter = creditsAdapter

            binding.rvCredits.isNestedScrollingEnabled = false

            detailViewModel.getCredits(movieId = movieId).observe(this) { credits ->

                binding.shimmerLayout.apply {
                    stopShimmer()
                    visibility = View.GONE
                }

                binding.rvCredits.visibility = View.VISIBLE

                creditList.clear()
                creditList.addAll(credits)

                creditsAdapter.notifyDataSetChanged()
            }

            binding.rvSimilarMovies.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            similarMoviesAdapter = SimilarMoviesAdapter(similarMovieList)
            binding.rvSimilarMovies.adapter = similarMoviesAdapter

            binding.rvSimilarMovies.isNestedScrollingEnabled = false

            detailViewModel.getSimilarMovies(movieId = movieId)
                .observe(this) { similarMovies ->

                    val movies = similarMovies.filter {
                        
                        it.getPosterPath().isNotEmpty()
                    }

                    binding.shimmerLayout.apply {
                        stopShimmer()
                        visibility = View.GONE
                    }

                    binding.rvSimilarMovies.visibility = View.VISIBLE

                    similarMovieList.clear()
                    similarMovieList.addAll(movies)

                    similarMoviesAdapter.notifyDataSetChanged()
                }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.menu_detail, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {

            android.R.id.home -> {

                onBackPressedDispatcher.onBackPressed()

                true
            }

            R.id.action_bookmark -> {

                isBookmarked = if (!isBookmarked) {

                    item.setIcon(R.drawable.bookmark_1)

                    scope.launch(Dispatchers.IO) {

                        bookmarkMovieDao.insertMovie(BookmarkMovieEntity(movieId, movieName))

                        val bookmarkMovieIds =
                            MainActivity.MovieListHolder.bookmarkedMovies.map { it.movieId }

                        if (movieId !in bookmarkMovieIds) {

                            if (movieId > 0
                                && movieName.isNotEmpty()
                                && releaseDate.isNotEmpty()
                                && posterUrl.isNotEmpty()
                            ) {

                                movieDao.insertMovie(
                                    MovieEntity(
                                        100, // page 100 is for bookmarks
                                        movieId,
                                        movieName,
                                        releaseDate,
                                        posterUrl
                                    )
                                )

                                Snackbar.make(binding.root, "Bookmarked", Snackbar.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    }

                    true
                } else {

                    item.setIcon(R.drawable.bookmark_2)

                    scope.launch(Dispatchers.IO) {

                        bookmarkMovieDao.deleteMovie(BookmarkMovieEntity(movieId, movieName))
                    }

                    false
                }

                return true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()

        val list = MainActivity.MovieListHolder.bookmarkedMovies.filter {

            it.movieId == movieId
        }

        isBookmarked = list.isNotEmpty()
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {

        if (isBookmarked)
            menu.findItem(R.id.action_bookmark).setIcon(R.drawable.bookmark_1)
        else
            menu.findItem(R.id.action_bookmark).setIcon(R.drawable.bookmark_2)

        return super.onPrepareOptionsMenu(menu)
    }
}
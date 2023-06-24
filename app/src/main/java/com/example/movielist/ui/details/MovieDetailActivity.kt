package com.example.movielist.ui.details

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
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

        val position = intent.getIntExtra("position", -1)
        movieId = intent.getIntExtra("movieId", -1)

        if (position != -1 || movieId != -1) {

            movieId = if (movieId != -1) movieId else
                MainActivity.MovieListHolder.movieList[position].movieId

            val movieImage = binding.collapsingToolbarImageView

            detailViewModel.getDetails(movieId = movieId).observe(this) { movieDetails ->

                movieDetails?.let {

                    Glide.with(this)
                        .load(movieDetails.getBackDropUrl())
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

                    binding.tvName.text = it.name
                    binding.tvReview.text = it.review
                }
            }

            binding.rvCredits.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            creditsAdapter = CreditsAdapter(creditList)
            binding.rvCredits.adapter = creditsAdapter

            detailViewModel.getCredits(movieId = movieId).observe(this) { credits ->

                creditList.clear()
                creditList.addAll(credits)

                creditsAdapter.notifyDataSetChanged()
            }

            binding.rvSimilarMovies.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            similarMoviesAdapter = SimilarMoviesAdapter(similarMovieList)
            binding.rvSimilarMovies.adapter = similarMoviesAdapter

            detailViewModel.getSimilarMovies(movieId = movieId)
                .observe(this) { similarMovies ->
                    similarMovieList.clear()
                    similarMovieList.addAll(similarMovies)

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
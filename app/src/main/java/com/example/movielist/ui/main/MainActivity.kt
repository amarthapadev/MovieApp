package com.example.movielist.ui.main

import android.app.Dialog
import android.content.Context
import android.content.res.Configuration
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioGroup
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movielist.R
import com.example.movielist.databinding.ActivityMainBinding
import com.example.movielist.ui.model.Movie
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Locale

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding
    private lateinit var movieItemAdapter: MovieItemAdapter

    private lateinit var sortDialog: Dialog

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    private var showbookmarks = false

    object MovieListHolder {

        val swapList: ArrayList<Movie> = ArrayList()
        val movieList: ArrayList<Movie> = ArrayList()
        val bookmarkedMovies: ArrayList<Movie> = ArrayList()
    }

    fun Context.isDarkThemeOn(): Boolean {

        return resources.configuration.uiMode and
                Configuration.UI_MODE_NIGHT_MASK == UI_MODE_NIGHT_YES
    }

    private suspend fun getBookmarkedMovies(): List<Movie> = withContext(Dispatchers.IO) {
        
        return@withContext mainViewModel.getBookMarkedMovies()
    }

    private fun handleSortOption(selectedOptionId: Int) {
        when (selectedOptionId) {

            R.id.radioButtonNames -> {

                MovieListHolder.movieList.sortBy {

                    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

                    val date = dateFormat.parse(it.releaseDate)
                    date?.time
                }
                // Perform any additional actions for sorting by names
            }

            R.id.radioButtonDates -> {

                MovieListHolder.movieList.sortByDescending {

                    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

                    val date = dateFormat.parse(it.releaseDate)
                    date?.time
                }
                // Perform any additional actions for sorting by dates
            }
        }

        // Update the RecyclerView adapter with the sorted list
        movieItemAdapter.notifyDataSetChanged()
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        binding.rvMainActivity.layoutManager = LinearLayoutManager(baseContext)

        movieItemAdapter = MovieItemAdapter(MovieListHolder.movieList)

        binding.rvMainActivity.adapter = movieItemAdapter

        mainViewModel.movieList.observe(this) { list ->

            MovieListHolder.movieList.clear()
            MovieListHolder.movieList.addAll(list)

            movieItemAdapter.notifyDataSetChanged()
        }


        sortDialog = Dialog(this@MainActivity)

        sortDialog.setContentView(R.layout.dialog_sort)
        sortDialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        sortDialog.setCancelable(false)

        val sortButton = sortDialog.findViewById<Button>(R.id.buttonSort)
        sortButton.setOnClickListener {

            sortDialog.dismiss()

            val radioGroup = sortDialog.findViewById<RadioGroup>(R.id.radioGroupSortOptions)
            val selectedOptionId = radioGroup.checkedRadioButtonId

            handleSortOption(selectedOptionId)
        }
    }

    override fun onResume() {
        super.onResume()

        scope.launch(Dispatchers.IO) {

            MovieListHolder.bookmarkedMovies.clear()
            MovieListHolder.bookmarkedMovies.addAll(getBookmarkedMovies())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {

            R.id.action_settings -> true

            R.id.action_sort -> {

                sortDialog.show()

                return true
            }

            R.id.action_bookmark -> {

                if (!showbookmarks) {

                    showbookmarks = true

                    item.setIcon(R.drawable.bookmark_1)

                    MovieListHolder.swapList.clear()
                    MovieListHolder.swapList.addAll(MovieListHolder.movieList)

                    MovieListHolder.movieList.clear()
                    MovieListHolder.movieList.addAll(MovieListHolder.bookmarkedMovies)

                    movieItemAdapter.notifyDataSetChanged()
                } else {

                    showbookmarks = false

                    item.setIcon(R.drawable.bookmark_2)

                    MovieListHolder.movieList.clear()
                    MovieListHolder.movieList.addAll(MovieListHolder.swapList)

                    movieItemAdapter.notifyDataSetChanged()
                }

                return true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}
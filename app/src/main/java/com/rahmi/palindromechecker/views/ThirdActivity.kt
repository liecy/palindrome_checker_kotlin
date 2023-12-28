package com.rahmi.palindromechecker.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.rahmi.palindromechecker.data.User
import androidx.activity.viewModels
import androidx.paging.LoadState
import com.rahmi.palindromechecker.R
import com.rahmi.palindromechecker.adapter.UserAdapter
import com.rahmi.palindromechecker.databinding.ActivityThirdBinding

class ThirdActivity : AppCompatActivity() {

    private var isRefreshing = false

    private val viewModel by viewModels<ThirdViewModel> {
        ViewModelFactory.getInstance()
    }

    private lateinit var binding: ActivityThirdBinding
    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThirdBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.third_screen)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back)

        setupRecyclerView()
        setupSwipeRefreshLayout()
        setupLoadStateListener()
        observeViewModel()

        showLoading(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupRecyclerView() {
        adapter = UserAdapter(object : UserAdapter.OnItemClickListener {
            override fun onUserItemClick(user: User) {
                onUserSelected(user)
            }
        })
        binding.rvUser.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@ThirdActivity.adapter
        }
    }

    private fun observeViewModel() {
        viewModel.users.observe(this) { pagingData ->
            adapter.submitData(lifecycle, pagingData)
        }
    }

    private fun onUserSelected(user: User) {
        val data = Intent()
        data.putExtra("SELECTED_USER", "${user.firstName} ${user.lastName}")
        setResult(RESULT_OK, data)
        finish()
    }

    private fun setupSwipeRefreshLayout() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            isRefreshing = true
            adapter.refresh()
        }
    }

    private fun setupLoadStateListener() {
        adapter.addLoadStateListener { loadState ->
            if (loadState.refresh is LoadState.NotLoading) {
                if (!isRefreshing) {
                    showLoading(false)
                }
                binding.swipeRefreshLayout.isRefreshing = false
                isRefreshing = false
            } else if (loadState.refresh is LoadState.Loading) {
                if (!isRefreshing) {
                    showLoading(true)
                }
            }

            val errorState = loadState.refresh as? LoadState.Error
            errorState?.let {
                showToast(it.error.localizedMessage ?: "An error occurred")
                binding.swipeRefreshLayout.isRefreshing = false
                isRefreshing = false
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
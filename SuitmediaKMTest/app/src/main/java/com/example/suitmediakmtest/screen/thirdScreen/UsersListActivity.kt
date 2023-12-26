package com.example.suitmediakmtest.screen.thirdScreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.suitmediakmtest.adapter.DataListAdapter
import com.example.suitmediakmtest.data.api.ApiConfig
import com.example.suitmediakmtest.data.response.DataItem
import com.example.suitmediakmtest.data.response.Response
import com.example.suitmediakmtest.databinding.ActivityUsersListBinding
import com.example.suitmediakmtest.screen.secondScreen.SelectedUserActivity
import retrofit2.Call
import retrofit2.Callback

class UsersListActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityUsersListBinding
    private lateinit var adapter: DataListAdapter
    private var page: Int = 1
    private var perPage: Int = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUsersListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener(this)

        getData(page.toString(), perPage.toString())
    }

    private fun getData(page: String, per_page: String) {
        try {
            showProgressBar(true)
            val client = ApiConfig.getApiService().getData(page, per_page)
            client.enqueue(object : Callback<Response> {
                override fun onResponse(
                    call: Call<Response>,
                    response: retrofit2.Response<Response>,
                ) {
                    if (response.isSuccessful) {
                        showProgressBar(false)
                        val responseBody = response.body()
                        if (responseBody != null) {
                            setAdapter(responseBody.data)
                        }
                    }
                }

                override fun onFailure(call: Call<Response>, t: Throwable) {
                    Log.d("ERROR", t.toString())
                }

            })
        } catch (e: Exception) {
            Log.d("ERROR", e.toString())
        }
    }

    private fun setAdapter(dataList: List<DataItem?>?) {
        adapter = DataListAdapter()
        adapter.submitList(dataList)

        val layoutManager = LinearLayoutManager(this)
        binding.rvUsersList.layoutManager = layoutManager

        binding.rvUsersList.adapter = adapter

        if (dataList.isNullOrEmpty()) {
            binding.tvEmpty.visibility = View.VISIBLE
        }

        adapter.setOnItemClickCallback(object : DataListAdapter.OnItemClickCallback {
            override fun onItemClicked(dataList: DataItem) {
                val username = intent.getStringExtra(USER_NAME)
                val intent = Intent(this@UsersListActivity, SelectedUserActivity::class.java)
                intent.putExtra(SelectedUserActivity.SELECTED_USER_NAME, "${dataList.firstName} ${dataList.lastName}")
                intent.putExtra(SelectedUserActivity.USER_NAME, username)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
                finish()
            }

        })

        binding.rvUsersList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (layoutManager.canScrollVertically() &&
                    !binding.rvUsersList.canScrollVertically(1)
                    && dy > 0) {
                    if (layoutManager.itemCount > 7) {
                        page += 1
                        getData("$page", "$perPage")
                    }
                } else if (layoutManager.canScrollVertically() &&
                    !binding.rvUsersList.canScrollVertically(-1)
                    && dy < 0) {
                    page -= 1
                    getData("$page", "$perPage")
                }
            }
        })
    }

    private fun showProgressBar(isRunning: Boolean) {
        if (isRunning) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onClick(view: View?) {
        when(view) {
            binding.btnBack -> finish()
        }
    }

    companion object {
        const val USER_NAME = "user_name"
    }
}
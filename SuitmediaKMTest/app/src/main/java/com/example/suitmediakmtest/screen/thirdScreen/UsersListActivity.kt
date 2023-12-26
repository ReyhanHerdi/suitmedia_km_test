package com.example.suitmediakmtest.screen.thirdScreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUsersListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener(this)

        getData("1", "10")
    }

    private fun getData(page: String, per_page: String) {
        try {
            val client = ApiConfig.getApiService().getData(page, per_page)
            client.enqueue(object : Callback<Response> {
                override fun onResponse(
                    call: Call<Response>,
                    response: retrofit2.Response<Response>,
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            setData(responseBody.data)
                        } else {
                            Log.d("NO DATA", "null")
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

    private fun setData(dataList: List<DataItem?>?) {
        adapter = DataListAdapter()
        adapter.submitList(dataList)

        val layoutManager = LinearLayoutManager(this)
        binding.rvUsersList.layoutManager = layoutManager

        binding.rvUsersList.adapter = adapter

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
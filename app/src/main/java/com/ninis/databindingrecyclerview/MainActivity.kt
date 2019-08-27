package com.ninis.databindingrecyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ninis.databindingrecyclerview.base.BaseRecyclerView
import com.ninis.databindingrecyclerview.data.AlbumItemModel
import com.ninis.databindingrecyclerview.data.MainViewModel
import com.ninis.databindingrecyclerview.databinding.ActivityMainBinding
import com.ninis.databindingrecyclerview.databinding.LayoutAlbumItemRowBinding
import com.ninis.databindingrecyclerview.network.ApiService
import com.ninis.databindingrecyclerview.network.RetrofitManager
import io.reactivex.functions.Consumer

class MainActivity : AppCompatActivity() {

    @Suppress("UNCHECKED_CAST")
    val viewModel by lazy {
        ViewModelProviders.of(this,
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                    return MainViewModel(RetrofitManager.getRetrofitService(ApiService::class.java)) as T
                }

            }
            ).get(MainViewModel::class.java)
    }

    private val mainAdapter = BaseRecyclerView.Adapter<AlbumItemModel, LayoutAlbumItemRowBinding>(
        layoutResId = R.layout.layout_album_item_row,
        bindingVariableId = BR.dataModel,
        callBack = Consumer {
            Toast.makeText(this@MainActivity,
                String.format("Selected ID : %d", it.id),
                Toast.LENGTH_SHORT).show()
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.rvMainList.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            isNestedScrollingEnabled = false
            adapter = mainAdapter
        }

        DividerItemDecoration(binding.rvMainList.context, LinearLayout.VERTICAL).apply {
            binding.rvMainList.addItemDecoration(this)
        }

        viewModel.getData()
    }
}

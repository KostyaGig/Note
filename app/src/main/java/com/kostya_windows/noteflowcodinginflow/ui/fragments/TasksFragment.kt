package com.kostya_windows.noteflowcodinginflow.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.*

import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.kostya_windows.noteflowcodinginflow.R
import com.kostya_windows.noteflowcodinginflow.adapters.TaskAdapter
import com.kostya_windows.noteflowcodinginflow.data.datastore.SortOrder
import com.kostya_windows.noteflowcodinginflow.databinding.FragmentTasksBinding
import com.kostya_windows.noteflowcodinginflow.other.onQueryTextChanged
import com.kostya_windows.noteflowcodinginflow.ui.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@AndroidEntryPoint
class TasksFragment :Fragment(R.layout.fragment_tasks){

    private val TAG = "MyTag"

    @Inject
    lateinit var viewModel: MainViewModel

    private val taskAdapter = TaskAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentTasksBinding.bind(view)


        binding.apply {
            taskRecyclerView.apply {
                adapter = taskAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }
        }

        setHasOptionsMenu(true)
    }



    override fun onStart() {
        super.onStart()

        viewModel.tasks.observe(this, Observer {
            taskAdapter.submitList(it)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu,menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        searchView.onQueryTextChanged { query->
            //update searchQuery
            viewModel.searchTasks(query)
        }

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){

            R.id.action_search -> {

            }


            R.id.action_sort_by_name ->{
                viewModel.setOrder(SortOrder.BY_NAME)
            }

            R.id.action_sort_by_date ->{
                viewModel.setOrder(SortOrder.BY_DATE)
            }

            R.id.action_hide_completed_tasks ->{
                item.isChecked = !item.isChecked
                viewModel.setHideCompleted(item.isChecked)
            }

            R.id.action_delete_all_completed_tasks ->{

            }

        }

        return true
    }
}

package vantutrieu97.myapplication.views

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_list.*
import vantutrieu97.myapplication.R
import vantutrieu97.myapplication.viewmodel.AnimalsListViewModel
import vantutrieu97.myapplication.views.adapters.AnimalsListAdapter

/**
 * A simple [Fragment] subclass.
 */
class ListFragment : Fragment() {
    private val TAG = "ViewModel_Flow"
    private lateinit var viewModel: AnimalsListViewModel
    private lateinit var animalsListAdapter: AnimalsListAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.i(TAG, "onCreateView")
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.i(TAG, "onViewCreated")
        super.onViewCreated(view, savedInstanceState)
        animalsListAdapter = AnimalsListAdapter(arrayListOf())
        viewModel = ViewModelProviders.of(this).get(AnimalsListViewModel::class.java)
        viewModel.refresh()

        animalsRcV.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = animalsListAdapter
        }

        refreshLayout.setOnRefreshListener {
            animalsRcV.visibility = View.GONE
            errorsTxt.visibility = View.GONE
            loadingView.visibility = View.INVISIBLE
            viewModel.refresh()
            refreshLayout.isRefreshing = false
        }
        observeViewModel()
    }

    private fun observeViewModel() {

        Log.i(TAG, "observeViewModel")
        viewModel.animals.observe(viewLifecycleOwner, Observer { animalsListResult ->
            animalsListResult?.let {

                Log.i(TAG, "animalsListResult != null")
                animalsRcV.visibility = View.VISIBLE
//                loadingView.visibility = View.GONE
                animalsListAdapter.updateAnimalsList(animalsListResult)
            }
        })

        viewModel.animalLoadError.observe(viewLifecycleOwner, Observer { isError ->
            isError?.let {

                Log.i(TAG, "isError != null ---> $isError")
                errorsTxt.visibility = if (it) View.VISIBLE else View.GONE
            }

        })

        viewModel.loading.observe(viewLifecycleOwner, Observer { isLoading ->
            isLoading?.let {

                Log.i(TAG, "isLoading != null --> $isLoading")
                loadingView.visibility = if (it) View.VISIBLE else View.GONE
            }
        })
    }
}

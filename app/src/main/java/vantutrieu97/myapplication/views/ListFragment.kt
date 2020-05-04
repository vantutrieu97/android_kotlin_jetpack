package vantutrieu97.myapplication.views

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
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
    private val TAG2 = "LIFE_CYCLE"
    private lateinit var viewModel: AnimalsListViewModel
    private lateinit var animalsListAdapter: AnimalsListAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.i(TAG, "onCreateView")
        Log.i(TAG2, "onCreateView")
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.i(TAG, "onViewCreated")
        Log.i(TAG2, "onViewCreated")
        super.onViewCreated(view, savedInstanceState)
        animalsListAdapter = AnimalsListAdapter(arrayListOf())
        viewModel = ViewModelProviders.of(this).get(AnimalsListViewModel::class.java)

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

    override fun onResume() {
        super.onResume()
        Log.i(TAG2, "onResume")
        viewModel.fetchFromRemote()

    }

    private fun observeViewModel() {
        Log.i(TAG, "observeViewModel")
        viewModel.animals.observe(viewLifecycleOwner, Observer { animalsListResult ->
            animalsListResult?.let {

                Log.i(TAG, "animalsListResult != null")
                animalsRcV.visibility = View.VISIBLE
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.list_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.actionSetting -> {
                view?.let {
                    Navigation.findNavController(it)
                        .navigate(ListFragmentDirections.actionSetting())
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}

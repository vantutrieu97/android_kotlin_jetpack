package vantutrieu97.myapplication.views.tabs

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
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
class AnimalsListFragment : Fragment() {

    private lateinit var viewModel: AnimalsListViewModel
    private lateinit var animalsListAdapter: AnimalsListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as AppCompatActivity)?.supportActionBar?.hide()
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
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

    private fun observeViewModel() {
        viewModel.animals.observe(viewLifecycleOwner, Observer { animalsListResult ->
            animalsListResult?.let {
                animalsRcV.visibility = View.VISIBLE
                animalsListAdapter.updateAnimalsList(animalsListResult)
            }
        })

        viewModel.animalLoadError.observe(viewLifecycleOwner, Observer { isError ->
            isError?.let {
                errorsTxt.visibility = if (it) View.VISIBLE else View.GONE
            }

        })

        viewModel.loading.observe(viewLifecycleOwner, Observer { isLoading ->
            isLoading?.let {
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
                        .navigate(AnimalsListFragmentDirections.actionSetting())
                }
            }
            R.id.actionDashboard -> {
                view?.let {
                    Navigation.findNavController(it)
                        .navigate(AnimalsListFragmentDirections.actionDashboard())
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }


}

package vantutrieu97.myapplication.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_list.*
import vantutrieu97.myapplication.R
import vantutrieu97.myapplication.viewmodel.ListViewModel
import vantutrieu97.myapplication.views.adapters.AnimalsListAdapter

/**
 * A simple [Fragment] subclass.
 */
class ListFragment : Fragment() {
    private lateinit var viewModel: ListViewModel
    private lateinit var animalsListAdapter: AnimalsListAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        animalsListAdapter = AnimalsListAdapter(arrayListOf())
        viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)
        viewModel.refresh()

        animalsRcV.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = animalsListAdapter
        }
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.animals.observe(viewLifecycleOwner, Observer { animalsListResult ->
            animalsListResult?.let {
                animalsRcV.visibility = View.VISIBLE
//                loadingView.visibility = View.GONE
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
}

package vantutrieu97.myapplication.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_detail.*
import vantutrieu97.myapplication.R
import vantutrieu97.myapplication.databinding.FragmentDetailBinding
import vantutrieu97.myapplication.viewmodel.AnimalDetailViewModel

/**
 * A simple [Fragment] subclass.
 */
class DetailFragment : Fragment() {
    private lateinit var viewModel: AnimalDetailViewModel
    private lateinit var dataBinding: FragmentDetailBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AnimalDetailViewModel::class.java)
        viewModel.fetch()
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.animal.observe(viewLifecycleOwner, Observer { result ->
            result?.let {
                idTxt.text = result.breedId
                breedTxt.text = result.breed
                lifeSpanTxt.text = result.lifeSpan
                breedGroupTxt.text = result.breedGroup
                breedForTxt.text = result.breedFor
                temperamenTxt.text = result.temperament
                imageUrlTxt.text = result.imageUrl
            }
        })
    }


}

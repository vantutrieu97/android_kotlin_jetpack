package vantutrieu97.myapplication.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_detail.*
import vantutrieu97.myapplication.R

/**
 * A simple [Fragment] subclass.
 */
class DetailFragment : Fragment() {
    private var dogUid = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            dogUid = DetailFragmentArgs.fromBundle(it).dogUid
            textView.text = dogUid.toString()
        }
        listBtn.setOnClickListener {
            if (it.findNavController().currentDestination?.id == R.id.detailFragment) {
                var action = DetailFragmentDirections.actionListFragment()
                Navigation.findNavController(it).navigate(action)
            }
        }
    }
}

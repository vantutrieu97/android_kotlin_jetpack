package vantutrieu97.myapplication.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_list.*
import vantutrieu97.myapplication.R

/**
 * A simple [Fragment] subclass.
 */
class ListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        detailBtn.setOnClickListener {
            if (it.findNavController().currentDestination?.id == R.id.listFragment) {
                var action = ListFragmentDirections.actionDetailFragment(10)
                Navigation.findNavController(it).navigate(action)
            }
        }
    }


}

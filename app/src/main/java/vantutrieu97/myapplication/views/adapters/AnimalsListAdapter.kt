package vantutrieu97.myapplication.views.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import vantutrieu97.myapplication.R
import vantutrieu97.myapplication.databinding.ThumnailAnimalItemBinding
import vantutrieu97.myapplication.models.Animal
import vantutrieu97.myapplication.views.AnimalItemClickListener
import vantutrieu97.myapplication.views.ListFragmentDirections

class AnimalsListAdapter(val animals: ArrayList<Animal>) :
    RecyclerView.Adapter<AnimalsListAdapter.Holder>(), AnimalItemClickListener {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalsListAdapter.Holder {
        val inflater =
            LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ThumnailAnimalItemBinding>(
            inflater,
            R.layout.thumnail_animal_item,
            parent,
            false
        )
        return Holder(view)
    }

    fun updateAnimalsList(newAnimals: ArrayList<Animal>) {
        animals.clear()
        animals.addAll(newAnimals)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return animals.count()
    }

    override fun onBindViewHolder(holder: AnimalsListAdapter.Holder, position: Int) {
        holder.view.animal = animals[position]
        holder.view.listener = this
    }

    override fun onAnimalItemClicked(view: View) {
        super.onAnimalItemClicked(view)
        Navigation.findNavController(view)
            .navigate(ListFragmentDirections.actionDetailFragment(20))

    }

    inner class Holder(var view: ThumnailAnimalItemBinding) : RecyclerView.ViewHolder(view.root)
}
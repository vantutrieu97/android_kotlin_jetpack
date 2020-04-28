package vantutrieu97.myapplication.views.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.thumnail_animal_item.view.*
import vantutrieu97.myapplication.R
import vantutrieu97.myapplication.models.AnimalBreed
import vantutrieu97.myapplication.views.ListFragmentDirections

class AnimalsListAdapter(val animals: ArrayList<AnimalBreed>) :
    RecyclerView.Adapter<AnimalsListAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalsListAdapter.Holder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.thumnail_animal_item, parent, false)
        return Holder(view)
    }

    fun updateAnimalsList(newAnimals: ArrayList<AnimalBreed>) {
        animals.clear()
        animals.addAll(newAnimals)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return animals.count()
    }

    override fun onBindViewHolder(holder: AnimalsListAdapter.Holder, position: Int) {
        val animal = animals[position]
        holder.itemView.titleTxt.text = animal.breed
        holder.itemView.textTxt.text = animal.lifeSpan
        holder.itemView.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(ListFragmentDirections.actionDetailFragment(20))
        }
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
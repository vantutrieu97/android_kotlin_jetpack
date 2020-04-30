package vantutrieu97.myapplication.views.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.thumnail_animal_item.view.*
import vantutrieu97.myapplication.R
import vantutrieu97.myapplication.models.Animal
import vantutrieu97.myapplication.utils.getProgressDrawable
import vantutrieu97.myapplication.utils.loadImage
import vantutrieu97.myapplication.views.ListFragmentDirections

class AnimalsListAdapter(val animals: ArrayList<Animal>) :
    RecyclerView.Adapter<AnimalsListAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalsListAdapter.Holder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.thumnail_animal_item, parent, false)
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
        val animal = animals[position]
        holder.itemView.titleTxt.text = animal.breed
        holder.itemView.lifeSpantTxt.text = animal.lifeSpan
        holder.itemView.groupTxt.text = animal.breedGroup
        val temp = animal.origin
        if (animal.origin == "" || animal.origin == null) {
            holder.itemView.originTxt.visibility = View.GONE
        } else {
            holder.itemView.originTxt.text = animal.origin
        }
        if (animal.breedGroup == "" || animal.breedGroup == null) {
            holder.itemView.groupTxt.visibility = View.GONE
        } else {
            holder.itemView.groupTxt.text = animal.breedGroup
        }
        holder.itemView.temperamenTxt.text = animal.temperament
        holder.itemView.imageView.loadImage(
            animal.imageUrl,
            getProgressDrawable(holder.itemView.imageView.context)
        )

        holder.itemView.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(ListFragmentDirections.actionDetailFragment(20))
        }
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
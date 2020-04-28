package vantutrieu97.myapplication.views.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.thumnail_animal_item.view.*
import vantutrieu97.myapplication.R
import vantutrieu97.myapplication.models.AnimalBreed

class AnimalsListAdapter(val context: Context, val animals: ArrayList<AnimalBreed>) :
    RecyclerView.Adapter<AnimalsListAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalsListAdapter.Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.thumnail_animal_item, parent)
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
        holder.itemView.titleTxt.text = animals[position].dogBreed
        holder.itemView.textTxt.text = animals[position].lifeSpan

    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
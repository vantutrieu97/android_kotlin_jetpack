package vantutrieu97.myapplication.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import vantutrieu97.myapplication.R
import vantutrieu97.myapplication.databinding.ThumnailAnimalItemBinding
import vantutrieu97.myapplication.models.Animal

class AnimalsListAdapter(val animals: ArrayList<Animal>) :
    RecyclerView.Adapter<AnimalsListAdapter.Holder>() {
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
//        val animal = animals[position]
////        holder.
//        holder.itemView.titleTxt.text = animal.breed
//        holder.itemView.lifeSpantTxt.text = animal.lifeSpan
//        holder.itemView.groupTxt.text = animal.breedGroup
//        val temp = animal.origin
//        print("animal.origin ==:$temp")
//        if (animal.origin == "" || animal.origin == null) {
//            print(" Đủ điều kiện")
//            holder.itemView.originTxt.visibility = View.GONE
//        } else {
//            holder.itemView.originTxt.text = animal.origin
//        }
//        if (animal.breedGroup == "" || animal.breedGroup == null) {
//            holder.itemView.groupTxt.visibility = View.GONE
//        } else {
//            holder.itemView.groupTxt.text = animal.breedGroup
//        }
//        println("")
//        holder.itemView.temperamenTxt.text = animal.temperament
//        holder.itemView.imageView.loadImage(
//            animal.imageUrl,
//            getProgressDrawable(holder.itemView.imageView.context)
//        )
//
//        holder.itemView.setOnClickListener {
//            Navigation.findNavController(it)
//                .navigate(ListFragmentDirections.actionDetailFragment(20))
//        }
        holder.view.animal = animals[position]
    }

    inner class Holder(var view: ThumnailAnimalItemBinding) : RecyclerView.ViewHolder(view.root)
}
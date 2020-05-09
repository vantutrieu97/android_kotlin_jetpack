package vantutrieu97.myapplication.views

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import vantutrieu97.myapplication.R
import vantutrieu97.myapplication.databinding.FragmentDetailBinding
import vantutrieu97.myapplication.models.AnimalPlatte
import vantutrieu97.myapplication.utils.PERMISSION_SEND_SMS
import vantutrieu97.myapplication.viewmodel.AnimalDetailViewModel


/**
 * A simple [Fragment] subclass.
 */
class DetailFragment : Fragment() {
    private lateinit var viewModel: AnimalDetailViewModel
    private lateinit var uuid: String
    private lateinit var dataBinding: FragmentDetailBinding
    private var sendSmsStarted = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as AppCompatActivity)?.supportActionBar?.show()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AnimalDetailViewModel::class.java)
        arguments?.let {
            uuid = DetailFragmentArgs.fromBundle(it).uuid.toString()
            println(".dogUid.toString() ----- $uuid")
        }
        viewModel.fetch(uuid)
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.animal.observe(viewLifecycleOwner, Observer { result ->
            result?.let {
                dataBinding.animal = result
                result.imageUrl?.let {
                    setBackgroundColor(it)
                }
            }
        })
    }

    private fun setBackgroundColor(url: String) {
        Glide.with(this)
            .asBitmap()
            .load(url)
            .into(object : CustomTarget<Bitmap>() {
                override fun onLoadCleared(placeholder: Drawable?) {

                }

                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    Palette.from(resource)
                        .generate { palette ->
                            val intColor = palette?.lightVibrantSwatch?.rgb ?: 0
                            val myPalette = AnimalPlatte(intColor)
                            dataBinding.palette = myPalette
                        }
                }

            })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.detail_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_send_sms -> {
                println("onOptionsItemSelected action_send_sms")
                sendSmsStarted = true
                context?.let { checkSmsPermission(it) }
                true
            }
            R.id.action_share -> {
                println("onOptionsItemSelected action_share")
                true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun checkSmsPermission(context: Context) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS)
            != PackageManager.PERMISSION_GRANTED
        ) {
            // Permission is not granted
            requestSmsPermission()


        } else {
            println("Permission is granted")
        }
    }

    private fun requestSmsPermission() {
        requestPermissions(arrayOf(Manifest.permission.SEND_SMS), PERMISSION_SEND_SMS)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_SEND_SMS -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    println("Xin quyen thanh cong")
                } else {
                    println("Xin quyen that bai")
                }
            }

        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}

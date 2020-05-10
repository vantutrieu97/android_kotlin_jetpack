package vantutrieu97.myapplication.views

import android.Manifest
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.telephony.SmsManager
import android.view.*
import androidx.appcompat.app.AlertDialog
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
import vantutrieu97.myapplication.databinding.SendSmsDialogBinding
import vantutrieu97.myapplication.models.Animal
import vantutrieu97.myapplication.models.AnimalPlatte
import vantutrieu97.myapplication.models.AnimalSms
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
    private lateinit var currentAnimal: Animal

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
                currentAnimal = result
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
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "text/plain"
                intent.putExtra(Intent.EXTRA_SUBJECT, "This is subject ^^")
                intent.putExtra(Intent.EXTRA_TITLE, "This is title ^^")
                intent.putExtra(
                    Intent.EXTRA_TEXT,
                    "This is animal's image link: ${currentAnimal.imageUrl}"
                )
                intent.putExtra(
                    Intent.EXTRA_STREAM,
                    currentAnimal.imageUrl
                )
                startActivity(Intent.createChooser(intent, "Share with"))
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
            context?.let {
                val animalSms =
                    AnimalSms(
                        "",
                        "${currentAnimal.temperament}",
                        "${currentAnimal.imageUrl}"
                    )
                val smsDialogBinding = DataBindingUtil.inflate<SendSmsDialogBinding>(
                    LayoutInflater.from(it), R.layout.send_sms_dialog, null, false
                )
                AlertDialog.Builder(it)
                    .setView(smsDialogBinding.root)
                    .setPositiveButton("Send SMS") { dialog, which ->
                        if (!smsDialogBinding.smsDestination.text.isNullOrEmpty()) {
                            animalSms.to = smsDialogBinding.smsDestination.text.toString()
                            sendSms(animalSms)
                        }
                    }.setNegativeButton("Cancel") { dialog, which -> }
                    .show()
                smsDialogBinding.animalSms = animalSms
            }
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


                } else {
                    println("Xin quyen that bai")
                }
            }

        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun sendSms(animalSms: AnimalSms) {
        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
        val sms = SmsManager.getDefault()
        sms.sendTextMessage(
            animalSms.to,
            null,
            "This is link to download this image:\n${animalSms.imageUrl}",
            pendingIntent,
            null
        )

    }
}

package com.playsdev.testsecond.view

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.StrictMode
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import com.playsdev.testsecond.MainActivity
import com.playsdev.testsecond.R
import com.playsdev.testsecond.databinding.FragmentDetailsBinding
import com.squareup.picasso.Picasso
import io.reactivex.rxjava3.core.Observable
import java.io.File
import java.io.FileOutputStream
import java.util.*
import java.util.concurrent.TimeUnit


class DetailsFragment(private val image: String) : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = checkNotNull(_binding)
    private var tutorialPreference: SharedPreferences? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tutorialPreference = activity?.getSharedPreferences(TUTORIAL, Context.MODE_PRIVATE)
        Picasso.get()
            .load(image)
            .fit()
            .centerCrop()
            .into(binding.cvMain)


        binding.ivBack.setOnClickListener {
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
        }

        binding.ivShare.setOnClickListener {
            shareImage()
        }
    }

    @SuppressLint("CommitPrefEdits")
    override fun onStart() {
        super.onStart()
        if (tutorialPreference?.getBoolean(FIRST_RUN,true)!!){
            val dialog = activity?.let {
                AlertDialog.Builder(it,R.style.DialogTheme)
                    .setView(R.layout.tutuorial_dialog)
                    .setCancelable(true)
                    .show()
            }
            Observable.timer(3,TimeUnit.SECONDS)
                .subscribe{
                    dialog?.dismiss()
                }


            tutorialPreference!!.edit().putBoolean(FIRST_RUN,false).apply()
        }

    }

    private fun shareImage(){
        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())

        val bitmap = convertStringToBitmap(image)
        val file = File(activity?.externalCacheDir, "/" + getString(R.string.app_name)+".png")
        val intent = Intent(Intent.ACTION_SEND)

        try {
            val outputStream = FileOutputStream(file)
            bitmap?.compress(Bitmap.CompressFormat.PNG, 100, outputStream)

            outputStream.flush()
            outputStream.close()

            intent.type = "image/*"
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file))
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }catch (e: Exception){
            throw RuntimeException(e)
        }
        startActivity(Intent.createChooser(intent,"share image"))
    }

    private fun convertStringToBitmap(string: String?): Bitmap? {
        val byteArray1: ByteArray = Base64.decode(string, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(
            byteArray1, 0,
            byteArray1.size
        )

    }

    companion object{
        private const val TUTORIAL = "main_tutorial"
        private const val FIRST_RUN = "FIRST_RUN"
    }



}
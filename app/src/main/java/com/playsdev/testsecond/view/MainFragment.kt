package com.playsdev.testsecond.view

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.terrakok.cicerone.Router
import com.google.android.material.snackbar.Snackbar
import com.playsdev.testsecond.MainApplication
import com.playsdev.testsecond.adapter.MainAdapter
import com.playsdev.testsecond.adapter.OnItemClickListener
import com.playsdev.testsecond.contracts.MainContract
import com.playsdev.testsecond.databinding.FragmentMainBinding
import com.playsdev.testsecond.navigator.Screens.details
import com.playsdev.testsecond.responce.Info
import javax.inject.Inject

class MainFragment: Fragment(), MainContract.View, OnItemClickListener  {

    @Inject lateinit var router: Router
    @Inject lateinit var presenter: MainContract.Presenter
    private var _binding: FragmentMainBinding? = null
    private val binding get() = checkNotNull(_binding)

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.application as MainApplication).appComponent.inject(this@MainFragment)
        presenter.attachView(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.loadNetworkAPI()
    }

    override fun onLoadList(list: Info) {
        GridLayoutManager(
            requireContext(),
            2,
            RecyclerView.VERTICAL,
            false
        ).apply {
            binding.rvIcons.layoutManager = this
        }
        val adapter = MainAdapter(this)
        adapter.setItems(list.photos.toMutableList())
        binding.rvIcons.adapter = adapter
    }

    override fun showLoadErrorMessage(error: String) {
        Snackbar.make(binding.fragmentMain, error, Snackbar.LENGTH_LONG).show()
    }

    override fun openFragment(image: String) {
        router.navigateTo(details(image))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        presenter.unSubscribe()
    }

    companion object{
        const val IMAGE_PUT = "IMAGE_PUT"
    }




}
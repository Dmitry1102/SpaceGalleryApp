package com.playsdev.testsecond.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.playsdev.testsecond.R
import com.playsdev.testsecond.adapter.MarkerAdapter
import com.playsdev.testsecond.adapter.OnCrossClickListener
import com.playsdev.testsecond.databinding.MapFragmentBinding
import com.playsdev.testsecond.responce.MarkersValue

private var listMarkersValue = arrayListOf<MarkersValue>()
private var markers = arrayListOf<MarkerOptions>()


class MapFragment : Fragment(), OnMapReadyCallback, OnCrossClickListener {

    private var _binding: MapFragmentBinding? = null
    private val binding get() = checkNotNull(_binding)
    private var mMap: GoogleMap? = null
    private var restoreMarkers = arrayListOf<Marker>()
    private var bottomSheet: BottomSheetBehavior<ConstraintLayout>? = null
    private val markerAdapter = MarkerAdapter(listMarkersValue, this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MapFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.map.onCreate(savedInstanceState)
        binding.map.getMapAsync(this)

        bottomSheet = BottomSheetBehavior.from(requireActivity().findViewById(R.id.bottom_sheet))
        bottomSheet?.state = BottomSheetBehavior.STATE_COLLAPSED

        binding.bottomSheet.rvMarkers.adapter = markerAdapter
        markerAdapter.submitList(listMarkersValue)

        binding.bottomSheet.ivNormal.setOnClickListener {
            mMap?.mapType = GoogleMap.MAP_TYPE_NORMAL

        }

        binding.bottomSheet.ivHybrid.setOnClickListener {
            mMap?.mapType = GoogleMap.MAP_TYPE_HYBRID
        }

        binding.bottomSheet.ivSatellite.setOnClickListener {
            mMap?.mapType = GoogleMap.MAP_TYPE_SATELLITE
        }

        binding.bottomSheet.ivTerrain.setOnClickListener {
            mMap?.mapType = GoogleMap.MAP_TYPE_TERRAIN
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap?.mapType = GoogleMap.MAP_TYPE_NORMAL
        mMap?.uiSettings?.isRotateGesturesEnabled = true
        mMap?.uiSettings?.isZoomGesturesEnabled = true
        mMap?.setMapStyle(
            MapStyleOptions.loadRawResourceStyle(
                requireContext(),
                R.raw.night_mode
            )
        )

        for (i in 0 until markers.size) {
            mMap!!.addMarker(markers[i])
        }


        setMarker()
    }

    private fun setMarker() {
        var id = 0
        mMap?.setOnMapClickListener {
            val latLng = it
            val builder = AlertDialog.Builder(requireContext())
            val layout = LayoutInflater.from(requireContext()).inflate(R.layout.marker_dialog, null)
            builder.setView(layout)
            val dialog = builder.show()
            val editText = layout.findViewById<EditText>(R.id.marker_name)
            val cancelButton = layout.findViewById<TextView>(R.id.tv_cancel)
            val saveButton = layout.findViewById<TextView>(R.id.tv_save)

            cancelButton.setOnClickListener {
                dialog.dismiss()
            }

            saveButton.setOnClickListener {
                id += 1
                val options = MarkerOptions().title(editText.text.toString()).position(latLng)
                 restoreMarkers.add(mMap!!.addMarker(
                    options
                )!!)

                markers.add(options)
                listMarkersValue.add(
                    MarkersValue(
                        id = id,
                        name = editText.text.toString(),
                        longitude = latLng.longitude,
                        width = latLng.latitude
                    )
                )
                dialog.dismiss()
            }
        }
    }


    override fun onStart() {
        super.onStart()
        binding.map.onStart()
    }

    override fun onResume() {
        super.onResume()
        binding.map.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.map.onPause()
    }

    override fun onStop() {
        super.onStop()
        binding.map.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.map.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.map.onLowMemory()
    }

    override fun deleteItem(position: Int) {
        listMarkersValue.removeAt(position)
        markerAdapter.notifyItemRemoved(position)
        restoreMarkers[position].isVisible = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding?.bottomSheet?.rvMarkers?.adapter = null
    }
}

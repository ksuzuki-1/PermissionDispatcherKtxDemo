package com.example.permissiondispatcherktxdemo

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.permissiondispatcherktxdemo.databinding.FragmentMainBinding
import permissions.dispatcher.PermissionRequest
import permissions.dispatcher.ktx.PermissionsRequester
import permissions.dispatcher.ktx.constructPermissionsRequest

class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var storagePermissionRequester: PermissionsRequester
    private val storagePermission: String
        get() {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                Manifest.permission.READ_MEDIA_IMAGES
            } else {
                Manifest.permission.READ_EXTERNAL_STORAGE
            }
        }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        storagePermissionRequester = constructPermissionsRequest(
            storagePermission,
            onShowRationale = ::onShowRationale,
            onPermissionDenied = ::onPermissionDenied,
            onNeverAskAgain = ::onNeverAskAgain,
            requiresPermission = ::onGrantedPermission
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        storagePermissionRequester.launch()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onGrantedPermission() {
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.granted_permission)
            .setPositiveButton(R.string.ok) { _, _ ->
                // noop
            }
            .show()
    }

    private fun onShowRationale(permission: PermissionRequest) {
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.not_granted_permission)
            .setMessage(R.string.required_permission)
            .setPositiveButton(R.string.confirm) { _, _ ->
                permission.proceed()
            }
            .setNegativeButton(R.string.deny) { _, _ ->
                permission.cancel()
            }
            .show()
    }

    private fun onPermissionDenied() {
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.denied_permission)
            .setMessage(R.string.required_permission)
            .setPositiveButton(R.string.ok) { _, _ ->
                requireActivity().finish()
            }
            .show()
    }

    private fun onNeverAskAgain() {
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.not_granted_permission)
            .setMessage(R.string.required_permission)
            .setPositiveButton(R.string.ok) { _, _ ->
                requireActivity().finish()
            }
            .show()
    }
}

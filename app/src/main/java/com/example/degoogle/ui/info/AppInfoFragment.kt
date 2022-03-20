/*
  This class is used to download the apk file from the firebase storage and install it
 */
package com.example.degoogle.ui.info

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.downloader.*
import com.example.degoogle.DegoogleApplication
import com.example.degoogle.data.entities.InstalledPackages
import com.example.degoogle.databinding.FragmentAppInfoBinding
import com.example.degoogle.dialogs.PermissionDialog
import com.example.degoogle.installer.KotlinInstallMotor
import com.google.android.gms.tasks.Task
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File

class AppInfoFragment : BottomSheetDialogFragment() {
    var db = FirebaseFirestore.getInstance()
    private var binding: FragmentAppInfoBinding? = null
    var storage = FirebaseStorage.getInstance()
    var appReference: StorageReference? = null
    var apk: String? = ""
    var file: File? = null
    var downloadUri: String? = null
    var outputFile: File? = null
    var packageName:String? = null
    var version:String? = null
    var name: String? = null
    private val appInfoViewModel: AppInfoViewModel? = null
    private val viewModel: AppInfoViewModel by activityViewModels{
        AppInfoViewModelFactory(
            (activity?.application as DegoogleApplication).database
                .packagesDao()
        )
    }
    lateinit var item: InstalledPackages

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAppInfoBinding.inflate(inflater, container, false)
        if(ContextCompat.checkSelfPermission(requireActivity(), android.Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(READ_EXTERNAL_STORAGE), 1)
        }
        val config = PRDownloaderConfig.newBuilder()
            .setReadTimeout(30000)
            .setConnectTimeout(30000)
            .build()
        PRDownloader.initialize(requireContext(), config)
        return binding!!.root
    }
    private fun isEntryValid():Boolean{
        return viewModel.isEntryValid(
            packageName.toString(),
            version.toString()
        )
    }
    @RequiresApi(api = Build.VERSION_CODES.S)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        files
        getDataFromFirebase(args)
        binding!!.downloadbutton.setOnClickListener { downloadFile() }
        binding!!.button1.setOnClickListener { installApk() }
    }

    private val args: String?
        get() {
            val args = arguments
            return args!!.getString("key")
        }

    private fun getDataFromFirebase(id: String?) {
        db.collection("apps")
            .document(id!!)
            .get()
            .addOnCompleteListener { task: Task<DocumentSnapshot> ->
                val document = task.result
                name = document.getString("name")
                binding!!.appName.text = name
                binding!!.appDescription.text = document.getString("description")
                Glide.with(requireContext()).load(document.getString("icon"))
                    .into(
                        binding!!.appIcon
                    )
                outputFile = File(file, name!!.lowercase() +".apk")
                downloadUri = document.getString("downloadUrl")
                packageName = document.getString("packageName")
                version = document.getString("version")
            }
    }

    private val files: Unit
        get() {
            file = requireContext().getExternalFilesDir("downloads")
        }

    fun downloadFile() {
        if (downloadUri == null) {
            appReference = storage.getReference("$name.apk")
            appReference!!.downloadUrl.addOnSuccessListener { uri: Uri ->
                downloader(
                    uri.toString()
                )
            }
        } else {
            downloader(downloadUri!!)
        }
    }

    private fun downloader(uri: String) {
        PRDownloader.download(uri, file.toString(), "$name.apk")
            .build()
            .setOnProgressListener { progress: Progress ->
                val downloadProgress =
                    (progress.currentBytes * 1f / progress.totalBytes * 100).toInt()
                binding!!.downloadProgressBar.progress = downloadProgress
            }
            .start(object : OnDownloadListener {
                @RequiresApi(api = Build.VERSION_CODES.S)
                override fun onDownloadComplete() {
                    installApk()
                }

                override fun onError(error: Error) {
                    Log.d(TAG, "onError: download error")
                }
            })
    }

    @RequiresApi(api = Build.VERSION_CODES.S)
    private fun installApk() {
        if (!file!!.mkdirs()) {
            Log.d(TAG, "installApk: failed to create directories")
        }
        if (!requireContext().packageManager.canRequestPackageInstalls()) {
            PermissionDialog().show(parentFragmentManager, "test")
        } else {
            val motor = KotlinInstallMotor(requireActivity().application)
            motor.install(Uri.fromFile(outputFile))
            if(isEntryValid()){
                viewModel.addNewItem(
                    packageName.toString(),
                    version.toString()
                )

            }
        }

    }

    companion object {
        private const val TAG = "AppInfoFragment"
    }
}
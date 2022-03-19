package com.example.degoogle.installer

import android.app.Application
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageInstaller
import android.net.Uri
import androidx.documentfile.provider.DocumentFile
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val NAME = "test"
private const val PI_INSTALL = 3439

class KotlinInstallMotor(app: Application) : AndroidViewModel(app) {
    private val installer = app.packageManager.packageInstaller
    private val resolver = app.contentResolver
    fun install(apkUri: Uri) {
        viewModelScope.launch(Dispatchers.Main) {
            installCoroutine(apkUri)
        }
    }

    private suspend fun installCoroutine(apkUri: Uri) =
            withContext(Dispatchers.IO) {
                resolver.openInputStream(apkUri)?.use { apkStream ->
                    //@param length used in session.openwrite lengthbytes
                    val length =
                            DocumentFile.fromSingleUri(getApplication(), apkUri)?.length() ?: -1
                    val params =
                            PackageInstaller.SessionParams(PackageInstaller.SessionParams.MODE_FULL_INSTALL)
                    val sessionId = installer.createSession(params)
                    val session = installer.openSession(sessionId)
                    session.openWrite(NAME, 0, -1).use { sessionStream ->

                        apkStream.copyTo(sessionStream)
                        session.fsync(sessionStream)

                    }

                    val intent = Intent(getApplication(), InstallReceiver::class.java)
                    val pi = PendingIntent.getBroadcast(
                            getApplication(),
                            PI_INSTALL,
                            intent,
                        //FLAG_MUTABLE
                            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE,
                    )
                    session.commit(pi.intentSender)
                    session.close()
                }


            }


}
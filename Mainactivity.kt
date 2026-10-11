package com.mediafirewall.app
import android.content.Intent
import android.net.VpnService
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val vpnIntent = VpnService.prepare(this)
        if (vpnIntent!= null) {
            startActivityForResult(vpnIntent, 0)
        } else {
            onActivityResult(0, RESULT_OK, null)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            startService(Intent(this, FirewallVpnService::class.java))
        }
    }
}

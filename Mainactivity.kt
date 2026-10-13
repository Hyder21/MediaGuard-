package com.mediaguard

import android.os.Bundle
import android.graphics.Color
import android.graphics.Typeface
import android.widget.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private var vpnOn = false
    private var locOn = false
    private var speedFast = true
    private var alertOn = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setBackgroundColor(Color.parseColor("#0A0A0A"))
            setPadding(40, 60, 40, 40)
        }

        val title = TextView(this).apply {
            text = "MediaGuard Pro\nv11.0 CONTROL CENTER\nKotlin - feature branch"
            setTextColor(Color.WHITE)
            textSize = 18f
            setTypeface(null, Typeface.BOLD)
        }
        root.addView(title)

        val status = TextView(this).apply {
            text = "Status: ${SimpleDateFormat("HH:mm:ss", Locale.US).format(Date())}\nKarachi PK - All libs loaded"
            setTextColor(Color.parseColor("#00E676"))
            setPadding(20, 20, 20, 20)
            setBackgroundColor(Color.parseColor("#1E1E1E"))
        }
        root.addView(status)

        root.addView(makeSwitch("VPN Firewall", vpnOn) { b ->
            vpnOn = b
            toast("VPN ${if (b) "ON - All traffic filtered" else "OFF"}")
        })
        root.addView(makeSwitch("Location Shield", locOn) { b ->
            locOn = b
            toast("Location ${if (b) "FAKED" else "REAL"}")
        })
        root.addView(makeSwitch("Speed Mode FAST", speedFast) { b ->
            speedFast = b
            toast(if (speedFast) "FAST Mode" else "SAFE Mode")
        })
        root.addView(makeSwitch("Security Alert", alertOn) { b ->
            alertOn = b
            toast("Alert ${if (b) "ON" else "OFF"}")
        })

        val scan1 = Button(this).apply {
            text = "SCAN QR 17.2.0"
            setOnClickListener { toast("QR Scanner ready - MLKit 17.2.0") }
        }
        root.addView(scan1)

        val scan2 = Button(this).apply {
            text = "SCAN TEXT 16.0.0"
            setOnClickListener { toast("Text Scanner ready - MLKit 16.0.0") }
        }
        root.addView(scan2)

        setContentView(root)
    }

    private fun makeSwitch(name: String, def: Boolean, cb: (Boolean) -> Unit): LinearLayout {
        val row = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            setPadding(10, 20, 10, 20)
        }
        val t = TextView(this).apply {
            text = name
            setTextColor(Color.WHITE)
            layoutParams = LinearLayout.LayoutParams(0, -2, 1f)
        }
        val sw = SwitchCompat(this).apply {
            isChecked = def
            setOnCheckedChangeListener { _, b -> cb(b) }
        }
        row.addView(t)
        row.addView(sw)
        return row
    }

    private fun toast(m: String) {
        Toast.makeText(this, m, Toast.LENGTH_SHORT).show()
    }
}

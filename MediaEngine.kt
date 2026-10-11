package com.mediafirewall.app
data class ScanResult(val block: Boolean, val reason: String)

object MediaEngine {
    fun scan(data: ByteArray): ScanResult {
        val text = String(data, Charsets.UTF_8)

        // Agent 1: QR - phishing / bomb
        if (text.length > 3000 && text.contains("http"))
            return ScanResult(true, "Blocked: QR bomb")
        if (text.contains("phishing") || text.contains("fake-login"))
            return ScanResult(true, "Blocked: Phishing QR")

        // Agent 2: Picture - malformed JPEG
        if (data.size > 4 && data[0] == 0xFF.toByte() && data[1] == 0xD8.toByte() && data.size < 100)
            return ScanResult(true, "Blocked: Malformed JPEG")

        // Agent 3: Video - malformed MP4
        val header = String(data.take(10).toByteArray())
        if (header.contains("ftyp") && data.size < 100)
            return ScanResult(true, "Blocked: Malformed Video")

        // Agent 4: Emoji - unicode bomb
        if (text.count { it.code > 1000 } > 100)
            return ScanResult(true, "Blocked: Emoji bomb")

        // Agent 5: Whole App - APK with malformed media
        val raw = String(data.take(200).toByteArray(), Charsets.ISO_8859_1)
        if (raw.contains("PK") && text.contains(".jpg") && data.size < 500)
            return ScanResult(true, "Blocked: App with malformed media")

        return ScanResult(false, "clean")
    }
}

package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.Socket

class MainActivity : AppCompatActivity() {

    private val serverIp = "192.168.2.35"
    private val serverPort = 65432

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val openTerminalButton: Button = findViewById(R.id.openTerminalButton)
        val closeTerminalButton: Button = findViewById(R.id.closeTerminalButton)
        val resultTextView: TextView = findViewById(R.id.resultTextView)

        openTerminalButton.setOnClickListener {
            sendCommand("open_terminal", resultTextView)
        }

        closeTerminalButton.setOnClickListener {
            sendCommand("close_terminal", resultTextView)
        }
    }

    private fun sendCommand(command: String, resultTextView: TextView) {
        Thread {
            var response = ""
            try {
                val socket = Socket(serverIp, serverPort)
                val outputStreamWriter = OutputStreamWriter(socket.getOutputStream())
                outputStreamWriter.write("$command\n")
                outputStreamWriter.flush()

                val inputStreamReader = InputStreamReader(socket.getInputStream())
                val bufferedReader = BufferedReader(inputStreamReader)
                response = bufferedReader.readLine()

                socket.close()
            } catch (e: Exception) {
                response = "Error: ${e.message}"
            }

            runOnUiThread {
                resultTextView.text = response
            }
        }.start()
    }
}
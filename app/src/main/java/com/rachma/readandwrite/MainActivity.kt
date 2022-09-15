package com.rachma.readandwrite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Context
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import java.io.*
import com.rachma.readandwrite.databinding.ActivityMainBinding

// Untuk mendeklarasikan kelas yang bernama MainActivity
lateinit var binding: ActivityMainBinding
class MainActivity : AppCompatActivity() {

    // Untuk memanggil kelas super onCreate dalam pembuatan activity ini
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Untuk mendeklarasikan variabel fileName dan menentukan metode masukkan
        val fileName = findViewById<EditText>(R.id.editFile)
        // Untuk mendeklarasikan variabel fileData dan menentukan metode masukkan
        val fileData = findViewById<EditText>(R.id.editData)

        // Untuk mendeklarasikan variabel btnSave dan menentukan metode masukkan
        val btnSave = findViewById<Button>(R.id.btnSave)
        // Untuk mendeklarasikan variabel btnSave dan menentukan metode masukkan
        val btnView = findViewById<Button>(R.id.btnView)

        val btnDelete = findViewById<Button>(R.id.btnDelete)

        //Deklarasi nilai awal untuk variabel hasil
        var hasil ="";

        // Untuk memberikan fungsi klik listener pada btnSave
        btnSave.setOnClickListener(View.OnClickListener {

            // Mengambil nama file yang diketikkan oleh user di edittext EditFile
            // Dan kemudian dikonversi dari teks menjadi string
            val file:String = fileName.text.toString()

            // Mengambil isi file yang diketikkan oleh user di editText EditData
            // Dan kemudian dikonversi dari teks menjadi string
            val data:String = fileData.text.toString()

            // Membuat variabel fileOutputStream untuk penyimpanan data
            // Untuk menulis file di penyimpanan internal perangkat
            // Try block digunakan untuk menyertakan kode yang mungkin menimbulkan pengecualian
            // Untuk menulis lagi data ke dalam file, maka memanggil metode FileOutputStream .write()
            // Menggunakan metode openFileOutput() yang mengembalikan instance kelas FileOutputStream
            val fileOutputStream:FileOutputStream
            try {
                fileOutputStream = openFileOutput(file, Context.MODE_PRIVATE)
                fileOutputStream.write(data.toByteArray())
            } catch (e: FileNotFoundException){
                e.printStackTrace()
            }catch (e: NumberFormatException){
                e.printStackTrace()
            }catch (e: IOException){
                e.printStackTrace()
            }catch (e: Exception){
                e.printStackTrace()
            }

            // Membuat toast untuk menyampaikan pesan data save
            Toast.makeText(applicationContext,"data save",Toast.LENGTH_LONG).show()

            // Clear editText dari inputan user
            fileName.text.clear()
            fileData.text.clear()
        })

        // Untuk memberikan fungsi klik listener pada btnView untuk membaca data
        btnView.setOnClickListener(View.OnClickListener {
            // Mengambil nama file dari editText filename
            // Dan kemudian dikonversi dari teks menjadi string
            val filename = fileName.text.toString()+".txt"

            // Mengecek nama file kosong atau tidak, jika tidak kosong maka program didalam if dijalankan
            if(filename.toString()!=null && filename.toString().trim()!=""){
                // Variabel fileInputStream untuk membuka file
                var fileInputStream: FileInputStream? = null
                fileInputStream = openFileInput(filename)
                var inputStreamReader: InputStreamReader = InputStreamReader(fileInputStream)
                val bufferedReader: BufferedReader = BufferedReader(inputStreamReader)
                val stringBuilder: StringBuilder = StringBuilder()
                var text: String? = null
                while ({ text = bufferedReader.readLine(); text }() != null) {
                    stringBuilder.append(text)
                }

                //Menampilkan teks di editText fileData
                fileData.setText(stringBuilder.toString()).toString()

                // Jika nama file kosong maka akan menampilkan toast atau popup berupa file name cannot be blank
            }else{
                Toast.makeText(applicationContext,"file name cannot be blank",Toast.LENGTH_LONG).show()

            }
        })

        btnDelete.setOnClickListener(View.OnClickListener {

            try {
                val filename = fileName.text.toString()+".txt"
                //Mencari direktori file
                val dir = filesDir
                //Menemukan file
                val file = File(dir, filename)
                //file dihapus
                file.delete()
                //set ulang hasil
                hasil = ""
                //Menangkap error dan diabaikan
            }catch (e: Exception) {
                //muncul pop up "Tidak bisa dihaspus"
                Toast.makeText(this, "Tidak bisa dihapus", Toast.LENGTH_LONG).show()
                e.printStackTrace()
            }

            //bersihkan text
            fileName.text.clear()
            fileData.text.clear()


        })

    }
}
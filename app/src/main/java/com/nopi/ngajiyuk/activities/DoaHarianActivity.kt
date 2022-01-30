package com.nopi.ngajiyuk.activities

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.nopi.ngajiyuk.R
import com.nopi.ngajiyuk.adapter.DoaHarianAdapter
import com.nopi.ngajiyuk.model.ModelBacaan
import kotlinx.android.synthetic.main.activity_doa_harian.*
import org.json.JSONArray
import org.json.JSONException
import java.io.IOException
import java.nio.charset.StandardCharsets
import java.util.*

class DoaHarianActivity : AppCompatActivity() {

    var doaHarianAdapter: DoaHarianAdapter? = null
    var modelBacaan: MutableList<ModelBacaan> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doa_harian)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }

        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
            window.statusBarColor = Color.TRANSPARENT
        }

        toolbar.setTitle(null)
        setSupportActionBar(toolbar)
        assert(supportActionBar != null)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        doaHarianAdapter = DoaHarianAdapter(modelBacaan)

        rvDoaHarian.setLayoutManager(LinearLayoutManager(this))
        rvDoaHarian.setHasFixedSize(true)
        rvDoaHarian.setAdapter(doaHarianAdapter)

        getDataDoaHarian()
    }

    private fun getDataDoaHarian() {
        try {
            val stream = assets.open("doaharian.json")
            val size = stream.available()
            val buffer = ByteArray(size)
            stream.read(buffer)
            stream.close()
            val strResponse = String(buffer, StandardCharsets.UTF_8)
            try {
                val jsonArray = JSONArray(strResponse)
                for (i in 0 until jsonArray.length()) {
                    val jsonObject = jsonArray.getJSONObject(i)
                    val dataModel = ModelBacaan()
                    dataModel.id = jsonObject.getString("id")
                    dataModel.name = jsonObject.getString("name")
                    dataModel.arabic = jsonObject.getString("arabic")
                    dataModel.latin = jsonObject.getString("latin")
                    dataModel.terjemahan = jsonObject.getString("terjemahan")
                    modelBacaan.add(dataModel)
                }
                doaHarianAdapter?.notifyDataSetChanged()
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        } catch (ignored: IOException) {
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        fun setWindowFlag(activity: Activity, bits: Int, on: Boolean) {
            val window = activity.window
            val layoutParams = window.attributes
            if (on) {
                layoutParams.flags = layoutParams.flags or bits
            } else {
                layoutParams.flags = layoutParams.flags and bits.inv()
            }
            window.attributes = layoutParams
        }
    }
}
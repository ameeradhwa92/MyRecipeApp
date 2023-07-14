package com.ameeradhwa92.myrecipeapp

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ListView
import android.widget.SimpleAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.ameeradhwa92.myrecipeapp.helper.CustomDialog
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.xml.sax.SAXException
import java.io.IOException
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.parsers.ParserConfigurationException

open class RecipeListActivity : AppCompatActivity() {
    private lateinit var sharedPreference: SharedPreferences

    private var lblHeader: TextView? = null
    private var lblHeaderProfile: TextView? = null

    private var btnLogout: Button? = null

    var recDataHashMap = HashMap<String, String>()
    var recList: ArrayList<HashMap<String, String>> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_list)

        sharedPreference = getSharedPreferences("USER_ACCOUNT", Context.MODE_PRIVATE)

        lblHeader = findViewById<View>(R.id.lblHeader) as TextView
        lblHeaderProfile = findViewById<View>(R.id.lblHeaderProfile) as TextView
        btnLogout = findViewById<View>(R.id.btnLogout) as Button

        lblHeader!!.text = "Welcome, ${sharedPreference.getString("FullName", "No Name")}"
        lblHeaderProfile!!.text = sharedPreference.getString("Email", "")

        try {
            val lv = findViewById<ListView>(R.id.listView)
            val istream = assets.open("recipetypes.xml")
            val builderFactory = DocumentBuilderFactory.newInstance()
            val docBuilder = builderFactory.newDocumentBuilder()
            val doc = docBuilder.parse(istream)
            val nList = doc.getElementsByTagName("recipetype")
            for (i in 0 until nList.length) {
                if (nList.item(0).nodeType == Node.ELEMENT_NODE) {
                    //creating instance of HashMap to put the data of node value
                    recDataHashMap = HashMap()
                    val element = nList.item(i) as Element
                    recDataHashMap["type"] = getNodeValue("type", element)
                    recDataHashMap["name"] = getNodeValue("name", element)
                    recDataHashMap["desc"] = getNodeValue("desc", element)
                    //adding the HashMap data to ArrayList
                    recList.add(recDataHashMap)
                }
            }
            val adapter = SimpleAdapter(
                this@RecipeListActivity,
                recList,
                R.layout.recipe_list,
                arrayOf("type", "name", "desc"),
                intArrayOf(R.id.type, R.id.name, R.id.desc)
            )
            lv.adapter = adapter
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: ParserConfigurationException) {
            e.printStackTrace()
        } catch (e: SAXException) {
            e.printStackTrace()
        }

        btnLogout?.setOnClickListener {
            CustomDialog().show(supportFragmentManager, "CustomDialog")
        }
    }

    private fun getNodeValue(tag: String, element: Element): String {
        val nodeList = element.getElementsByTagName(tag)
        val node = nodeList.item(0)
        if (node != null) {
            if (node.hasChildNodes()) {
                val child = node.firstChild
                while (child != null) {
                    if (child.nodeType == Node.TEXT_NODE) {
                        return child.nodeValue
                    }
                }
            }
        }
        return ""
    }
}
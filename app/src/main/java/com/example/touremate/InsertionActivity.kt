package com.example.touremate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.touremate.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class InsertionActivity : AppCompatActivity() {

    private lateinit var etVstName: EditText
    private lateinit var etDate : EditText
    private lateinit var etReview : EditText
    private lateinit var etRate : EditText
    private lateinit var btnSave : Button

    private lateinit var dbRef: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insertion)

        etVstName = findViewById(R.id.etVstName)
        etDate = findViewById(R.id.etDate)
        etReview = findViewById(R.id.etReview)
        etRate = findViewById(R.id.etRate)
        btnSave = findViewById(R.id.btnSave)


        dbRef = FirebaseDatabase.getInstance().getReference("Visitors")

        btnSave.setOnClickListener {
            saveVisitorData()
        }
    }

    private fun saveVisitorData(){

        //getting values

        val vstName = etVstName.text.toString()
        val vstDate = etDate.text.toString()
        val vstReview = etReview.text.toString()
        val vstRate = etRate.text.toString()

        if(vstName.isEmpty()){
            etVstName.error="Please enter name"
        }

        if(vstDate.isEmpty()){
            etDate.error="Please enter date"
        }

        if(vstReview.isEmpty()){
            etReview.error="Please enter your review"
        }

        if(vstRate.isEmpty()){
            etRate.error="Please enter rate"
        }

        val vstId = dbRef.push().key!!

        val visitor = VisitorModel(vstId, vstName, vstDate, vstReview, vstRate)

        if(vstName.isNotEmpty() && vstDate.isNotEmpty() && vstReview.isNotEmpty() && vstRate.isNotEmpty()) {

            dbRef.child(vstId).setValue(visitor).addOnCompleteListener {
                Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()

                etVstName.text.clear()
                etDate.text.clear()
                etReview.text.clear()
                etRate.text.clear()
            }.addOnFailureListener { err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }
        }
        else{
            Toast.makeText(this, "Data not inserted.", Toast.LENGTH_LONG).show()
        }

    }


}
package com.example.touremate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import com.example.touremate.R
import com.google.firebase.database.FirebaseDatabase
import java.util.Date

class ReviewDetails : AppCompatActivity() {

    private lateinit var tvVstId : TextView
    private lateinit var tvVstName : TextView
    private lateinit var tvVstDate : TextView
    private lateinit var tvVstReview : TextView
    private lateinit var tvVstRatings : TextView
    private lateinit var btnUpdate : Button
    private lateinit var btnDelete : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review_details)




        initView()
        setValuesToViews()

        btnUpdate.setOnClickListener{
            openUpdateDialog(
                intent.getStringExtra("vstId"). toString() ,
                intent.getStringExtra("vstName"). toString()

            )
        }

        btnDelete.setOnClickListener {
            deleteRecord(
                intent.getStringExtra("vstId").toString()
            )
        }

    }

    private fun deleteRecord(
        id: String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Visitors").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this, "Review data deleted", Toast.LENGTH_LONG).show()

            val intent = Intent(this, FetchingActivity::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{ error ->
            Toast.makeText(this, "Deleting Error ${error.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun initView(){
        tvVstId = findViewById(R.id.tvVstId)
        tvVstName = findViewById(R.id.tvVstName)
        tvVstDate = findViewById(R.id.tvVstDate)
        tvVstReview = findViewById(R.id.tvVstReview)
        tvVstRatings = findViewById(R.id.tvVstRatings)

        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)
    }

    private fun setValuesToViews(){
        tvVstId.text = intent.getStringExtra("vstId")
        tvVstName .text = intent.getStringExtra("vstName")
        tvVstDate .text = intent.getStringExtra("vstDate")
        tvVstReview .text = intent.getStringExtra("vstReview")
        tvVstRatings .text = intent.getStringExtra("vstRate")
    }

    private fun openUpdateDialog(
        vstId:String,
        vstName: String
    ){

        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.update_dialog,null)

        mDialog.setView(mDialogView)

        val etVstName = mDialogView.findViewById<EditText>(R.id.etVstName)
        val etDate = mDialogView.findViewById<EditText>(R.id.etDate)
        val etReview = mDialogView.findViewById<EditText>(R.id.etReview)
        val etRate = mDialogView.findViewById<EditText>(R.id.etRate)
        val btnUpdateData = mDialogView.findViewById<AppCompatButton>(R.id.btnUpdateData)


        etVstName.setText(intent.getStringExtra("vstName").toString())
        etDate.setText(intent.getStringExtra("vstDate").toString())
        etReview.setText(intent.getStringExtra("vstReview").toString())
        etRate.setText(intent.getStringExtra("vstRate").toString())


        mDialog.setTitle("Updating $vstName Record")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener{
            updateVstData(
                vstId,
                etVstName.text.toString(),
                etDate.text.toString(),
                etReview.text.toString(),
                etRate.text.toString()
            )

            Toast.makeText(applicationContext, "Review Data Updated", Toast.LENGTH_LONG).show()

            tvVstName.text = etVstName.text.toString()
            tvVstDate.text = etDate.text.toString()
            tvVstReview.text = etReview.text.toString()
            tvVstRatings.text = etRate.text.toString()

            alertDialog.dismiss()

        }
    }

    private fun updateVstData(
        id:String,
        name:String,
        date: String ,
        review:String,
        rate : String


    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Visitors").child(id)
        val vstInfo = VisitorModel(id,name,date,review,rate)
        dbRef.setValue(vstInfo)
    }


}
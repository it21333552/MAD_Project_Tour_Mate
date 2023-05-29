package com.example.touremate

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase


class FetchingActivity : AppCompatActivity() {

    private lateinit var vstRecyclerView: RecyclerView
    private lateinit var tvLoadingData : TextView
    private lateinit var vstList: ArrayList<VisitorModel>
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fetching)

        vstRecyclerView = findViewById(R.id.rvVst)
        vstRecyclerView.layoutManager = LinearLayoutManager(this)
        vstRecyclerView.setHasFixedSize(true)
        tvLoadingData = findViewById(R.id.tvLoadingData)

        vstList = arrayListOf<VisitorModel>()

        getVisitorsData()
    }

    private fun getVisitorsData(){
        vstRecyclerView.visibility = View.GONE
        tvLoadingData.visibility = View.VISIBLE

        dbRef= FirebaseDatabase.getInstance().getReference("Visitors")

        dbRef.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                vstList.clear()
                if(snapshot.exists()){
                    for(vstSnap in snapshot.children){
                        val vstData = vstSnap.getValue(VisitorModel::class.java)
                        vstList.add(vstData!!)
                    }
                    val mAdapter = VstAdapter(vstList)
                    vstRecyclerView.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object : VstAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
                            val intent = Intent(this@FetchingActivity, ReviewDetails::class.java)

                            //put extras
                            intent.putExtra("vstId", vstList[position].vstId)
                            intent.putExtra("vstName", vstList[position].vstName)
                            intent.putExtra("vstDate", vstList[position].vstDate)
                            intent.putExtra("vstReview", vstList[position].vstReview)
                            intent.putExtra("vstRate", vstList[position].vstRate)
                            startActivity(intent)
                        }

                    })

                    vstRecyclerView.visibility = View.VISIBLE
                    tvLoadingData.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}
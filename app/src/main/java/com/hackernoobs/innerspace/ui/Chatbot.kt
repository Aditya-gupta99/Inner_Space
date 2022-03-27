package com.hackernoobs.innerspace.ui

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.hackernoobs.innerspace.*
import com.hackernoobs.innerspace.data.Message
import com.hackernoobs.innerspace.utils.BotResponse
import com.hackernoobs.innerspace.utils.Constants.OPEN_GOOGLE
import com.hackernoobs.innerspace.utils.Constants.OPEN_SEARCH
import com.hackernoobs.innerspace.utils.Constants.RECEIVE_ID
import com.hackernoobs.innerspace.utils.Constants.SEND_ID
import com.hackernoobs.innerspace.utils.Time
import kotlinx.android.synthetic.main.activity_chatbot.*
import kotlinx.coroutines.*

class chatbot : AppCompatActivity() {
    private  lateinit var adapter: MessagingAdapter
    private val botList= listOf("Maaz","Aditya","Yasir")
    private var set : HashSet<Int> = HashSet()
    private var index : Int =(0..10).random();
    private var previndex : Int = -1;
    private var firstquestion = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatbot)
        recyclerView()
        clickEvents()
        val random = (0..2).random()
        customMessage("Hello ! Today you're speaking with ${botList[random]}, Are you ready to begin?")
    }
   private fun clickEvents(){
      btn_send.setOnClickListener {
          sendMessage()
      }
       et_message.setOnClickListener {
           GlobalScope.launch {
               delay(100)
               withContext(Dispatchers.Main){
                   rv_messages.scrollToPosition(adapter.itemCount-1)
               }
           }
       }
   }

    private fun recyclerView(){
        adapter = MessagingAdapter()
        rv_messages.adapter =adapter
        rv_messages.layoutManager =LinearLayoutManager(applicationContext)
    }
    private fun sendMessage(){
        val message =et_message.text.toString()
        val timestamp= Time.timeStamp()
        var cont : Boolean = true
        if(message.isNotEmpty())
        {
            et_message.setText("")

            adapter.insertMessage(Message(message, SEND_ID,timestamp))

            rv_messages.scrollToPosition(adapter.itemCount-1)
            if(firstquestion)
            {
                botResponse(message,-2)
                if(message.toLowerCase().compareTo("yes")!=0 ) cont=false
                if(cont) {
                    botResponse("sometime", -4)
                    set.add(0)
                    firstquestion = false;
                }
                else
                {
                    startActivity(Intent(this,MainActivity::class.java))
                }
            }
            else if(set.contains(index) && set.size!=8){
                while (set.contains(index))
                    index=(0..10).random()

                set.add(index)
                botResponse(message,index)
            }
            else if(set.size==8)
            {
                var score = BotResponse.Score()
                botResponse(message,-1)
                if(score>=8)
                {
                    startActivity(Intent(this,SucideActivity::class.java))
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    score=0
                    finish()
                }
                else if(score<8 && score >0)
                {
                    startActivity(Intent(this,NormalActivity::class.java))
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    score=0
                    finish()
                }
                else if(score <0)
                {
                    startActivity(Intent(this,GoodActivity::class.java))
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    score=0
                    
                    finish()
                }
            }
            else
            {
                botResponse(message,index)
                set.add(index)
            }


        }
    }

    private fun botResponse(message: String,index :Int){
        val timestamp= Time.timeStamp()
        GlobalScope.launch {
            delay(1000)
            withContext(Dispatchers.Main){
                val response = BotResponse.basicResponses(message,index)

                adapter.insertMessage(Message(response, RECEIVE_ID,timestamp))
                rv_messages.scrollToPosition(adapter.itemCount-1)
                when(response){
                    OPEN_GOOGLE->{
                       val site =Intent(Intent.ACTION_VIEW)
                        site.data= Uri.parse("https://www.google.com/")
                        startActivity(site)
                    }
                    OPEN_SEARCH ->{
                        val site =Intent(Intent.ACTION_VIEW)
                        val searchTerm:String?= message.substringAfter("search")
                        site.data=Uri.parse("https://www.google.com/search?&q=$searchTerm")
                        startActivity(site)
                    }
                }
            }
        }

    }

    override fun onStart() {
        super.onStart()

        GlobalScope.launch {
            delay(1000)
            withContext(Dispatchers.Main){
                rv_messages.scrollToPosition(adapter.itemCount-1)
            }
        }
    }

    private fun customMessage(message :String){
        GlobalScope.launch {
            delay(1000)
            withContext(Dispatchers.Main){
val timestamp = Time.timeStamp()
                adapter.insertMessage(Message(message,RECEIVE_ID,timestamp))


                rv_messages.scrollToPosition(adapter.itemCount-1)
            }
        }
    }
}
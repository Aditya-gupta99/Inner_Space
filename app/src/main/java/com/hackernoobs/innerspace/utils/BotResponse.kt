package com.hackernoobs.innerspace.utils

import com.hackernoobs.innerspace.utils.Constants.OPEN_GOOGLE
import com.hackernoobs.innerspace.utils.Constants.OPEN_SEARCH
import java.lang.Exception

object BotResponse {

    private var score : Int =0;
    fun basicResponses(_message:String):String{

        val message= _message.toLowerCase()
        val index = (0..1).random()
        return when{

            message.contains("yes")  ->{
                andaboutyou.get_questions(index)
            }

            else -> {
                when(index%2)
                {
                    0 -> "Explain clearly"
                    1-> "Try asking me something different!"
                    2->"Pardon me"
                    else->"error"
                }
            }
        }
    }

    fun basicResponses (_message:String,_index : Int) : String{
        val message=_message.toString().toLowerCase()
        val index = _index

        return when{

            index ==-2 ->{
                when {
                    message.contains("yes") ->{
                    "Let\'s Begin.\nAnswer from the following-\n\n Everytime\n Often \n Sometime \n Rarely \n Seldom\n"
                     }
                    else ->{
                        "We will meet again"

                    }
                }

            }
            index ==-4 ->{
                andaboutyou.get_questions(0)
            }
            message.contains("everytime") ->{
                score+=2
                andaboutyou.get_questions(index)
        }
            message.contains("often")->{
                score+=1
                andaboutyou.get_questions(index)
            }
            message.contains("sometime") ->{
                score+=0
                andaboutyou.get_questions(index)
            }
            message.contains("rarely") ->
            {
                score+= -1
                andaboutyou.get_questions(index);
            }
            message.contains("seldom") ->{
                score+= -2
                andaboutyou.get_questions(index)
            }
        else ->{
            when(index%2)
            {
                0 ->"Could'nt get you ans"
                else-> "Pardon"
            }
        }
        }
    }

    fun Score() : Int{
        return score
    }
}
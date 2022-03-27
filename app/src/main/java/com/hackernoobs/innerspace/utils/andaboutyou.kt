package com.hackernoobs.innerspace.utils

object andaboutyou {
    val questions : Array<String> = arrayOf("Are you having thoughts of suicide?", "How much time you utilize to talk with people?","How much time you utilize to do physical activity?","Do you get panic attacks?","How much do you think negative and worry about future?","Do you have any type of anxiety after met with any accident?","Is there any thoughts playing like tape recording?","Did you stop interacting with people?","Do you have any problem with murmuring?","Do you find hard to accept reality?","Do you consume alcohol in large amounts?","Do you prefer to stay at home rather than going out and doing new things?")

    fun get_questions(index: Int) :String{
        if(index == -1){
            var score= BotResponse.Score()
            return "Wait a moment $score"

        }
        return questions.get(index)
    }

}
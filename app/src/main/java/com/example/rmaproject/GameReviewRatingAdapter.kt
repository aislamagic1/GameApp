package com.example.rmaproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GameReviewRatingAdapter(
    private var users: List<UserImpression>
    ): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.getContext())
        val view : View
        if(viewType == 0){
            view = layoutInflater.inflate(R.layout.item_review, parent, false)
            return GameReviewHolder(view)
        }else{
            view = layoutInflater.inflate(R.layout.item_rating, parent, false)
            return GameRatingHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(getItemViewType(position) == 0){
            val user : UserReview = users[position] as UserReview
            (holder as GameReviewHolder).userName.text = user.userName
            holder.userReview.text = user.review
        }else{
            val user : UserRating = users[position] as UserRating
            (holder as GameRatingHolder).userName.text = user.userName
            holder.userRating.rating = user.rating.toFloat()
        }
    }

    override fun getItemCount(): Int = users.size

    override fun getItemViewType(position: Int): Int{
        val typeOfuser = users.get(position)
        if(typeOfuser is UserRating) return 1
        else return 0
    }

    inner class GameReviewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val userName: TextView = itemView.findViewById(R.id.username_textview)
        val userReview: TextView = itemView.findViewById(R.id.review_textview)
    }

    inner class GameRatingHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val userName: TextView = itemView.findViewById(R.id.username_textview)
        val userRating: RatingBar = itemView.findViewById(R.id.rating_bar)
    }

    fun updateUsers(users: List<UserImpression>){
        this.users = users
        notifyDataSetChanged()
    }
}